package com.example.merchant.controller.merchant;


import com.example.common.enums.UnionpayBankType;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.AssociatedTasksDTO;
import com.example.merchant.dto.merchant.AddPaymentOrderDTO;
import com.example.merchant.dto.merchant.PaymentOrderMerchantDTO;
import com.example.merchant.dto.merchant.PaymentOrderPayDTO;
import com.example.merchant.exception.CommonException;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.CompanyUnionpayService;
import com.example.merchant.service.MerchantService;
import com.example.merchant.service.PaymentOrderService;
import com.example.mybatis.entity.Merchant;
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
@Api(value = "商户端总包+分包支付管理", tags = "商户端总包+分包支付管理")
@RestController
@RequestMapping("/merchant/paymentOrder")
@Validated
public class PaymentOrderMerchantController {

    @Resource
    private MerchantService merchantService;

    @Resource
    private CompanyUnionpayService companyUnionpayService;

    @Resource
    private PaymentOrderService paymentOrderService;

    @PostMapping("/getPaymentOrderAll")
    @ApiOperation(value = "查询总包+分包支付订单", notes = "查询总包+分包支付订单")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentOrderMerchantDto", value = "查询条件", required = true, dataType = "PaymentOrderMerchantDTO")
    })
    @LoginRequired
    public ReturnJson getPaymentOrderAll(@ApiParam(hidden = true) @RequestAttribute("userId") String merchantId,
                                         @Valid @RequestBody PaymentOrderMerchantDTO paymentOrderMerchantDto) {
        return paymentOrderService.getPaymentOrder(merchantId, paymentOrderMerchantDto);
    }

    @GetMapping("/getPaymentOrderInfo")
    @ApiOperation(value = "查询总包+分包支付订单详情", notes = "查询总包+分包支付订单详情")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "总包+分包支付订单ID", required = true)
    })
    public ReturnJson getPaymentOrderInfo(@NotBlank(message = "支付订单ID不能为空") @RequestParam(required = false) String id) {
        return paymentOrderService.getPaymentOrderInfo(id);
    }

    @PostMapping("/saveOrUpdata")
    @ApiOperation(value = "创建或修改总包+分包支付订单", notes = "创建或修改总包+分包支付订单")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "addPaymentOrderDto", value = "新建或修改的支付订单", required = true, dataType = "AddPaymentOrderDTO")
    })
    @LoginRequired
    public ReturnJson saveOrUpdataPaymentOrder(@Valid @RequestBody AddPaymentOrderDTO addPaymentOrderDto,
                                               @RequestAttribute(value = "userId") @ApiParam(hidden = true) String merchantId) throws CommonException {
        return paymentOrderService.saveOrUpdataPaymentOrder(addPaymentOrderDto, merchantId);
    }

    @PostMapping("/paymentOrderPay")
    @ApiOperation(value = "商户总包支付", notes = "商户总包支付")
    public ReturnJson paymentOrderPay(@Valid @RequestBody PaymentOrderPayDTO paymentOrderPayDTO) throws Exception {
        return paymentOrderService.paymentOrderPay(paymentOrderPayDTO);
    }

    @PostMapping("/associatedTasks")
    @ApiOperation(value = "商户选择可关联任务", notes = "商户选择可关联任务")
    @LoginRequired
    public ReturnJson associatedTask(@RequestAttribute("userId") @ApiParam(hidden = true) String merchantId,
                                     AssociatedTasksDTO associatedTasksDto) {
        return paymentOrderService.associatedTask(merchantId, associatedTasksDto);
    }

    @PostMapping("/gradientPrice")
    @LoginRequired
    @ApiOperation(value = "获取梯度价", notes = "获取梯度价")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taxId", value = "服务商ID", required = true),
            @ApiImplicitParam(name = "packageStatus", value = "支付类型0总包 1 众包", required = true)})
    public ReturnJson gradientPrice(@RequestAttribute("userId") @ApiParam(hidden = true) String merchantId,
                                    @NotBlank(message = "服务商ID不能为空") @RequestParam(required = false) String taxId,
                                    @NotNull(message = "支付类型不能为空！") @RequestParam(required = false) Integer packageStatus) {
        return paymentOrderService.gradientPrice(merchantId, taxId, packageStatus);
    }

    @GetMapping("/queryCompanyUnionpayBalance")
    @ApiOperation(value = "查询商户相应银联的余额详情", notes = "查询商户相应银联的余额详情")
    @LoginRequired
    public ReturnJson queryCompanyUnionpayDetail(@ApiParam(value = "商户ID", hidden = true) @RequestAttribute(value = "userId") String merchantId,
                                                 @ApiParam(value = "服务商ID") @NotBlank(message = "请选择服务商") @RequestParam(required = false) String taxId,
                                                 @ApiParam(value = "银联银行类型") @NotNull(message = "请选择银联银行类型") @RequestParam(required = false) UnionpayBankType unionpayBankType) throws Exception {

        Merchant merchant = merchantService.getById(merchantId);
        if (merchant == null) {
            return ReturnJson.error("商户员工不存在");
        }

        return companyUnionpayService.queryCompanyUnionpayDetail(merchant.getCompanyId(), taxId, unionpayBankType);
    }

    @PostMapping("/updatePaymentInventory")
    @ApiOperation(value = "修改支付清单创客银行卡号", notes = "修改支付清单创客银行卡号")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "paymentInventoryId", value = "支付清单ID", required = true),
            @ApiImplicitParam(name = "bankCode", value = "银行卡号", required = true)})
    public ReturnJson updatePaymentInventory(@RequestParam String paymentInventoryId ,@RequestParam String bankCode) {
        return paymentOrderService.updatePaymentInventory(paymentInventoryId, bankCode);
    }

}
