package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.DateUtil;
import com.example.common.util.KdniaoTrackQueryAPI;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.AddApplicationCrowdSourcingDto;
import com.example.merchant.dto.platform.AddCrowdSourcingInvoiceDto;
import com.example.merchant.service.CrowdSourcingInvoiceService;
import com.example.merchant.vo.merchant.*;
import com.example.merchant.vo.merchant.InvoiceVo;
import com.example.mybatis.dto.QueryCrowdSourcingDto;
import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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


    /**
     * 众包开票申请
     *
     * @param addApplicationCrowdSourcingDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson addCrowdSourcingInvoice(AddApplicationCrowdSourcingDto addApplicationCrowdSourcingDto) {
        CrowdSourcingApplication applicationCrowdSourcing = new CrowdSourcingApplication();
        BeanUtils.copyProperties(addApplicationCrowdSourcingDto, applicationCrowdSourcing);
        if (applicationCrowdSourcing.getId() == null) {
            PaymentOrderMany paymentOrderMany = paymentOrderManyDao.selectById(addApplicationCrowdSourcingDto.getPaymentOrderManyId());
            if (paymentOrderMany == null) {
                return ReturnJson.error("不存在此众包订单");
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

    /**
     * 众包发票详情信息
     *
     * @param queryCrowdSourcingDto
     * @param userId
     * @return
     */
    @Override
    public ReturnJson getCrowdSourcingInfo(QueryCrowdSourcingDto queryCrowdSourcingDto, String userId) {
        Page page = new Page(queryCrowdSourcingDto.getPageNo(), queryCrowdSourcingDto.getPageSize());
        Merchant merchant = merchantDao.selectById(userId);
        IPage<CrowdSourcingInfoVo> vos = crowdSourcingInvoiceDao.getCrowdSourcingInfo(page, queryCrowdSourcingDto, merchant.getCompanyId());
        return ReturnJson.success(vos);
    }

    /**
     * 查询单条众包信息
     *
     * @param csiId
     * @return
     */
    @Override
    public ReturnJson getInvoiceById(String csiId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        InvoiceInformationVo vo = crowdSourcingInvoiceDao.getInvoiceById(csiId);
        if (vo != null) {
            returnJson = new ReturnJson("操作成功", vo, 200);
        }
        return returnJson;
    }

    /**
     * 平台端查询众包待开票
     *
     * @param tobeinvoicedDto
     * @return
     */
    @Override
    public ReturnJson getTobeCrowdSourcingInvoice(TobeinvoicedDto tobeinvoicedDto) {
        Page page = new Page(tobeinvoicedDto.getPageNo(), tobeinvoicedDto.getPageSize());
        IPage<CrowdSourcingInvoiceVo> list = crowdSourcingInvoiceDao.getCrowdSourcingInvoicePass(page, tobeinvoicedDto);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getPaymentOrderMany(String payId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        PaymentOrderManyVo vo = crowdSourcingInvoiceDao.getPaymentOrderManyPass(payId);
        if (vo != null) {
            returnJson = new ReturnJson("操作成功", vo, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getPaymentInventoryPass(String payId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        BigDecimal totalTaxPrice = new BigDecimal("0.00");
        DecimalFormat df = new DecimalFormat("0.00");
        Map<String, Object> map = new HashMap();
        List<InvoiceDetailsVo> list = crowdSourcingInvoiceDao.getPaymentInventoryPass(payId);
        for (InvoiceDetailsVo vo : list) {
            totalTaxPrice = totalTaxPrice.add(vo.getTaskMoney());
            PaymentInventory paymentInventory = new PaymentInventory();
            paymentInventory.setId(vo.getId());
            List<InvoiceLadderPrice> invoiceLadderPrice = invoiceLadderPriceDao.selectList(new QueryWrapper<InvoiceLadderPrice>().eq("tax_id", vo.getTaxId()));
            if (invoiceLadderPrice != null) {
                for (InvoiceLadderPrice price : invoiceLadderPrice) {
                    if ((vo.getTaskMoney().compareTo(price.getStartMoney()) > -1) && (vo.getTaskMoney().compareTo(price.getEndMoney()) == -1)) {
                        //纳税率
                        vo.setTaxRate(price.getRate());
                        BigDecimal bigDecimal = vo.getTaxRate().multiply(vo.getTaskMoney());
                        //个人服务费
                        vo.setPersonalServiceFee(bigDecimal.setScale(2, RoundingMode.HALF_UP));
                        //纳税金额
                        vo.setTaxAmount((vo.getTaskMoney().subtract(vo.getPersonalServiceFee())).divide((vo.getTaxRate().add(new BigDecimal("1.00"))).multiply(vo.getTaxRate()), 2, BigDecimal.ROUND_HALF_UP));
                    }
                }
            }
            map.put("list", list);
            map.put("税价合计", totalTaxPrice);
            returnJson = new ReturnJson("操作成功", map, 200);
        }
        return returnJson;
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
        BuyerVo buyerVo = crowdSourcingInvoiceDao.getBuyer(id);
        if (buyerVo != null) {
            returnJson = new ReturnJson("操作成功", buyerVo, 200);
        }
        return returnJson;
    }

    /**
     * 众包申请开票信息查询
     *
     * @param applicationId
     * @return
     */
    @Override
    public ReturnJson getApplicationInfo(String applicationId) {
        CrowdSourcingApplication crowdSourcingApplication = crowdSourcingApplicationDao.selectById(applicationId);
        return ReturnJson.success("操作成功", crowdSourcingApplication);
    }

    @Override
    public ReturnJson saveCrowdSourcingInvoice(AddCrowdSourcingInvoiceDto addCrowdSourcingInvoiceDto) {
        //时间转换
        DateTimeFormatter dfd = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        CrowdSourcingInvoice crowdSourcingInvoice = new CrowdSourcingInvoice();
        BeanUtils.copyProperties(addCrowdSourcingInvoiceDto, crowdSourcingInvoice);
        if (addCrowdSourcingInvoiceDto.getId() == null) {
            if (addCrowdSourcingInvoiceDto.getApplicationId() == null) {
                return ReturnJson.error("商户未申请，支付id不能为空", 300);
            }
            if (crowdSourcingInvoice.getInvoicePrintDate() == null) {
                //开票时间给系统默认时间
                crowdSourcingInvoice.setInvoicePrintDate(LocalDateTime.parse(DateUtil.getTime(), dfd));
            }
            String invoiceCode = crowdSourcingInvoiceDao.getCrowdInvoiceCode();
            int code = Integer.valueOf(invoiceCode.substring(2)) + 1;
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
            int num = crowdSourcingInvoiceDao.insert(crowdSourcingInvoice);
            if (crowdSourcingInvoice.getApplicationId() != null) {
                CrowdSourcingApplication crowdSourcingApplication = new CrowdSourcingApplication();
                crowdSourcingApplication.setId(crowdSourcingInvoice.getApplicationId());
                crowdSourcingApplication.setApplicationState(3);
                crowdSourcingApplicationDao.updateById(crowdSourcingApplication);
            }
            return ReturnJson.success("添加成功");
        } else {
            crowdSourcingInvoiceDao.updateById(crowdSourcingInvoice);
            return ReturnJson.success("修改成功");
        }
    }

    /**
     * 平台端查询众包已开票
     *
     * @param tobeinvoicedDto
     * @return
     */
    @Override
    public ReturnJson getCrowdSourcingInfoPass(TobeinvoicedDto tobeinvoicedDto) {
        Page page = new Page(tobeinvoicedDto.getPageNo(), tobeinvoicedDto.getPageSize());
        IPage<CrowdSourcingInfoVo> vos = crowdSourcingInvoiceDao.getCrowdSourcingInfoPass(page, tobeinvoicedDto);
        return ReturnJson.success(vos);
    }

    @Override
    public ReturnJson getPaymentInventoryInfoPass(String invoiceId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        List<InvoiceDetailsVo> list = crowdSourcingInvoiceDao.getPaymentInventoryInfoPass(invoiceId);
        if (list != null) {
            returnJson = new ReturnJson("操作成功", list, 300);
            returnJson.setPageSize(9);
        }
        return returnJson;
    }

    @Override
    public ReturnJson queryApplicationInfo(String applicationId, String merchantId) {
        QueryApplicationInfoVo queryApplicationInfo = new QueryApplicationInfoVo();
        CrowdSourcingApplication crowdSourcingApplication = crowdSourcingApplicationDao.selectById(applicationId);
        if (crowdSourcingApplication == null) {
            return ReturnJson.error("此申请不存在！");
        }
        PaymentOrderManyVo paymentOrderManyVo = paymentOrderManyDao.getPayOrderManyById(crowdSourcingApplication.getPaymentOrderManyId());
        queryApplicationInfo.setPaymentOrderManyVo(paymentOrderManyVo);
        BuyerVo buyerVo = merchantDao.getBuyerById(merchantId);
        queryApplicationInfo.setBuyerVo(buyerVo);
        InvoiceApplicationVo invoiceApplicationVo = new InvoiceApplicationVo();
        BeanUtils.copyProperties(crowdSourcingApplication, invoiceApplicationVo);
        invoiceApplicationVo.setApplicationAddress(crowdSourcingApplication.getApplicationAddressId());
        queryApplicationInfo.setInvoiceApplicationVo(invoiceApplicationVo);
        return ReturnJson.success(queryApplicationInfo);
    }

    @Override
    public ReturnJson queryInvoiceInfo(String invoiceId, String merchantId) {
        QueryInvoiceInfoVo queryInvoiceInfoVo = new QueryInvoiceInfoVo();
        CrowdSourcingInvoice crowdSourcingInvoice = crowdSourcingInvoiceDao.selectById(invoiceId);
        if (crowdSourcingInvoice == null) {
            return ReturnJson.error("不存在此发票！");
        }

        CrowdSourcingApplication crowdSourcingApplication = crowdSourcingApplicationDao.selectById(crowdSourcingInvoice.getApplicationId());
        PaymentOrderManyVo paymentOrderManyVo = paymentOrderManyDao.getPayOrderManyById(crowdSourcingApplication.getPaymentOrderManyId());
        queryInvoiceInfoVo.setPaymentOrderManyVo(paymentOrderManyVo);
        PaymentOrderMany paymentOrderMany = paymentOrderManyDao.selectById(crowdSourcingApplication.getPaymentOrderManyId());
        BuyerVo buyerVo = merchantDao.getBuyerById(merchantId);
        queryInvoiceInfoVo.setBuyerVo(buyerVo);
        InvoiceApplicationVo invoiceApplicationVo = new InvoiceApplicationVo();
        BeanUtils.copyProperties(crowdSourcingApplication, invoiceApplicationVo);
        invoiceApplicationVo.setApplicationAddress(crowdSourcingApplication.getApplicationAddressId());
        queryInvoiceInfoVo.setInvoiceApplicationVo(invoiceApplicationVo);
        InvoiceVo invoiceVo = new InvoiceVo();
        BeanUtils.copyProperties(crowdSourcingInvoice, invoiceVo);
        queryInvoiceInfoVo.setInvoiceVo(invoiceVo);
        Tax tax = taxDao.selectById(paymentOrderMany.getTaxId());
        SendAndReceiveVo sendAndReceiveVo = new SendAndReceiveVo();
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
        InvoiceCatalogVo invoiceCatalogVo = new InvoiceCatalogVo();
        BeanUtils.copyProperties(invoiceCatalog, invoiceCatalogVo);
        queryInvoiceInfoVo.setInvoiceCatalogVo(invoiceCatalogVo);
        queryInvoiceInfoVo.setExpressLogisticsInfoList(KdniaoTrackQueryAPI.getExpressInfo(crowdSourcingInvoice.getExpressCompanyName(), crowdSourcingInvoice.getExpressSheetNo()));
        return ReturnJson.success(queryInvoiceInfoVo);
    }
}
