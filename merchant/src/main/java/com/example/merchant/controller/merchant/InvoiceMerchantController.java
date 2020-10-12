package com.example.merchant.controller.merchant;

import com.example.common.util.ReturnJson;
import com.example.merchant.service.*;
import com.example.mybatis.dto.InvoiceApplicationDto;
import com.example.mybatis.dto.TobeinvoicedDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 发票相关 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "商户总包操作接口", tags = {"商户总包操作接口"})
@RestController
@RequestMapping("/merchant/invoice")
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
    public ReturnJson gettobeinvoiced(TobeinvoicedDto tobeinvoicedDto) {
        return invoiceService.selectTobeinvoiced(tobeinvoicedDto);
    }

    @ApiOperation("总包发票列表,已开票")
    @PostMapping(value = "/getInvoiceList")
    public ReturnJson getInvoiceList(TobeinvoicedDto tobeinvoicedDto) {
        return invoiceService.getInvoiceList(tobeinvoicedDto);
    }

    @ApiOperation("总包发票列表已开票,发票信息")
    @PostMapping(value = "/getInvInfoById")
    public ReturnJson getInvInfoById(String InvId) {
        return invoiceService.getInvInfoById(InvId);
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
    public ReturnJson getBuyerById(String id) {
        return merchantService.getBuyerById(id);
    }

    @ApiOperation("开票信息，销售方,税源地id")
    @GetMapping(value = "/getSellerById")
    public ReturnJson getSellerById(String id) {
        return taxService.getSellerById(id);
    }

    @ApiOperation("开票类目")
    @GetMapping(value = "/getListInv")
    public ReturnJson getListInv(String id) {
        return invoiceCatalogService.getListInv(id);
    }

    @ApiOperation("判断是否为同一服务商,同时传入多个name")
    @GetMapping(value = "isServiceProvider")
    public ReturnJson isServiceProvider(String serviceProviderNames) {
        return invoiceService.isServiceProvider(serviceProviderNames);
    }

    @ApiOperation("添加申请开票信息")
    @PostMapping(value = "/addInvApplication")
    public ReturnJson addInvApplication(InvoiceApplicationDto invoiceApplicationDto) {
        return invoiceApplicationService.addInvApplication(invoiceApplicationDto);
    }

}
