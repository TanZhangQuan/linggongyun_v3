package com.example.merchant.controller.merchant;

import com.example.common.mybank.entity.Enterprise;
import com.example.common.util.ReturnJson;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.MyBankPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

@Api(value = "商户端网商银行相关操作接口", tags = {"商户端网商银行相关操作接口"})
@RestController
@RequestMapping("/merchant/myBank")
public class MyBankController {

    @Autowired
    private MyBankPayService myBankPayService;

    @LoginRequired
    @RequestMapping("/paymentOrderByPayId")
    @ApiOperation(value = "商户总包支付", notes = "商户总包支付")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentOrderId", value = "支付订单ID", required = true)
    })
    public ReturnJson myBankPayByPayId(@NotBlank(message = "支付订单不能为空！") @RequestParam(required = false) String paymentOrderId) throws Exception {
        return myBankPayService.myBankPayByPayId(paymentOrderId);
    }


    @LoginRequired
    @RequestMapping("/paymentOrderManyByPayId")
    @ApiOperation(value = "商户众包支付", notes = "商户众包支付")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentOrderId", value = "支付订单ID", required = true)
    })
    public ReturnJson merchantPayMany(@NotBlank(message = "支付订单不能为空！") @RequestParam(required = false) String paymentOrderId) throws Exception {
        return myBankPayService.myBankPayManyByPayId(paymentOrderId);
    }

    @RequestMapping("/merchantNotifyUrl")
    @ApiOperation(value = "商户总包支付回调接口", notes = "商户总包支付回调接口", hidden = true)
    public Map<String, String> merchantNotifyUrl(HttpServletRequest request) {
        myBankPayService.myBankPayNotifyUrl(request);
        Map<String, String> map = new HashMap<>();
        map.put("ret_code", "0000");
        map.put("ret_msg", "交易成功");
        return map;
    }

    @RequestMapping("/merchantManyNotifyUrl")
    @ApiOperation(value = "商户众包支付回调接口", notes = "商户众包支付回调接口", hidden = true)
    public Map<String, String> merchantManyNotifyUrl(HttpServletRequest request) {
        myBankPayService.myBankPayManyNotifyUrl(request);
        Map<String, String> map = new HashMap<>();
        map.put("ret_code", "0000");
        map.put("ret_msg", "交易成功");
        return map;
    }

    @LoginRequired
    @RequestMapping("/enterpriseRegister")
    @ApiOperation(value = "商户注册企业会员信息", notes = "商户注册企业会员信息")
    public ReturnJson enterpriseRegister(Enterprise enterprise, @RequestAttribute("userId") String userId) throws Exception {
        return myBankPayService.enterpriseRegister(enterprise, userId);
    }


    @LoginRequired
    @RequestMapping("/enterpriseInfoModify")
    @ApiOperation(value = "商户修改企业会员信息", notes = "商户修改企业会员信息")
    public ReturnJson enterpriseInfoModify(Enterprise enterprise, @RequestAttribute("userId") String userId) throws Exception {
        return myBankPayService.enterpriseInfoModify(enterprise, userId);
    }
}