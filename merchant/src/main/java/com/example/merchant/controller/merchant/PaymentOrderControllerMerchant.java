package com.example.merchant.controller.merchant;


import com.example.common.util.ReturnJson;
import com.example.merchant.dto.PaymentOrderDto;
import com.example.merchant.service.PaymentOrderService;
import com.example.mybatis.entity.PaymentInventory;
import com.example.mybatis.entity.PaymentOrder;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 * 总包+分包支付单信息
 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/merchant/paymentOrder")
@Api(value = "总包+分包支付管理", tags = "总包+分包支付管理")
@Validated
public class PaymentOrderControllerMerchant {

    @Autowired
    private PaymentOrderService paymentOrderService;

    @PostMapping("/getPaymentOrderAll")
    @ApiOperation(value = "查询总包+分包支付订单", notes = "查询总包+分包支付订单", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="paymentOrderDto",value = "查询条件",required = true, dataType = "PaymentOrderDto")})
    public ReturnJson getPaymentOrderAll(@Valid @RequestBody PaymentOrderDto paymentOrderDto){
        return paymentOrderService.getPaymentOrder(paymentOrderDto);
    }

    @GetMapping("/getPaymentOrderInfo")
    @ApiOperation(value = "查询总包+分包支付订单详情", notes = "查询总包+分包支付订单详情", httpMethod = "GET")
    @ApiImplicitParams(value={@ApiImplicitParam(name="id",value = "总包+分包支付订单ID",required = true)})
    public ReturnJson getPaymentOrderInfo(@NotBlank(message = "支付订单ID不能为空") @RequestParam(required = false) String id){
        return paymentOrderService.getPaymentOrderInfo(id);
    }

    @PostMapping("/saveOrUpdata")
    @ApiOperation(value = "创建或修改总包+分包支付订单", notes = "创建或修改总包+分包支付订单", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="paymentOrder",value = "新建或修改的支付订单",required = true, dataType = "PaymentOrder"),
            @ApiImplicitParam(name="paymentInventories",value = "支付清单",required = true, allowMultiple = true, dataType = "PaymentInventory")})
    public ReturnJson saveOrUpdataPaymentOrder(@NotEmpty(message = "订单内容不能为空") @RequestBody PaymentOrder paymentOrder, @NotEmpty(message = "支付清单不能为空") @RequestBody List<PaymentInventory> paymentInventories){
        return paymentOrderService.saveOrUpdataPaymentOrder(paymentOrder, paymentInventories);
    }


    @PostMapping("/offlinePayment")
    @ApiOperation(value = "线下支付", notes = "线下支付", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="paymentOrderId",value = "支付订单ID",required = true),
            @ApiImplicitParam(name="turnkeyProjectPayment",value = "支付回单存储地址",required = true)})
    public ReturnJson offlinePayment(@NotBlank @RequestParam(required = false) String paymentOrderId, @NotBlank @RequestParam(required = false) String turnkeyProjectPayment){
        return paymentOrderService.offlinePayment(paymentOrderId, turnkeyProjectPayment);
    }

}
