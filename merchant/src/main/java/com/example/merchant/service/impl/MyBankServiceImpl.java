package com.example.merchant.service.impl;

import com.example.common.mybank.entity.*;
import com.example.common.mybank.util.BankService;
import com.example.common.mybank.util.BaseField;
import com.example.common.mybank.util.MapRemoveNullUtil;
import com.example.common.mybank.util.SignData;
import com.example.merchant.config.MyBankConfig;
import com.example.merchant.service.MyBankService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;


@Service
public class MyBankServiceImpl implements MyBankService {

    @Autowired
    private MyBankConfig myBankConfig;
    @Autowired
    private SecurityService securityService;
    @Resource
    private RemoteService remoteService;

    //注册个人会员
    @Override
    public Map<String, Object> personalRegister(Personal params) throws Exception {
        params = (Personal) baseRequest(params);
        params.setService("mybank.tc.user.personal.register");
        params.setCertificate_type("ID_CARD");
        params.setIs_verify("Y");
        return buildCjmsgAndSend(BeanUtils.describe(params));
    }

    //注册企业会员
    @Override
    public Map<String, Object> enterpriseRegister(Enterprise params) throws Exception {
        params = (Enterprise) baseRequest(params);
        params.setService("mybank.tc.user.enterprise.register");
        return buildCjmsgAndSend(BeanUtils.describe(params));
    }

    //修改个人信息
    @Override
    public Map<String, Object> personalInfoModify(Personal params) throws Exception {
        params = (Personal) baseRequest(params);
        params.setService("mybank.tc.user.personal.info.modify");
        return buildCjmsgAndSend(BeanUtils.describe(params));
    }

    //修改企业信息
    @Override
    public Map<String, Object> enterpriseInfoModify(Enterprise params) throws Exception {
        params = (Enterprise) baseRequest(params);
        params.setService("mybank.tc.user.enterprise.info.modify");
        return buildCjmsgAndSend(BeanUtils.describe(params));
    }

    //查询个人信息
    @Override
    public Map<String, Object> personalInfoQuery(String uid) throws Exception {
        BaseRequest params = baseRequest(new BaseRequest());
        params.setService("mybank.tc.user.personal.info.query");
        Map<String, String> map = BeanUtils.describe(params);
        map.put("uid", uid);
        return buildCjmsgAndSend(map);
    }

    //查询企业信息
    @Override
    public Map<String, Object> enterpriseInfoQuery(String uid) throws Exception {
        BaseRequest params = baseRequest(new BaseRequest());
        params.setService("mybank.tc.user.enterprise.info.query");
        Map<String, String> map = BeanUtils.describe(params);
        map.put("uid", uid);
        return buildCjmsgAndSend(map);
    }

    //查余额
    @Override
    public Map<String, Object> accountBalance(String uid, String account_type) throws Exception {
        BaseRequest params = baseRequest(new BaseRequest());
        params.setService("mybank.tc.user.account.balance");
        Map<String, String> map = BeanUtils.describe(params);
        map.put("uid", uid);
        map.put("account_type", account_type);
        return buildCjmsgAndSend(map);
    }

    //绑定银行卡
    @Override
    public Map<String, Object> bankCardBind(BankCardBind params) throws Exception {
        params = (BankCardBind) baseRequest(params);
        params.setCard_type("IC_CARD");
        params.setPay_attribute("NORMAL");
        params.setCard_type("DC");
        params.setService("mybank.tc.user.bankcard.bind");
        return buildCjmsgAndSend(BeanUtils.describe(params));
    }

    //解绑银行卡
    @Override
    public Map<String, Object> bankCardUnBind(String uid, String bank_id) throws Exception {
        BaseRequest params = baseRequest(new BaseRequest());
        params.setService("mybank.tc.user.bankcard.unbind");
        Map<String, String> map = BeanUtils.describe(params);
        map.put("uid", uid);
        map.put("bank_id", bank_id);
        return buildCjmsgAndSend(map);
    }

    //查询绑卡列表
    @Override
    public Map<String, Object> bankCardQuery(String uid) throws Exception {
        BaseRequest params = baseRequest(new BaseRequest());
        params.setService("mybank.tc.user.bankcard.query");
        Map<String, String> map = BeanUtils.describe(params);
        map.put("uid", uid);
        return buildCjmsgAndSend(map);
    }

    //绑定支付宝
    @Override
    public Map<String, Object> aliPayBind(AliPayBind params) throws Exception {
        params = (AliPayBind) baseRequest(params);
        params.setService("mybank.tc.user.alipay.bind");
        return buildCjmsgAndSend(BeanUtils.describe(params));
    }

    //单笔提现
    @Override
    public Map<String, Object> tradePayToCard(TradePayToCard params) throws Exception {
        params = (TradePayToCard) baseRequest(params);
        params.setNotify_url(myBankConfig.getAsyncNotifyUrl());
        params.setService("mybank.tc.trade.paytocard");
        return buildCjmsgAndSend(BeanUtils.describe(params));
    }

    //单笔提现到卡
    @Override
    public Map<String, Object> withDrawToCard(WithDrawToCard params) throws Exception {
        params = (WithDrawToCard) baseRequest(params);
        params.setReturn_url(myBankConfig.getNotifyUrl());
        params.setNotify_url(myBankConfig.getAsyncNotifyUrl());
        params.setService("mybank.tc.trade.withdrawtocard");
        return buildCjmsgAndSend(BeanUtils.describe(params));
    }

    //交易详情查询
    @Override
    public Map<String, Object> tradeInfoQuery(String outer_trade_no) throws Exception {
        BaseRequest params = baseRequest(new BaseRequest());
        params.setService("mybank.tc.trade.info.query");
        Map<String, String> map = BeanUtils.describe(params);
        map.put("outer_trade_no", outer_trade_no);
        return buildCjmsgAndSend(map);
    }

    //交易流水查询
    @Override
    public Map<String, Object> tradeQuery(String start_time, String end_time) throws Exception {
        BaseRequest params = baseRequest(new BaseRequest());
        params.setService("mybank.tc.trade.query");
        Map<String, String> map = BeanUtils.describe(params);
        map.put("start_time", start_time);
        map.put("end_time", end_time);
        return buildCjmsgAndSend(map);
    }

    //退票
    @Override
    public Map<String, Object> tradeRefundTicket(String request_no, String orig_outer_inst_order_no) throws Exception {
        BaseRequest params = baseRequest(new BaseRequest());
        params.setService("mybank.tc.trade.refundticket");
        Map<String, String> map = BeanUtils.describe(params);
        map.put("request_no", request_no);
        map.put("orig_outer_inst_order_no", orig_outer_inst_order_no);
        return buildCjmsgAndSend(map);
    }

    //即时交易入账
    @Override
    public Map<String, Object> tradePayInstant(TradePayInstant params) throws Exception {
        params = (TradePayInstant) baseRequest(params);
        params.setReturn_url(myBankConfig.getNotifyUrl());
        params.setService("mybank.tc.trade.pay.instant");
        return buildCjmsgAndSend(BeanUtils.describe(params));
    }

    //来账通知模拟回调
    @Override
    public Map<String, Object> mockNotify(MockNotify params) throws Exception {
        params = (MockNotify) baseRequest(params);
        StringBuilder ac = new StringBuilder("5");
        ac.append(myBankConfig.getSettlementAccount().substring(6));
        ac.append(params.getPayee_card_no());
        System.out.println(ac);
        params.setPayee_card_no(ac.toString());
        params.setNotify_url(myBankConfig.getAsyncNotifyUrl());
        params.setService("mybank.tc.trade.remit.subaccount");
        return buildCjmsgAndSend(BeanUtils.describe(params));
    }

    //转账入账
    @Override
    public Map<String, Object> tradeTransfer(TradeTransfer params) throws Exception {
        params = (TradeTransfer) baseRequest(params);
        params.setNotify_url(myBankConfig.getAsyncNotifyUrl());
        params.setService("mybank.tc.trade.transfer");
        return buildCjmsgAndSend(BeanUtils.describe(params));
    }

    /**
     * 组织报文，并发送
     */
    private Map<String, Object> buildCjmsgAndSend(Map<String, String> data) throws Exception {
        MapRemoveNullUtil.removeNullValue(data);
        try {
            String sign = sign(data);
            data.put("sign", sign);
            //判断接口调用gop地址
            String service = data.get("service");
            String url;
            BankService byServiceName = BankService.getByServiceName(service);
            if (byServiceName != null) {
                if ("tpu".equals(byServiceName.getServiceUrl())) {
                    //调用gop
                    url = myBankConfig.getGopTpuUrl();
                } else {
                    //调用mag
                    url = myBankConfig.getGopMagUrl();
                }
            } else {
                throw new RuntimeException("接口名称不存在");
            }
            return remoteService.invoke(data, url);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new RuntimeException(t);
        }
    }


    /**
     * 基本方法的声明（由子类实现）
     */
    private String sign(Map<String, String> data) {
        String sign_type = data.get(BaseField.SIGN_TYPE.getCode());
        String inputCharset = data.get(BaseField.INPUT_CHARSET.getCode());
        securityService.filter(data);

        SignData sign2 = securityService.sign(data, inputCharset, sign_type);

        return sign2.getSign();
    }

    private BaseRequest baseRequest(BaseRequest request) {
        request.setCharset("UTF-8");
        request.setMemo("");
        request.setPartner_id(myBankConfig.getPartnerId());
        request.setVersion("2.1");
        request.setSign_type("TWSIGN");
        return request;
    }
}
