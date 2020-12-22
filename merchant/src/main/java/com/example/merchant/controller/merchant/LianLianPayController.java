package com.example.merchant.controller.merchant;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.AddLianLianPay;
import com.example.merchant.exception.CommonException;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.LianLianPayService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

@Api(value = "商户端连连支付关操作接口", tags = {"商户端连连支付关操作接口"})
@RestController
@RequestMapping("/merchant/lianlianpay")
public class LianLianPayController {
    @Resource
    private LianLianPayService lianLianPayService;

    @LoginRequired
    @ApiOperation("添加或修改连连支付商户号和私钥")
    @PostMapping(value = "/addLianlianPay")
    public ReturnJson addLianlianPay(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String merchantId, @Valid @RequestBody AddLianLianPay addLianLianPay) {
        return lianLianPayService.addLianlianPay(merchantId, addLianLianPay);
    }

    @LoginRequired
    @PostMapping("/merchantPay")
    @ApiOperation(value = "商户总包支付", notes = "商户总包支付", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentOrderId", value = "支付订单ID", required = true),
            @ApiImplicitParam(name = "payPassWord", value = "支付密码", required = true)
    })
    public ReturnJson merchantPay(@ApiParam(hidden = true) @RequestAttribute("userId") String merchantId, @NotBlank(message = "支付订单不能为空！") @RequestParam(required = false) String paymentOrderId, @NotBlank(message = "支付密码不能为空！") @RequestParam(required = false) String payPassWord) throws CommonException {
        return lianLianPayService.merchantPay(merchantId, payPassWord, paymentOrderId);
    }

    @LoginRequired
    @PostMapping("/merchantPayMany")
    @ApiOperation(value = "商户众包支付", notes = "商户众包支付", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentOrderId", value = "支付订单ID", required = true),
            @ApiImplicitParam(name = "payPassWord", value = "支付密码", required = true)
    })
    public ReturnJson merchantPayMany(@ApiParam(hidden = true) @RequestAttribute("userId") String merchantId, @NotBlank(message = "支付订单不能为空！") @RequestParam(required = false) String paymentOrderId, @NotBlank(message = "支付密码不能为空！") @RequestParam(required = false) String payPassWord) throws CommonException {
        return lianLianPayService.merchantPayMany(merchantId, payPassWord, paymentOrderId);
    }

    @RequestMapping("/merchantNotifyUrl")
    @ApiOperation(value = "商户总包支付回调接口", notes = "商户总包支付回调接口", hidden = true, httpMethod = "POST")
    public Map<String, String> merchantNotifyUrl(HttpServletRequest request) {
        lianLianPayService.merchantNotifyUrl(request);
        Map<String, String> map = new HashMap<>();
        map.put("ret_code", "0000");
        map.put("ret_msg", "交易成功");
        return map;
    }

    @RequestMapping("/merchantManyNotifyUrl")
    @ApiOperation(value = "商户众包支付回调接口", notes = "商户众包支付回调接口", hidden = true, httpMethod = "POST")
    public Map<String, String> merchantManyNotifyUrl(HttpServletRequest request) {
        lianLianPayService.merchantManyNotifyUrl(request);
        Map<String, String> map = new HashMap<>();
        map.put("ret_code", "0000");
        map.put("ret_msg", "交易成功");
        return map;
    }

    @PostMapping("/queryPayment")
    @LoginRequired
    @ApiOperation(value = "商户订单查询", notes = "商户订单查询", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "oidPaybill", value = "连连订单号", required = true)
    })
    public Map<String, String> queryPayment(@ApiParam(hidden = true) @NotBlank(message = "您的登录已过期！") @RequestAttribute(value = "userId", required = false) String merchantId, @NotBlank(message = "请选择订单") @RequestParam String oidPaybill) throws CommonException {
        return lianLianPayService.queryPaymentByorderId(merchantId, oidPaybill);
    }

}
