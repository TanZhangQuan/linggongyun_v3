package com.example.merchant.controller.makerend;

import com.example.common.mybank.entity.BankCardBind;
import com.example.common.mybank.entity.Personal;
import com.example.common.util.ReturnJson;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.MyBankWorkerService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Jwei
 */
@Api(value = "创客网商银行个人信息", tags = "创客网商银行个人信息")
@RestController
@RequestMapping("/makerend/myBankWorker")
@Validated
public class MyBankWorkerController {

    @Autowired
    private MyBankWorkerService myBankWorkerService;

    @PostMapping("/personalRegister")
    @LoginRequired
    @ApiOperation(value = "注册网商个人会员", notes = "注册网商个人会员", httpMethod = "POST")
    public ReturnJson personalRegister(@RequestBody Personal personal,
                                       @RequestAttribute(value = "userId") @ApiParam(hidden = true) String userId) throws Exception {
        return myBankWorkerService.personalRegister(personal, userId);
    }

    @PostMapping("/personalInfoModify")
    @LoginRequired
    @ApiOperation(value = "修改网商个人会员信息", notes = "修改网商个人会员信息", httpMethod = "POST")
    public ReturnJson personalInfoModify(@RequestBody Personal personal,
                                         @RequestAttribute(value = "userId") @ApiParam(hidden = true) String userId) throws Exception {
        return myBankWorkerService.personalInfoModify(personal, userId);
    }

    @PostMapping("/personalInfoQuery")
    @LoginRequired
    @ApiOperation(value = "查询网商个人会员信息", notes = "查询网商个人会员信息", httpMethod = "POST")
    public ReturnJson personalInfoQuery(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String userId) throws Exception {
        return myBankWorkerService.personalInfoQuery(userId);
    }

    @PostMapping("/bankCardBind")
    @LoginRequired
    @ApiOperation(value = "绑定网商银行卡", notes = "绑定网商银行卡", httpMethod = "POST")
    public ReturnJson bankCardBind(@RequestBody BankCardBind bankCardBind,
                                   @RequestAttribute(value = "userId") @ApiParam(hidden = true) String userId) throws Exception {
        return myBankWorkerService.bankCardBind(bankCardBind, userId);
    }

    @PostMapping("/bankCardUnBind")
    @LoginRequired
    @ApiOperation(value = "解绑网商银行卡", notes = "解绑网商银行卡", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "bankId", value = "绑定的网商银行卡", required = true)})
    public ReturnJson bankCardUnBind(String bankId,
                                     @RequestAttribute(value = "userId") @ApiParam(hidden = true) String userId) throws Exception {
        return myBankWorkerService.bankCardUnBind(bankId, userId);
    }
}
