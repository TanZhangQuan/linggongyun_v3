package com.example.merchant.controller.platform;


import com.example.common.enums.OrderType;
import com.example.common.enums.PaymentMethod;
import com.example.common.enums.TradeObject;
import com.example.common.enums.TradeStatus;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.TaxListDTO;
import com.example.merchant.dto.platform.AddInvoiceCatalogDTO;
import com.example.merchant.dto.platform.AddOrUpdateTaxUnionpayDTO;
import com.example.merchant.dto.platform.TaxDTO;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 合作园区信息 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "平台端服务商管理", tags = "平台端服务商管理")
@RestController
@RequestMapping("/platform/tax")
@Validated
public class TaxController {

    @Resource
    private TaxService taxService;

    @Resource
    private TaxUnionpayService taxUnionpayService;

    @Resource
    private MerchantService merchantService;

    @Resource
    private PaymentOrderService paymentOrderService;

    @Resource
    private PaymentHistoryService paymentHistoryService;

    @Resource
    private PaymentOrderManyService paymentOrderManyService;


    @GetMapping("/getTaxAll")
    @ApiOperation(value = "获取商户可用的平台服务商(平台端帮助商户创建支付订单时，通过选择的商户获取商户的服务商)", notes = "获取商户可用的平台服务商(平台端帮助商户创建支付订单时，通过选择的商户获取商户的服务商)")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "companyId", value = "商户的企业ID", required = true), @ApiImplicitParam(name = "packageStatus", value = "合作类型不能为空，0为总包，1为众包（建立支付订单通过支付订单的类型自动获取,不是选择）", required = true)})
    public ReturnJson getTaxAll(@NotNull(message = "商户ID不能为空") @RequestParam(required = false) String companyId, @NotBlank(message = "合作类型不能为空，0为总包，1为众包") @RequestParam(required = false) Integer packageStatus) {
        return taxService.getTaxPaasAll(companyId, packageStatus);
    }

    @GetMapping("/getCatalogAll")
    @ApiOperation(value = "获取服务商的开票类目", notes = "获取服务商的开票类目")
    public ReturnJson getCatalogAll() {
        return taxService.getCatalogAll();
    }

    @PostMapping("/saveCatalog")
    @ApiOperation(value = "添加开票类目", notes = "添加开票类目")
    public ReturnJson saveCatalog(@Valid @RequestBody AddInvoiceCatalogDTO addInvoiceCatalogDto) {
        return taxService.saveCatalog(addInvoiceCatalogDto);
    }

    @PostMapping("/saveTax")
    @ApiOperation(value = "添加或修改平台服务商", notes = "添加或修改平台服务商")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taxDto", value = "平台服务商的信息", required = true, dataType = "TaxDTO")})
    public ReturnJson saveTax(@Valid @RequestBody TaxDTO taxDto) throws Exception {
        return taxService.saveTax(taxDto);
    }

    @PostMapping("/getTaxList")
    @ApiOperation(value = "查询服务商列表", notes = "查询服务商列表")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taxListDto", value = "查询条件", dataType = "TaxListDTO", paramType = "body")})
    public ReturnJson getTaxList(@Valid @RequestBody TaxListDTO taxListDto) {
        return taxService.getTaxList(taxListDto, null);
    }

    @PostMapping("/updateTaxStatus")
    @ApiOperation(value = "修改服务商状态", notes = "修改服务商状态")
    public ReturnJson updateTaxStatus(@ApiParam(value = "服务商ID") @NotBlank(message = "请选择服务商") @RequestParam(required = false) String taxId,
                                 @ApiParam(value = "服务商状态") @NotBlank(message = "请选择服务商状态") @Range(min = 0, max = 1, message = "请选择0或1") @RequestParam(required = false) Integer taxStatus) {
        return taxService.updateTaxStatus(taxId, taxStatus);
    }

    @GetMapping("/getTaxInfo")
    @ApiOperation(value = "查询服务商详情", notes = "查询服务商详情")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taxId", value = "服务商ID")})
    public ReturnJson getTaxInfo(@NotBlank(message = "服务商ID不能为空！") @RequestParam String taxId) {
        return taxService.getTaxInfo(taxId);
    }

    @PostMapping("/transactionRecordCount")
    @ApiOperation(value = "查询服务商交易流水统计", notes = "查询服务商交易流水统计")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taxId", value = "服务商ID")})
    public ReturnJson transactionRecordCount(@NotBlank(message = "服务商ID不能为空！") @RequestParam String taxId) {
        return taxService.transactionRecordCount(taxId);
    }

    @PostMapping("/transactionRecord")
    @ApiOperation(value = "查询服务商交易流水", notes = "查询服务商交易流水")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taxId", value = "服务商ID"), @ApiImplicitParam(name = "pageNo", value = "页数"), @ApiImplicitParam(name = "pageSize", value = "一页的条数")})
    public ReturnJson transactionRecord(@NotBlank(message = "服务商ID不能为空！") @RequestParam String taxId,
                                        @NotBlank(message = "商户ID不能为空！") @RequestParam String companyId,
                                        @RequestParam(defaultValue = "1") Integer pageNo,
                                        @RequestParam(defaultValue = "10") Integer pageSize) {
        return taxService.transactionRecord(taxId, companyId, pageNo, pageSize);
    }

    @GetMapping("/queryTaxPaasList")
    @ApiOperation(value = "查询服务商列表", notes = "查询服务商列表")
    public ReturnJson getTaxPaasList() {
        return taxService.getTaxPaasList();
    }

    @PostMapping("/queryTaxList")
    @ApiOperation(value = "查询服务商列表（添加商户）", notes = "查询服务商列表（添加商户）")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "packageStatus", value = "服务商类型0总包 1众包")})
    public ReturnJson getTaxPaasList(@NotNull(message = "服务商类型不能为空！") @RequestParam Integer packageStatus) {
        return taxService.getTaxList(packageStatus);
    }

    @GetMapping("/queryTaxUnionpayList")
    @ApiOperation(value = "查询服务商银联", notes = "查询服务商银联")
    public ReturnJson queryTaxUnionpayList(@ApiParam(value = "服务商") @NotNull(message = "请选择服务商") @RequestParam(required = false) String taxId) {
        return taxUnionpayService.queryTaxUnionpayList(taxId);
    }

    @PostMapping("/addOrUpdateTaxUnionpay")
    @ApiOperation(value = "添加或修改服务商银联", notes = "添加或修改服务商银联")
    public ReturnJson addOrUpdateTaxUnionpay(@Valid @RequestBody AddOrUpdateTaxUnionpayDTO addOrUpdateTaxUnionpayDTO) {
        return taxUnionpayService.addOrUpdateTaxUnionpay(addOrUpdateTaxUnionpayDTO);
    }

    @PostMapping("/boolEnableTaxUnionpay")
    @ApiOperation(value = "开启或关闭服务商银联", notes = "开启或关闭服务商银联")
    public ReturnJson boolEnableTaxUnionpay(@ApiParam(value = "服务商银联") @NotBlank(message = "请选择服务商银联") @RequestParam(required = false) String taxUnionpayId, @ApiParam(value = "是否开启") @NotNull(message = "请选择是否开启") @RequestParam(required = false) Boolean boolEnable) {
        return taxUnionpayService.boolEnableTaxUnionpay(taxUnionpayId, boolEnable);
    }

    @GetMapping("/queryTaxUnionpayBalance")
    @ApiOperation(value = "查询服务商银联余额", notes = "查询服务商银联余额")
    public ReturnJson queryTaxUnionpayBalance(@ApiParam(value = "服务商银联") @NotBlank(message = "请选择服务商银联") @RequestParam(required = false) String taxUnionpayId) throws Exception {
        return taxUnionpayService.queryTaxUnionpayBalance(taxUnionpayId);
    }

    @GetMapping("/queryPaymentHistoryList")
    @ApiOperation(value = "查询交易记录", notes = "查询交易记录")
    @LoginRequired
    public ReturnJson queryCompanyUnionpayDetail(@ApiParam(value = "服务商ID") @NotBlank(message = "请选择服务商") @RequestParam(required = false) String taxId,
                                                 @ApiParam(value = "开始日期") @JsonFormat(pattern = "yyyy-MM-dd") @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date beginDate,
                                                 @ApiParam(value = "结束日期") @JsonFormat(pattern = "yyyy-MM-dd") @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date endDate,
                                                 @ApiParam(value = "交易类型") @RequestParam(required = false) OrderType orderType,
                                                 @ApiParam(value = "交易方式") @RequestParam(required = false) PaymentMethod paymentMethod,
                                                 @ApiParam(value = "交易结果") @RequestParam(required = false) TradeStatus tradeStatus,
                                                 @ApiParam(value = "当前页") @Min(value = 1, message = "当前页数最小为1") @RequestParam(required = false) long pageNo,
                                                 @ApiParam(value = "每页条数") @Min(value = 1, message = "每页页数最小为1") @RequestParam(required = false) long pageSize) {

        return paymentHistoryService.queryPaymentHistoryList(TradeObject.TAX, taxId, beginDate, endDate, orderType, paymentMethod, tradeStatus, pageNo, pageSize);
    }

    @GetMapping("/queryTaxUnionpayCompanyUnionpayList")
    @ApiOperation(value = "查询服务商银联所有子账号的商户", notes = "查询服务商银联所有子账号的商户")
    public ReturnJson queryTaxUnionpayCompanyUnionpayList(@ApiParam(value = "服务商银联") @NotBlank(message = "请选择服务商银联") @RequestParam(required = false) String taxUnionpayId) {
        return taxUnionpayService.queryTaxUnionpayCompanyUnionpayList(taxUnionpayId);
    }

    @PostMapping("/clarify")
    @ApiOperation(value = "服务商清分操作", notes = "服务商清分操作")
    @LoginRequired
    public ReturnJson clarify(@ApiParam(hidden = true) @RequestAttribute(value = "userId") String userId,
                              @ApiParam(value = "服务商银联") @NotBlank(message = "请选择服务商银联") @RequestParam(required = false) String taxUnionpayId,
                              @ApiParam(value = "商户ID") @NotBlank(message = "请选择商户") @RequestParam(required = false) String companyId,
                              @ApiParam(value = "清分金额") @NotNull(message = "请输入清分金额") @RequestParam(required = false) BigDecimal amount) throws Exception {
        return taxUnionpayService.clarify(userId, taxUnionpayId, companyId, amount);
    }

    @GetMapping("/downloadTaxPlatformReconciliationFile")
    @ApiOperation(value = "银联对账文件下载", notes = "银联对账文件下载")
    public void queryTaxPlatformReconciliationFile(@ApiParam(value = "开始日期") @NotNull(message = "请选择开始日期") @JsonFormat(pattern = "yyyy-MM-dd") @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date beginDate,
                                                   @ApiParam(value = "结束日期") @NotNull(message = "请选择结束日期") @JsonFormat(pattern = "yyyy-MM-dd") @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date endDate,
                                                   @ApiParam(value = "服务商银联") @NotBlank(message = "请选择服务商银联") @RequestParam(required = false) String taxUnionpayId,
                                                   HttpServletResponse response) throws Exception {
        paymentOrderManyService.downloadTaxPlatformReconciliationFile(beginDate, endDate, taxUnionpayId, response);
    }

    @PostMapping("/queryTaxTransactionFlow")
    @ApiOperation(value = "查询服务商交易流水", notes = "查询服务商交易流水", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taxId", value = "服务商ID"), @ApiImplicitParam(name = "pageNo", value = "页数"), @ApiImplicitParam(name = "pageSize", value = "一页的条数")})
    public ReturnJson queryTaxTransactionFlow(@NotBlank(message = "服务商ID不能为空！") @RequestParam String taxId, @RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        return taxService.queryTaxTransactionFlow(taxId, pageNo, pageSize);
    }

    @GetMapping("/queryCompanyInfoById")
    @ApiOperation(value = "公司的信息", notes = "公司的信息", httpMethod = "GET")
    public ReturnJson queryCompanyInfoById(@NotBlank(message = "商户ID不能为空！") @RequestParam String companyId) {
        return merchantService.queryCompanyInfoById(companyId);
    }

    @PostMapping("/taxMerchantInfoPaas")
    @ApiOperation(value = "获取商户信息", notes = "获取商户信息", httpMethod = "POST")
    public ReturnJson merchantInfo(@NotBlank(message = "商户ID不能为空！") @RequestParam String merchantId, @NotBlank(message = "服务商ID不能为空！") @RequestParam String taxId) {
        return merchantService.taxMerchantInfoPaas(merchantId, taxId);
    }

    @PostMapping("/getMerchantPaymentList")
    @ApiOperation(value = "获取商户的支付列表", notes = "获取商户的支付列表", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "merchantId", value = "商户ID", required = true),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页的条数", required = true)
    })
    public ReturnJson getMerchantPaymentList(@NotBlank(message = "商户ID不能为空！") @RequestParam(required = false) String merchantId, @NotBlank(message = "服务商ID不能为空！") @RequestParam String taxId, @RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        return merchantService.getMerchantPaymentList(merchantId, taxId, pageNo, pageSize);
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

    @GetMapping("/deleteInvoiceCatalog")
    @ApiOperation(value = "删除开票类目", notes = "删除开票类目")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "invoiceCatalogId", value = "开票类目ID", required = true)
    })
    public ReturnJson deleteInvoiceCatalog(@RequestParam @NotBlank(message = "开票类目ID不能为空！") String invoiceCatalogId) {
        return taxService.deleteInvoiceCatalog(invoiceCatalogId);
    }
}
