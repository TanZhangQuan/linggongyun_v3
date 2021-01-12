package com.example.merchant.controller.platform;


import com.example.common.util.ReturnJson;
import com.example.merchant.dto.AssociatedTasksDTO;
import com.example.merchant.dto.merchant.AddPaymentOrderDTO;
import com.example.merchant.dto.platform.PaymentOrderDTO;
import com.example.merchant.exception.CommonException;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.PaymentOrderService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 总包+分包支付单信息
 * 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "平台端总包+分包支付管理", tags = "平台端总包+分包支付管理")
@RestController
@RequestMapping("/platform/paymentOrder")
@Validated
public class PaymentOrderPaasController {

    @Resource
    private PaymentOrderService paymentOrderService;

    @PostMapping("/getPaymentOrderAll")
    @LoginRequired
    @ApiOperation(value = "查询总包+分包支付订单", notes = "查询总包+分包支付订单", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "paymentOrderDto", value = "查询条件", required = true, dataType = "PaymentOrderDTO")})
    public ReturnJson getPaymentOrderAll(@Valid @RequestBody PaymentOrderDTO paymentOrderDto, @ApiParam(hidden = true) @RequestAttribute(value = "userId") String managersId) throws CommonException {
        return paymentOrderService.getPaymentOrderPaas(paymentOrderDto, managersId);
    }

    @GetMapping("/getPaymentOrderInfo")
    @ApiOperation(value = "查询总包+分包支付订单详情", notes = "查询总包+分包支付订单详情", httpMethod = "GET")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "总包+分包支付订单ID", required = true)})
    public ReturnJson getPaymentOrderInfo(@NotBlank(message = "支付订单ID不能为空") @RequestParam(required = false) String id) {
        return paymentOrderService.getPaymentOrderInfo(id);
    }

    @LoginRequired
    @PostMapping("/findMerchant")
    @ApiOperation(value = "查询商户", notes = "查询商户", httpMethod = "POST")
    public ReturnJson findMerchant(@NotBlank(message = "支付订单不能为空！") @ApiParam(hidden = true) @RequestAttribute(value = "userId") String managersId) {
        return paymentOrderService.findMerchantPaas(managersId);
    }

    @PostMapping("/saveOrUpdata")
    @ApiOperation(value = "创建或修改总包+分包支付订单", notes = "创建或修改总包+分包支付订单", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "addPaymentOrderDto", value = "新建或修改的支付订单", required = true, dataType = "AddPaymentOrderDTO")})
    public ReturnJson saveOrUpdataPaymentOrder(@Valid @RequestBody AddPaymentOrderDTO addPaymentOrderDto, @RequestParam String merchantId) throws CommonException {
        return paymentOrderService.saveOrUpdataPaymentOrder(addPaymentOrderDto, merchantId);
    }

    @PostMapping("/offlinePayment")
    @ApiOperation(value = "总包线下支付", notes = "总包线下支付", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "paymentOrderId", value = "支付订单ID", required = true),
            @ApiImplicitParam(name = "turnkeyProjectPayment", value = "支付回单存储地址", required = true)})
    public ReturnJson offlinePayment(@NotBlank @RequestParam(required = false) String paymentOrderId, @NotBlank @RequestParam(required = false) String turnkeyProjectPayment) {
        return paymentOrderService.offlinePaymentPaas(paymentOrderId, turnkeyProjectPayment);
    }

    @PostMapping("subpackagePayment")
    @ApiOperation(value = "分包线下支付", notes = "分包线下支付", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "paymentOrderId", value = "总包支付订单ID", required = true),
            @ApiImplicitParam(name = "subpackagePayment", value = "分包支付回单存储地址", required = true)})
    public ReturnJson subpackagePay(@NotBlank(message = "支付订单不能为空！") @RequestParam(required = false) String paymentOrderId,
                                    @NotBlank(message = "分包支付回单URL不能为空！") @RequestParam(required = false) String subpackagePayment) {
        return paymentOrderService.subpackagePayPaas(paymentOrderId, subpackagePayment);
    }

    @PostMapping("/confirmReceipt")
    @ApiOperation(value = "确认收款", notes = "确认收款", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "paymentOrderId", value = "支付订单ID", required = true)})
    public ReturnJson confirmReceipt(@NotBlank(message = "支付订单不能为空！") @RequestParam(required = false) String paymentOrderId) {
        return paymentOrderService.confirmReceiptPaas(paymentOrderId);
    }

    @PostMapping("/associatedTasks")
    @ApiOperation(value = "商户选择可关联任务", notes = "商户选择可关联任务", httpMethod = "POST")
    public ReturnJson associatedTasks(@NotBlank(message = "商户ID不能为空！") @RequestParam String merchantId,
                                      AssociatedTasksDTO associatedTasksDto) {
        return paymentOrderService.associatedTasks(merchantId, associatedTasksDto);
    }

    @PostMapping("/reject")
    @ApiOperation(value = "支付驳回", notes = "支付驳回", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "paymentOrderId", value = "支付订单ID", required = true),
            @ApiImplicitParam(name = "reasonsForRejection", value = "驳回理由", required = true)})
    public ReturnJson reject(@NotBlank(message = "支付订单不能为空！") @RequestParam(required = false) String paymentOrderId,
                             @NotBlank(message = "驳回理由不能为空！") @RequestParam(required = false) String reasonsForRejection) {
        return paymentOrderService.reject(paymentOrderId, reasonsForRejection);
    }

    @PostMapping("/gradientPrice")
    @ApiOperation(value = "获取梯度价", notes = "获取梯度价", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "merchantId", value = "商户ID", required = true),
            @ApiImplicitParam(name = "taxId", value = "服务商ID", required = true),
            @ApiImplicitParam(name = "packageStatus", value = "支付类型0总包 1 众包", required = true)})
    public ReturnJson gradientPrice(@NotBlank(message = "商户ID") @RequestParam(required = false) String merchantId,
                                    @NotBlank(message = "服务商ID不能为空") @RequestParam(required = false) String taxId,
                                    @NotNull(message = "支付类型不能为空") @RequestParam(required = false) Integer packageStatus) {
        return paymentOrderService.gradientPrice(merchantId, taxId, packageStatus);
    }

}
