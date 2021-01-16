package com.example.merchant.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.UnionpayBankType;
import com.example.common.util.*;
import com.example.merchant.dto.AssociatedTasksDTO;
import com.example.merchant.dto.merchant.AddPaymentOrderDTO;
import com.example.merchant.dto.merchant.PaymentDTO;
import com.example.merchant.dto.merchant.PaymentOrderMerchantDTO;
import com.example.merchant.dto.merchant.PaymentOrderPayDTO;
import com.example.merchant.dto.platform.PaymentOrderDTO;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.*;
import com.example.merchant.util.AcquireID;
import com.example.merchant.vo.ExpressInfoVO;
import com.example.merchant.vo.PaymentOrderInfoVO;
import com.example.merchant.vo.platform.CompanyBriefVO;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.po.InvoiceInfoPO;
import com.example.mybatis.po.PaymentOrderInfoPO;
import com.example.mybatis.vo.*;
import com.example.redis.dao.RedisDao;
import lombok.extern.slf4j.Slf4j;
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
 * 支付单信息
 * 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Slf4j
@Service
public class PaymentOrderServiceImpl extends ServiceImpl<PaymentOrderDao, PaymentOrder> implements PaymentOrderService {

    @Value("${TOKEN}")
    private String TOKEN;

    @Resource
    private RedisDao redisDao;

    @Resource
    private PaymentOrderDao paymentOrderDao;

    @Resource
    private PaymentInventoryDao paymentInventoryDao;

    @Resource
    private PaymentInventoryService paymentInventoryService;

    @Resource
    private ManagersDao managersDao;

    @Resource
    private CompanyInfoService companyInfoService;

    @Resource
    private CompanyTaxDao companyTaxDao;

    @Resource
    private CompanyLadderServiceDao companyLadderServiceDao;

    @Resource
    private InvoiceDao invoiceDao;

    @Resource
    private AcquireID acquireID;

    @Resource
    private TaxDao taxDao;

    @Resource
    private MerchantDao merchantDao;

    @Resource
    private TaskDao taskDao;

    @Resource
    private CompanyUnionpayService companyUnionpayService;

    @Resource
    private TaxUnionpayService taxUnionpayService;

    @Override
    public ReturnJson getPaymentOrder(String merchantId, PaymentOrderMerchantDTO paymentOrderMerchantDto) {
        Merchant merchant = merchantDao.selectById(merchantId);
        String paymentOrderId = paymentOrderMerchantDto.getPaymentOrderId();
        String taxId = paymentOrderMerchantDto.getTaxId();
        String beginDate = paymentOrderMerchantDto.getBeginDate();
        String endDate = paymentOrderMerchantDto.getEndDate();
        Page<PaymentOrder> paymentOrderPage = new Page<>(paymentOrderMerchantDto.getPageNo(), paymentOrderMerchantDto.getPageSize());
        IPage<PaymentOrder> paymentOrderIPage = paymentOrderDao.selectMany(paymentOrderPage, merchant.getCompanyId(), paymentOrderId, taxId, beginDate, endDate);
        return ReturnJson.success(paymentOrderIPage);
    }

    @Override
    public ReturnJson getPaymentOrderInfo(String paymentOrderId) {
        PaymentOrderInfoVO paymentOrderInfoVO = new PaymentOrderInfoVO();
        ExpressInfoVO expressInfoVO = new ExpressInfoVO();
        // 为总包订单
        PaymentOrderInfoPO paymentOrderInfoPO = paymentOrderDao.selectPaymentOrderInfo(paymentOrderId);
        if (paymentOrderInfoPO == null) {
            return ReturnJson.error("总包订单不存在");
        }

        //查询商户银联支付银行
        List<UnionpayBankType> companyUnionpayBankTypeList = companyUnionpayService.queryCompanyUnionpayMethod(paymentOrderInfoPO.getMerchantId(), paymentOrderInfoPO.getTaxId());
        paymentOrderInfoPO.setCompanyUnionpayBankTypeList(companyUnionpayBankTypeList);
        //获取商户主账号手机号
        String loginMobile = merchantDao.queryMainMerchantloginMobile(paymentOrderInfoPO.getMerchantId());
        paymentOrderInfoPO.setLoginMobile(loginMobile);

        InvoiceInfoPO invoiceInfoPO = invoiceDao.selectInvoiceInfoPO(paymentOrderId);
        if (invoiceInfoPO != null) {
            //总包发票信息
            paymentOrderInfoVO.setInvoice(invoiceInfoPO.getInvoiceUrl());
            paymentOrderInfoVO.setSubpackageInvoice(invoiceInfoPO.getMakerInvoiceUrl());
            expressInfoVO.setExpressCompanyName(invoiceInfoPO.getExpressCompanyName());
            expressInfoVO.setExpressCode(invoiceInfoPO.getExpressSheetNo());
            List<ExpressLogisticsInfo> expressLogisticsInfos = KdniaoTrackQueryAPI.getExpressInfo(invoiceInfoPO.getExpressCompanyName(), invoiceInfoPO.getExpressSheetNo());
            expressInfoVO.setExpressLogisticsInfos(expressLogisticsInfos);
        }
        List<PaymentInventoryVO> paymentInventories = paymentInventoryDao.selectPaymentInventoryList(paymentOrderId, null);
        paymentOrderInfoVO.setPaymentInventories(paymentInventories);
        paymentOrderInfoVO.setPaymentOrderInfoPO(paymentOrderInfoPO);
        paymentOrderInfoVO.setExpressInfoVO(expressInfoVO);
        return ReturnJson.success(paymentOrderInfoVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson saveOrUpdataPaymentOrder(AddPaymentOrderDTO addPaymentOrderDto, String merchantId) throws CommonException {
        if (addPaymentOrderDto.getPaymentInventories() == null) {
            throw new CommonException(300, "支付清单不能为空！");
        }
        PaymentDTO paymentDto = addPaymentOrderDto.getPaymentDto();
        PaymentOrder paymentOrder = new PaymentOrder();
        BeanUtils.copyProperties(paymentDto, paymentOrder);
        Tax tax = taxDao.selectById(paymentDto.getTaxId());
        paymentOrder.setPlatformServiceProvider(tax.getTaxName());
        List<PaymentInventory> paymentInventories = addPaymentOrderDto.getPaymentInventories();
        String id = paymentOrder.getId();
        paymentOrder.setMerchantId(merchantId);
        paymentOrder.setTradeNo(OrderTradeNo.GetRandom() + "PO");
        CompanyInfo companyInfo = companyInfoService.getById(merchantId);
        if (companyInfo == null) {
            Merchant merchant = merchantDao.selectById(merchantId);
            paymentOrder.setCompanyId(merchant.getCompanyId());
            paymentOrder.setCompanySName(merchant.getCompanyName());
        }
        if (companyInfo != null) {
            paymentOrder.setCompanyId(companyInfo.getId());
            paymentOrder.setCompanySName(companyInfo.getCompanyName());
        }
        if (id != null && paymentOrder.getPaymentOrderStatus() == 0) {
            List<PaymentInventory> paymentInventoryList = paymentInventoryDao.selectList(new QueryWrapper<PaymentInventory>().eq("payment_order_id", id));
            List<String> ids = new ArrayList<>();
            for (PaymentInventory paymentInventory : paymentInventoryList) {
                ids.add(paymentInventory.getId());
            }
            paymentInventoryDao.delete(new QueryWrapper<PaymentInventory>().eq("payment_order_id", id));
            this.removeById(id);
        }

        if (paymentDto.getTaxStatus() == 0) {
            paymentOrder.setMerchantTax(new BigDecimal("100"));
        }
        if (paymentDto.getTaxStatus() == 1) {
            paymentOrder.setReceviceTax(new BigDecimal("100"));
        }

        BigDecimal receviceTax = paymentOrder.getReceviceTax().divide(BigDecimal.valueOf(100));
        BigDecimal merchantTax = paymentOrder.getMerchantTax().divide(BigDecimal.valueOf(100));
        BigDecimal compositeTax = new BigDecimal("0");
        BigDecimal countMoney = new BigDecimal("0");
        BigDecimal countWorkerMoney = new BigDecimal("0");
        BigDecimal countServiceMoney = new BigDecimal("0");

        CompanyTax companyTax = companyTaxDao.selectOne(new QueryWrapper<CompanyTax>()
                .eq("tax_id", paymentOrder.getTaxId())
                .eq("company_id", paymentOrder.getCompanyId())
                .eq("package_status", 0));
        Integer taxStatus = paymentOrder.getTaxStatus();
        //判断服务费是一口价还是梯度价
        if (companyTax.getChargeStatus() == 0) {
            compositeTax = companyTax.getServiceCharge().divide(BigDecimal.valueOf(100));
            paymentOrder.setCompositeTax(compositeTax.multiply(BigDecimal.valueOf(100)));
            for (PaymentInventory paymentInventory : paymentInventories) {
                BigDecimal realMoney = paymentInventory.getRealMoney();
                paymentInventory.setTaskMoney(realMoney);
                paymentInventory.setCompositeTax(compositeTax.multiply(BigDecimal.valueOf(100)));
                if (taxStatus == 0) {
                    paymentInventory.setMerchantPaymentMoney(realMoney.multiply(compositeTax.add(new BigDecimal(1))));
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                } else if (taxStatus == 1) {
                    paymentInventory.setMerchantPaymentMoney(realMoney);
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                    paymentInventory.setRealMoney(realMoney.subtract(realMoney.multiply(compositeTax)));
                } else {
                    paymentInventory.setMerchantPaymentMoney(realMoney.multiply(compositeTax.multiply(merchantTax).add(new BigDecimal("1"))));
                    paymentInventory.setRealMoney(realMoney.subtract(realMoney.multiply(compositeTax.multiply(receviceTax))));
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                }
                countMoney = countMoney.add(paymentInventory.getMerchantPaymentMoney());
                countWorkerMoney = countWorkerMoney.add(paymentInventory.getRealMoney());
                countServiceMoney = countServiceMoney.add(paymentInventory.getServiceMoney());
            }
        } else {
            List<CompanyLadderService> companyLadderServices = companyLadderServiceDao.selectList(new QueryWrapper<CompanyLadderService>().eq("company_tax_id", companyTax.getId()).orderByAsc("start_money"));
            BigDecimal compositeTaxCount = new BigDecimal(0);
            for (PaymentInventory paymentInventory : paymentInventories) {
                BigDecimal realMoney = paymentInventory.getRealMoney();
                compositeTax = this.getCompositeTax(companyLadderServices, realMoney);
                paymentInventory.setCompositeTax(compositeTax.multiply(BigDecimal.valueOf(100)));
                compositeTaxCount = compositeTaxCount.add(compositeTax);
                paymentInventory.setTaskMoney(realMoney);
                if (taxStatus == 0) {
                    paymentInventory.setMerchantPaymentMoney(realMoney.multiply(compositeTax.add(new BigDecimal(1))));
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                } else if (taxStatus == 1) {
                    paymentInventory.setMerchantPaymentMoney(realMoney);
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                    paymentInventory.setRealMoney(realMoney.subtract(realMoney.multiply(compositeTax)));
                } else {
                    paymentInventory.setMerchantPaymentMoney(realMoney.multiply(compositeTax.multiply(merchantTax).add(new BigDecimal("1"))));
                    paymentInventory.setRealMoney(realMoney.subtract(realMoney.multiply(compositeTax.multiply(receviceTax))));
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                }

                countMoney = countMoney.add(paymentInventory.getMerchantPaymentMoney());
                countWorkerMoney = countWorkerMoney.add(paymentInventory.getRealMoney());
                countServiceMoney = countServiceMoney.add(paymentInventory.getServiceMoney());
            }
            //算平均税率
            paymentOrder.setCompositeTax(compositeTaxCount.multiply(new BigDecimal(100).divide(new BigDecimal(paymentInventories.size()))));
        }
        paymentOrder.setRealMoney(countMoney);
        paymentOrder.setServiceMoney(countServiceMoney);
        paymentOrder.setWorkerMoney(countWorkerMoney);
        //生成总包支付订单
        if (paymentOrder.getPaymentOrderStatus() == 5) {
            paymentOrder.setPaymentOrderStatus(0);
        }
        boolean b = this.saveOrUpdate(paymentOrder);
        if (!b) {
            throw new CommonException(300, "订单创建失败！");
        }
        for (PaymentInventory paymentInventory : paymentInventories) {
            paymentInventory.setPaymentOrderId(paymentOrder.getId());
            //生成支付明细
            paymentInventory.setTradeNo(OrderTradeNo.GetRandom() + "PI");
            paymentInventory.setPackageStatus(0);
            paymentInventoryService.saveOrUpdate(paymentInventory);
        }
        return ReturnJson.success("支付订单创建成功！");
    }

    @Override
    public ReturnJson paymentOrderPay(PaymentOrderPayDTO paymentOrderPayDTO) {

        PaymentOrder paymentOrder = getById(paymentOrderPayDTO.getPaymentOrderId());
        if (paymentOrder == null) {
            return ReturnJson.error("总包支付单不存在");
        }

        //检查总包+分包清单总手续费
        BigDecimal serviceCharge = paymentOrder.getServiceMoney();
        if (serviceCharge == null || serviceCharge.compareTo(BigDecimal.ZERO) <= 0) {
            return ReturnJson.error("总包总手续费有误");
        }

        //判断支付密码是否正确
        boolean flag = companyInfoService.verifyPayPwd(paymentOrder.getCompanyId(), paymentOrderPayDTO.getPayPwd());
        if (!flag) {
            return ReturnJson.error("支付密码不正确");
        }

        //判断短信验证码是否正确
        //获取商户主账号手机号
        String loginMobile = merchantDao.queryMainMerchantloginMobile(paymentOrder.getCompanyId());
        String redisCheckCode = redisDao.get(loginMobile);
        if (!(paymentOrderPayDTO.getCheckCode().equals(redisCheckCode))) {
            return ReturnJson.error("短信验证码不正确");
        }

        switch (paymentOrderPayDTO.getPaymentMode()) {

            case 0:

                if (StringUtils.isBlank(paymentOrderPayDTO.getTurnkeyProjectPayment())) {
                    return ReturnJson.error("请上传总包支付回单");
                }

                paymentOrder.setTurnkeyProjectPayment(paymentOrderPayDTO.getTurnkeyProjectPayment());

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

        paymentOrder.setPaymentDate(LocalDateTime.now());
        paymentOrder.setPaymentMode(paymentOrderPayDTO.getPaymentMode());
        paymentOrder.setPaymentOrderStatus(4);
        updateById(paymentOrder);

        return ReturnJson.error("操作成功");
    }

    @Override
    public ReturnJson getPaymentOrderById(String id) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        List<PaymentOrderVO> paymentOrderVOList = new ArrayList<>();
        String[] ids = id.split(",");
        for (int i = 0; i < ids.length; i++) {
            PaymentOrderVO paymentOrder = paymentOrderDao.getPaymentOrderById(ids[i]);
            if (paymentOrder != null) {
                paymentOrderVOList.add(paymentOrder);
                returnJson = new ReturnJson("查询成功", paymentOrder, 200);
            }
        }
        return returnJson;
    }

    @Override
    public ReturnJson getBillingInfo(String id) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        BillingInfoVO billingInfo = paymentOrderDao.getBillingInfo(id);
        if (billingInfo != null) {
            returnJson = new ReturnJson("查询成功", billingInfo, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getDayPaas(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        List<PaymentOrder> list;
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success((List) null);
        }
        list = paymentOrderDao.selectDaypaas(merchantIds);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getWeekPaas(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        List<PaymentOrder> list;
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success((List) null);
        }
        list = paymentOrderDao.selectWeekpaas(merchantIds);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getMonthPaas(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        List<PaymentOrder> list;
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success((List) null);
        }
        list = paymentOrderDao.selectMonthpaas(merchantIds);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getYearPaas(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        List<PaymentOrder> list;
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success((List) null);
        }
        list = paymentOrderDao.selectYearpaas(merchantIds);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getPaymentOrderPaas(PaymentOrderDTO paymentOrderDto, String managersId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(managersId);
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success((List) null);
        }
        String merchantName = paymentOrderDto.getMerchantName();
        String paymentOrderId = paymentOrderDto.getPaymentOrderId();
        String taxId = paymentOrderDto.getTaxId();
        Integer pageSize = paymentOrderDto.getPageSize();
        Integer page = paymentOrderDto.getPageNo();
        String beginDate = paymentOrderDto.getBeginDate();
        String endDate = paymentOrderDto.getEndDate();
        Page<PaymentOrder> paymentOrderPage = new Page<>(page, pageSize);
        IPage<PaymentOrder> paymentOrderIPage = paymentOrderDao.selectManyPaas(paymentOrderPage, merchantIds, merchantName, paymentOrderId, taxId, beginDate, endDate);
        return ReturnJson.success(paymentOrderIPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson paymentOrderAudit(String paymentOrderId, Boolean boolPass, String reasonsForRejection) throws Exception {

        PaymentOrder paymentOrder = getById(paymentOrderId);
        if (paymentOrder == null) {
            return ReturnJson.error("总包+分包支付单不存在");
        }

        if (paymentOrder.getPaymentOrderStatus() != 4) {
            return ReturnJson.error("非支付中状态总包+分包支付单不可审核");
        }

        if (boolPass == null || !boolPass) {

            if (StringUtils.isBlank(reasonsForRejection)) {
                return ReturnJson.error("请输入驳回原因");
            }

            paymentOrder.setPaymentOrderStatus(5);
            paymentOrder.setReasonsForRejection(reasonsForRejection);
            paymentOrderDao.updateById(paymentOrder);
            return ReturnJson.success("驳回成功");

        } else {

            //查询总包清单总手续费
            BigDecimal serviceCharge = paymentOrder.getServiceMoney();
            if (serviceCharge == null || serviceCharge.compareTo(BigDecimal.ZERO) <= 0) {
                return ReturnJson.error("总包+分包总手续费有误");
            }

            TaxUnionpay taxUnionpay;
            CompanyUnionpay companyUnionpay;
            JSONObject jsonObject;
            Boolean boolSuccess;
            JSONObject returnValue;
            String rtnCode;
            switch (paymentOrder.getPaymentMode()) {

                case 0:

                    paymentOrder.setPaymentOrderStatus(2);
                    updateById(paymentOrder);

                    break;

                case 1:

                    return ReturnJson.error("连连支付暂未开通");

                case 2:

                    return ReturnJson.error("网商银行支付暂未开通");

                case 3:

                    //查询服务商盛京银联记录
                    taxUnionpay = taxUnionpayService.queryTaxUnionpay(paymentOrder.getTaxId(), UnionpayBankType.SJBK);
                    if (taxUnionpay == null) {
                        return ReturnJson.error("服务商未开通银联盛京银行支付");
                    }

                    //查询商户是否已开通银联盛京银行子账号
                    companyUnionpay = companyUnionpayService.queryMerchantUnionpayUnionpayBankType(paymentOrder.getCompanyId(), paymentOrder.getTaxId(), UnionpayBankType.SJBK);
                    if (companyUnionpay == null) {
                        return ReturnJson.error("商户未开通服务商的银联盛京银行子账号");
                    }

                    //支付总包手续费
                    jsonObject = UnionpayUtil.AC054(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), paymentOrder.getTradeNo(), companyUnionpay.getUid(), taxUnionpay.getServiceChargeNo(), serviceCharge);
                    if (jsonObject == null) {
                        return ReturnJson.error("支付总包手续费失败");
                    }

                    boolSuccess = jsonObject.getBoolean("success");
                    if (boolSuccess == null || !boolSuccess) {
                        String errMsg = jsonObject.getString("err_msg");
                        return ReturnJson.error("支付总包手续费失败：" + errMsg);
                    }

                    returnValue = jsonObject.getJSONObject("return_value");
                    rtnCode = returnValue.getString("rtn_code");
                    if (!("S00000".equals(rtnCode))) {
                        String errMsg = returnValue.getString("err_msg");
                        return ReturnJson.error("支付总包手续费失败：" + errMsg);
                    }

                    break;

                case 4:

                    //查询服务商平安银联记录
                    taxUnionpay = taxUnionpayService.queryTaxUnionpay(paymentOrder.getTaxId(), UnionpayBankType.PABK);
                    if (taxUnionpay == null) {
                        return ReturnJson.error("服务商未开通银联平安银行支付");
                    }

                    //查询商户是否已开通银联平安银行子账号
                    companyUnionpay = companyUnionpayService.queryMerchantUnionpayUnionpayBankType(paymentOrder.getCompanyId(), paymentOrder.getTaxId(), UnionpayBankType.PABK);
                    if (companyUnionpay == null) {
                        return ReturnJson.error("商户未已开通服务商的银联平安银行子账号");
                    }

                    //支付总包手续费
                    jsonObject = UnionpayUtil.AC054(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), paymentOrder.getTradeNo(), companyUnionpay.getUid(), taxUnionpay.getServiceChargeNo(), serviceCharge);
                    if (jsonObject == null) {
                        return ReturnJson.error("支付总包手续费失败");
                    }

                    boolSuccess = jsonObject.getBoolean("success");
                    if (boolSuccess == null || !boolSuccess) {
                        String errMsg = jsonObject.getString("err_msg");
                        return ReturnJson.error("支付总包手续费失败：" + errMsg);
                    }

                    returnValue = jsonObject.getJSONObject("return_value");
                    rtnCode = returnValue.getString("rtn_code");
                    if (!("S00000".equals(rtnCode))) {
                        String errMsg = returnValue.getString("err_msg");
                        return ReturnJson.error("支付总包手续费失败：" + errMsg);
                    }

                    break;

                case 5:

                    //查询服务商网商银联记录
                    taxUnionpay = taxUnionpayService.queryTaxUnionpay(paymentOrder.getTaxId(), UnionpayBankType.WSBK);
                    if (taxUnionpay == null) {
                        return ReturnJson.error("服务商未开通银联网商银行支付");
                    }

                    //查询商户是否已开通银联网商银行子账号
                    companyUnionpay = companyUnionpayService.queryMerchantUnionpayUnionpayBankType(paymentOrder.getCompanyId(), paymentOrder.getTaxId(), UnionpayBankType.WSBK);
                    if (companyUnionpay == null) {
                        return ReturnJson.error("商户未已开通服务商的银联网商银行子账号");
                    }

                    //支付总包手续费
                    jsonObject = UnionpayUtil.AC054(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), paymentOrder.getTradeNo(), companyUnionpay.getUid(), taxUnionpay.getServiceChargeNo(), serviceCharge);
                    if (jsonObject == null) {
                        return ReturnJson.error("支付总包手续费失败");
                    }

                    boolSuccess = jsonObject.getBoolean("success");
                    if (boolSuccess == null || !boolSuccess) {
                        String errMsg = jsonObject.getString("err_msg");
                        return ReturnJson.error("支付总包手续费失败：" + errMsg);
                    }

                    returnValue = jsonObject.getJSONObject("return_value");
                    rtnCode = returnValue.getString("rtn_code");
                    if (!("S00000".equals(rtnCode))) {
                        String errMsg = returnValue.getString("err_msg");
                        return ReturnJson.error("支付总包手续费失败：" + errMsg);
                    }

                    break;

                case 6:

                    //查询服务商招商银联记录
                    taxUnionpay = taxUnionpayService.queryTaxUnionpay(paymentOrder.getTaxId(), UnionpayBankType.ZSBK);
                    if (taxUnionpay == null) {
                        return ReturnJson.error("服务商未开通银联招商银行支付");
                    }

                    //查询商户是否已开通银联招商银行子账号
                    companyUnionpay = companyUnionpayService.queryMerchantUnionpayUnionpayBankType(paymentOrder.getCompanyId(), paymentOrder.getTaxId(), UnionpayBankType.ZSBK);
                    if (companyUnionpay == null) {
                        return ReturnJson.error("商户未已开通服务商的银联招商银行子账号");
                    }

                    //支付总包手续费
                    jsonObject = UnionpayUtil.AC054(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), paymentOrder.getTradeNo(), companyUnionpay.getUid(), taxUnionpay.getServiceChargeNo(), serviceCharge);
                    if (jsonObject == null) {
                        return ReturnJson.error("支付总包手续费失败");
                    }

                    boolSuccess = jsonObject.getBoolean("success");
                    if (boolSuccess == null || !boolSuccess) {
                        String errMsg = jsonObject.getString("err_msg");
                        return ReturnJson.error("支付总包手续费失败：" + errMsg);
                    }

                    returnValue = jsonObject.getJSONObject("return_value");
                    rtnCode = returnValue.getString("rtn_code");
                    if (!("S00000".equals(rtnCode))) {
                        String errMsg = returnValue.getString("err_msg");
                        return ReturnJson.error("支付总包手续费失败：" + errMsg);
                    }

                    break;

                default:
                    return ReturnJson.error("支付方式不存在");
            }

        }

        return ReturnJson.success("操作成功");
    }

    @Override
    public ReturnJson findMerchantPaas(String managersId) {
        Managers managers = managersDao.selectById(managersId);
        Integer userSign = managers.getUserSign();
        List<CompanyInfo> companyInfos;
        //管理人员为代理商
        if (userSign == 1) {
            companyInfos = companyInfoService.list(new QueryWrapper<CompanyInfo>().eq("agent_id", managers.getId()));
        } else if (userSign == 2) {
            //管理人员为业务员
            companyInfos = companyInfoService.list(new QueryWrapper<CompanyInfo>().eq("sales_man_id", managers.getId()));
        } else {
            companyInfos = companyInfoService.list(new QueryWrapper<>());
        }
        List<CompanyBriefVO> companyBriefVOS = new ArrayList<>();
        companyInfos.forEach(companyInfo -> {
            CompanyBriefVO companyBriefVO = new CompanyBriefVO();
            BeanUtils.copyProperties(companyInfo, companyBriefVO);
            companyBriefVOS.add(companyBriefVO);
        });
        return ReturnJson.success(companyBriefVOS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson subpackagePay(String paymentOrderId, String subpackagePayment) throws Exception {

        PaymentOrder paymentOrder = getById(paymentOrderId);
        if (paymentOrder == null) {
            return ReturnJson.error("总包+分包支付单不存在");
        }

        if (paymentOrder.getPaymentOrderStatus() != 2) {
            return ReturnJson.error("非已支付状态总包+分包支付单不可分包支付");
        }

        TaxUnionpay taxUnionpay;
        CompanyUnionpay companyUnionpay;
        JSONObject jsonObject;
        Boolean boolSuccess;
        JSONObject returnValue;
        String rtnCode;
        List<PaymentInventory> paymentInventoryList;
        StringBuilder failMessage = new StringBuilder();
        switch (paymentOrder.getPaymentMode()) {

            case 0:

                if (StringUtils.isBlank(subpackagePayment)) {
                    return ReturnJson.error("请上传分包支付回单");
                }

                paymentOrder.setPaymentOrderStatus(6);
                paymentOrder.setSubpackagePayment(subpackagePayment);
                updateById(paymentOrder);

                //修改分包状态为支付成功
                List<PaymentInventory> list = paymentInventoryDao.selectList(new QueryWrapper<PaymentInventory>().eq("payment_order_id", paymentOrderId));
                for (int i = 0; i < list.size(); i++) {
                    PaymentInventory paymentInventory = list.get(i);
                    paymentInventory.setPaymentStatus(1);
                    paymentInventoryDao.updateById(paymentInventory);
                }

                break;

            case 1:

                return ReturnJson.error("连连支付暂未开通");

            case 2:

                return ReturnJson.error("网商银行支付暂未开通");

            case 3:

                //查询服务商盛京银联记录
                taxUnionpay = taxUnionpayService.queryTaxUnionpay(paymentOrder.getTaxId(), UnionpayBankType.SJBK);
                if (taxUnionpay == null) {
                    return ReturnJson.error("服务商未开通银联盛京银行支付");
                }

                //查询商户是否已开通银联盛京银行子账号
                companyUnionpay = companyUnionpayService.queryMerchantUnionpayUnionpayBankType(paymentOrder.getCompanyId(), paymentOrder.getTaxId(), UnionpayBankType.SJBK);
                if (companyUnionpay == null) {
                    return ReturnJson.error("商户未开通服务商的银联盛京银行子账号");
                }

                //查询所有未支付成功的分包
                paymentInventoryList = paymentInventoryService.queryPaymentInventoryToPayList(paymentOrderId);
                if (paymentInventoryList != null && paymentInventoryList.size() > 0) {
                    for (PaymentInventory paymentInventory : paymentInventoryList) {

                        //支付总包手续费
                        jsonObject = UnionpayUtil.AC041(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), paymentInventory.getTradeNo(), companyUnionpay.getUid(), paymentInventory.getRealMoney(), paymentInventory.getWorkerName(), paymentInventory.getBankCode());
                        if (jsonObject == null) {
                            failMessage.append("订单号为" + paymentInventory.getTradeNo() + "的分包支付失败");
                            continue;
                        }

                        boolSuccess = jsonObject.getBoolean("success");
                        if (boolSuccess == null || !boolSuccess) {
                            String errMsg = jsonObject.getString("err_msg");
                            failMessage.append("订单号为" + paymentInventory.getTradeNo() + "的分包支付失败：" + errMsg);
                            continue;
                        }

                        returnValue = jsonObject.getJSONObject("return_value");
                        rtnCode = returnValue.getString("rtn_code");
                        if (!("S00000".equals(rtnCode))) {
                            String errMsg = returnValue.getString("err_msg");
                            failMessage.append("订单号为" + paymentInventory.getTradeNo() + "的分包支付失败：" + errMsg);
                        }

                    }
                }

                break;

            case 4:

                //查询服务商平安银联记录
                taxUnionpay = taxUnionpayService.queryTaxUnionpay(paymentOrder.getTaxId(), UnionpayBankType.PABK);
                if (taxUnionpay == null) {
                    return ReturnJson.error("服务商未开通银联平安银行支付");
                }

                //查询商户是否已开通银联平安银行子账号
                companyUnionpay = companyUnionpayService.queryMerchantUnionpayUnionpayBankType(paymentOrder.getCompanyId(), paymentOrder.getTaxId(), UnionpayBankType.PABK);
                if (companyUnionpay == null) {
                    return ReturnJson.error("商户未已开通服务商的银联平安银行子账号");
                }

                //查询所有未支付成功的分包
                paymentInventoryList = paymentInventoryService.queryPaymentInventoryToPayList(paymentOrderId);
                if (paymentInventoryList != null && paymentInventoryList.size() > 0) {
                    for (PaymentInventory paymentInventory : paymentInventoryList) {

                        //支付总包手续费
                        jsonObject = UnionpayUtil.AC041(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), paymentInventory.getTradeNo(), companyUnionpay.getUid(), paymentInventory.getRealMoney(), paymentInventory.getWorkerName(), paymentInventory.getBankCode());
                        if (jsonObject == null) {
                            failMessage.append("订单号为" + paymentInventory.getTradeNo() + "的分包支付失败");
                            continue;
                        }

                        boolSuccess = jsonObject.getBoolean("success");
                        if (boolSuccess == null || !boolSuccess) {
                            String errMsg = jsonObject.getString("err_msg");
                            failMessage.append("订单号为" + paymentInventory.getTradeNo() + "的分包支付失败：" + errMsg);
                            continue;
                        }

                        returnValue = jsonObject.getJSONObject("return_value");
                        rtnCode = returnValue.getString("rtn_code");
                        if (!("S00000".equals(rtnCode))) {
                            String errMsg = returnValue.getString("err_msg");
                            failMessage.append("订单号为" + paymentInventory.getTradeNo() + "的分包支付失败：" + errMsg);
                        }

                    }
                }

                break;

            case 5:

                //查询服务商网商银联记录
                taxUnionpay = taxUnionpayService.queryTaxUnionpay(paymentOrder.getTaxId(), UnionpayBankType.WSBK);
                if (taxUnionpay == null) {
                    return ReturnJson.error("服务商未开通银联网商银行支付");
                }

                //查询商户是否已开通银联网商银行子账号
                companyUnionpay = companyUnionpayService.queryMerchantUnionpayUnionpayBankType(paymentOrder.getCompanyId(), paymentOrder.getTaxId(), UnionpayBankType.WSBK);
                if (companyUnionpay == null) {
                    return ReturnJson.error("商户未已开通服务商的银联网商银行子账号");
                }

                //查询所有未支付成功的分包
                paymentInventoryList = paymentInventoryService.queryPaymentInventoryToPayList(paymentOrderId);
                if (paymentInventoryList != null && paymentInventoryList.size() > 0) {
                    for (PaymentInventory paymentInventory : paymentInventoryList) {

                        //支付总包手续费
                        jsonObject = UnionpayUtil.AC041(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), paymentInventory.getTradeNo(), companyUnionpay.getUid(), paymentInventory.getRealMoney(), paymentInventory.getWorkerName(), paymentInventory.getBankCode());
                        if (jsonObject == null) {
                            failMessage.append("订单号为" + paymentInventory.getTradeNo() + "的分包支付失败");
                            continue;
                        }

                        boolSuccess = jsonObject.getBoolean("success");
                        if (boolSuccess == null || !boolSuccess) {
                            String errMsg = jsonObject.getString("err_msg");
                            failMessage.append("订单号为" + paymentInventory.getTradeNo() + "的分包支付失败：" + errMsg);
                            continue;
                        }

                        returnValue = jsonObject.getJSONObject("return_value");
                        rtnCode = returnValue.getString("rtn_code");
                        if (!("S00000".equals(rtnCode))) {
                            String errMsg = returnValue.getString("err_msg");
                            failMessage.append("订单号为" + paymentInventory.getTradeNo() + "的分包支付失败：" + errMsg);
                        }

                    }
                }

                break;

            case 6:

                //查询服务商招商银联记录
                taxUnionpay = taxUnionpayService.queryTaxUnionpay(paymentOrder.getTaxId(), UnionpayBankType.ZSBK);
                if (taxUnionpay == null) {
                    return ReturnJson.error("服务商未开通银联招商银行支付");
                }

                //查询商户是否已开通银联招商银行子账号
                companyUnionpay = companyUnionpayService.queryMerchantUnionpayUnionpayBankType(paymentOrder.getCompanyId(), paymentOrder.getTaxId(), UnionpayBankType.ZSBK);
                if (companyUnionpay == null) {
                    return ReturnJson.error("商户未已开通服务商的银联招商银行子账号");
                }

                //查询所有未支付成功的分包
                paymentInventoryList = paymentInventoryService.queryPaymentInventoryToPayList(paymentOrderId);
                if (paymentInventoryList != null && paymentInventoryList.size() > 0) {
                    for (PaymentInventory paymentInventory : paymentInventoryList) {

                        //支付总包手续费
                        jsonObject = UnionpayUtil.AC041(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), paymentInventory.getTradeNo(), companyUnionpay.getUid(), paymentInventory.getRealMoney(), paymentInventory.getWorkerName(), paymentInventory.getBankCode());
                        if (jsonObject == null) {
                            failMessage.append("订单号为" + paymentInventory.getTradeNo() + "的分包支付失败");
                            continue;
                        }

                        boolSuccess = jsonObject.getBoolean("success");
                        if (boolSuccess == null || !boolSuccess) {
                            String errMsg = jsonObject.getString("err_msg");
                            failMessage.append("订单号为" + paymentInventory.getTradeNo() + "的分包支付失败：" + errMsg);
                            continue;
                        }

                        returnValue = jsonObject.getJSONObject("return_value");
                        rtnCode = returnValue.getString("rtn_code");
                        if (!("S00000".equals(rtnCode))) {
                            String errMsg = returnValue.getString("err_msg");
                            failMessage.append("订单号为" + paymentInventory.getTradeNo() + "的分包支付失败：" + errMsg);
                        }

                    }
                }

                break;

            default:
                return ReturnJson.error("支付方式不存在");
        }

        if (StringUtils.isNotBlank(failMessage)) {
            return ReturnJson.success(failMessage);
        } else {
            return ReturnJson.success("操作成功");
        }

    }

    @Override
    public ReturnJson getDaypaa(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success((List) null);
        }
        TodayVO todayVO = paymentOrderDao.selectDaypaa(merchantIds);
        return ReturnJson.success(todayVO);
    }

    @Override
    public ReturnJson getWeekPaa(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success((List) null);
        }
        WeekTradeVO weekTradeVO = paymentOrderDao.selectWeekpaa(merchantIds);
        return ReturnJson.success(weekTradeVO);
    }

    @Override
    public ReturnJson getMonthPaa(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        List<PaymentOrder> list;
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success((List) null);
        }
        MonthTradeVO monthTradeVO = paymentOrderDao.selectMonthpaa(merchantIds);
        return ReturnJson.success(monthTradeVO);
    }

    @Override
    public ReturnJson getYearPaa(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success((List) null);
        }
        YearTradeVO yearTradeVO = paymentOrderDao.selectYearpaa(merchantIds);
        return ReturnJson.success(yearTradeVO);
    }

    @Override
    public ReturnJson associatedTasks(String merchantId, AssociatedTasksDTO associatedTasksDto) {
        Page page = new Page(associatedTasksDto.getPageNo(), associatedTasksDto.getPageSize());
        IPage<AssociatedTasksVO> voiPage = taskDao.getAssociatedTask(page, merchantId, associatedTasksDto.getCooperateMode());
        return ReturnJson.success(voiPage);
    }

    @Override
    public ReturnJson gradientPrice(String merchantId, String taxId, Integer packageStatus) {
        CompanyInfo companyInfo = companyInfoService.getById(merchantId);
        if (companyInfo == null) {
            Merchant merchant = merchantDao.selectById(merchantId);
            companyInfo = companyInfoService.getById(merchant.getCompanyId());
        }
        if (companyInfo == null) {
            return ReturnJson.error("商户信息错误请重新选择");
        }
        CompanyTax companyTax = companyTaxDao.selectOne(new QueryWrapper<CompanyTax>()
                .eq("company_id", companyInfo.getId())
                .eq("tax_id", taxId)
                .eq("package_status", packageStatus));
        if (companyTax == null) {
            return ReturnJson.error("此商户还未和此服务商取得合作！");
        }
        if (companyTax.getChargeStatus() == 0) {
            Double serviceCharge = companyTaxDao.getCompanyTax(companyInfo.getId(), taxId, packageStatus);
            return ReturnJson.success(serviceCharge);
        } else {
            List<CompanyTaxMoneyVO> list = companyTaxDao.getCompanyTaxMoney(companyInfo.getId(), taxId, packageStatus);
            return ReturnJson.success(list);
        }
    }

    @Override
    public ReturnJson associatedTask(String merchantId, AssociatedTasksDTO associatedTasksDto) {
        Page page = new Page(associatedTasksDto.getPageNo(), associatedTasksDto.getPageSize());
        IPage<AssociatedTasksVO> voiPage = taskDao.getAssociatedTasks(page, merchantId, associatedTasksDto.getCooperateMode());
        return ReturnJson.success(voiPage);
    }

    @Override
    public PaymentOrder queryPaymentOrderByTradeNo(String tradeNo) {

        QueryWrapper<PaymentOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentOrder::getTradeNo, tradeNo);

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
        BigDecimal compositeTax = null;
        for (CompanyLadderService companyLadderService : companyLadderServices) {
            BigDecimal startMoney = companyLadderService.getStartMoney();
            if (realMoney.compareTo(startMoney) >= 0) {
                compositeTax = companyLadderService.getServiceCharge().divide(new BigDecimal(100));
            }
        }
        return compositeTax;
    }

}
