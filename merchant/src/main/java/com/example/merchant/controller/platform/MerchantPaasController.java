package com.example.merchant.controller.platform;


import com.example.common.enums.OrderType;
import com.example.common.enums.PaymentMethod;
import com.example.common.enums.TradeObject;
import com.example.common.enums.TradeStatus;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.AddressDTO;
import com.example.merchant.dto.platform.CompanyDTO;
import com.example.merchant.dto.platform.UpdateCompanyDTO;
import com.example.merchant.exception.CommonException;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.*;
import com.example.mybatis.entity.Linkman;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>
 * 商户信息
 * 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "平台端商户相关操作接口", tags = {"平台端商户相关操作接口"})
@RestController
@RequestMapping("/platform/merchant")
@Validated
public class MerchantPaasController {

    @Resource
    private MerchantService merchantService;

    @Resource
    private LinkmanService linkmanService;

    @Resource
    private AddressService addressService;

    @Resource
    private TaxUnionpayService taxUnionpayService;

    @Resource
    private CompanyUnionpayService companyUnionpayService;

    @Resource
    private TaxService taxService;

    @Resource
    private PaymentOrderManyService paymentOrderManyService;

    @Resource
    private PaymentOrderService paymentOrderService;

    @Resource
    private PaymentHistoryService paymentHistoryService;

    @ApiOperation("商户列表")
    @GetMapping(value = "/getIdAndName")
    public ReturnJson getIdAndName() {
        return merchantService.getIdAndName();
    }

    @PostMapping("/getMerchantList")
    @LoginRequired
    @ApiOperation(value = "获取已签约的商户", notes = "获取已签约的商户")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "merchantId", value = "商户ID"),
            @ApiImplicitParam(name = "merchantName", value = "商户名称"),
            @ApiImplicitParam(name = "linkMobile", value = "商户的联系人电话"),
            @ApiImplicitParam(name = "pageNo", value = "页数"),
            @ApiImplicitParam(name = "pageSize", value = "一页的条数")
    })
    public ReturnJson getMerchantList(@RequestAttribute("userId") @ApiParam(hidden = true) String managersId, String merchantId, String merchantName, String linkMobile, @RequestParam(defaultValue = "1") Integer pageNo,
                                      @RequestParam(defaultValue = "10") Integer pageSize) throws CommonException {
        return merchantService.getMerchantList(managersId, merchantId, merchantName, linkMobile, 1, pageNo, pageSize);
    }

    @PostMapping("/getAuditMerchantList")
    @LoginRequired
    @ApiOperation(value = "获取待审核的商户", notes = "获取待审核的商户")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "merchantId", value = "商户ID", paramType = "query"),
            @ApiImplicitParam(name = "merchantName", value = "商户名称", paramType = "query"),
            @ApiImplicitParam(name = "linkMobile", value = "商户的联系人电话", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "页数"),
            @ApiImplicitParam(name = "pageSize", value = "一页的条数")
    })
    public ReturnJson getAuditMerchantList(@RequestAttribute("userId") @ApiParam(hidden = true) String managersId, String merchantId, String merchantName, String linkMobile, @RequestParam(defaultValue = "1") Integer pageNo,
                                           @RequestParam(defaultValue = "10") Integer pageSize) throws CommonException {
        return merchantService.getMerchantList(managersId, merchantId, merchantName, linkMobile, 0, pageNo, pageSize);
    }

    @PostMapping("/auditMerchant")
    @ApiOperation(value = "审核商户", notes = "审核商户")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "companyId", value = "企业ID", required = true)
    })
    @LoginRequired
    public ReturnJson auditMerchant(@NotBlank(message = "商户公司ID不能为空！") @RequestParam(required = false) String companyId,
                                    @RequestAttribute("userId") @ApiParam(hidden = true) String userId) throws CommonException {
        return merchantService.auditMerchant(companyId,userId);
    }

    @PostMapping("/removeMerchant")
    @ApiOperation(value = "删除商户", notes = "删除商户")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "companyId", value = "商户公司ID", required = true)
    })
    public ReturnJson removeMerchant(@NotBlank(message = "商户公司ID不能为空！") @RequestParam(required = false) String companyId) {
        return merchantService.removeMerchant(companyId);
    }

    @PostMapping("/merchantInfo")
    @ApiOperation(value = "获取商户信息", notes = "获取商户信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "merchantId", value = "商户ID", required = true)
    })
    public ReturnJson merchantInfo(@NotBlank(message = "商户ID不能为空！") @RequestParam(required = false) String merchantId) {
        return merchantService.merchantInfoPaas(merchantId);
    }

    @GetMapping("/queryTaxUnionpayMethod")
    @ApiOperation(value = "查询服务商拥有的银联支付方式", notes = "查询服务商拥有的银联支付方式")
    public ReturnJson queryTaxUnionpayMethod(@ApiParam(value = "服务商") @NotBlank(message = "请选择服务商") @RequestParam(required = false) String taxId) {
        return ReturnJson.success(taxUnionpayService.queryTaxUnionpayMethod(taxId));
    }

    @GetMapping("/queryTaxPackage")
    @ApiOperation(value = "查询服务商总包或众包合作信息(商户创建时使用)", notes = "查询服务商总包或众包合作信息(商户创建时使用)")
    @LoginRequired
    public ReturnJson queryTaxPackage(@ApiParam(value = "服务商ID") @NotBlank(message = "请选择服务商") @RequestParam(required = false) String taxId,
                                      @ApiParam(value = "总包或众包") @NotNull(message = "请选择总包或众包") @Range(min = 0, max = 1, message = "请选择正确的合作类型") @RequestParam(required = false) Integer packageStatus) {
        return merchantService.queryTaxPackage(taxId, packageStatus);
    }

    @PostMapping("/addMerchant")
    @ApiOperation(value = "添加商户", notes = "添加商户")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "companyDto", value = "商户ID", required = true, dataType = "CompanyDTO")
    })
    @LoginRequired
    public ReturnJson addMerchant(@Valid @RequestBody CompanyDTO companyDto, @RequestAttribute("userId") @ApiParam(hidden = true) String userId) throws Exception {
        return merchantService.addMerchant(companyDto, userId);
    }

    @PostMapping("/getMerchantPaymentList")
    @ApiOperation(value = "获取商户的支付列表", notes = "获取商户的支付列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "merchantId", value = "商户ID", required = true),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页的条数", required = true)
    })
    public ReturnJson getMerchantPaymentList(@NotBlank(message = "商户ID不能为空！") @RequestParam(required = false) String merchantId, @RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        return merchantService.getMerchantPaymentList(merchantId, null, pageNo, pageSize);
    }

    @PostMapping("/getMerchantPaymentInfo")
    @ApiOperation(value = "获取商户的支付详情", notes = "获取商户的支付详情")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentOrderId", value = "支付订单ID", required = true),
            @ApiImplicitParam(name = "packgeStatus", value = "合作类型0总包，1众包", required = true)
    })
    public ReturnJson getMerchantPaymentInfo(@NotBlank(message = "支付订单ID不能为空") @RequestParam(required = false) String paymentOrderId, @NotNull(message = "合作类型不能为空！") @RequestParam(required = false) Integer packgeStatus) {
        return merchantService.getMerchantPaymentInfo(paymentOrderId, packgeStatus);
    }

    @PostMapping("/getMerchantPaymentInventory")
    @ApiOperation(value = "获取商户的支付清单列表", notes = "获取商户的支付清单列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentOrderId", value = "支付订单ID", required = true),
            @ApiImplicitParam(name = "pageNo", value = "当前页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页的条数", required = true)
    })
    public ReturnJson getMerchantPaymentInventory(@NotBlank(message = "商户ID不能为空！") @RequestParam(required = false) String paymentOrderId, @RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        return merchantService.getMerchantPaymentInventory(paymentOrderId, pageNo, pageSize);
    }

    @PostMapping("/addOrUpdataLinkman")
    @ApiOperation(value = "添加或修改联系人", notes = "添加或修改联系人")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "linkman", value = "联系人信息", required = true, dataType = "Linkman")
    })
    public ReturnJson addOrUpdataLinkman(@Valid @RequestBody Linkman linkman) {
        return linkmanService.addOrUpdataLinkman(linkman, null);
    }

    @PostMapping("/updataLinkmanStatus")
    @ApiOperation(value = "停用或启用联系人", notes = "停用或启用联系人")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "linkmanId", value = "联系人ID", required = true),
            @ApiImplicitParam(name = "status", value = "联系人状态", required = true)
    })
    public ReturnJson updataStatus(@NotBlank(message = "联系人ID不能为空！") @RequestParam(required = false) String linkmanId, @NotNull(message = "状态不能为空！") @RequestParam(required = false) Integer status) {
        return linkmanService.updataStatus(linkmanId, status);
    }

    @PostMapping("/removeLinkmenById")
    @ApiOperation(value = "删除商户的联系人", notes = "删除商户的联系人")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "linkmanId", value = "联系人ID", required = true)
    })
    public ReturnJson removeLinkmenById(@NotBlank(message = "联系人ID不能为空") @RequestParam(required = false) String linkmanId) {
        return linkmanService.removeLinkmenById(linkmanId);
    }

    @GetMapping("/getLinkmanAll")
    @ApiOperation(value = "查询商户的联系人", notes = "查询商户的联系人")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "merchantId", value = "商户ID", required = true)
    })
    public ReturnJson getLinkmanAll(@NotBlank(message = "商户ID不能为空！") @RequestParam(required = false) String merchantId) {
        return linkmanService.getLinkmanAll(merchantId);
    }

    @GetMapping("/getAddressAll")
    @ApiOperation(value = "查询商户的快递地址信息", notes = "查询商户的快递地址信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "merchantId", value = "商户ID", required = true)
    })
    public ReturnJson getAddressAll(@NotBlank(message = "商户ID") @RequestParam(required = false) String merchantId) {
        return addressService.getAddressAll(merchantId);
    }

    @PostMapping("/addOrUpdataAddress")
    @ApiOperation(value = "添加或修改快递地址", notes = "添加或修改快递地址")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "address", value = "快递地址信息", required = true, dataType = "Address")
    })
    public ReturnJson addOrUpdataAddress(@Valid @RequestBody AddressDTO addressDto, String merchantId) {
        return addressService.addOrUpdataAddress(addressDto, merchantId);
    }

    @PostMapping("/updataAddressStatus")
    @ApiOperation(value = "修改快递地址状态", notes = "修改快递地址状态")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "addressId", value = "快递地址ID", required = true),
            @ApiImplicitParam(name = "status", value = "快递地址状态", required = true)
    })
    public ReturnJson updataAddressStatus(@NotBlank(message = "地址ID不能为空") @RequestParam(required = false) String addressId, @NotNull(message = "地址状态不能为空") @RequestParam(required = false) Integer status) {
        return addressService.updataAddressStatus(addressId, status);
    }

    @PostMapping("/removeAddressById")
    @ApiOperation(value = "删除快递地址", notes = "删除快递地址")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "addressId", value = "快递地址ID", required = true)
    })
    public ReturnJson removeAddressById(@NotBlank(message = "地址ID不能为空") @RequestParam(required = false) String addressId) {
        return addressService.removeAddressById(addressId);
    }

    @PostMapping("/updataPassWord")
    @ApiOperation(value = "修改或忘记密码", notes = "修改或忘记密码")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "loginMobile", value = "登录用的手机号码", required = true),
            @ApiImplicitParam(name = "checkCode", value = "验证码", required = true),
            @ApiImplicitParam(name = "newPassWord", value = "新密码", required = true)
    })
    public ReturnJson updataPassWord(@NotBlank(message = "手机号不能为空") @RequestParam(required = false) String loginMobile, @NotBlank(message = "验证码不能为空") @RequestParam(required = false) String checkCode, @NotBlank(message = "新密码不能为空") @RequestParam(required = false) String newPassWord) {
        return merchantService.updatePassWord(loginMobile, checkCode, newPassWord);
    }

    @GetMapping("/queryAgent")
    @ApiOperation(value = "代理商列表", notes = "代理商列表")
    @LoginRequired
    public ReturnJson queryAgent(@RequestAttribute("userId") @ApiParam(hidden = true) String userId) {
        return merchantService.queryAgent(userId);
    }

    @GetMapping("/queryCompanyInfoById")
    @ApiOperation(value = "公司的信息", notes = "公司的信息")
    public ReturnJson queryCompanyInfoById(String merchantId) {
        return merchantService.queryCompanyInfoById(merchantId);
    }

    @PostMapping("/updateCompanyInfo")
    @ApiOperation(value = "修改公司的信息", notes = "修改或忘记密码")
    public ReturnJson updateCompanyInfo(@Valid @RequestBody UpdateCompanyDTO updateCompanyDto) throws Exception {
        return merchantService.updateCompanyInfo(updateCompanyDto);
    }

    @GetMapping("/queryOfflineTaxList")
    @ApiOperation(value = "查询线下支付关联的服务商", notes = "查询线下支付关联的服务商")
    public ReturnJson queryOfflineTaxList(@ApiParam(value = "商户ID") @NotBlank(message = "请选择商户") @RequestParam(required = false) String merchantId,
                                          @ApiParam(value = "当前页") @Min(value = 1, message = "当前页数最小为1") @RequestParam(required = false) long pageNo,
                                          @ApiParam(value = "每页条数") @Min(value = 1, message = "每页页数最小为1") @RequestParam(required = false) long pageSize) {
        return companyUnionpayService.queryOfflineTaxList(merchantId, pageNo, pageSize);
    }

    @GetMapping("/queryUninopayTaxList")
    @ApiOperation(value = "查询银联支付关联的服务商", notes = "查询银联支付关联的服务商")
    public ReturnJson queryUninopayTaxList(@ApiParam(value = "商户ID") @NotBlank(message = "请选择商户") @RequestParam(required = false) String merchantId,
                                           @ApiParam(value = "当前页") @Min(value = 1, message = "当前页数最小为1") @RequestParam(required = false) long pageNo,
                                           @ApiParam(value = "每页条数") @Min(value = 1, message = "每页页数最小为1") @RequestParam(required = false) long pageSize) {
        return companyUnionpayService.queryUninopayTaxList(merchantId, pageNo, pageSize);
    }

    @GetMapping("/queryCompanyTaxInfo")
    @ApiOperation(value = "查询商户服务商合作信息", notes = "查询商户服务商合作信息")
    public ReturnJson queryCompanyTaxList(@ApiParam(value = "商户ID") @NotBlank(message = "请选择商户") @RequestParam(required = false) String merchantId,
                                          @ApiParam(value = "服务商ID") @NotBlank(message = "请选择服务商") @RequestParam(required = false) String taxId) {
        return merchantService.queryCompanyTaxInfo(merchantId, taxId);
    }

    @GetMapping("/queryTaxInBankInfo")
    @ApiOperation(value = "查询服务商线下来款银行账号信息", notes = "查询服务商线下来款银行账号信息")
    public ReturnJson queryTaxInBankInfo(@ApiParam(value = "服务商ID") @NotBlank(message = "请选择服务商") @RequestParam(required = false) String taxId) {
        return taxService.queryTaxInBankInfo(taxId);
    }

    @GetMapping("/queryCompanyUnionpayDetail")
    @ApiOperation(value = "查询商户银联详情", notes = "查询商户银联详情")
    public ReturnJson queryCompanyUnionpayBalance(@ApiParam(value = "商户ID") @NotBlank(message = "请选择商户") @RequestParam(required = false) String merchantId,
                                                  @ApiParam(value = "服务商ID") @NotBlank(message = "请选择服务商") @RequestParam(required = false) String taxId) throws Exception {
        return companyUnionpayService.queryCompanyUnionpayDetail(merchantId, taxId, null);
    }

    @PostMapping("/queryTaxTransactionFlow")
    @ApiOperation(value = "查询商户交易流水", notes = "查询商户交易流水", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taxId", value = "商户ID"), @ApiImplicitParam(name = "pageNo", value = "页数"), @ApiImplicitParam(name = "pageSize", value = "一页的条数")})
    public ReturnJson queryTaxTransactionFlow(@NotBlank(message = "商户ID不能为空！") @RequestParam String merchantId, @RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        return merchantService.queryMerchantTransactionFlow(merchantId, pageNo, pageSize);
    }

    @GetMapping("/getTaxInfo")
    @ApiOperation(value = "查询服务商信息", notes = "查询服务商信息", httpMethod = "GET")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taxId", value = "服务商ID")})
    public ReturnJson getTaxInfo(@NotBlank(message = "服务商ID不能为空！") @RequestParam String taxId) {
        return taxService.getTaxInfo(taxId);
    }

    @PostMapping("/taxMerchantInfoPaas")
    @ApiOperation(value = "获取服务商流水信息", notes = "获取服务商流水信息", httpMethod = "POST")
    public ReturnJson taxMerchantInfoPaas(@NotBlank(message = "服务商ID不能为空！") @RequestParam String taxId) {
        return taxService.transactionRecordCount(taxId);
    }

    @PostMapping("/transactionRecord")
    @ApiOperation(value = "查询服务商交易流水", notes = "查询服务商交易流水", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taxId", value = "服务商ID"), @ApiImplicitParam(name = "pageNo", value = "页数"), @ApiImplicitParam(name = "pageSize", value = "一页的条数")})
    public ReturnJson transactionRecord(@NotBlank(message = "服务商ID不能为空！") @RequestParam String taxId, @NotBlank(message = "商户ID不能为空！") @RequestParam String merchantId, @RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        return taxService.transactionRecord(taxId, merchantId, pageNo, pageSize);
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

    @GetMapping("/queryPaymentHistoryList")
    @ApiOperation(value = "查询交易记录", notes = "查询交易记录")
    @LoginRequired
    public ReturnJson queryCompanyUnionpayDetail(@ApiParam(value = "商户ID") @NotBlank(message = "请选择商户") @RequestParam(required = false) String merchantId,
                                                 @ApiParam(value = "开始日期") @JsonFormat(pattern = "yyyy-MM-dd") @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date beginDate,
                                                 @ApiParam(value = "结束日期") @JsonFormat(pattern = "yyyy-MM-dd") @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) Date endDate,
                                                 @ApiParam(value = "交易类型") @RequestParam(required = false) OrderType orderType,
                                                 @ApiParam(value = "交易方式") @RequestParam(required = false) PaymentMethod paymentMethod,
                                                 @ApiParam(value = "交易结果") @RequestParam(required = false) TradeStatus tradeStatus,
                                                 @ApiParam(value = "当前页") @Min(value = 1, message = "当前页数最小为1") @RequestParam(required = false) long pageNo,
                                                 @ApiParam(value = "每页条数") @Min(value = 1, message = "每页页数最小为1") @RequestParam(required = false) long pageSize) {

        return paymentHistoryService.queryPaymentHistoryList(TradeObject.COMPANY, merchantId, beginDate, endDate, orderType, paymentMethod, tradeStatus, pageNo, pageSize);
    }

}
