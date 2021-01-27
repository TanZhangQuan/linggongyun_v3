package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.*;
import com.example.merchant.vo.merchant.*;
import com.example.merchant.vo.platform.AddressVO;
import com.example.merchant.vo.platform.PlaInvoiceInfoVO;
import com.example.merchant.vo.platform.QueryApplicationVO;
import com.example.merchant.vo.platform.QueryPlaInvoiceVO;
import com.example.mybatis.dto.QueryTobeinvoicedDTO;
import com.example.merchant.service.InvoiceApplicationService;
import com.example.merchant.service.InvoiceService;
import com.example.mybatis.dto.AddInvoiceDTO;
import com.example.mybatis.dto.TobeInvoicedDTO;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.vo.*;
import com.example.mybatis.vo.InvoiceVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 * 发票相关 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class InvoiceServiceImpl extends ServiceImpl<InvoiceDao, Invoice> implements InvoiceService {

    @Resource
    private InvoiceDao invoiceDao;
    @Resource
    private InvoiceApplicationService invoiceApplicationService;
    @Resource
    private InvoiceLadderPriceDao invoiceLadderPriceDao;
    @Resource
    private MerchantDao merchantDao;
    @Resource
    private InvoiceApplicationDao invoiceApplicationDao;
    @Resource
    private PaymentOrderDao paymentOrderDao;
    @Resource
    private TaxDao taxDao;
    @Resource
    private AddressDao addressDao;
    @Resource
    private InvoiceCatalogDao invoiceCatalogDao;


    @Override
    public ReturnJson selectTobeinvoiced(QueryTobeinvoicedDTO queryTobeinvoicedDto, String merchantId) {
        Merchant merchant = merchantDao.selectById(merchantId);
        Page page = new Page(queryTobeinvoicedDto.getPageNo(), queryTobeinvoicedDto.getPageSize());
        IPage<TobeinvoicedVO> list = invoiceDao.selectTobeinvoiced(page, queryTobeinvoicedDto, merchant.getCompanyId());
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getInvoiceList(QueryTobeinvoicedDTO queryTobeinvoicedDto, String merchantId) {
        Merchant merchant = merchantDao.selectById(merchantId);
        Page page = new Page(queryTobeinvoicedDto.getPageNo(), queryTobeinvoicedDto.getPageSize());
        IPage<InvoiceVO> list = invoiceDao.getInvoiceList(page, queryTobeinvoicedDto, merchant.getCompanyId());
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getInvInfoById(String invId, String merchantId) {
        InvoiceInformationVO vo = invoiceDao.getInvInfoById(invId);
        QueryInvoiceVO queryInvoiceVo = new QueryInvoiceVO();
        List<PaymentOrderVO> paymentOrderVOList = paymentOrderDao.queryPaymentOrderInfo(vo.getInvAppId());
        queryInvoiceVo.setPaymentOrderVoList(paymentOrderVOList);
        List<BillingInfoVO> billingInfoVOList = new ArrayList<>();
        for (int i = 0; i < paymentOrderVOList.size(); i++) {
            BillingInfoVO billingInfo = paymentOrderDao.getBillingInfo(paymentOrderVOList.get(i).getId());
            billingInfoVOList.add(billingInfo);
        }
        queryInvoiceVo.setBillingInfoVoList(billingInfoVOList);
        queryInvoiceVo.setBuyerVo(merchantDao.getBuyerById(merchantId));
        PaymentOrder paymentOrderOne = paymentOrderDao.selectById(paymentOrderVOList.get(0).getId());
        queryInvoiceVo.setSellerVo(taxDao.getSellerById(paymentOrderOne.getTaxId()));
        InvoiceApplication invoiceApplication = invoiceApplicationDao.selectById(vo.getInvAppId());
        InvoiceApplicationVO invoiceApplicationVo = new InvoiceApplicationVO();
        BeanUtils.copyProperties(invoiceApplication, invoiceApplicationVo);
        InvoiceCatalog invoiceCatalog = invoiceCatalogDao.selectById(invoiceApplication.getInvoiceCatalogType());
        InvoiceCatalogVO invoiceCatalogVo = new InvoiceCatalogVO();
        BeanUtils.copyProperties(invoiceCatalog, invoiceCatalogVo);
        queryInvoiceVo.setInvoiceCatalogVo(invoiceCatalogVo);
        queryInvoiceVo.setInvoiceApplicationVo(invoiceApplicationVo);
        List<ExpressLogisticsInfo> expressLogisticsInfos = KdniaoTrackQueryAPI.getExpressInfo(vo.getExpressCompanyName(), vo.getExpressSheetNo());
        queryInvoiceVo.setExpressLogisticsInfoList(expressLogisticsInfos);
        SendAndReceiveVO sendAndReceiveVo = new SendAndReceiveVO();
        sendAndReceiveVo.setLogisticsCompany(vo.getExpressCompanyName());
        sendAndReceiveVo.setLogisticsOrderNo(vo.getExpressSheetNo());
        Address address = addressDao.selectById(invoiceApplicationVo.getApplicationAddress());
        sendAndReceiveVo.setFrom(queryInvoiceVo.getSellerVo().getTaxName());
        sendAndReceiveVo.setFromTelephone(queryInvoiceVo.getSellerVo().getPhone());
        sendAndReceiveVo.setSendingAddress(queryInvoiceVo.getSellerVo().getTaxAddress());
        sendAndReceiveVo.setToAddress(address.getAddressName());
        sendAndReceiveVo.setAddressee(address.getLinkName());
        sendAndReceiveVo.setAddresseeTelephone(address.getLinkMobile());
        queryInvoiceVo.setSendAndReceiveVo(sendAndReceiveVo);
        queryInvoiceVo.setInvoiceUrl(vo.getInvoiceUrl());
        queryInvoiceVo.setTaxReceiptUrl(StringUtils.isNotEmpty(vo.getTaxReceiptUrl()) ? vo.getTaxReceiptUrl() : null);
        return ReturnJson.success("查询成功", queryInvoiceVo);
    }

    /**
     * 判断是否为同一服务商
     *
     * @param serviceProviderNames
     * @return
     */
    @Override
    public ReturnJson isServiceProvider(String serviceProviderNames) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        String[] serviceProviderName = serviceProviderNames.split(",");
        for (int i = 0; i < serviceProviderName.length; i++) {
            if (serviceProviderName[0].equals(serviceProviderName[i])) {
                returnJson = new ReturnJson("操作成功", 200);
            }
        }
        return returnJson;
    }


    @Override
    public ReturnJson getPlaInvoiceList(TobeInvoicedDTO tobeinvoicedDto) {
        Page page = new Page(tobeinvoicedDto.getPageNo(), tobeinvoicedDto.getPageSize());
        IPage<PlaInvoiceListVO> list = invoiceDao.getPlaInvoiceList(page, tobeinvoicedDto);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getPlaInvoiceInfo(String applicationId) {
        QueryApplicationVO queryApplicationInvoiceVo = new QueryApplicationVO();
        List<PaymentOrderVO> paymentOrderVOList = paymentOrderDao.queryPaymentOrderInfo(applicationId);
        queryApplicationInvoiceVo.setPaymentOrderVoList(paymentOrderVOList);
        List<BillingInfoVO> billingInfoVOList = new ArrayList<>();
        for (int i = 0; i < paymentOrderVOList.size(); i++) {
            BillingInfoVO billingInfo = paymentOrderDao.getBillingInfo(paymentOrderVOList.get(i).getId());
            billingInfoVOList.add(billingInfo);
        }
        queryApplicationInvoiceVo.setBillingInfoVoList(billingInfoVOList);
        PaymentOrder paymentOrderOne = paymentOrderDao.selectById(paymentOrderVOList.get(0).getId());
        queryApplicationInvoiceVo.setBuyerVo(merchantDao.getBuyerById(paymentOrderOne.getMerchantId()));
        queryApplicationInvoiceVo.setSellerVo(taxDao.getSellerById(paymentOrderOne.getTaxId()));
        InvoiceApplication invoiceApplication = invoiceApplicationDao.selectById(applicationId);
        InvoiceApplicationVO invoiceApplicationVo = new InvoiceApplicationVO();
        BeanUtils.copyProperties(invoiceApplication, invoiceApplicationVo);
        queryApplicationInvoiceVo.setInvoiceApplicationVo(invoiceApplicationVo);
        Address address = addressDao.selectById(invoiceApplication.getApplicationAddress());
        AddressVO addressVo = new AddressVO();
        BeanUtils.copyProperties(address, addressVo);
        queryApplicationInvoiceVo.setAddressVo(addressVo);
        InvoiceCatalog invoiceCatalog = invoiceCatalogDao.selectById(invoiceApplication.getInvoiceCatalogType());
        InvoiceCatalogVO invoiceCatalogVo = new InvoiceCatalogVO();
        BeanUtils.copyProperties(invoiceCatalog, invoiceCatalogVo);
        queryApplicationInvoiceVo.setInvoiceCatalogVo(invoiceCatalogVo);
        return ReturnJson.success(queryApplicationInvoiceVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson saveInvoice(AddInvoiceDTO addInvoiceDto) {
        ReturnJson returnJson = new ReturnJson("添加失败", 300);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Invoice invoice = new Invoice();
        if (addInvoiceDto.getId() == null) {
            InvoiceApplication invoiceApplication = invoiceApplicationDao.selectById(addInvoiceDto.getApplicationId());
            if (invoiceApplication.getApplicationState() == 3) {
                return ReturnJson.error("已经开过票了，请勿重复提交");
            }
            invoice.setApplicationId(addInvoiceDto.getApplicationId());
            String invoiceCode = this.getInvoiceCode();
            if (invoiceCode != null) {
                int code = Integer.parseInt(invoiceCode.substring(2)) + 1;
                invoice.setInvoiceCode("FP" + code);
            } else {
                invoice.setInvoiceCode("FP" + 0000);
            }
            //输入时间不正确给系统当前默认时间
            invoice.setInvoicePrintDate(LocalDateTime.parse(DateUtil.getTime(), df));
            invoice.setInvoicePrintPerson(addInvoiceDto.getInvoicePrintPerson());
            invoice.setApplicationInvoicePerson(addInvoiceDto.getApplicationInvoicePerson());
            invoice.setInvoiceMoney(addInvoiceDto.getInvoiceMoney());
            invoice.setInvoiceCatalog(addInvoiceDto.getInvoiceCatalog());
            invoice.setInvoiceUrl(addInvoiceDto.getInvoiceUrl());
            invoice.setTaxReceiptUrl(addInvoiceDto.getTaxReceiptUrl());
            invoice.setExpressSheetNo(addInvoiceDto.getExpressSheetNo());
            invoice.setExpressCompanyName(addInvoiceDto.getExpressCompanyName());
            invoice.setInvoiceDesc(addInvoiceDto.getInvoiceDesc());
            invoice.setCreateDate((LocalDateTime.parse(DateUtil.getTime(), df)));
            int num = invoiceDao.insert(invoice);
            if (num > 0) {
                int num2 = invoiceApplicationService.updateById(addInvoiceDto.getApplicationId(), 3);
                if (num2 > 0) {
                    List<String> list = invoiceDao.selectInvoiceListPayId(addInvoiceDto.getApplicationId());
                    for (int i = 0; i < list.size(); i++) {
                        PaymentOrder paymentOrder = paymentOrderDao.selectById(list.get(i));
                        paymentOrder.setIsNotInvoice(1);
                        paymentOrderDao.updateById(paymentOrder);
                    }
                    returnJson = new ReturnJson("添加成功", 200);
                }
            }
            return returnJson;
        } else {
            invoice.setId(addInvoiceDto.getId());
            invoice.setInvoiceCatalog(addInvoiceDto.getInvoiceCatalog());
            invoice.setInvoiceUrl(addInvoiceDto.getInvoiceUrl());
            invoice.setTaxReceiptUrl(addInvoiceDto.getTaxReceiptUrl());
            invoice.setExpressSheetNo(addInvoiceDto.getExpressSheetNo());
            invoice.setExpressCompanyName(addInvoiceDto.getExpressCompanyName());
            invoice.setInvoiceDesc(addInvoiceDto.getInvoiceDesc());
            invoice.setUpdateDate((LocalDateTime.parse(DateUtil.getTime(), df)));
            int num = invoiceDao.updateById(invoice);
            if (num > 0) {
                returnJson = new ReturnJson("操作成功", 200);
            }
            return returnJson;
        }

    }

    @Override
    public String getInvoiceCode() {
        return invoiceDao.getInvoiceCode();
    }

    @Override
    public ReturnJson getListInvoicequery(TobeInvoicedDTO tobeinvoicedDto) {
        Page page = new Page(tobeinvoicedDto.getPageNo(), tobeinvoicedDto.getPageSize());
        IPage<InvoiceVO> list = invoiceDao.getListInvoicequery(page, tobeinvoicedDto);
        return ReturnJson.success(list);
    }

    /**
     * 分包开票待开票
     *
     * @param tobeinvoicedDto
     * @return
     */
    @Override
    public ReturnJson getListSubQuery(TobeInvoicedDTO tobeinvoicedDto) {
        Page page = new Page(tobeinvoicedDto.getPageNo(), tobeinvoicedDto.getPageSize());
        IPage<ToSubcontractInvoiceVO> list = invoiceDao.getListSubQuery(page, tobeinvoicedDto);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getInvoiceListQuery(String invoiceId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        DecimalFormat df = new DecimalFormat("0.00");
        Map<String, Object> map = new HashMap();
        BigDecimal totalTaxPrice = new BigDecimal("0.00");
        List<String> list = Arrays.asList(invoiceId.split(","));
        List<InvoiceListVO> voList = invoiceDao.getInvoiceListQuery(list);
        for (InvoiceListVO vo : voList) {
            PaymentInventory paymentInventory = new PaymentInventory();
            paymentInventory.setId(vo.getId());
            totalTaxPrice = totalTaxPrice.add(vo.getRealMoney());
            List<InvoiceLadderPrice> invoiceLadderPrice = invoiceLadderPriceDao.selectList(new QueryWrapper<InvoiceLadderPrice>()
                    .lambda().eq(InvoiceLadderPrice::getTaxId, vo.getTaxId()));
            if (invoiceLadderPrice != null) {
                for (InvoiceLadderPrice price : invoiceLadderPrice) {
                    if ((vo.getTaskMoney().compareTo(price.getStartMoney()) > -1) && (vo.getTaskMoney().compareTo(price.getEndMoney()) == -1)) {
                        vo.setTaxRate(price.getRate());//纳税率
                        BigDecimal bigDecimal = vo.getTaxRate().multiply(vo.getTaskMoney());
                        vo.setPersonalServiceFee(bigDecimal.setScale(2, RoundingMode.HALF_UP));//个人服务费
                        vo.setTaxAmount((vo.getTaskMoney().subtract(vo.getPersonalServiceFee())).divide((vo.getTaxRate().add(new BigDecimal("1.00"))).multiply(vo.getTaxRate()), 2, BigDecimal.ROUND_HALF_UP));//纳税金额
                    }
                }
            }
            map.put("voList", voList);
            map.put("totalTaxPrice", totalTaxPrice);
            returnJson = new ReturnJson("操作成功", map, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson queryInvoice(String invoiceId) {
        Invoice invoice = invoiceDao.selectById(invoiceId);
        if (invoice == null) {
            return ReturnJson.error("不存在此发票");
        }
        InvoiceInformationVO vo = invoiceDao.getInvInfoById(invoiceId);
        QueryPlaInvoiceVO queryApplicationInvoiceVo = new QueryPlaInvoiceVO();
        List<PaymentOrderVO> paymentOrderVOList = paymentOrderDao.queryPaymentOrderInfo(invoice.getApplicationId());
        queryApplicationInvoiceVo.setPaymentOrderVoList(paymentOrderVOList);
        List<BillingInfoVO> billingInfoVOList = new ArrayList<>();
        for (int i = 0; i < paymentOrderVOList.size(); i++) {
            BillingInfoVO billingInfo = paymentOrderDao.getBillingInfo(paymentOrderVOList.get(i).getId());
            billingInfoVOList.add(billingInfo);
        }
        queryApplicationInvoiceVo.setBillingInfoVoList(billingInfoVOList);
        PaymentOrder paymentOrderOne = paymentOrderDao.selectById(paymentOrderVOList.get(0).getId());
        queryApplicationInvoiceVo.setBuyerVo(merchantDao.getBuyerById(paymentOrderOne.getMerchantId()));
        queryApplicationInvoiceVo.setSellerVo(taxDao.getSellerById(paymentOrderOne.getTaxId()));
        InvoiceApplication invoiceApplication = invoiceApplicationDao.selectById(invoice.getApplicationId());

        Address address = addressDao.selectById(invoiceApplication.getApplicationAddress());
        AddressVO addressVo = new AddressVO();
        BeanUtils.copyProperties(address, addressVo);
        queryApplicationInvoiceVo.setAddressVo(addressVo);
        InvoiceCatalog invoiceCatalog = invoiceCatalogDao.selectById(invoiceApplication.getInvoiceCatalogType());
        if (invoiceCatalog == null) {
            return ReturnJson.error("开票类目已被后台清除，请联系管理员更改！");
        }
        List<ExpressLogisticsInfo> expressLogisticsInfos = KdniaoTrackQueryAPI.getExpressInfo(vo.getExpressCompanyName(), vo.getExpressSheetNo());
        queryApplicationInvoiceVo.setExpressLogisticsInfoList(expressLogisticsInfos);
        SendAndReceiveVO sendAndReceiveVo = new SendAndReceiveVO();
        sendAndReceiveVo.setLogisticsCompany(vo.getExpressCompanyName());
        sendAndReceiveVo.setLogisticsOrderNo(vo.getExpressSheetNo());
        sendAndReceiveVo.setFrom(queryApplicationInvoiceVo.getSellerVo().getTaxName());
        sendAndReceiveVo.setFromTelephone(queryApplicationInvoiceVo.getSellerVo().getPhone());
        sendAndReceiveVo.setSendingAddress(queryApplicationInvoiceVo.getSellerVo().getTaxAddress());
        sendAndReceiveVo.setToAddress(address.getAddressName());
        sendAndReceiveVo.setAddressee(address.getLinkName());
        sendAndReceiveVo.setAddresseeTelephone(address.getLinkMobile());
        queryApplicationInvoiceVo.setSendAndReceiveVo(sendAndReceiveVo);
        InvoiceCatalogVO invoiceCatalogVo = new InvoiceCatalogVO();
        BeanUtils.copyProperties(invoiceCatalog, invoiceCatalogVo);
        queryApplicationInvoiceVo.setInvoiceCatalogVo(invoiceCatalogVo);
        PlaInvoiceInfoVO invoiceInfoVo = new PlaInvoiceInfoVO();
        BeanUtils.copyProperties(invoice, invoiceInfoVo);
        invoiceInfoVo.setInvoiceDesc(invoiceApplication.getApplicationDesc());
        queryApplicationInvoiceVo.setPlaInvoiceInfoVo(invoiceInfoVo);
        return ReturnJson.success(queryApplicationInvoiceVo);
    }
}
