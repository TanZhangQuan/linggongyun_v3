package com.example.merchant.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.example.common.mybank.MyBankClient;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.myBank.*;
import com.example.merchant.service.MyBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class MyBankServiceImpl implements MyBankService {

    private String service;  //调用的接口


    @Autowired
    private MyBankClient myBankClient;

    /**
     * 注册商户 企业会员
     *
     * @param addEnterpriseDto
     * @return
     * @throws Exception
     */
    @Override
    public ReturnJson registerMerchantMember(AddEnterpriseDto addEnterpriseDto) throws Exception {
        if (addEnterpriseDto.getUid() == null || addEnterpriseDto.getEnterprise_name() == null) {
            ReturnJson.error("Uid,Enterprise_name不能为空");
        }
        service = "mybank.tc.user.enterprise.register";
        Map<String, String> map = new HashMap<>();  //接口所需要的参数
        map.put("service", service);

        map = (Map) JSONUtils.parse(myBankClient.myBank(map));  //返回的参数
        if (map.get("is_success").equals("T")) {  //判断返回的状态
            return ReturnJson.success("操作成功", map);
        }
        return ReturnJson.error("操作失败");

    }

    /**
     * 注册创客 个人会员
     *
     * @param uId           创客ID
     * @param realName      创客真实姓名
     * @param memberName    会员名称。用户昵称(平台个人会员登录名)
     * @param certificateNo 作为会员实名认证通过后的证件号
     * @return
     */
    @Override
    public ReturnJson registerWorkerMember(String uId, String realName, String memberName, String certificateNo) throws Exception {
        Map<String, String> map = new HashMap<>();  //接口所需要的参数
        service = "mybank.tc.user.personal.register";
        String certificateType = "ID_CARD";
        map.put("service", service);
        map.put("uid", uId);
        map.put("real_name", realName);
        map.put("member_name", memberName);
        map.put("certificate_type", certificateType);
        map.put("certificate_no", certificateNo);

        map = (Map) JSONUtils.parse(myBankClient.myBank(map));  //返回的参数
        if (map.get("is_success").equals("T")) {  //判断返回的状态
            return ReturnJson.success("操作成功", map);
        }
        return ReturnJson.error("操作失败");
    }

    /**
     * 绑定银行卡
     *
     * @param bankCardDto
     * @return
     */
    @Override
    public ReturnJson bindingBankCard(BankCardDto bankCardDto) throws Exception {
        service = "mybank.tc.user.bankcard.bind";
        Map<String, String> map = JSON.parseObject(JSON.toJSONString(bankCardDto), Map.class);
        map.put("service", service);
        
        map = (Map) JSONUtils.parse(myBankClient.myBank(map));  //返回的参数
        if (map.get("is_success").equals("T")) {  //判断返回的状态
            return ReturnJson.success("操作成功", map);
        }
        return ReturnJson.error("操作失败");
    }

    /**
     * 查询余额
     *
     * @param uId
     * @return
     * @throws Exception
     */
    @Override
    public ReturnJson checkTheBalance(String uId) throws Exception {
        Map<String, String> map = new HashMap<>();  //接口所需要的参数
        service = "mybank.tc.user.account.balance";
        map.put("service", service);
        map.put("uid", uId);

        map = (Map) JSONUtils.parse(myBankClient.myBank(map));  //返回的参数
        if (map.get("is_success").equals("T")) {  //判断返回的状态
            return ReturnJson.success("操作成功", map);
        }
        return ReturnJson.error("操作失败");
    }

    /**
     * 修改个人会员
     *
     * @param personalDto
     * @return
     * @throws Exception
     */
    @Override
    public ReturnJson workerInfoModify(PersonalDto personalDto) throws Exception {
        if (("").equals(personalDto.getMobile()) || personalDto.getMobile() == null && ("").equals(personalDto.getEmail()) || personalDto.getEmail() == null) {
            return ReturnJson.success("手机号与邮箱有一个不能为空");
        }
        service = "mybank.tc.user.personal.info.modify";
        Map<String, String> map = JSON.parseObject(JSON.toJSONString(personalDto), Map.class);
        map.put("service", service);

        map = (Map) JSONUtils.parse(myBankClient.myBank(map));  //返回的参数
        if (map.get("is_success").equals("T")) {  //判断返回的状态
            return ReturnJson.success("操作成功", map);
        }
        return ReturnJson.error("操作失败");
    }

    /**
     * 修改企业会员
     *
     * @param
     * @return
     * @throws Exception
     */
    @Override
    public ReturnJson merchantInfoModify(EnterpriseDto enterpriseDto) throws Exception {
        service = "mybank.tc.user.enterprise.info.modify";
        Map map = JSON.parseObject(JSON.toJSONString(enterpriseDto), Map.class);
        map.put("service", service);

        map = (Map) JSONUtils.parse(myBankClient.myBank(map));  //返回的参数
        if (map.get("is_success").equals("T")) {  //判断返回的状态
            return ReturnJson.success("操作成功", map);
        }
        return ReturnJson.error("操作失败");
    }

    /**
     * 查询个人信息
     *
     * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public ReturnJson workerInfoQuery(String uid) throws Exception {
        service = "mybank.tc.user.personal.info.query";
        Map<String, String> map = new HashMap<>();
        map.put("service", service);
        map.put("uid", uid);

        map = (Map) JSONUtils.parse(myBankClient.myBank(map));  //返回的参数
        if (map.get("is_success").equals("T")) {  //判断返回的状态
            return ReturnJson.success("操作成功", map);
        }
        return ReturnJson.error("操作失败");
    }

    /**
     * 查询企业信息
     *
     * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public ReturnJson merchantInfoQuery(String uid) throws Exception {
        service = "mybank.tc.user.enterprise.info.query";
        Map<String, String> map = new HashMap<>();
        map.put("service", service);
        map.put("uid", uid);

        map = (Map) JSONUtils.parse(myBankClient.myBank(map));  //返回的参数
        if (map.get("is_success").equals("T")) {  //判断返回的状态
            return ReturnJson.success("操作成功", map);
        }
        return ReturnJson.error("操作失败");
    }

    /**
     * 创建会员账户
     *
     * @param memberAccountDto
     * @return
     * @throws Exception
     */
    @Override
    public ReturnJson createAMemberAccount(MemberAccountDto memberAccountDto) throws Exception {
        service = "mybank.tc.user.account.create";
        Map map = JSON.parseObject(JSON.toJSONString(memberAccountDto), Map.class);
        map.put("service", service);

        map = (Map) JSONUtils.parse(myBankClient.myBank(map));  //返回的参数
        if (map.get("is_success").equals("T")) {  //判断返回的状态
            return ReturnJson.success("操作成功", map);
        }
        return ReturnJson.error("操作失败");
    }

    /**
     * 绑定支付宝
     *
     * @param alipayDto
     * @return
     */
    @Override
    public ReturnJson bindingAlipay(AlipayDto alipayDto) throws Exception {
        service = "mybank.tc.user.alipay.bind";
        Map map = JSON.parseObject(JSON.toJSONString(alipayDto), Map.class);
        map.put("service", service);

        map = (Map) JSONUtils.parse(myBankClient.myBank(map));  //返回的参数
        if (map.get("is_success").equals("T")) {  //判断返回的状态
            return ReturnJson.success("操作成功", map);
        }
        return ReturnJson.error("操作失败");
    }

    /**
     * 解绑银行卡
     *
     * @param uId
     * @param bankId
     * @return
     */
    @Override
    public ReturnJson unbindBankCard(String uId, String bankId) throws Exception {
        service = "mybank.tc.user.bankcard.unbind";
        Map<String, String> map = new HashMap<>();
        map.put("service", service);
        map.put("uid", uId);
        map.put("bank_id", bankId);

        map = (Map) JSONUtils.parse(myBankClient.myBank(map));  //返回的参数
        if (map.get("is_success").equals("T")) {  //判断返回的状态
            return ReturnJson.success("操作成功", map);
        }
        return ReturnJson.error("操作失败");
    }

    /**
     * 及时入账
     *
     * @param entryDto
     * @return
     * @throws Exception
     */
    @Override
    public ReturnJson timelyEntry(EntryDto entryDto) throws Exception {
        service = "mybank.tc.trade.pay.instant";  //及时入账接口
        Map<String, String> map = JSON.parseObject(JSON.toJSONString(entryDto), Map.class);
        map.put("service", service);

        map = (Map) JSONUtils.parse(myBankClient.myBank(map));  //返回的参数
        if (map.get("is_success").equals("T")) {  //判断返回的状态
            return ReturnJson.success("操作成功", map);
        }
        return ReturnJson.error("操作失败");
    }

    /**
     * 单笔提现
     *
     * @param withdrawalDto
     * @return
     */
    @Override
    public ReturnJson singleWithdrawal(WithdrawalDto withdrawalDto) throws Exception {
        service = "mybank.tc.trade.paytocard";
        Map map = JSON.parseObject(JSON.toJSONString(withdrawalDto), Map.class);
        map.put("service", service);

        map = (Map) JSONUtils.parse(myBankClient.myBank(map));  //返回的参数
        if (map.get("is_success").equals("T")) {  //判断返回的状态
            return ReturnJson.success("操作成功", map);
        }
        return ReturnJson.error("操作失败");
    }

    /**
     * 单笔提现到卡或支付宝
     *
     * @param cashWithdrawalToCardDto
     * @return
     */
    @Override
    public ReturnJson singleWithdrawalCard(CashWithdrawalToCardDto cashWithdrawalToCardDto) throws Exception {
        service = "mybank.tc.trade.withdrawtocard";
        Map map = JSON.parseObject(JSON.toJSONString(cashWithdrawalToCardDto), Map.class);
        map.put("service", service);

        map = (Map) JSONUtils.parse(myBankClient.myBank(map));  //返回的参数
        if (map.get("is_success").equals("T")) {  //判断返回的状态
            return ReturnJson.success("操作成功", map);
        }
        return ReturnJson.error("操作失败");
    }

    /**
     * 转账入账
     *
     * @param transferDto
     * @return
     */
    @Override
    public ReturnJson tradeTransfer(TransferDto transferDto) throws Exception {
        service = "mybank.tc.trade.transfer";
        Map map = JSON.parseObject(JSON.toJSONString(transferDto), Map.class);
        map.put("service", service);

        map = (Map) JSONUtils.parse(myBankClient.myBank(map));  //返回的参数
        if (map.get("is_success").equals("T")) {  //判断返回的状态
            return ReturnJson.success("操作成功", map);
        }
        return ReturnJson.error("操作失败");
    }


}
