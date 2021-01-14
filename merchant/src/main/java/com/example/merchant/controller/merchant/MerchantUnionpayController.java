package com.example.merchant.controller.merchant;


import com.example.common.util.ReturnJson;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.MerchantService;
import com.example.merchant.service.CompanyUnionpayService;
import com.example.merchant.service.TaxService;
import com.example.mybatis.entity.Merchant;
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
    private CompanyUnionpayService companyUnionpayService;

    @Resource
    private TaxService taxService;

    @GetMapping("/queryOfflineTaxList")
    @ApiOperation(value = "查询线下支付关联的服务商", notes = "查询线下支付关联的服务商")
    @LoginRequired
    public ReturnJson queryOfflineTaxList(@ApiParam(value = "商户ID", hidden = true) @RequestAttribute(value = "userId") String merchantId,
                                          @ApiParam(value = "当前页") @Min(value = 1, message = "当前页数最小为1") @RequestParam(required = false) long pageNo,
                                          @ApiParam(value = "每页条数") @Min(value = 1, message = "每页页数最小为1") @RequestParam(required = false) long pageSize) {

        Merchant merchant = merchantService.getById(merchantId);
        if (merchant == null){
            return ReturnJson.error("商户员工不存在");
        }

        return companyUnionpayService.queryOfflineTaxList(merchant.getCompanyId(), pageNo, pageSize);
    }

    @GetMapping("/queryUninopayTaxList")
    @ApiOperation(value = "查询银联支付关联的服务商", notes = "查询银联支付关联的服务商")
    @LoginRequired
    public ReturnJson queryUninopayTaxList(@ApiParam(value = "商户ID", hidden = true) @RequestAttribute(value = "userId") String merchantId,
                                           @ApiParam(value = "当前页") @Min(value = 1, message = "当前页数最小为1") @RequestParam(required = false) long pageNo,
                                           @ApiParam(value = "每页条数") @Min(value = 1, message = "每页页数最小为1") @RequestParam(required = false) long pageSize) {

        Merchant merchant = merchantService.getById(merchantId);
        if (merchant == null){
            return ReturnJson.error("商户员工不存在");
        }

        return companyUnionpayService.queryUninopayTaxList(merchant.getCompanyId(), pageNo, pageSize);
    }

    @GetMapping("/queryCompanyTaxInfo")
    @ApiOperation(value = "查询商户服务商合作信息", notes = "查询商户服务商合作信息")
    @LoginRequired
    public ReturnJson queryCompanyTaxList(@ApiParam(value = "商户ID", hidden = true) @RequestAttribute(value = "userId") String merchantId,
                                          @ApiParam(value = "服务商ID") @NotBlank(message = "请选择服务商") @RequestParam(required = false) String taxId) {

        Merchant merchant = merchantService.getById(merchantId);
        if (merchant == null){
            return ReturnJson.error("商户员工不存在");
        }

        return merchantService.queryCompanyTaxInfo(merchant.getCompanyId(), taxId);
    }

    @GetMapping("/queryTaxInBankInfo")
    @ApiOperation(value = "查询服务商线下来款银行账号信息", notes = "查询服务商线下来款银行账号信息")
    public ReturnJson queryTaxInBankInfo(@ApiParam(value = "服务商ID") @NotBlank(message = "请选择服务商") @RequestParam(required = false) String taxId) {
        return taxService.queryTaxInBankInfo(taxId);
    }

    @GetMapping("/queryCompanyUnionpayDetail")
    @ApiOperation(value = "查询商户银联详情", notes = "查询商户银联详情")
    @LoginRequired
    public ReturnJson queryCompanyUnionpayDetail(@ApiParam(value = "商户ID", hidden = true) @RequestAttribute(value = "userId") String merchantId,
                                                 @ApiParam(value = "服务商ID") @NotBlank(message = "请选择服务商") @RequestParam(required = false) String taxId) throws Exception {

        Merchant merchant = merchantService.getById(merchantId);
        if (merchant == null){
            return ReturnJson.error("商户员工不存在");
        }

        return companyUnionpayService.queryCompanyUnionpayDetail(merchant.getCompanyId(), taxId);
    }

}