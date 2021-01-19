package com.example.merchant.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.common.enums.*;
import com.example.common.util.UnionpayUtil;
import com.example.merchant.service.*;
import com.example.mybatis.entity.*;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 异步回调相关接口 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Slf4j
@Service
public class NotifyServiceImpl implements NotifyService {

    @Resource
    private TaxUnionpayService taxUnionpayService;

    @Resource
    private PaymentOrderService paymentOrderService;

    @Resource
    private PaymentOrderManyService paymentOrderManyService;

    @Resource
    private PaymentInventoryService paymentInventoryService;

    @Resource
    private PaymentHistoryService paymentHistoryService;

    @Resource
    private CompanyUnionpayService companyUnionpayService;

    @Override
    public String depositNotice(HttpServletRequest request) {

        Map<String, Object> parameters = WebUtils.getParametersStartingWith(request, "");
        log.info("银联入金回调接收:" + JSON.toJSONString(parameters));
        //转化为json格式
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(parameters));
        if (jsonObject == null) {
            log.error("银联入金回调接收不到参数");
        }

        //获取商户号, 查询相应的服务商银联
        String merchNo = jsonObject.getString("merchNo");
        TaxUnionpay taxUnionpay = taxUnionpayService.queryTaxUnionpayByMerchNo(merchNo);
        if (taxUnionpay == null) {
            log.error("对应商户号的服务商银联不存在");
            return "fail";
        }

        //子帐户帐号
        String acctNo = jsonObject.getString("acctNo");
        CompanyUnionpay companyUnionpay = companyUnionpayService.queryMerchantUnionpay(acctNo);
        if (companyUnionpay == null) {
            log.error("对应子账户账号的商户银联子账号不存在");
            return "fail";
        }

        PaymentMethod paymentMethod;
        switch (taxUnionpay.getUnionpayBankType()) {

            case SJBK:

                paymentMethod = PaymentMethod.UNIONSJBK;
                break;

            case PABK:

                paymentMethod = PaymentMethod.UNIONPABK;
                break;

            case WSBK:

                paymentMethod = PaymentMethod.UNIONWSBK;
                break;

            case ZSBK:

                paymentMethod = PaymentMethod.UNIONZSBK;
                break;

            default:
                log.error("对应商户号的服务商银联类型不存在");
                return "fail";
        }

        //获取其他参数
        //通知类型
        String notifyType = jsonObject.getString("notifyType");
        //入金交易凭证号
        String inacctBillId = jsonObject.getString("inacctBillId");
        //入金金额
        BigDecimal amount = jsonObject.getBigDecimal("amount");
        //来款账户帐号
        String rcvAcctNo = jsonObject.getString("rcvAcctNo");
        //来款户名
        String rcvAcctName = jsonObject.getString("rcvAcctName");
        //来款联行号
        String rcvBankCode = jsonObject.getString("rcvBankCode");
        //入金交易类型（01:绑卡帐户来/款,02:白名单帐户来款,03:第三方来款）bizType=03 时，入金转入清分子账户
        String bizType = jsonObject.getString("bizType");
        //到账时间
        String rcvTime = jsonObject.getString("rcvTime");
        //来款备注
        String remarks = jsonObject.getString("remarks");
        //参数的签名串
        String sign = jsonObject.getString("sign");
        //加签方式（本字段不参与加签）
        String signType = jsonObject.getString("signType");

        //验签处理
        Map<String, String> params = Maps.newHashMap();
        params.put("notifyType", notifyType);
        params.put("merchNo", merchNo);
        params.put("acctNo", acctNo);
        params.put("inacctBillId", inacctBillId);
        params.put("amount", String.valueOf(amount));
        params.put("rcvAcctNo", rcvAcctNo);
        params.put("rcvAcctName", rcvAcctName);
        params.put("rcvBankCode", rcvBankCode);
        params.put("bizType", bizType);
        params.put("rcvTime", rcvTime);
        params.put("remarks", remarks);
        params.put("sign", sign);

        log.info("验签参数：{}", params);
        boolean checkPass = UnionpayUtil.checkSign(params, taxUnionpay.getPfmpubkey(), "UTF-8", signType);
        if (!checkPass) {
            log.error("验签失败");
            return "fail";
        }

        // 业务逻辑处理 ****************************
        //添加成功的交易记录
        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setTradeStatus(TradeStatus.SUCCESS);
        paymentHistory.setOuterTradeNo(inacctBillId);
        paymentHistory.setOrderType(OrderType.RECHARGE);
        paymentHistory.setPaymentMethod(paymentMethod);
        paymentHistory.setTradeObject(TradeObject.COMPANY);
        paymentHistory.setTradeObjectId(companyUnionpay.getCompanyId());
        paymentHistory.setAmount(amount);
        paymentHistoryService.save(paymentHistory);

        return "success";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String txResult(HttpServletRequest request) {

        Map<String, Object> parameters = WebUtils.getParametersStartingWith(request, "");
        log.info("银联提现到卡回调接收:" + JSON.toJSONString(parameters));
        //转化为json格式
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(parameters));
        if (jsonObject == null) {
            log.error("银联提现到卡回调接收不到参数");
        }

        //获取商户号, 查询相应的服务商银联
        String merchNo = jsonObject.getString("merchNo");
        TaxUnionpay taxUnionpay = taxUnionpayService.queryTaxUnionpayByMerchNo(merchNo);
        if (taxUnionpay == null) {
            log.error("对应商户号的服务商银联不存在");
            return "fail";
        }

        //子帐户帐号
        String acctNo = jsonObject.getString("acctNo");
        CompanyUnionpay companyUnionpay = companyUnionpayService.queryMerchantUnionpay(acctNo);
        if (companyUnionpay == null) {
            log.error("对应子账户账号的商户银联子账号不存在");
            return "fail";
        }

        PaymentMethod paymentMethod;
        switch (taxUnionpay.getUnionpayBankType()) {

            case SJBK:

                paymentMethod = PaymentMethod.UNIONSJBK;
                break;

            case PABK:

                paymentMethod = PaymentMethod.UNIONPABK;
                break;

            case WSBK:

                paymentMethod = PaymentMethod.UNIONWSBK;
                break;

            case ZSBK:

                paymentMethod = PaymentMethod.UNIONZSBK;
                break;

            default:
                log.error("对应商户号的服务商银联类型不存在");
                return "fail";
        }

        //获取其他参数
        //通知类型
        String notifyType = jsonObject.getString("notifyType");
        //提现出款交易凭证 ID
        String dchBillId = jsonObject.getString("dchBillId");
        //合作方业务平台订单号（外部请求流水号）
        String outerTradeNo = jsonObject.getString("outer_trade_no");
        //交易状态（91:交易成功,99:交易失败）
        String status = jsonObject.getString("status");
        //失败原因
        String errDesc = jsonObject.getString("errDesc");
        //交易时间
        String txTime = jsonObject.getString("txTime");
        //参数的签名串
        String sign = jsonObject.getString("sign");
        //加签方式（本字段不参与加签）
        String signType = jsonObject.getString("signType");

        //验签处理
        Map<String, String> params = Maps.newHashMap();
        params.put("notifyType", notifyType);
        params.put("merchNo", merchNo);
        params.put("acctNo", acctNo);
        params.put("dchBillId", dchBillId);
        params.put("outerTradeNo", outerTradeNo);
        params.put("status", status);
        params.put("errDesc", errDesc);
        params.put("txTime", txTime);
        params.put("sign", sign);

        log.info("验签参数：{}", params);
        boolean checkPass = UnionpayUtil.checkSign(params, taxUnionpay.getPfmpubkey(), "UTF-8", signType);
        if (!checkPass) {
            log.error("验签失败");
            return "fail";
        }

        // 业务逻辑处理 ****************************
        //拆分订单号，获取银联交易类型
        String tradeType = outerTradeNo.replaceAll("\\d+", "");
        TradeNoType tradeNoType = Enum.valueOf(TradeNoType.class, tradeType);
        switch (tradeNoType) {

            case PI:

                //查询分包是否存在
                PaymentInventory paymentInventory = paymentInventoryService.queryPaymentInventoryByTradeNo(outerTradeNo);
                if (paymentInventory == null) {
                    log.error("订单号为{}的分包支付订单不存在", outerTradeNo);
                    return "fail";
                }

                if (paymentInventory.getPaymentStatus() == 1) {
                    log.error("订单号为{}的分包支付订单状态已为支付成功", outerTradeNo);
                    return "fail";
                }

                //判断交易结果
                //创建交易记录
                PaymentHistory paymentHistory = new PaymentHistory();
                if ("91".equals(status)) {
                    //修改分包支付状态为成功
                    paymentInventory.setPaymentStatus(1);
                    //添加成功的交易记录
                    paymentHistory.setTradeStatus(TradeStatus.SUCCESS);
                } else {
                    log.error("订单号为{}的分包支付订单支付失败：{}", outerTradeNo, errDesc);
                    //修改分包支付状态为失败
                    paymentInventory.setPaymentStatus(-1);
                    paymentInventory.setTradeFailReason(errDesc);
                    //添加失败的交易记录
                    paymentHistory.setTradeStatus(TradeStatus.FAIL);
                }
                paymentInventoryService.updateById(paymentInventory);

                paymentHistory.setTradeNo(paymentInventory.getTradeNo());
                paymentHistory.setOuterTradeNo(dchBillId);
                paymentHistory.setOrderType(OrderType.INVENTORY);
                paymentHistory.setPaymentMethod(paymentMethod);
                paymentHistory.setTradeObject(TradeObject.COMPANY);
                paymentHistory.setTradeObjectId(companyUnionpay.getCompanyId());
                paymentHistory.setAmount(paymentInventory.getRealMoney());
                paymentHistoryService.save(paymentHistory);

                //查看是否所有分包已经支付完成
                boolean isAllSuccess = paymentInventoryService.checkAllPaymentInventoryPaySuccess(paymentInventory.getPaymentOrderId());
                if (isAllSuccess) {
                    PaymentOrder paymentOrder = paymentOrderService.getById(paymentInventory.getPaymentOrderId());
                    paymentOrder.setPaymentOrderStatus(6);
                    paymentOrderService.updateById(paymentOrder);
                }

                break;

            default:
                log.error("银联订单号交易类型有误");
                return "fail";
        }

        return "success";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String transferResult(HttpServletRequest request) {

        Map<String, Object> parameters = WebUtils.getParametersStartingWith(request, "");
        log.info("银联内部转账(清分接口，会员间交易)回调接收:" + JSON.toJSONString(parameters));
        //转化为json格式
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(parameters));
        if (jsonObject == null) {
            log.error("银联内部转账(清分接口，会员间交易)回调接收不到参数");
            return "fail";
        }

        //获取商户号, 查询相应的服务商银联
        String merchNo = jsonObject.getString("merchNo");
        TaxUnionpay taxUnionpay = taxUnionpayService.queryTaxUnionpayByMerchNo(merchNo);
        if (taxUnionpay == null) {
            log.error("对应商户号的服务商银联不存在");
            return "fail";
        }

        PaymentMethod paymentMethod;
        switch (taxUnionpay.getUnionpayBankType()) {

            case SJBK:

                paymentMethod = PaymentMethod.UNIONSJBK;
                break;

            case PABK:

                paymentMethod = PaymentMethod.UNIONPABK;
                break;

            case WSBK:

                paymentMethod = PaymentMethod.UNIONWSBK;
                break;

            case ZSBK:

                paymentMethod = PaymentMethod.UNIONZSBK;
                break;

            default:
                log.error("对应商户号的服务商银联类型不存在");
                return "fail";
        }

        //获取其他参数
        //通知类型
        String notifyType = jsonObject.getString("notifyType");
        //出金子帐户帐号
        String outAcctNo = jsonObject.getString("outAcctNo");
        //入金子帐户帐号
        String inAcctNo = jsonObject.getString("inAcctNo");
        //内部转账交易凭证 ID
        String itfBillId = jsonObject.getString("itfBillId");
        //合作方业务平台订单号（外部请求流水号）
        String outerTradeNo = jsonObject.getString("outer_trade_no");
        //交易状态（91:交易成功,99:交易失败）
        String status = jsonObject.getString("status");
        //失败原因
        String errDesc = jsonObject.getString("errDesc");
        //交易时间
        String txTime = jsonObject.getString("txTime");
        //参数的签名串
        String sign = jsonObject.getString("sign");
        //加签方式（本字段不参与加签）
        String signType = jsonObject.getString("signType");

        //验签处理
        Map<String, String> params = Maps.newHashMap();
        params.put("notifyType", notifyType);
        params.put("merchNo", merchNo);
        params.put("outAcctNo", outAcctNo);
        params.put("inAcctNo", inAcctNo);
        params.put("itfBillId", itfBillId);
        params.put("outerTradeNo", outerTradeNo);
        params.put("status", status);
        params.put("errDesc", errDesc);
        params.put("txTime", txTime);
        params.put("sign", sign);

        log.info("验签参数：{}", params);
        boolean checkPass = UnionpayUtil.checkSign(params, taxUnionpay.getPfmpubkey(), "UTF-8", signType);
        if (!checkPass) {
            log.error("验签失败");
            return "fail";
        }

        //出金帐号等于主账号清分账号则为清分回调
        if (taxUnionpay.getClearNo().equals(outAcctNo)) {

            //根据订单号查询充值交易记录
            PaymentHistory paymentHistory = paymentHistoryService.queryPaymentHistoryByOuterTradeNo(outerTradeNo);
            if (paymentHistory == null) {
                log.error("订单号为{}的交易记录不存在", outerTradeNo);
                return "fail";
            }

            if ("91".equals(status)) {
                //编辑交易记录为失败
                paymentHistory.setTradeStatus(TradeStatus.SUCCESS);
            } else {
                //编辑交易记录为成功
                paymentHistory.setTradeStatus(TradeStatus.FAIL);
            }

            //编辑充值交易记录
            paymentHistory.setOuterTradeNo(itfBillId);
            paymentHistoryService.updateById(paymentHistory);
            return "success";
        }

        CompanyUnionpay companyUnionpay = companyUnionpayService.queryMerchantUnionpay(outAcctNo);
        if (companyUnionpay == null) {
            log.error("对应子账户账号的商户银联子账号不存在");
            return "fail";
        }


        //业务逻辑处理 ****************************
        //拆分订单号，获取银联交易类型
        String tradeType = outerTradeNo.replaceAll("\\d+", "");
        TradeNoType tradeNoType = Enum.valueOf(TradeNoType.class, tradeType);
        PaymentHistory paymentHistory;
        switch (tradeNoType) {

            case PO:

                //查询总包是否存在
                PaymentOrder paymentOrder = paymentOrderService.queryPaymentOrderByTradeNo(outerTradeNo);
                if (paymentOrder == null) {
                    log.error("订单号为{}的总包支付订单不存在", outerTradeNo);
                    return "fail";
                }

                if (paymentOrder.getPaymentOrderStatus() == 2) {
                    log.error("订单号为{}的总包支付订单状态已为已支付", outerTradeNo);
                    return "fail";
                }

                //判断交易结果
                //创建交易记录
                paymentHistory = new PaymentHistory();
                if ("91".equals(status)) {
                    //修改总包支付状态为成功
                    paymentOrder.setPaymentOrderStatus(2);
                    //添加成功的交易记录
                    paymentHistory.setTradeStatus(TradeStatus.SUCCESS);
                } else {
                    log.error("订单号为{}的总包支付订单支付失败：{}", outerTradeNo, errDesc);
                    paymentOrder.setTradeFailReason(errDesc);
                    //添加失败的交易记录
                    paymentHistory.setTradeStatus(TradeStatus.FAIL);
                }
                paymentOrderService.updateById(paymentOrder);

                paymentHistory.setTradeNo(paymentOrder.getTradeNo());
                paymentHistory.setOuterTradeNo(itfBillId);
                paymentHistory.setOrderType(OrderType.TOTALORDER);
                paymentHistory.setPaymentMethod(paymentMethod);
                paymentHistory.setTradeObject(TradeObject.COMPANY);
                paymentHistory.setTradeObjectId(companyUnionpay.getCompanyId());
                paymentHistory.setAmount(paymentOrder.getServiceMoney());
                paymentHistoryService.save(paymentHistory);

                break;

            case POM:

                //查询众包是否存在
                PaymentOrderMany paymentOrderMany = paymentOrderManyService.queryPaymentOrderManyByTradeNo(outerTradeNo);
                if (paymentOrderMany == null) {
                    log.error("订单号为{}的众包支付订单不存在", outerTradeNo);
                    return "fail";
                }

                if (paymentOrderMany.getPaymentOrderStatus() == 3) {
                    log.error("订单号为{}的众包支付订单状态已为已完成", outerTradeNo);
                    return "fail";
                }

                //判断交易结果
                //创建交易记录
                paymentHistory = new PaymentHistory();
                if ("91".equals(status)) {
                    //修改总包支付状态为成功
                    paymentOrderMany.setPaymentOrderStatus(3);
                    //添加成功的交易记录
                    paymentHistory.setTradeStatus(TradeStatus.SUCCESS);
                } else {
                    log.error("订单号为{}的众包支付订单支付失败：{}", outerTradeNo, errDesc);
                    paymentOrderMany.setTradeFailReason(errDesc);
                    //添加失败的交易记录
                    paymentHistory.setTradeStatus(TradeStatus.FAIL);
                }
                paymentOrderManyService.updateById(paymentOrderMany);

                paymentHistory.setTradeNo(paymentOrderMany.getTradeNo());
                paymentHistory.setOuterTradeNo(itfBillId);
                paymentHistory.setOrderType(OrderType.MANYORDER);
                paymentHistory.setPaymentMethod(paymentMethod);
                paymentHistory.setTradeObject(TradeObject.COMPANY);
                paymentHistory.setTradeObjectId(companyUnionpay.getCompanyId());
                paymentHistory.setAmount(paymentOrderMany.getServiceMoney());
                paymentHistoryService.save(paymentHistory);

                break;

            default:
                log.error("银联订单号交易类型有误");
                return "fail";
        }

        return "success";
    }

}
