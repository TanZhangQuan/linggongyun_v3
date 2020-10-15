package com.example.merchant.controller.merchant;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.AddPaymentOrderManyDto;
import com.example.merchant.dto.merchant.PaymentOrderMerchantDto;
import com.example.merchant.service.PaymentOrderManyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 众包支付单信息
 * 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "商户端众包支付管理", tags = "商户端众包支付管理")
@RestController
@RequestMapping("/merchant/paymentOrderMany")
public class PaymentOrderManyMerchantController {

    @Resource
    private PaymentOrderManyService paymentOrderManyService;

    @PostMapping("/getPaymentOrderManyAll")
    @ApiOperation(value = "查询众包订单", notes = "查询众包订单", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentOrderMerchantDto", value = "查询条件", required = true, dataType = "PaymentOrderMerchantDto")
    })
    public ReturnJson getPaymentOrderManyAll(@Valid @RequestBody PaymentOrderMerchantDto paymentOrderMerchantDto) {
        return paymentOrderManyService.getPaymentOrderMany(paymentOrderMerchantDto);
    }

    @GetMapping("/getPaymentOrderManyInfo")
    @ApiOperation(value = "查询众包支付订单详情", notes = "查询众包支付订单详情", httpMethod = "GET")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "支付订单ID", required = true)
    })
    public ReturnJson getPaymentOrderManyInfo(@NotBlank(message = "支付订单ID不能为空") @RequestParam(required = false) String id) {
        return paymentOrderManyService.getPaymentOrderManyInfo(id);
    }

    @PostMapping("/saveOrUpdata")
    @ApiOperation(value = "创建或修改众包支付订单", notes = "创建或修改众包支付订单", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "addPaymentOrderManyDto", value = "添加众包支付订单的内容", required = true, dataType = "AddPaymentOrderManyDto")
    })
    public ReturnJson saveOrUpdataPaymentOrderMany(@Valid @RequestBody AddPaymentOrderManyDto addPaymentOrderManyDto) {
        return paymentOrderManyService.saveOrUpdataPaymentOrderMany(addPaymentOrderManyDto);
    }

    @PostMapping("/offlinePayment")
    @ApiOperation(value = "众包支付订单线下支付", notes = "众包支付订单线下支付", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "支付订单ID", required = true),
            @ApiImplicitParam(name = "manyPayment", value = "支付回单地址", required = true)
    })
    public ReturnJson offlinePayment(@NotBlank @RequestParam(required = false) String id, @NotBlank @RequestParam(required = false) String manyPayment) {
        return paymentOrderManyService.offlinePayment(id, manyPayment);
    }
}
