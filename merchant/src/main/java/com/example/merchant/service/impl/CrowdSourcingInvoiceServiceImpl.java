package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.DateUtil;
import com.example.common.util.KdniaoTrackQueryAPI;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.AddApplicationCrowdSourcingDTO;
import com.example.merchant.dto.platform.AddCrowdSourcingInvoiceDTO;
import com.example.merchant.exception.CommonException;
import com.example.merchant.vo.platform.CrowdSourcingInvoiceVO;
import com.example.merchant.vo.platform.QueryInvoicedVO;
import com.example.merchant.service.CrowdSourcingInvoiceService;
import com.example.merchant.vo.merchant.*;
import com.example.merchant.vo.merchant.InvoiceVO;
import com.example.merchant.vo.platform.AddressVO;
import com.example.merchant.vo.platform.QueryNotInvoicedVO;
import com.example.mybatis.dto.QueryCrowdSourcingDTO;
import com.example.mybatis.dto.TobeInvoicedDTO;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jwei
 */
@Service
public class CrowdSourcingInvoiceServiceImpl extends ServiceImpl<CrowdSourcingInvoiceDao, CrowdSourcingInvoice> implements CrowdSourcingInvoiceService {

    @Resource
    private CrowdSourcingInvoiceDao crowdSourcingInvoiceDao;
    @Resource
    private InvoiceLadderPriceDao invoiceLadderPriceDao;
    @Resource
    private CrowdSourcingApplicationDao crowdSourcingApplicationDao;
    @Resource
    private MerchantDao merchantDao;
    @Resource
    private PaymentOrderManyDao paymentOrderManyDao;
    @Resource
    private AddressDao addressDao;
    @Resource
    private TaxDao taxDao;
    @Resource
    private InvoiceCatalogDao invoiceCatalogDao;
    @Resource
    private PaymentInventoryDao paymentInventoryDao;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson addCrowdSourcingInvoice(AddApplicationCrowdSourcingDTO addApplicationCrowdSourcingDto) throws CommonException {
        CrowdSourcingApplication applicationCrowdSourcing = new CrowdSourcingApplication();
        BeanUtils.copyProperties(addApplicationCrowdSourcingDto, applicationCrowdSourcing);
        if (applicationCrowdSourcing.getId() == null) {
            PaymentOrderMany paymentOrderMany = paymentOrderManyDao.selectById(addApplicationCrowdSourcingDto.
                    getPaymentOrderManyId());
            if (paymentOrderMany == null) {
                throw new CommonException(300,"不存在此众包订单");
            }
            applicationCrowdSourcing.setApplicationState(1);
            applicationCrowdSourcing.setApplicationDate(new Date());
            crowdSourcingApplicationDao.insert(applicationCrowdSourcing);
            paymentOrderMany.setIsApplication(1);
            paymentOrderManyDao.updateById(paymentOrderMany);
            return ReturnJson.success("操作成功");
        } else {
            applicationCrowdSourcing = crowdSourcingApplicationDao.selectById(applicationCrowdSourcing.getId());
            BeanUtils.copyProperties(addApplicationCrowdSourcingDto, applicationCrowdSourcing);
            crowdSourcingApplicationDao.updateById(applicationCrowdSourcing);
            return ReturnJson.success("操作成功");
        }

    }

    @Override
    public ReturnJson getCrowdSourcingInfo(QueryCrowdSourcingDTO queryCrowdSourcingDto, String userId) {
        Page page = new Page(queryCrowdSourcingDto.getPageNo(), queryCrowdSourcingDto.getPageSize());
        Merchant merchant = merchantDao.selectById(userId);
        IPage<CrowdSourcingInfoVO> vos = crowdSourcingInvoiceDao.getCrowdSourcingInfo(page,
                queryCrowdSourcingDto, merchant.getCompanyId());
        return ReturnJson.success(vos);
    }

    @Override
    public ReturnJson getInvoiceById(String csiId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        InvoiceInformationVO vo = crowdSourcingInvoiceDao.getInvoiceById(csiId);
        if (vo != null) {
            returnJson = new ReturnJson("操作成功", vo, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getTobeCrowdSourcingInvoice(TobeInvoicedDTO tobeinvoicedDto) {
        Page page = new Page(tobeinvoicedDto.getPageNo(), tobeinvoicedDto.getPageSize());
        IPage<CrowdSourcingInvoiceInfoVO> list = crowdSourcingInvoiceDao.
                getCrowdSourcingInvoicePass(page, tobeinvoicedDto);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getPaymentOrderMany(String payId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        PaymentOrderManyVO vo = crowdSourcingInvoiceDao.getPaymentOrderManyPass(payId);
        if (vo != null) {
            returnJson = new ReturnJson("操作成功", vo, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getPaymentInventoryPass(String invoiceId, Integer pageNo, Integer pageSize) {
        BigDecimal totalTaxPrice = new BigDecimal("0.00");
        Map<String, Object> map = new HashMap(0);
        List<InvoiceDetailsVO> list = crowdSourcingInvoiceDao.getPaymentInventoryPass(invoiceId);
        for (InvoiceDetailsVO vo : list) {
            totalTaxPrice = totalTaxPrice.add(vo.getRealMoney()).add(vo.getServiceMoney());
            PaymentInventory paymentInventory = new PaymentInventory();
            paymentInventory.setId(vo.getId());
            List<InvoiceLadderPrice> invoiceLadderPrice = invoiceLadderPriceDao.
                    selectList(new QueryWrapper<InvoiceLadderPrice>().
                            eq("tax_id", vo.getTaxId()));
            if (invoiceLadderPrice != null) {
                for (InvoiceLadderPrice price : invoiceLadderPrice) {
                    if ((vo.getTaskMoney().compareTo(price.getStartMoney()) > -1) && (vo.getTaskMoney().
                            compareTo(price.getEndMoney()) == -1)) {
                        //纳税率
                        vo.setTaxRate(price.getRate());
                        BigDecimal bigDecimal = vo.getTaxRate().multiply(vo.getTaskMoney());
                        //个人服务费
                        vo.setPersonalServiceFee(bigDecimal.setScale(2, RoundingMode.HALF_UP));
                        //纳税金额
                        vo.setTaxAmount((vo.getTaskMoney().subtract(vo.getPersonalServiceFee())).divide((vo.getTaxRate().
                                add(new BigDecimal("1.00"))).multiply(vo.getTaxRate()), 2, BigDecimal.ROUND_HALF_UP));
                    }
                }
            }
            map.put("list", list);
            map.put("totalTaxPrice", totalTaxPrice);
        }
        return ReturnJson.success(map);
    }

    /**
     * 根据商户id查询购买方信息
     *
     * @param id
     * @return
     */
    @Override
    public ReturnJson getBuyer(String id) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        BuyerVO buyerVo = crowdSourcingInvoiceDao.getBuyer(id);
        if (buyerVo != null) {
            returnJson = new ReturnJson("操作成功", buyerVo, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getApplicationInfo(String applicationId) {
        CrowdSourcingApplication crowdSourcingApplication = crowdSourcingApplicationDao.selectById(applicationId);

        return ReturnJson.success("操作成功", crowdSourcingApplication);
    }

    @Override
    public ReturnJson saveCrowdSourcingInvoice(AddCrowdSourcingInvoiceDTO addCrowdSourcingInvoiceDto) {
        //时间转换
        DateTimeFormatter dfd = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (addCrowdSourcingInvoiceDto.getId() == null) {
            CrowdSourcingInvoice crowdSourcingInvoice = new CrowdSourcingInvoice();
            BeanUtils.copyProperties(addCrowdSourcingInvoiceDto, crowdSourcingInvoice);
            if (addCrowdSourcingInvoiceDto.getApplicationId() == null) {
                return ReturnJson.error("商户未申请，支付id不能为空", 300);
            }
            if (crowdSourcingInvoice.getInvoicePrintDate() == null) {
                //开票时间给系统默认时间
                crowdSourcingInvoice.setInvoicePrintDate(LocalDateTime.parse(DateUtil.getTime(), dfd));
            }
            String invoiceCode = crowdSourcingInvoiceDao.getCrowdInvoiceCode();
            if (invoiceCode == null) {
                invoiceCode = "001";
            }
            Integer code = Integer.valueOf(invoiceCode.substring(2)) + 1;
            String codes = String.valueOf(code);
            if (codes.length() < 4) {
                if (codes.length() == 1) {
                    codes = "000" + code;
                } else if (codes.length() == 2) {
                    codes = "00" + code;
                } else {
                    codes = "0" + code;
                }
            }
            crowdSourcingInvoice.setInvoiceCode("FP" + codes);
            //创建时间
            crowdSourcingInvoice.setCreateDate(LocalDateTime.parse(DateUtil.getTime(), dfd));
            crowdSourcingInvoiceDao.insert(crowdSourcingInvoice);
            if (crowdSourcingInvoice.getApplicationId() != null) {
                CrowdSourcingApplication crowdSourcingApplication = crowdSourcingApplicationDao.
                        selectById(crowdSourcingInvoice.getApplicationId());
                crowdSourcingApplication.setApplicationState(3);
                crowdSourcingApplicationDao.updateById(crowdSourcingApplication);
                PaymentOrderMany paymentOrderMany = paymentOrderManyDao.selectById(crowdSourcingApplication.
                        getPaymentOrderManyId());
                paymentOrderMany.setIsNotInvoice(1);
                paymentOrderManyDao.updateById(paymentOrderMany);
            }
            return ReturnJson.success("添加成功");
        } else {
            CrowdSourcingInvoice crowdSourcingInvoice = crowdSourcingInvoiceDao.selectById(addCrowdSourcingInvoiceDto.getId());
            BeanUtils.copyProperties(addCrowdSourcingInvoiceDto, crowdSourcingInvoice);
            crowdSourcingInvoiceDao.updateById(crowdSourcingInvoice);
            return ReturnJson.success("修改成功");
        }
    }

    @Override
    public ReturnJson getCrowdSourcingInfoPass(TobeInvoicedDTO tobeinvoicedDto) {
        Page page = new Page(tobeinvoicedDto.getPageNo(), tobeinvoicedDto.getPageSize());
        IPage<CrowdSourcingInfoVO> vos = crowdSourcingInvoiceDao.getCrowdSourcingInfoPass(page, tobeinvoicedDto);
        return ReturnJson.success(vos);
    }

    @Override
    public ReturnJson getPaymentInventoryInfoPass(String invoiceId, Integer pageNo, Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        IPage<InvoiceDetailsVO> list = crowdSourcingInvoiceDao.getPaymentInventoryInfoPass(page, invoiceId);

        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson queryApplicationInfo(String applicationId, String merchantId) throws CommonException {
        QueryApplicationInfoVO queryApplicationInfo = new QueryApplicationInfoVO();
        CrowdSourcingApplication crowdSourcingApplication = crowdSourcingApplicationDao.selectById(applicationId);
        if (crowdSourcingApplication == null) {
            throw new CommonException(300,"此申请不存在！");
        }
        PaymentOrderManyVO paymentOrderManyVo = paymentOrderManyDao.getPayOrderManyById(crowdSourcingApplication.
                getPaymentOrderManyId());
        queryApplicationInfo.setPaymentOrderManyVo(paymentOrderManyVo);
        BuyerVO buyerVo = merchantDao.getBuyerById(merchantId);
        queryApplicationInfo.setBuyerVo(buyerVo);
        InvoiceApplicationVO invoiceApplicationVo = new InvoiceApplicationVO();
        BeanUtils.copyProperties(crowdSourcingApplication, invoiceApplicationVo);
        invoiceApplicationVo.setApplicationAddress(crowdSourcingApplication.getApplicationAddressId());
        queryApplicationInfo.setInvoiceApplicationVo(invoiceApplicationVo);
        return ReturnJson.success(queryApplicationInfo);
    }

    @Override
    public ReturnJson queryInvoiceInfo(String invoiceId, String merchantId) {
        QueryInvoiceInfoVO queryInvoiceInfoVo = new QueryInvoiceInfoVO();
        CrowdSourcingInvoice crowdSourcingInvoice = crowdSourcingInvoiceDao.selectById(invoiceId);
        if (crowdSourcingInvoice == null) {
            return ReturnJson.error("不存在此发票！");
        }

        CrowdSourcingApplication crowdSourcingApplication = crowdSourcingApplicationDao.selectById(crowdSourcingInvoice.
                getApplicationId());
        PaymentOrderManyVO paymentOrderManyVo = paymentOrderManyDao.getPayOrderManyById(crowdSourcingApplication.
                getPaymentOrderManyId());
        queryInvoiceInfoVo.setPaymentOrderManyVo(paymentOrderManyVo);
        PaymentOrderMany paymentOrderMany = paymentOrderManyDao.selectById(crowdSourcingApplication.getPaymentOrderManyId());
        BuyerVO buyerVo = merchantDao.getBuyerById(merchantId);
        queryInvoiceInfoVo.setBuyerVo(buyerVo);
        InvoiceApplicationVO invoiceApplicationVo = new InvoiceApplicationVO();
        BeanUtils.copyProperties(crowdSourcingApplication, invoiceApplicationVo);
        invoiceApplicationVo.setApplicationAddress(crowdSourcingApplication.getApplicationAddressId());
        queryInvoiceInfoVo.setInvoiceApplicationVo(invoiceApplicationVo);
        InvoiceVO invoiceVo = new InvoiceVO();
        BeanUtils.copyProperties(crowdSourcingInvoice, invoiceVo);
        queryInvoiceInfoVo.setInvoiceVo(invoiceVo);
        Tax tax = taxDao.selectById(paymentOrderMany.getTaxId());
        SendAndReceiveVO sendAndReceiveVo = new SendAndReceiveVO();
        sendAndReceiveVo.setLogisticsCompany(crowdSourcingInvoice.getExpressCompanyName());
        sendAndReceiveVo.setLogisticsOrderNo(crowdSourcingInvoice.getExpressSheetNo());
        Address address = addressDao.selectById(invoiceApplicationVo.getApplicationAddress());
        sendAndReceiveVo.setFrom(tax.getTaxName());
        sendAndReceiveVo.setFromTelephone(tax.getLinkMobile());
        sendAndReceiveVo.setSendingAddress(tax.getTaxAddress());
        sendAndReceiveVo.setToAddress(address.getAddressName());
        sendAndReceiveVo.setAddressee(address.getLinkName());
        sendAndReceiveVo.setAddresseeTelephone(address.getLinkMobile());
        queryInvoiceInfoVo.setSendAndReceiveVo(sendAndReceiveVo);
        InvoiceCatalog invoiceCatalog = invoiceCatalogDao.selectById(invoiceApplicationVo.getInvoiceCatalogType());
        InvoiceCatalogVO invoiceCatalogVo = new InvoiceCatalogVO();
        BeanUtils.copyProperties(invoiceCatalog, invoiceCatalogVo);
        queryInvoiceInfoVo.setInvoiceCatalogVo(invoiceCatalogVo);
        queryInvoiceInfoVo.setExpressLogisticsInfoList(KdniaoTrackQueryAPI.getExpressInfo(crowdSourcingInvoice.
                getExpressCompanyName(), crowdSourcingInvoice.getExpressSheetNo()));
        return ReturnJson.success(queryInvoiceInfoVo);
    }

    @Override
    public ReturnJson queryNotInvoiced(String applicationId) {
        QueryNotInvoicedVO queryNotInvoicedVo = new QueryNotInvoicedVO();
        PaymentOrderManyVO vo = crowdSourcingInvoiceDao.getPaymentOrderManyPass(applicationId);
        queryNotInvoicedVo.setPaymentOrderManyVo(vo);
        CrowdSourcingApplication crowdSourcingApplication = crowdSourcingApplicationDao.selectById(applicationId);
        BuyerVO buyerVo = crowdSourcingInvoiceDao.getBuyer(applicationId);
        queryNotInvoicedVo.setBuyerVo(buyerVo);
        InvoiceCatalog invoiceCatalog = invoiceCatalogDao.selectById(crowdSourcingApplication.getInvoiceCatalogType());
        InvoiceCatalogVO invoiceCatalogVo = new InvoiceCatalogVO();
        BeanUtils.copyProperties(invoiceCatalog, invoiceCatalogVo);
        queryNotInvoicedVo.setInvoiceCatalogVo(invoiceCatalogVo);
        queryNotInvoicedVo.setRemarks(crowdSourcingApplication.getApplicationDesc());
        Address address = addressDao.selectById(crowdSourcingApplication.getApplicationAddressId());
        AddressVO addressVo = new AddressVO();
        BeanUtils.copyProperties(address, addressVo);
        queryNotInvoicedVo.setAddressVo(addressVo);
        return ReturnJson.success(queryNotInvoicedVo);
    }

    @Override
    public ReturnJson queryInvoiced(String invoiceId) {
        CrowdSourcingInvoice crowdSourcingInvoice = crowdSourcingInvoiceDao.selectById(invoiceId);
        if (crowdSourcingInvoice == null) {
            return ReturnJson.error("不存在此发票信息");
        }
        QueryInvoicedVO queryInvoicedVo = new QueryInvoicedVO();
        PaymentOrderManyVO vo = crowdSourcingInvoiceDao.getPaymentOrderManySPass(invoiceId);
        queryInvoicedVo.setPaymentOrderManyVo(vo);
        CrowdSourcingApplication crowdSourcingApplication = crowdSourcingApplicationDao.selectById(crowdSourcingInvoice.
                getApplicationId());
        BuyerVO buyerVo = crowdSourcingInvoiceDao.getBuyer(crowdSourcingInvoice.getApplicationId());
        queryInvoicedVo.setBuyerVo(buyerVo);
        InvoiceCatalog invoiceCatalog = invoiceCatalogDao.selectById(crowdSourcingApplication.getInvoiceCatalogType());
        InvoiceCatalogVO invoiceCatalogVo = new InvoiceCatalogVO();
        BeanUtils.copyProperties(invoiceCatalog, invoiceCatalogVo);
        queryInvoicedVo.setInvoiceCatalogVo(invoiceCatalogVo);
        queryInvoicedVo.setRemarks(crowdSourcingInvoice.getInvoiceDesc());
        Address address = addressDao.selectById(crowdSourcingApplication.getApplicationAddressId());
        AddressVO addressVo = new AddressVO();
        BeanUtils.copyProperties(address, addressVo);
        queryInvoicedVo.setAddressVo(addressVo);
        SendAndReceiveVO sendAndReceiveVo = crowdSourcingInvoiceDao.querySendAndReceive(invoiceId);
        queryInvoicedVo.setSendAndReceiveVo(sendAndReceiveVo);
        queryInvoicedVo.setExpressLogisticsInfoList(KdniaoTrackQueryAPI.
                getExpressInfo(sendAndReceiveVo.getLogisticsCompany(), sendAndReceiveVo.getLogisticsOrderNo()));
        CrowdSourcingInvoiceVO crowdSourcingInvoiceVo = new CrowdSourcingInvoiceVO();
        BeanUtils.copyProperties(crowdSourcingInvoice, crowdSourcingInvoiceVo);
        queryInvoicedVo.setCrowdSourcingInvoiceVo(crowdSourcingInvoiceVo);
        List<PaymentInventory> paymentInventoryList = paymentInventoryDao.selectList(new QueryWrapper<PaymentInventory>().
                eq("payment_order_id", crowdSourcingApplication.getPaymentOrderManyId()));
        BigDecimal totalTaxPrice = new BigDecimal("0.00");
        for (int i = 0; i < paymentInventoryList.size(); i++) {
            totalTaxPrice = totalTaxPrice.add(paymentInventoryList.get(i).getRealMoney()).add(paymentInventoryList.get(i).getServiceMoney());
        }
        queryInvoicedVo.setTotalTaxPrice(totalTaxPrice);
        return ReturnJson.success(queryInvoicedVo);
    }
}
