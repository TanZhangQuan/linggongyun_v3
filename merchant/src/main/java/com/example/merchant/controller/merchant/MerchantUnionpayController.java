package com.example.merchant.controller.merchant;


import com.example.common.util.ReturnJson;
import com.example.merchant.service.MerchantService;
import com.example.merchant.service.MerchantUnionpayService;
import com.example.merchant.service.TaxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 商户端支付方式相关接口（移动到服务商Controller）
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "商户端支付方式相关接口", tags = "商户端支付方式相关接口")
@RestController
@RequestMapping("/merchant/paymethod")
@Validated
public class MerchantUnionpayController {

    @Resource
    private MerchantService merchantService;
    @Resource
    private MerchantUnionpayService merchantUnionpayService;
    @Resource
    private TaxService taxService;

    @GetMapping("/queryOfflineTaxList")
    @ApiOperation(value = "查询线下支付关联的服务商", notes = "查询线下支付关联的服务商")
    public ReturnJson queryOfflineTaxList(@ApiParam(value = "商户ID", hidden = true) @RequestAttribute(value = "userId") String merchantId,
                                          @ApiParam(value = "当前页") @Min(value = 1, message = "当前页数最小为1") @RequestParam(required = false) long pageNo,
                                          @ApiParam(value = "每页条数") @Min(value = 1, message = "每页页数最小为1") @RequestParam(required = false) long pageSize) {
        return merchantUnionpayService.queryOfflineTaxList(merchantId, pageNo, pageSize);
    }

    @GetMapping("/queryUninopayTaxList")
    @ApiOperation(value = "查询银联支付关联的服务商", notes = "查询银联支付关联的服务商")
    public ReturnJson queryUninopayTaxList(@ApiParam(value = "商户ID", hidden = true) @RequestAttribute(value = "userId") String merchantId,
                                           @ApiParam(value = "当前页") @Min(value = 1, message = "当前页数最小为1") @RequestParam(required = false) long pageNo,
                                           @ApiParam(value = "每页条数") @Min(value = 1, message = "每页页数最小为1") @RequestParam(required = false) long pageSize) {
        return merchantUnionpayService.queryUninopayTaxList(merchantId, pageNo, pageSize);
    }

    @GetMapping("/queryCompanyTaxInfo")
    @ApiOperation(value = "查询商户服务商合作信息", notes = "查询商户服务商合作信息")
    public ReturnJson queryCompanyTaxList(@ApiParam(value = "商户ID", hidden = true) @RequestAttribute(value = "userId") String merchantId,
                                          @ApiParam(value = "服务商ID") @NotBlank(message = "请选择服务商") @RequestAttribute(required = false) String taxId) {
        return merchantService.queryCompanyTaxInfo(merchantId, taxId);
    }

    @GetMapping("/queryTaxInBankInfo")
    @ApiOperation(value = "查询服务商线下来款银行账号信息", notes = "查询服务商线下来款银行账号信息")
    public ReturnJson queryTaxInBankInfo(@ApiParam(value = "服务商ID") @NotBlank(message = "请选择服务商") @RequestAttribute(required = false) String taxId) {
        return taxService.queryTaxInBankInfo(taxId);
    }

    @GetMapping("/queryCompanyUnionpayDetail")
    @ApiOperation(value = "查询商户银联详情", notes = "查询商户银联详情")
    public ReturnJson queryCompanyUnionpayDetail(@ApiParam(value = "商户ID", hidden = true) @RequestAttribute(value = "userId") String merchantId,
                                                 @ApiParam(value = "服务商ID") @NotBlank(message = "请选择服务商") @RequestAttribute(required = false) String taxId) throws Exception {
        return merchantUnionpayService.queryCompanyUnionpayDetail(merchantId, taxId);
    }

}
