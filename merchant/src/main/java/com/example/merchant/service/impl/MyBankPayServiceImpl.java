package com.example.merchant.service.impl;


import com.alibaba.druid.support.json.JSONUtils;
import com.example.common.util.JsonUtils;
import com.example.common.util.ReturnJson;
import com.example.common.mybank.entity.Enterprise;
import com.example.common.mybank.entity.TradeTransfer;
import com.example.merchant.service.MyBankPayService;
import com.example.merchant.service.MyBankService;
import com.example.merchant.util.RealnameVerifyUtil;
import com.example.mybatis.entity.CompanyInfo;
import com.example.mybatis.entity.PaymentOrder;
import com.example.mybatis.entity.PaymentOrderMany;
import com.example.mybatis.entity.Tax;
import com.example.mybatis.mapper.CompanyInfoDao;
import com.example.mybatis.mapper.PaymentOrderDao;
import com.example.mybatis.mapper.PaymentOrderManyDao;
import com.example.mybatis.mapper.TaxDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jwei
 */
@Slf4j
@Service
public class MyBankPayServiceImpl implements MyBankPayService {

    @Autowired
    private PaymentOrderDao paymentOrderDao;

    @Autowired
    private PaymentOrderManyDao paymentOrderManyDao;

    @Autowired
    private MyBankService myBankService;

    @Autowired
    private CompanyInfoDao companyInfoDao;

    @Autowired
    private TaxDao taxDao;


    @Override
    public ReturnJson myBankPayByPayId(String paymentOrderId) throws Exception {
        PaymentOrder paymentOrder = paymentOrderDao.selectById(paymentOrderId);
        if (paymentOrder == null) {
            log.error("您输入的总订单不存在！");
            return ReturnJson.error("您输入的订单不存在！");
        }

        if (paymentOrder.getPaymentOrderStatus() > 1) {
            log.error("请勿重复支付");
            return ReturnJson.error("请勿重复支付");
        }
        //查询对应商户并判断商户是否注册网商银行账号没有就创建
        CompanyInfo companyInfo = companyInfoDao.selectById(paymentOrder.getCompanyId());
        if (companyInfo.getMemberId() == null) {
            Enterprise enterprise = new Enterprise();
            enterprise.setUid(companyInfo.getId());
            enterprise.setEnterprise_name(companyInfo.getCompanyName());
            enterprise.setLegal_person_certificate_no(companyInfo.getCompanyManIdCard());
            enterprise.setLegal_person_certificate_type("ID_CARD");
            Map<String, Object> result = myBankService.enterpriseRegister(enterprise);
            if ("F".equals(result.get("is_success"))) {
                log.error("商户注册企业会员失败" + JSONUtils.toJSONString(result));
                return ReturnJson.error(300, "商户注册企业会员失败", JSONUtils.toJSONString(result));
            } else {
                companyInfo.setMemberId((String) result.get("member_id"));
                companyInfo.setSubAccountNo((String) result.get("sub_account_no"));
                companyInfoDao.updateById(companyInfo);
            }
        }
        //查询余额
        Map<String, Object> balancebefore = myBankService.accountBalance(companyInfo.getId(), "");
        BigDecimal balance_amount_before = BigDecimal.ZERO;
        List bInfoList_before = (List) balancebefore.get("account_list");
        if (!"F".equals(balancebefore.get("is_success").toString())) {
            if (bInfoList_before.size() > 0) {
                Map<String, Object> bInfo = (Map) bInfoList_before.get(0);
                balance_amount_before = new BigDecimal(bInfo.get("available_balance").toString());
            }
            //判断余额是否足够支付本次订单金额
            if (balance_amount_before.compareTo(paymentOrder.getRealMoney()) == -1) {
                log.error("余额不足,请先充值");
                return new ReturnJson("余额不足,请先充值", 300);
            }
        }
        Tax tax = taxDao.selectById(paymentOrder.getTaxId());
        if (tax.getMemberId() == null) {
            Enterprise enterprise = new Enterprise();
            enterprise.setUid(tax.getId());
            enterprise.setEnterprise_name(tax.getTaxName());
            Map<String, Object> result = myBankService.enterpriseRegister(enterprise);
            if ("F".equals(result.get("is_success"))) {
                log.error("服务商注册企业会员失败");
                return ReturnJson.error(300, "服务商注册企业会员失败", JSONUtils.toJSONString(result));
            } else {
                tax.setMemberId((String) result.get("member_id"));
                tax.setSubAccountNo((String) result.get("sub_account_no"));
                taxDao.updateById(tax);
            }
        }
        TradeTransfer transfer = new TradeTransfer();
        transfer.setOuter_trade_no(paymentOrder.getId());
        transfer.setFundin_uid(tax.getId());
        transfer.setFundin_account_type("BASIC");
        transfer.setFundout_uid(companyInfo.getId());
        transfer.setFundout_account_type("BASIC");
        //所有该付的钱先转到平台
        transfer.setTransfer_amount(paymentOrder.getRealMoney());
        Map<String, Object> mybankresult = myBankService.tradeTransfer(transfer);
        if ("F".equals(mybankresult.get("is_success"))) {
            log.error("审核失败" + mybankresult.get("error_message").toString());
            return ReturnJson.error(300, "审核失败", mybankresult.get("error_message").toString());
        } else {
            paymentOrder.setPaymentOrderStatus(1);
            paymentOrderDao.updateById(paymentOrder);
            return ReturnJson.success("正在支付中，请稍后！");
        }
    }

    @Override
    public void myBankPayNotifyUrl(HttpServletRequest request) {
        String requestBody = null;
        try {
            requestBody = RealnameVerifyUtil.getRequestBody(request, "UTF-8");
            log.info(requestBody);
            HashMap<String, String> result = JsonUtils.jsonToPojo(requestBody, new HashMap<String, String>().getClass());
            log.info(result.toString());
            String panymentOrderId = result.get("outer_trade_no");
            PaymentOrder paymentOrder = paymentOrderDao.selectById(panymentOrderId);
            if (paymentOrder == null) {
                log.error("支付订单为空！");
                return;
            }
            if (("T".equals(result.get("is_success")))) {
                paymentOrder.setPaymentOrderStatus(2);
                paymentOrder.setPaymentDate(LocalDateTime.now());
                paymentOrder.setPaymentMode(1);
                paymentOrderDao.updateById(paymentOrder);
            } else {
                //商户付款失败
                paymentOrder.setPaymentOrderStatus(-1);
                paymentOrderDao.updateById(paymentOrder);
                log.error("商户付款失败:" + result.toString());
            }
        } catch (Exception e) {
            log.info("出现不可预估操作！" + e.getMessage());
        }
    }

    @Override
    public ReturnJson myBankPayManyByPayId(String paymentOrderManyId) throws Exception {
        PaymentOrderMany paymentOrderMany = paymentOrderManyDao.selectById(paymentOrderManyId);
        if (paymentOrderMany == null) {
            log.error("您输入的众包订单不存在！");
            return ReturnJson.error("您输入的订单不存在！");
        }

        if (paymentOrderMany.getPaymentOrderStatus() > 1) {
            log.error("请勿重复支付");
            return ReturnJson.error("请勿重复支付");
        }
        //查询对应商户并判断商户是否注册网商银行账号没有就创建
        CompanyInfo companyInfo = companyInfoDao.selectById(paymentOrderMany.getCompanyId());
        if (companyInfo.getMemberId() == null) {
            Enterprise enterprise = new Enterprise();
            enterprise.setUid(companyInfo.getId());
            enterprise.setEnterprise_name(companyInfo.getCompanyName());
            enterprise.setLegal_person_certificate_no(companyInfo.getCompanyManIdCard());
            enterprise.setLegal_person_certificate_type("ID_CARD");
            Map<String, Object> result = myBankService.enterpriseRegister(enterprise);
            if ("F".equals(result.get("is_success"))) {
                log.error("商户注册企业会员失败:meg{}", JSONUtils.toJSONString(result));
                return ReturnJson.error(300, "商户注册企业会员失败", JSONUtils.toJSONString(result));
            } else {
                companyInfo.setMemberId((String) result.get("member_id"));
                companyInfo.setSubAccountNo((String) result.get("sub_account_no"));
                companyInfoDao.updateById(companyInfo);
            }
        }
        //查询余额
        Map<String, Object> balancebefore = myBankService.accountBalance(companyInfo.getId(), "");
        BigDecimal balance_amount_before = BigDecimal.ZERO;
        List bInfoList_before = (List) balancebefore.get("account_list");
        if (!"F".equals(balancebefore.get("is_success").toString())) {
            if (bInfoList_before.size() > 0) {
                Map<String, Object> bInfo = (Map) bInfoList_before.get(0);
                balance_amount_before = new BigDecimal(bInfo.get("available_balance").toString());
            }
            //判断余额是否足够支付本次订单金额
            if (balance_amount_before.compareTo(paymentOrderMany.getRealMoney()) == -1) {
                log.error("余额不足,请先充值{}", balance_amount_before);
                return new ReturnJson("余额不足,请先充值", 300);
            }
        }
        Tax tax = taxDao.selectById(paymentOrderMany.getTaxId());
        if (tax.getMemberId() == null) {
            Enterprise enterprise = new Enterprise();
            enterprise.setUid(tax.getId());
            enterprise.setEnterprise_name(tax.getTaxName());
            Map<String, Object> result = myBankService.enterpriseRegister(enterprise);
            if ("F".equals(result.get("is_success"))) {
                log.error("服务商注册企业会员失败{}", result.get("error_code"));
                return ReturnJson.error(300, "服务商注册企业会员失败", JSONUtils.toJSONString(result));
            } else {
                tax.setMemberId((String) result.get("member_id"));
                tax.setSubAccountNo((String) result.get("sub_account_no"));
                taxDao.updateById(tax);
            }
        }
        TradeTransfer transfer = new TradeTransfer();
        transfer.setOuter_trade_no(paymentOrderMany.getId());
        transfer.setFundin_uid(tax.getId());
        transfer.setFundin_account_type("BASIC");
        transfer.setFundout_uid(companyInfo.getId());
        transfer.setFundout_account_type("BASIC");
        //所有该付的钱先转到平台
        transfer.setTransfer_amount(paymentOrderMany.getRealMoney());
        Map<String, Object> mybankresult = myBankService.tradeTransfer(transfer);
        if ("F".equals(mybankresult.get("is_success"))) {
            log.error("审核失败" + mybankresult.get("error_message").toString());
            return ReturnJson.error(300, "审核失败", mybankresult.get("error_message").toString());
        } else {
            paymentOrderMany.setPaymentOrderStatus(1);
            paymentOrderManyDao.updateById(paymentOrderMany);
            return ReturnJson.success("正在支付中，请稍后！");
        }
    }

    @Override
    public void myBankPayManyNotifyUrl(HttpServletRequest request) {
        String requestBody = null;
        try {
            requestBody = RealnameVerifyUtil.getRequestBody(request, "UTF-8");
            log.info(requestBody);
            HashMap<String, String> result = JsonUtils.jsonToPojo(requestBody, new HashMap<String, String>().getClass());
            log.info(result.toString());
            String panymentOrderManyId = result.get("outer_trade_no");
            PaymentOrderMany paymentOrderMany = paymentOrderManyDao.selectById(panymentOrderManyId);
            if (paymentOrderMany == null) {
                log.error("支付订单为空！");
                return;
            }
            if (("T".equals(result.get("is_success")))) {
                paymentOrderMany.setPaymentOrderStatus(2);
                paymentOrderMany.setPaymentDate(LocalDateTime.now());
                paymentOrderMany.setPaymentMode(1);
                paymentOrderManyDao.updateById(paymentOrderMany);
            } else {
                //商户付款失败
                paymentOrderMany.setPaymentOrderStatus(-1);
                paymentOrderManyDao.updateById(paymentOrderMany);
                log.error("商户付款失败:{}", result.toString());
            }
        } catch (Exception e) {
            log.info("出现不可预估操作以进行回退！" + e.getMessage());
        }
    }

    @Override
        public ReturnJson enterpriseRegister(Enterprise enterprise, String userId) throws Exception {
        enterprise.setUid(userId);
        CompanyInfo companyInfo = companyInfoDao.selectById(userId);
        if (companyInfo == null) {
            log.info("不存在该商户{}", userId);
            return ReturnJson.error("不存在该商户");
        } else {
            Map<String, Object> map = myBankService.enterpriseRegister(enterprise);
            if (("F").equals(map.get("is_success"))) {
                log.info("商户注册企业会员失败{}", map.get("error_message"));
                return ReturnJson.error("商户注册企业会员失败");
            } else {
                companyInfo.setMemberId((String) map.get("member_id"));
                companyInfo.setSubAccountNo((String) map.get("sub_account_no"));
                companyInfoDao.updateById(companyInfo);
                return ReturnJson.success("注册成功");
            }
        }
    }

    @Override
    public ReturnJson enterpriseInfoModify(Enterprise enterprise, String userId) throws Exception {
        enterprise.setUid(userId);
        CompanyInfo companyInfo = companyInfoDao.selectById(userId);
        if (companyInfo == null) {
            log.info("不存在该商户{}", userId);
            return ReturnJson.error("不存在该商户");
        } else {
            Map<String, Object> map = myBankService.enterpriseInfoModify(enterprise);
            if (("F").equals(map.get("is_success"))) {
                log.info("商户修改企业会员失败{}", map.get("error_message"));
                return ReturnJson.error("商户修改企业会员失败");
            } else {
                return ReturnJson.success("修改成功");
            }
        }
    }

}