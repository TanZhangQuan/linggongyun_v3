package com.example.merchant.controller;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.PaymentOrderDto;
import com.example.merchant.service.PaymentOrderManyService;
import com.example.mybatis.entity.PaymentInventory;
import com.example.mybatis.entity.PaymentOrderMany;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 * 众包支付单信息
 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/merchant/paymentOrderMany")
@Api(value = "众包支付管理", tags = "众包支付管理")
public class PaymentOrderManyController {

    @Autowired
    private PaymentOrderManyService paymentOrderManyService;

    @PostMapping("/getPaymentOrderManyAll")
    @ApiOperation(value = "查询众包订单", notes = "查询众包订单", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="paymentOrderDto",value = "查询条件",required = true, dataType = "PaymentOrderDto")})
    public ReturnJson getPaymentOrderManyAll(@Valid @RequestBody PaymentOrderDto paymentOrderDto){
        return paymentOrderManyService.getPaymentOrderMany(paymentOrderDto);
    }

    @GetMapping("/getPaymentOrderManyInfo")
    @ApiOperation(value = "查询众包支付订单详情", notes = "查询众包支付订单详情", httpMethod = "GET")
    @ApiImplicitParams(value={@ApiImplicitParam(name="id",value = "支付订单ID",required = true)})
    public ReturnJson getPaymentOrderManyInfo(@NotBlank(message = "支付订单ID不能为空") @RequestParam(required = false) String id) {
        return paymentOrderManyService.getPaymentOrderManyInfo(id);
    }

    @PostMapping("/saveOrUpdata")
    @ApiOperation(value = "创建或修改众包支付订单", notes = "创建或修改众包支付订单", httpMethod = "GET")
    @ApiImplicitParams(value={@ApiImplicitParam(name="paymentOrderMany",value = "支付订单",required = true, dataType = "PaymentOrderMany"),
            @ApiImplicitParam(name="paymentInventories",value = "支付订单清单",required = true, allowMultiple = true, dataType = "PaymentInventory")})
    public ReturnJson saveOrUpdataPaymentOrderMany(@NotEmpty(message = "订单内容不能为空") @RequestBody PaymentOrderMany paymentOrderMany, @NotEmpty(message = "支付清单不能为空") @RequestBody List<PaymentInventory> paymentInventories){
        return paymentOrderManyService.saveOrUpdataPaymentOrderMany(paymentOrderMany, paymentInventories);
    }

    @PostMapping("/offlinePayment")
    @ApiOperation(value = "众包支付订单线下支付", notes = "众包支付订单线下支付", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="id",value = "支付订单ID",required = true),@ApiImplicitParam(name="manyPayment",value = "支付回单地址",required = true)})
    public ReturnJson offlinePayment(@NotBlank @RequestParam(required = false) String id, @NotBlank @RequestParam(required = false) String manyPayment){
        return paymentOrderManyService.offlinePayment(id, manyPayment);
    }
}
