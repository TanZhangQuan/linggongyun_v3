package com.example.merchant.controller;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.myBank.EnterpriseDto;
import com.example.merchant.dto.myBank.PersonalDto;
import com.example.merchant.service.MyBankService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(value = "测试", tags = "测试")
@RestController
@RequestMapping("/mybank")
public class MyBankController {

    @Autowired
    private MyBankService myBankService;

//    @GetMapping("/mybankMerchant")//创建企业会员
//    public ReturnJson mybankMerchant(String enterpriseName, String uId) throws Exception {
//        return myBankService.registerMerchantMember(enterpriseName, uId);
//    }

    @GetMapping("/mybankWorker")//创建个人会员
    public ReturnJson mybankWorker(String uId, String realName, String memberName, String certificateNo) throws Exception {
        return myBankService.registerWorkerMember(uId, realName, memberName, certificateNo);
    }

    @GetMapping("/mybankbinding")//绑定银行卡
    public ReturnJson mybankbinding(String uId, String bankAccountNo, String accountName, String cardType, String cardAttribute) throws Exception {
        return myBankService.bindingBankCard(uId, bankAccountNo, accountName, cardType, cardAttribute);
    }

    @GetMapping("/checkTheBalance")//查询余额
    public ReturnJson checkTheBalance(String uId) throws Exception {
        return myBankService.checkTheBalance(uId);
    }

    @GetMapping("/workerInfoModify")//修改个人会员信息
    public ReturnJson workerInfoModify(PersonalDto personalDto) throws Exception {
        return myBankService.workerInfoModify(personalDto);
    }

    @GetMapping("/merchantInfoModify")//修改企业会员信息
    public ReturnJson merchantInfoModify(EnterpriseDto enterpriseDto) throws Exception {
        return myBankService.merchantInfoModify(enterpriseDto);
    }

    @GetMapping("/workerInfoQuery")//查询个人信息
    public ReturnJson workerInfoQuery(String uId) throws Exception {
        return myBankService.workerInfoQuery(uId);
    }

    @GetMapping("/merchantInfoQuery")//查询企业信息
    public ReturnJson merchantInfoQuery(String uId) throws Exception {
        return myBankService.merchantInfoQuery(uId);
    }
}
