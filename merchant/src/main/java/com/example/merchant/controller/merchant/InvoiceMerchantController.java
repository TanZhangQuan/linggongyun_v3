package com.example.merchant.controller.merchant;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.UpdateApplication;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.*;
import com.example.mybatis.dto.InvoiceApplicationDTO;
import com.example.mybatis.dto.QueryTobeinvoicedDTO;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 发票相关 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "商户总包发票操作接口", tags = {"商户总包发票操作接口"})
@RestController
@RequestMapping("/merchant/invoice")
@Validated
public class InvoiceMerchantController {

    @Resource
    private InvoiceService invoiceService;

    @Resource
    private PaymentOrderService paymentOrderService;

    @Resource
    private MerchantService merchantService;

    @Resource
    private TaxService taxService;

    @Resource
    private InvoiceCatalogService invoiceCatalogService;

    @Resource
    private InvoiceApplicationService invoiceApplicationService;

    @ApiOperation("总包发票列表,待开票")
    @PostMapping(value = "/gettobeinvoiced")
    @LoginRequired
    public ReturnJson gettobeinvoiced(@Valid @RequestBody QueryTobeinvoicedDTO queryTobeinvoicedDto,
                                      @RequestAttribute(value = "userId") @ApiParam(hidden = true) String merchantId) {
        return invoiceService.selectTobeinvoiced(queryTobeinvoicedDto, merchantId);
    }

    @ApiOperation("总包发票列表,已开票")
    @PostMapping(value = "/getInvoiceList")
    @LoginRequired
    public ReturnJson getInvoiceList(@Valid @RequestBody QueryTobeinvoicedDTO queryTobeinvoicedDto,
                                     @RequestAttribute(value = "userId") @ApiParam(hidden = true) String merchantId) {
        return invoiceService.getInvoiceList(queryTobeinvoicedDto, merchantId);
    }


    @ApiOperation("总包发票列表已开票,发票信息")
    @PostMapping(value = "/getInvInfoById")
    @LoginRequired
    public ReturnJson getInvInfoById(String InvId, @RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return invoiceService.getInvInfoById(InvId, merchantId);
    }

    @ApiOperation("支付信息,对应多个支付支付id用逗号隔开")
    @GetMapping(value = "/getPaymentOrderById")
    public ReturnJson getPaymentOrderById(String id) {
        return paymentOrderService.getPaymentOrderById(id);
    }

    @ApiOperation("开票信息，支付")
    @GetMapping(value = "/getBillingInfo")
    public ReturnJson getBillingInfo(String id) {
        return paymentOrderService.getBillingInfo(id);
    }


    @ApiOperation("开票信息，购买方,商户id")
    @GetMapping(value = "/getBuyerById")
    @LoginRequired
    public ReturnJson getBuyerById(@RequestAttribute("userId") @ApiParam(hidden = true) String id) {
        return merchantService.getBuyerById(id);
    }

    @ApiOperation("开票信息，销售方,税源地id")
    @GetMapping(value = "/getSellerById")
    public ReturnJson getSellerById(String id) {
        return taxService.getSellerById(id);
    }

    @ApiOperation("开票类目")
    @GetMapping(value = "/getListInv")
    @LoginRequired
    public ReturnJson getListInv(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String id) {
        return invoiceCatalogService.getListInv(id);
    }

    @ApiOperation("判断是否为同一服务商,同时传入多个name")
    @GetMapping(value = "isServiceProvider")
    public ReturnJson isServiceProvider(String serviceProviderNames) {
        return invoiceService.isServiceProvider(serviceProviderNames);
    }

    @ApiOperation("添加申请开票信息")
    @PostMapping(value = "/addInvApplication")
    public ReturnJson addInvApplication(InvoiceApplicationDTO invoiceApplicationDto) {
        return invoiceApplicationService.addInvApplication(invoiceApplicationDto);
    }

    @ApiOperation("去申请开票")
    @PostMapping(value = "/goInvApplication")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "payIds", value = "支付订单ID,多个以,隔开", required = true)
    })
    @LoginRequired
    public ReturnJson goInvApplication(@NotBlank(message = "支付订单ID不能为空") @RequestParam String payIds, @RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return invoiceApplicationService.goInvApplication(payIds, merchantId);
    }


    @ApiOperation("修改开票申请")
    @PostMapping(value = "/updateApplication")
    @LoginRequired
    public ReturnJson updateApplication(@Valid @RequestBody UpdateApplication updateApplication, @RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return invoiceApplicationService.updateApplication(updateApplication, merchantId);
    }

    @ApiOperation("去编辑开票信息")
    @PostMapping(value = "/queryApplicationInfo")
    @LoginRequired
    public ReturnJson queryApplicationInfo(@NotBlank(message = "申请ID不能为空！") @RequestParam String applicationId, @RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return invoiceApplicationService.queryApplicationInfo(applicationId, merchantId);
    }

}