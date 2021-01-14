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
    @ApiOperation(value = "查询总包+分包支付订单", notes = "查询总包+分包支付订单")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "paymentOrderDto", value = "查询条件", required = true, dataType = "PaymentOrderDTO")})
    public ReturnJson getPaymentOrderAll(@Valid @RequestBody PaymentOrderDTO paymentOrderDto, @ApiParam(hidden = true) @RequestAttribute(value = "userId") String managersId) throws CommonException {
        return paymentOrderService.getPaymentOrderPaas(paymentOrderDto, managersId);
    }

    @GetMapping("/getPaymentOrderInfo")
    @ApiOperation(value = "查询总包+分包支付订单详情", notes = "查询总包+分包支付订单详情")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "总包+分包支付订单ID", required = true)})
    public ReturnJson getPaymentOrderInfo(@NotBlank(message = "支付订单ID不能为空") @RequestParam(required = false) String id) {
        return paymentOrderService.getPaymentOrderInfo(id);
    }

    @LoginRequired
    @PostMapping("/findMerchant")
    @ApiOperation(value = "查询商户", notes = "查询商户")
    public ReturnJson findMerchant(@NotBlank(message = "支付订单不能为空！") @ApiParam(hidden = true) @RequestAttribute(value = "userId") String managersId) {
        return paymentOrderService.findMerchantPaas(managersId);
    }

    @PostMapping("/saveOrUpdata")
    @ApiOperation(value = "创建或修改总包+分包支付订单", notes = "创建或修改总包+分包支付订单")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "addPaymentOrderDto", value = "新建或修改的支付订单", required = true, dataType = "AddPaymentOrderDTO")})
    public ReturnJson saveOrUpdataPaymentOrder(@Valid @RequestBody AddPaymentOrderDTO addPaymentOrderDto, @RequestParam String merchantId) throws CommonException {
        return paymentOrderService.saveOrUpdataPaymentOrder(addPaymentOrderDto, merchantId);
    }

    @PostMapping("/paymentOrderAudit")
    @ApiOperation(value = "总包+分包审核", notes = "总包+分包审核")
    public ReturnJson paymentOrderAudit(@ApiParam(value = "总包+分包支付订单ID") @NotBlank(message = "请选择总包+分包支付订单") @RequestParam(required = false) String paymentOrderId,
                                        @ApiParam(value = "是否审核通过") @NotBlank(message = "请选择是否审核通过") @RequestParam(required = false) Boolean boolPass,
                                        @ApiParam(value = "拒绝原因") @RequestParam(required = false) String reasonsForRejection) throws Exception {
        return paymentOrderService.paymentOrderAudit(paymentOrderId, boolPass, reasonsForRejection);
    }

    @PostMapping("subpackagePayment")
    @ApiOperation(value = "分包支付", notes = "分包支付")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "paymentOrderId", value = "总包支付订单ID", required = true),
            @ApiImplicitParam(name = "subpackagePayment", value = "分包支付回单存储地址", required = true)})
    public ReturnJson subpackagePay(@ApiParam(value = "总包+分包支付订单ID") @NotBlank(message = "请选择总包+分包支付订单") @RequestParam(required = false) String paymentOrderId,
                                    @ApiParam(value = "分包支付回单") @RequestParam(required = false) String subpackagePayment) throws Exception {
        return paymentOrderService.subpackagePay(paymentOrderId, subpackagePayment);
    }

    @PostMapping("/associatedTasks")
    @ApiOperation(value = "商户选择可关联任务", notes = "商户选择可关联任务")
    public ReturnJson associatedTasks(@NotBlank(message = "商户ID不能为空！") @RequestParam String merchantId,
                                      AssociatedTasksDTO associatedTasksDto) {
        return paymentOrderService.associatedTasks(merchantId, associatedTasksDto);
    }

    @PostMapping("/gradientPrice")
    @ApiOperation(value = "获取梯度价", notes = "获取梯度价")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "merchantId", value = "商户ID", required = true),
            @ApiImplicitParam(name = "taxId", value = "服务商ID", required = true),
            @ApiImplicitParam(name = "packageStatus", value = "支付类型0总包 1 众包", required = true)})
    public ReturnJson gradientPrice(@NotBlank(message = "商户ID") @RequestParam(required = false) String merchantId,
                                    @NotBlank(message = "服务商ID不能为空") @RequestParam(required = false) String taxId,
                                    @NotNull(message = "支付类型不能为空") @RequestParam(required = false) Integer packageStatus) {
        return paymentOrderService.gradientPrice(merchantId, taxId, packageStatus);
    }

}
