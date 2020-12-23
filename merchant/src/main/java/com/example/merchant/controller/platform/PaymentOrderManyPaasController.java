package com.example.merchant.controller.platform;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.AddPaymentOrderManyDTO;
import com.example.merchant.dto.platform.PaymentOrderDTO;
import com.example.merchant.exception.CommonException;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.PaymentOrderManyService;
import com.example.merchant.service.PaymentOrderService;
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
@Api(value = "平台端众包支付管理", tags = "平台端众包支付管理")
@RestController
@RequestMapping("/platform/paymentOrderMany")
@Validated
public class PaymentOrderManyPaasController {

    @Resource
    private PaymentOrderManyService paymentOrderManyService;

    @Resource
    private PaymentOrderService paymentOrderService;

    @PostMapping("/findMerchant")
    @ApiOperation(value = "查询商户", notes = "查询商户", httpMethod = "POST")
    @LoginRequired
    public ReturnJson findMerchant(@ApiParam(hidden = true) @RequestAttribute("userId") String managersId) {
        return paymentOrderService.findMerchantPaas(managersId);
    }

    @LoginRequired
    @PostMapping("/getPaymentOrderManyAll")
    @ApiOperation(value = "查询众包订单", notes = "查询众包订单", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "paymentOrderDto", value = "查询条件", required = true, dataType = "PaymentOrderDTO")})
    public ReturnJson getPaymentOrderManyAll(@Valid @RequestBody PaymentOrderDTO paymentOrderDto, @RequestAttribute("userId") @ApiParam(hidden = true) String managersId) throws CommonException {
        return paymentOrderManyService.getPaymentOrderManyPaas(paymentOrderDto, managersId);
    }

    @GetMapping("/getPaymentOrderManyInfo")
    @ApiOperation(value = "查询众包支付订单详情", notes = "查询众包支付订单详情", httpMethod = "GET")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "支付订单ID", required = true)})
    public ReturnJson getPaymentOrderManyInfo(@NotBlank(message = "支付订单ID不能为空") @RequestParam(required = false) String id) {
        return paymentOrderManyService.getPaymentOrderManyInfo(id);
    }

    @PostMapping("/saveOrUpdata")
    @ApiOperation(value = "创建或修改众包支付订单", notes = "创建或修改众包支付订单", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "addPaymentOrderManyDto", value = "添加众包支付订单", required = true, dataType = "AddPaymentOrderManyDTO")})
    public ReturnJson saveOrUpdataPaymentOrderMany(@Valid @RequestBody AddPaymentOrderManyDTO addPaymentOrderManyDto, @RequestParam String merchantId) {
        return paymentOrderManyService.saveOrUpdataPaymentOrderMany(addPaymentOrderManyDto, merchantId);
    }

    @PostMapping("/confirmPayment")
    @ApiOperation(value = "众包确认收款", notes = "众包确认收款", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "paymentOrderId", value = "众包支付订单ID", required = true)})
    public ReturnJson confirmPaymentMany(String paymentOrderId) {
        return paymentOrderManyService.confirmPaymentManyPaas(paymentOrderId);
    }
}
