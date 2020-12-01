package com.example.merchant.controller.merchant;


import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.AddPaymentOrderDto;
import com.example.merchant.dto.merchant.PaymentOrderMerchantDto;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.PaymentOrderService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 总包+分包支付单信息
 * 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "商户端总包+分包支付管理", tags = "商户端总包+分包支付管理")
@RestController
@RequestMapping("/merchant/paymentOrder")
@Validated
public class PaymentOrderMerchantController {

    @Resource
    private PaymentOrderService paymentOrderService;

    @PostMapping("/getPaymentOrderAll")
    @ApiOperation(value = "查询总包+分包支付订单", notes = "查询总包+分包支付订单", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentOrderMerchantDto", value = "查询条件", required = true, dataType = "PaymentOrderMerchantDto")
    })
    @LoginRequired
    public ReturnJson getPaymentOrderAll(@ApiParam(hidden = true) @RequestAttribute("userId") String merchantId, @Valid @RequestBody PaymentOrderMerchantDto paymentOrderMerchantDto) {
        return paymentOrderService.getPaymentOrder(merchantId, paymentOrderMerchantDto);
    }

    @GetMapping("/getPaymentOrderInfo")
    @ApiOperation(value = "查询总包+分包支付订单详情", notes = "查询总包+分包支付订单详情", httpMethod = "GET")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "总包+分包支付订单ID", required = true)
    })
    public ReturnJson getPaymentOrderInfo(@NotBlank(message = "支付订单ID不能为空") @RequestParam(required = false) String id) {
        return paymentOrderService.getPaymentOrderInfo(id);
    }

    @PostMapping("/saveOrUpdata")
    @ApiOperation(value = "创建或修改总包+分包支付订单", notes = "创建或修改总包+分包支付订单", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "addPaymentOrderDto", value = "新建或修改的支付订单", required = true, dataType = "AddPaymentOrderDto")
    })
    @LoginRequired
    public ReturnJson saveOrUpdataPaymentOrder(@Valid @RequestBody AddPaymentOrderDto addPaymentOrderDto,@RequestAttribute(value = "userId") String merchantId) {
        return paymentOrderService.saveOrUpdataPaymentOrder(addPaymentOrderDto,merchantId);
    }


    @PostMapping("/offlinePayment")
    @ApiOperation(value = "线下支付", notes = "线下支付", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentOrderId", value = "支付订单ID", required = true),
            @ApiImplicitParam(name = "turnkeyProjectPayment", value = "支付回单存储地址", required = true)
    })
    public ReturnJson offlinePayment(@NotBlank @RequestParam(required = false) String paymentOrderId,
                                     @NotBlank @RequestParam(required = false) String turnkeyProjectPayment) {
        return paymentOrderService.offlinePayment(paymentOrderId, turnkeyProjectPayment);
    }

}
