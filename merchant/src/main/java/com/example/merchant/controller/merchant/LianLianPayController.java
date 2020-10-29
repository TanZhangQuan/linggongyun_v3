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
    public ReturnJson addLianlianPay(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String merchantId, @RequestBody AddLianLianPay addLianLianPay) {
        return lianLianPayService.addLianlianPay(merchantId, addLianLianPay);
    }

    @LoginRequired
    @PostMapping("/merchantPay")
    @ApiOperation(value = "商户总包支付", notes = "商户总包支付", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentOrderId", value = "支付订单ID", required = true)
    })
    public ReturnJson merchantPay(@NotBlank(message = "支付订单不能为空！") @RequestParam(required = false) String paymentOrderId) throws CommonException {
        return lianLianPayService.merchantPay(paymentOrderId);
    }

    @LoginRequired
    @PostMapping("/merchantPayMany")
    @ApiOperation(value = "商户众包支付", notes = "商户众包支付", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentOrderId", value = "支付订单ID", required = true)
    })
    public ReturnJson merchantPayMany(@NotBlank(message = "支付订单不能为空！") @RequestParam(required = false) String paymentOrderId) throws CommonException {
        return lianLianPayService.merchantPayMany(paymentOrderId);
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
}
