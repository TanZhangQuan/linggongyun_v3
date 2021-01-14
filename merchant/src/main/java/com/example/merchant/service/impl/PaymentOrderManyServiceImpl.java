package com.example.merchant.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.UnionpayBankType;
import com.example.common.util.*;
import com.example.merchant.dto.merchant.AddPaymentOrderManyDTO;
import com.example.merchant.dto.merchant.PaymentOrderManyPayDTO;
import com.example.merchant.dto.merchant.PaymentOrderMerchantDTO;
import com.example.merchant.dto.platform.PaymentOrderDTO;
import com.example.merchant.dto.platform.PaymentOrderManyDTO;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.CompanyUnionpayService;
import com.example.merchant.service.PaymentInventoryService;
import com.example.merchant.service.PaymentOrderManyService;
import com.example.merchant.service.TaxUnionpayService;
import com.example.merchant.util.AcquireID;
import com.example.merchant.vo.ExpressInfoVO;
import com.example.merchant.vo.PaymentOrderInfoVO;
import com.example.mybatis.dto.QueryCrowdSourcingDTO;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.po.InvoiceInfoPO;
import com.example.mybatis.po.PaymentOrderInfoPO;
import com.example.mybatis.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 众包支付单操作
 * 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-08
 */
@Service
public class PaymentOrderManyServiceImpl extends ServiceImpl<PaymentOrderManyDao, PaymentOrderMany> implements PaymentOrderManyService {

    @Value("${TOKEN}")
    private String TOKEN;

    @Resource
    private PaymentOrderManyDao paymentOrderManyDao;

    @Resource
    private PaymentInventoryDao paymentInventoryDao;

    @Resource
    private CompanyTaxDao companyTaxDao;

    @Resource
    private PaymentInventoryService paymentInventoryService;

    @Resource
    private CompanyLadderServiceDao companyLadderServiceDao;

    @Resource
    private AcquireID acquireID;

    @Resource
    private TaxDao taxDao;

    @Resource
    private MerchantDao merchantDao;

    @Resource
    private CrowdSourcingInvoiceDao crowdSourcingInvoiceDao;

    @Resource
    private CompanyInfoDao companyInfoDao;

    @Resource
    private TaxUnionpayService taxUnionpayService;

    @Resource
    private CompanyUnionpayService companyUnionpayService;

    /**
     * 众包今天的支付金额
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getDay(String merchantId) {
        Merchant merchant = merchantDao.selectById(merchantId);
        if (merchant == null) {
            return ReturnJson.error("您输入的信息有误！");
        }
        return ReturnJson.success((paymentOrderManyDao.getTodayById(merchant.getCompanyId())));
    }

    /**
     * 众包本周的支付金额
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getWeek(String merchantId) {
        Merchant merchant = merchantDao.selectById(merchantId);
        if (merchant == null) {
            return ReturnJson.error("您输入的信息有误！");
        }
        return ReturnJson.success((paymentOrderManyDao.getWeekTradeById(merchant.getCompanyId())));
    }

    /**
     * 众包本月的支付金额
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getMonth(String merchantId) {
        Merchant merchant = merchantDao.selectById(merchantId);
        if (merchant == null) {
            return ReturnJson.error("您输入的信息有误！");
        }
        return ReturnJson.success((paymentOrderManyDao.getMonthTradeById(merchant.getCompanyId())));
    }

    /**
     * 众包今年的支付金额
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getYear(String merchantId) {
        Merchant merchant = merchantDao.selectById(merchantId);
        if (merchant == null) {
            return ReturnJson.error("您输入的信息有误！");
        }
        return ReturnJson.success((paymentOrderManyDao.getYearTradeById(merchant.getCompanyId())));
    }

    /**
     * 根据商户id查众包待开票数据
     *
     * @param queryCrowdSourcingDto
     * @param userId
     * @return
     */
    @Override
    public ReturnJson getListCSIByID(QueryCrowdSourcingDTO queryCrowdSourcingDto, String userId) {
        Page page = new Page(queryCrowdSourcingDto.getPageNo(), queryCrowdSourcingDto.getPageSize());
        Merchant merchant = merchantDao.selectById(userId);
        IPage<CrowdSourcingInvoiceInfoVO> list = paymentOrderManyDao.getListCSIByID(page, queryCrowdSourcingDto, merchant.getCompanyId());
        return ReturnJson.success(list);
    }

    /**
     * 根据支付id查询众包支付信息
     *
     * @param id
     * @return
     */
    @Override
    public ReturnJson getPayOrderManyById(String id) {
        PaymentOrderManyVO paymentOrderManyVo = paymentOrderManyDao.getPayOrderManyById(id);
        return ReturnJson.success(paymentOrderManyVo);
    }

    @Override
    public ReturnJson getInvoiceDetailsByPayId(String id, Integer pageNo, Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        IPage<InvoiceDetailsVO> list = paymentOrderManyDao.getInvoiceDetailsByPayId(page, id);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getPaymentOrderMany(String merchantId, PaymentOrderMerchantDTO paymentOrderMerchantDto) {
        Merchant merchant = merchantDao.selectById(merchantId);
        String companyId = merchant.getCompanyId();
        String paymentOrderId = paymentOrderMerchantDto.getPaymentOrderId();
        String taxId = paymentOrderMerchantDto.getTaxId();
        Integer pageSize = paymentOrderMerchantDto.getPageSize();
        Integer page = paymentOrderMerchantDto.getPageNo();
        String beginDate = paymentOrderMerchantDto.getBeginDate();
        String endDate = paymentOrderMerchantDto.getEndDate();
        Page<PaymentOrderMany> paymentOrderManyPage = new Page<>(page, pageSize);
        IPage<PaymentOrderMany> paymentOrderManyIPage = paymentOrderManyDao.selectMany(paymentOrderManyPage, companyId, paymentOrderId, taxId, beginDate, endDate);
        return ReturnJson.success(paymentOrderManyIPage);
    }

    @Override
    public ReturnJson getPaymentOrderManyInfo(String id) {
        PaymentOrderInfoVO paymentOrderInfoVO = new PaymentOrderInfoVO();
        ExpressInfoVO expressInfoVO = new ExpressInfoVO();

        //为众包订单
        PaymentOrderInfoPO paymentOrderInfoPO = paymentOrderManyDao.selectPaymentOrderInfo(id);
        if (paymentOrderInfoPO == null) {
            return ReturnJson.error("订单编号有误，请重新输入！");
        }
        InvoiceInfoPO invoiceInfoPO = crowdSourcingInvoiceDao.selectInvoiceInfoPO(id);
        if (invoiceInfoPO != null) {
            //众包发票信息
            paymentOrderInfoVO.setInvoice(invoiceInfoPO.getInvoiceUrl());
            expressInfoVO.setExpressCompanyName(invoiceInfoPO.getExpressCompanyName());
            expressInfoVO.setExpressCode(invoiceInfoPO.getExpressSheetNo());
            List<ExpressLogisticsInfo> expressLogisticsInfos = KdniaoTrackQueryAPI.getExpressInfo(invoiceInfoPO.getExpressCompanyName(), invoiceInfoPO.getExpressSheetNo());
            expressInfoVO.setExpressLogisticsInfos(expressLogisticsInfos);
        }
        List<PaymentInventoryVO> paymentInventories = paymentInventoryDao.selectPaymentOrderManyInfo(id, null);
        paymentOrderInfoVO.setPaymentInventories(paymentInventories);
        paymentOrderInfoVO.setPaymentOrderInfoPO(paymentOrderInfoPO);
        paymentOrderInfoVO.setExpressInfoVO(expressInfoVO);
        return ReturnJson.success(paymentOrderInfoVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson saveOrUpdataPaymentOrderMany(AddPaymentOrderManyDTO addPaymentOrderManyDto, String merchantId) throws CommonException {
        PaymentOrderMany paymentOrderMany = new PaymentOrderMany();
        PaymentOrderManyDTO paymentOrderManyDto = addPaymentOrderManyDto.getPaymentOrderManyDto();
        BeanUtils.copyProperties(paymentOrderManyDto, paymentOrderMany);
        paymentOrderMany.setTradeNo(OrderTradeNo.GetRandom()+"PM");
        Tax tax = taxDao.selectById(paymentOrderMany.getTaxId());
        paymentOrderMany.setPlatformServiceProvider(tax.getTaxName());
        Merchant merchant = merchantDao.selectById(merchantId);
        if (merchant != null) {
            paymentOrderMany.setCompanyId(merchant.getCompanyId());
            paymentOrderMany.setCompanySName(merchant.getCompanyName());
        } else {
            CompanyInfo companyInfo = companyInfoDao.selectById(merchantId);
            paymentOrderMany.setCompanyId(companyInfo.getId());
            paymentOrderMany.setCompanySName(companyInfo.getCompanyName());
        }
        List<PaymentInventory> paymentInventories = addPaymentOrderManyDto.getPaymentInventories();
        String id = paymentOrderMany.getId();
        if (id != "" && paymentOrderMany.getPaymentOrderStatus() == 0) {
            List<PaymentInventory> paymentInventoryList = paymentInventoryDao.selectList(new QueryWrapper<PaymentInventory>().eq("payment_order_id", id));
            List<String> ids = new ArrayList<>();
            for (PaymentInventory paymentInventory : paymentInventoryList) {
                ids.add(paymentInventory.getId());
            }
            paymentInventoryDao.delete(new QueryWrapper<PaymentInventory>().eq("payment_order_id", id));
            this.removeById(id);
        }

        BigDecimal merchantTax = new BigDecimal("100.00");
        BigDecimal receviceTax = new BigDecimal("0.00");
        BigDecimal compositeTax;
        BigDecimal countMoney = new BigDecimal("0");
        BigDecimal countServiceMoney = new BigDecimal("0");

        Integer taxStatus = paymentOrderMany.getTaxStatus();
        paymentOrderMany.setMerchantTax(merchantTax);
        paymentOrderMany.setReceviceTax(receviceTax);
        CompanyTax companyTax = companyTaxDao.selectOne(new QueryWrapper<CompanyTax>().
                eq("tax_id", paymentOrderMany.getTaxId()).
                eq("company_id", paymentOrderMany.getCompanyId()).
                eq("package_status", 1));

        if (companyTax.getChargeStatus() == 0) {
            compositeTax = companyTax.getServiceCharge().divide(BigDecimal.valueOf(100));
            for (PaymentInventory paymentInventory : paymentInventories) {
                BigDecimal realMoney = paymentInventory.getRealMoney();
                paymentInventory.setTaskMoney(realMoney);
                paymentInventory.setCompositeTax(compositeTax);
                if (taxStatus == 0) {
                    paymentInventory.setMerchantPaymentMoney(realMoney.multiply(compositeTax));
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                } else if (taxStatus == 1) {
                    paymentInventory.setMerchantPaymentMoney(realMoney);
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                    paymentInventory.setRealMoney(realMoney.subtract(realMoney.multiply(compositeTax)));
                } else {
                    paymentInventory.setMerchantPaymentMoney(realMoney.multiply(compositeTax.subtract(merchantTax).add(new BigDecimal("1"))));
                    paymentInventory.setRealMoney(realMoney.subtract(realMoney.multiply(compositeTax.subtract(receviceTax))));
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                }
                countMoney = countMoney.add(paymentInventory.getMerchantPaymentMoney());
                countServiceMoney = countServiceMoney.add(paymentInventory.getServiceMoney());
            }
        } else {
            List<CompanyLadderService> companyLadderServices = companyLadderServiceDao.selectList(new QueryWrapper<CompanyLadderService>().eq("company_tax_id", companyTax.getId()).orderByAsc("start_money"));
            for (PaymentInventory paymentInventory : paymentInventories) {
                BigDecimal realMoney = paymentInventory.getRealMoney();
                paymentInventory.setTaskMoney(realMoney);
                compositeTax = this.getCompositeTax(companyLadderServices, realMoney);
                paymentInventory.setCompositeTax(compositeTax);
                if (taxStatus == 0) {
                    paymentInventory.setMerchantPaymentMoney(realMoney.multiply(compositeTax));
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                } else if (taxStatus == 1) {
                    paymentInventory.setMerchantPaymentMoney(realMoney);
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                    paymentInventory.setRealMoney(realMoney.subtract(realMoney.multiply(compositeTax)));
                } else {
                    paymentInventory.setMerchantPaymentMoney(realMoney.multiply(compositeTax.subtract(merchantTax).add(new BigDecimal("1"))));
                    paymentInventory.setRealMoney(realMoney.subtract(realMoney.multiply(compositeTax.subtract(receviceTax))));
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                }
                countMoney = countMoney.add(paymentInventory.getMerchantPaymentMoney());
                countServiceMoney = countServiceMoney.add(paymentInventory.getServiceMoney());
            }
        }
        paymentOrderMany.setServiceMoney(countServiceMoney);
        paymentOrderMany.setRealMoney(countMoney);
        boolean b = this.saveOrUpdate(paymentOrderMany);
        if (!b) {
            throw new CommonException(300, "订单创建失败！");
        }
        for (PaymentInventory paymentInventory : paymentInventories) {
            paymentInventory.setTradeNo(OrderTradeNo.GetRandom()+"PI");
            paymentInventory.setPaymentOrderId(paymentOrderMany.getId());
            paymentInventory.setPackageStatus(1);
        }
        paymentInventoryService.saveOrUpdateBatch(paymentInventories);
        return ReturnJson.success("支付订单创建成功！");
    }

    @Override
    public ReturnJson paymentOrderManyPay(PaymentOrderManyPayDTO paymentOrderManyPayDTO) {

        PaymentOrderMany paymentOrderMany = getById(paymentOrderManyPayDTO.getPaymentOrderManyId());
        if (paymentOrderMany == null) {
            return ReturnJson.error("众包支付单信息不存在");
        }

        //检查众包清单总手续费
        BigDecimal serviceCharge = paymentOrderMany.getServiceMoney();
        if (serviceCharge == null || serviceCharge.compareTo(BigDecimal.ZERO) <= 0) {
            return ReturnJson.error("众包总手续费有误");
        }

        switch (paymentOrderManyPayDTO.getPaymentMode()) {

            case 0:

                if (StringUtils.isBlank(paymentOrderManyPayDTO.getTurnkeyProjectPayment())) {
                    return ReturnJson.error("请上传众包支付回单");
                }

                paymentOrderMany.setManyPayment(paymentOrderManyPayDTO.getTurnkeyProjectPayment());

                break;

            case 1:

                return ReturnJson.error("连连支付暂未开通");

            case 2:

                return ReturnJson.error("网商银行支付暂未开通");

            case 3:

            case 4:

            case 5:

            case 6:

                break;

            default:
                return ReturnJson.error("支付方式不存在");
        }

        paymentOrderMany.setPaymentDate(LocalDateTime.now());
        paymentOrderMany.setPaymentMode(paymentOrderManyPayDTO.getPaymentMode());
        paymentOrderMany.setPaymentOrderStatus(2);
        updateById(paymentOrderMany);

        return ReturnJson.error("操作成功");
    }


    @Override
    public ReturnJson getDayPaas(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        List<PaymentOrderMany> list = paymentOrderManyDao.selectDaypaas(merchantIds);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getWeekPaas(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        List<PaymentOrderMany> list = paymentOrderManyDao.selectWeekpaas(merchantIds);
        return ReturnJson.success(list);
    }


    @Override
    public ReturnJson getMonthPaas(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        List<PaymentOrderMany> list = paymentOrderManyDao.selectMonthpaas(merchantIds);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getYearPaas(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        List<PaymentOrderMany> list = paymentOrderManyDao.selectYearpaas(merchantIds);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson paymentOrderManyAudit(String paymentOrderId, Boolean boolPass, String reasonsForRejection) throws Exception {

        PaymentOrderMany paymentOrderMany = getById(paymentOrderId);
        if (paymentOrderMany == null) {
            return ReturnJson.error("众包支付单信息不存在");
        }

        if (paymentOrderMany.getPaymentOrderStatus() != 2) {
            return ReturnJson.error("非支付中状态众包支付单不可审核");
        }

        if (boolPass == null || !boolPass) {

            if (StringUtils.isBlank(reasonsForRejection)) {
                return ReturnJson.error("请输入驳回原因");
            }

            paymentOrderMany.setPaymentOrderStatus(4);
            paymentOrderMany.setReasonsForRejection(reasonsForRejection);
            paymentOrderManyDao.updateById(paymentOrderMany);

        } else {

            //查询众包清单总手续费
            BigDecimal serviceCharge = paymentOrderMany.getServiceMoney();
            if (serviceCharge == null || serviceCharge.compareTo(BigDecimal.ZERO) <= 0) {
                return ReturnJson.error("众包总手续费有误");
            }

            TaxUnionpay taxUnionpay;
            CompanyUnionpay companyUnionpay;
            JSONObject jsonObject;
            Boolean boolSuccess;
            JSONObject returnValue;
            String rtnCode;
            switch (paymentOrderMany.getPaymentMode()) {

                case 0:

                    paymentOrderMany.setPaymentOrderStatus(3);
                    paymentOrderManyDao.updateById(paymentOrderMany);

                    break;

                case 1:

                    return ReturnJson.error("连连支付暂未开通");

                case 2:

                    return ReturnJson.error("网商银行支付暂未开通");

                case 3:

                    //查询服务商盛京银联记录
                    taxUnionpay = taxUnionpayService.queryTaxUnionpay(paymentOrderMany.getTaxId(), UnionpayBankType.SJBK);
                    if (taxUnionpay == null) {
                        return ReturnJson.error("服务商未开通银联盛京银行支付");
                    }

                    //查询商户是否已开通银联盛京银行子账号
                    companyUnionpay = companyUnionpayService.queryMerchantUnionpayUnionpayBankType(paymentOrderMany.getCompanyId(), paymentOrderMany.getTaxId(), UnionpayBankType.SJBK);
                    if (companyUnionpay == null) {
                        return ReturnJson.error("商户未开通服务商的银联盛京银行子账号");
                    }

                    //支付众包手续费
                    jsonObject = UnionpayUtil.AC054(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), paymentOrderMany.getTradeNo(), companyUnionpay.getUid(), taxUnionpay.getServiceChargeNo(), serviceCharge);
                    if (jsonObject == null) {
                        return ReturnJson.error("支付众包手续费失败");
                    }

                    boolSuccess = jsonObject.getBoolean("success");
                    if (boolSuccess == null || !boolSuccess) {
                        String errMsg = jsonObject.getString("err_msg");
                        return ReturnJson.error("支付众包手续费失败：" + errMsg);
                    }

                    returnValue = jsonObject.getJSONObject("return_value");
                    rtnCode = returnValue.getString("rtn_code");
                    if (!("S00000".equals(rtnCode))) {
                        String errMsg = returnValue.getString("err_msg");
                        return ReturnJson.error("支付众包手续费失败：" + errMsg);
                    }

                    break;

                case 4:

                    //查询服务商平安银联记录
                    taxUnionpay = taxUnionpayService.queryTaxUnionpay(paymentOrderMany.getTaxId(), UnionpayBankType.PABK);
                    if (taxUnionpay == null) {
                        return ReturnJson.error("服务商未开通银联平安银行支付");
                    }

                    //查询商户是否已开通银联平安银行子账号
                    companyUnionpay = companyUnionpayService.queryMerchantUnionpayUnionpayBankType(paymentOrderMany.getCompanyId(), paymentOrderMany.getTaxId(), UnionpayBankType.PABK);
                    if (companyUnionpay == null) {
                        return ReturnJson.error("商户未已开通服务商的银联平安银行子账号");
                    }

                    //支付众包手续费
                    jsonObject = UnionpayUtil.AC054(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), paymentOrderMany.getTradeNo(), companyUnionpay.getUid(), taxUnionpay.getServiceChargeNo(), serviceCharge);
                    if (jsonObject == null) {
                        return ReturnJson.error("支付众包手续费失败");
                    }

                    boolSuccess = jsonObject.getBoolean("success");
                    if (boolSuccess == null || !boolSuccess) {
                        String errMsg = jsonObject.getString("err_msg");
                        return ReturnJson.error("支付众包手续费失败：" + errMsg);
                    }

                    returnValue = jsonObject.getJSONObject("return_value");
                    rtnCode = returnValue.getString("rtn_code");
                    if (!("S00000".equals(rtnCode))) {
                        String errMsg = returnValue.getString("err_msg");
                        return ReturnJson.error("支付众包手续费失败：" + errMsg);
                    }

                    break;

                case 5:

                    //查询服务商网商银联记录
                    taxUnionpay = taxUnionpayService.queryTaxUnionpay(paymentOrderMany.getTaxId(), UnionpayBankType.WSBK);
                    if (taxUnionpay == null) {
                        return ReturnJson.error("服务商未开通银联网商银行支付");
                    }

                    //查询商户是否已开通银联网商银行子账号
                    companyUnionpay = companyUnionpayService.queryMerchantUnionpayUnionpayBankType(paymentOrderMany.getCompanyId(), paymentOrderMany.getTaxId(), UnionpayBankType.WSBK);
                    if (companyUnionpay == null) {
                        return ReturnJson.error("商户未已开通服务商的银联网商银行子账号");
                    }

                    //支付众包手续费
                    jsonObject = UnionpayUtil.AC054(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), paymentOrderMany.getTradeNo(), companyUnionpay.getUid(), taxUnionpay.getServiceChargeNo(), serviceCharge);
                    if (jsonObject == null) {
                        return ReturnJson.error("支付众包手续费失败");
                    }

                    boolSuccess = jsonObject.getBoolean("success");
                    if (boolSuccess == null || !boolSuccess) {
                        String errMsg = jsonObject.getString("err_msg");
                        return ReturnJson.error("支付众包手续费失败：" + errMsg);
                    }

                    returnValue = jsonObject.getJSONObject("return_value");
                    rtnCode = returnValue.getString("rtn_code");
                    if (!("S00000".equals(rtnCode))) {
                        String errMsg = returnValue.getString("err_msg");
                        return ReturnJson.error("支付众包手续费失败：" + errMsg);
                    }

                    break;

                case 6:

                    //查询服务商招商银联记录
                    taxUnionpay = taxUnionpayService.queryTaxUnionpay(paymentOrderMany.getTaxId(), UnionpayBankType.ZSBK);
                    if (taxUnionpay == null) {
                        return ReturnJson.error("服务商未开通银联招商银行支付");
                    }

                    //查询商户是否已开通银联招商银行子账号
                    companyUnionpay = companyUnionpayService.queryMerchantUnionpayUnionpayBankType(paymentOrderMany.getCompanyId(), paymentOrderMany.getTaxId(), UnionpayBankType.ZSBK);
                    if (companyUnionpay == null) {
                        return ReturnJson.error("商户未已开通服务商的银联招商银行子账号");
                    }

                    //支付众包手续费
                    jsonObject = UnionpayUtil.AC054(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), paymentOrderMany.getTradeNo(), companyUnionpay.getUid(), taxUnionpay.getServiceChargeNo(), serviceCharge);
                    if (jsonObject == null) {
                        return ReturnJson.error("支付众包手续费失败");
                    }

                    boolSuccess = jsonObject.getBoolean("success");
                    if (boolSuccess == null || !boolSuccess) {
                        String errMsg = jsonObject.getString("err_msg");
                        return ReturnJson.error("支付众包手续费失败：" + errMsg);
                    }

                    returnValue = jsonObject.getJSONObject("return_value");
                    rtnCode = returnValue.getString("rtn_code");
                    if (!("S00000".equals(rtnCode))) {
                        String errMsg = returnValue.getString("err_msg");
                        return ReturnJson.error("支付众包手续费失败：" + errMsg);
                    }

                    break;

                default:
                    return ReturnJson.error("支付方式不存在");
            }

        }

        return ReturnJson.success("操作成功");
    }

    @Override
    public ReturnJson getPaymentOrderManyPaas(PaymentOrderDTO paymentOrderDto, String managersId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(managersId);
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success("");
        }
        String merchantName = paymentOrderDto.getMerchantName();
        String paymentOrderId = paymentOrderDto.getPaymentOrderId();
        String taxId = paymentOrderDto.getTaxId();
        Integer pageSize = paymentOrderDto.getPageSize();
        Integer page = paymentOrderDto.getPageNo();
        String beginDate = paymentOrderDto.getBeginDate();
        String endDate = paymentOrderDto.getEndDate();
        Page<PaymentOrderMany> paymentOrderManyPage = new Page<>(page, pageSize);
        IPage<PaymentOrderMany> paymentOrderManyIPage = paymentOrderManyDao.selectManyPaas(paymentOrderManyPage,
                merchantIds, merchantName, paymentOrderId, taxId, beginDate, endDate);
        return ReturnJson.success(paymentOrderManyIPage);
    }

    @Override
    public ReturnJson getDayPaa(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        TodayVO todayVO = paymentOrderManyDao.selectDaypaa(merchantIds);
        return ReturnJson.success(todayVO);
    }

    @Override
    public ReturnJson getWeekPaa(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        WeekTradeVO weekTradeVO = paymentOrderManyDao.selectWeekpaa(merchantIds);
        return ReturnJson.success(weekTradeVO);
    }

    @Override
    public ReturnJson getMonthPaa(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        MonthTradeVO monthTradeVO = paymentOrderManyDao.selectMonthpaa(merchantIds);
        return ReturnJson.success(monthTradeVO);
    }

    @Override
    public ReturnJson getYearPaa(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        YearTradeVO yearTradeVO = paymentOrderManyDao.selectYearpaa(merchantIds);
        return ReturnJson.success(yearTradeVO);
    }

    @Override
    public PaymentOrderMany queryPaymentOrderManyByTradeNo(String tradeNo) {

        QueryWrapper<PaymentOrderMany> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentOrderMany::getTradeNo, tradeNo);

        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 获取综合费率
     *
     * @param companyLadderServices
     * @param realMoney
     * @return
     */
    private BigDecimal getCompositeTax(List<CompanyLadderService> companyLadderServices, BigDecimal realMoney) {
        BigDecimal compositeTax = new BigDecimal("0");
        for (CompanyLadderService companyLadderService : companyLadderServices) {
            BigDecimal startMoney = companyLadderService.getStartMoney();
            if (realMoney.compareTo(startMoney) >= 0) {
                compositeTax = companyLadderService.getServiceCharge().divide(BigDecimal.valueOf(100));
            }
        }
        return compositeTax;
    }

}
