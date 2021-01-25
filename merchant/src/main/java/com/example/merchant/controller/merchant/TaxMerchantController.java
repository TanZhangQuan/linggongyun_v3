package com.example.merchant.controller.merchant;


import com.example.common.util.ReturnJson;
import com.example.merchant.dto.TaxListDTO;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.PaymentOrderManyService;
import com.example.merchant.service.PaymentOrderService;
import com.example.merchant.service.TaxService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 合作园区信息 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "商户端平台服务商", tags = "商户端平台服务商")
@RestController
@RequestMapping("/merchant/tax")
@Validated
public class TaxMerchantController {

    @Resource
    private TaxService taxService;

    @Resource
    private PaymentOrderService paymentOrderService;

    @Resource
    private PaymentOrderManyService paymentOrderManyService;

    @GetMapping("/getTaxAll")
    @LoginRequired
    @ApiOperation(value = "获取商户可用的平台服务商", notes = "获取商户可用的平台服务商")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "packageStatus", value = "合作类型0为总包，1为众包", required = true)
    })
    public ReturnJson getTaxAll(@ApiParam(hidden = true) @RequestAttribute(value = "userId") String merchantId, @NotNull(message = "包的类型不能为空，0为总包，1为众包") @RequestParam(required = false) Integer packageStatus) {
        return taxService.getTaxAll(merchantId, packageStatus);
    }

    @PostMapping("/queryTaxAll")
    @LoginRequired
    @ApiOperation(value = "获取商户可用的平台服务商", notes = "获取商户可用的平台服务商")
    public ReturnJson queryTaxAll(@ApiParam(hidden = true) @RequestAttribute(value = "userId") String merchantId, @Valid @RequestBody TaxListDTO taxListDto) {
        return taxService.getTaxList(taxListDto, merchantId);
    }

    @PostMapping("/queryTaxCompanyList")
    @LoginRequired
    @ApiOperation(value = "获取服务商与商户的交易清单", notes = "获取服务商与商户的交易清单")
    public ReturnJson queryTaxCompanyList(@ApiParam(hidden = true) @RequestAttribute(value = "userId") String userId,
                                          @RequestParam @NotBlank(message = "服务商ID不能为空") String taxId,
                                          @RequestParam(defaultValue = "1") Integer pageNo,
                                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return taxService.queryTaxCompanyList(taxId, userId, pageNo, pageSize);
    }

    @GetMapping("/queryCompanyFlowInfo")
    @LoginRequired
    @ApiOperation(value = "获取服务商与商户的交易流水", notes = "获取服务商与商户的交易流水")
    public ReturnJson queryCompanyFlowInfo(@ApiParam(hidden = true) @RequestAttribute(value = "userId") String userId,
                                          @RequestParam @NotBlank(message = "服务商ID不能为空") String taxId) {
        return taxService.queryCompanyFlowInfo(userId,taxId);
    }

    @GetMapping("/getPaymentOrderInfo")
    @ApiOperation(value = "查询总包+分包支付订单详情", notes = "查询总包+分包支付订单详情", httpMethod = "GET")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "总包+分包支付订单ID", required = true)
    })
    public ReturnJson getPaymentOrderInfo(@NotBlank(message = "支付订单ID不能为空") @RequestParam(required = false) String id) {
        return paymentOrderService.getPaymentOrderInfo(id);
    }

    @GetMapping("/getPaymentOrderManyInfo")
    @ApiOperation(value = "查询众包支付订单详情", notes = "查询众包支付订单详情", httpMethod = "GET")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "支付订单ID", required = true)
    })
    public ReturnJson getPaymentOrderManyInfo(@NotBlank(message = "支付订单ID不能为空") @RequestParam(required = false) String id) {
        return paymentOrderManyService.getPaymentOrderManyInfo(id);
    }
}
