package com.example.merchant.controller.merchant;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.AddPaymentOrderManyDTO;
import com.example.merchant.dto.merchant.PaymentOrderMerchantDTO;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.PaymentOrderManyService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class PaymentOrderManyMerchantController {

    @Resource
    private PaymentOrderManyService paymentOrderManyService;

    @PostMapping("/getPaymentOrderManyAll")
    @LoginRequired
    @ApiOperation(value = "查询众包订单", notes = "查询众包订单", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentOrderMerchantDto", value = "查询条件", required = true, dataType = "PaymentOrderMerchantDTO")
    })
    public ReturnJson getPaymentOrderManyAll(@ApiParam(hidden = true) @RequestAttribute("userId") String merchantId, @Valid @RequestBody PaymentOrderMerchantDTO paymentOrderMerchantDto) {
        return paymentOrderManyService.getPaymentOrderMany(merchantId, paymentOrderMerchantDto);
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
            @ApiImplicitParam(name = "addPaymentOrderManyDto", value = "添加众包支付订单的内容", required = true, dataType = "AddPaymentOrderManyDTO")
    })
    @LoginRequired
    public ReturnJson saveOrUpdataPaymentOrderMany(@Valid @RequestBody AddPaymentOrderManyDTO addPaymentOrderManyDto, @ApiParam(hidden = true) @RequestAttribute("userId") String merchantId) {
        return paymentOrderManyService.saveOrUpdataPaymentOrderMany(addPaymentOrderManyDto, merchantId);
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
