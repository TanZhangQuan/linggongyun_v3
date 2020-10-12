package com.example.merchant.controller.platform;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.AddPaymentOrderManyDto;
import com.example.merchant.dto.platform.PaymentOrderDto;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.PaymentOrderManyService;
import com.example.merchant.service.PaymentOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 众包支付单信息
 * 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/platform/paymentOrderMany")
@Api(value = "平台端众包支付管理", tags = "平台端众包支付管理")
public class PaymentOrderManyPaasController {

    @Autowired
    private PaymentOrderManyService paymentOrderManyService;

    @Autowired
    private PaymentOrderService paymentOrderService;


    @RequiresRoles("admin")
    @PostMapping("/findMerchant")
    @ApiOperation(value = "查询商户", notes = "查询商户", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "managersId", value = "管理人员ID", required = true)})
    public ReturnJson findMerchant(@NotBlank(message = "管理人员ID不能为空！") @RequestParam String managersId) {
        return paymentOrderService.findMerchantPaas(managersId);
    }


    @PostMapping("/getPaymentOrderManyAll")
    @ApiOperation(value = "查询众包订单", notes = "查询众包订单", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "paymentOrderDto", value = "查询条件", required = true, dataType = "PaymentOrderDto")})
    public ReturnJson getPaymentOrderManyAll(@Valid @RequestBody PaymentOrderDto paymentOrderDto) throws CommonException {
        return paymentOrderManyService.getPaymentOrderManyPaas(paymentOrderDto);
    }

    @GetMapping("/getPaymentOrderManyInfo")
    @ApiOperation(value = "查询众包支付订单详情", notes = "查询众包支付订单详情", httpMethod = "GET")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "支付订单ID", required = true)})
    public ReturnJson getPaymentOrderManyInfo(@NotBlank(message = "支付订单ID不能为空") @RequestParam(required = false) String id) {
        return paymentOrderManyService.getPaymentOrderManyInfoPaas(id);
    }

    @PostMapping("/saveOrUpdata")
    @ApiOperation(value = "创建或修改众包支付订单", notes = "创建或修改众包支付订单", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "addPaymentOrderManyDto", value = "添加众包支付订单", required = true, dataType = "AddPaymentOrderManyDto")})
    public ReturnJson saveOrUpdataPaymentOrderMany(@NotEmpty(message = "订单内容不能为空") @RequestBody AddPaymentOrderManyDto addPaymentOrderManyDto) {
        return paymentOrderManyService.saveOrUpdataPaymentOrderMany(addPaymentOrderManyDto);
    }

    @PostMapping("/confirmPayment")
    @ApiOperation(value = "众包确认支付", notes = "众包确认支付", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "众包支付订单ID", required = true)})
    public ReturnJson confirmPaymentMany(String id) {
        return paymentOrderManyService.confirmPaymentManyPaas(id);
    }
}