package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.myBank.*;

public interface MyBankService {

    //创建企业会员信息
    ReturnJson registerMerchantMember(String enterpriseName,String uId) throws Exception;

    //创建个人会员信息
    ReturnJson registerWorkerMember(String uId,String realName,String memberName,String certificateNo) throws Exception;

    //绑卡
    ReturnJson bindingBankCard(String uId,String bankAccountNo,String accountName,String cardType,String cardAttribute) throws Exception;

    //查询余额
    ReturnJson checkTheBalance(String uId) throws Exception;

    //修改个人会员信息
    ReturnJson workerInfoModify(PersonalDto personalDto) throws Exception;

    //修改企业会员信息
    ReturnJson merchantInfoModify(EnterpriseDto enterpriseDto) throws Exception;

    //查询个人信息
   ReturnJson workerInfoQuery(String uid) throws Exception;

    //查询企业信息
    ReturnJson merchantInfoQuery(String uid) throws Exception;

    //创建会员账户
    ReturnJson createAMemberAccount(MemberAccountDto memberAccountDto) throws Exception;

    //绑定支付宝
    ReturnJson bindingAlipay(AlipayDto alipayDto) throws Exception;

    //解绑银行卡
    ReturnJson unbindBankCard(String uId,String bankId) throws Exception;

    //及时入账
    ReturnJson timelyEntry(EntryDto entryDto) throws Exception;

    //单笔提现
    ReturnJson singleWithdrawal(WithdrawalDto withdrawalDto) throws Exception;

    //单笔提现到卡或支付宝
    ReturnJson singleWithdrawalCard(CashWithdrawalToCardDto cashWithdrawalToCardDto) throws Exception;

    //转账入账
    ReturnJson tradeTransfer(TransferDto transferDto) throws Exception;
}
