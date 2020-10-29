package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.myBank.*;

import java.util.Map;

public interface MyBankService {

    //注册个人会员
    Map<String, Object> personalRegister(Personal params) throws Exception;

    //注册企业会员
    Map<String, Object> enterpriseRegister(Enterprise params) throws Exception;

    //修改个人信息
    Map<String, Object> personalInfoModify(Personal params) throws Exception;

    //修改企业信息
    Map<String, Object> enterpriseInfoModify(Enterprise params) throws Exception;

    //查询个人信息
    Map<String, Object> personalInfoQuery(String uid) throws Exception;

    //查询企业信息
    Map<String, Object> enterpriseInfoQuery(String uid) throws Exception;

    //查余额
    Map<String, Object> accountBalance(String uid, String account_type) throws Exception;

    //绑定银行卡
    Map<String, Object> bankCardBind(BankCardBind params) throws Exception;

    //解绑银行卡
    Map<String, Object> bankCardUnBind(String uid, String bank_id) throws Exception;

    //查询绑卡列表
    Map<String, Object> bankCardQuery(String uid) throws Exception;

    //绑定支付宝
    Map<String, Object> aliPayBind(AliPayBind params) throws Exception;

    //单笔提现
    Map<String, Object> tradePayToCard(TradePayToCard params) throws Exception;

    //单笔提现到卡
    Map<String, Object> withDrawToCard(WithDrawToCard params) throws Exception;

    //交易详情查询
    Map<String, Object> tradeInfoQuery(String outer_trade_no) throws Exception;

    //交易流水查询
    Map<String, Object> tradeQuery(String start_time, String end_time) throws Exception;

    //退票
    Map<String, Object> tradeRefundTicket(String request_no, String orig_outer_inst_order_no) throws Exception;

    //即时交易入账
    Map<String, Object> tradePayInstant(TradePayInstant params) throws Exception;

    //转账入账
    Map<String, Object> tradeTransfer(TradeTransfer params) throws Exception;

    //来账通知模拟回调
    Map<String, Object> mockNotify(MockNotify params) throws Exception;
}
