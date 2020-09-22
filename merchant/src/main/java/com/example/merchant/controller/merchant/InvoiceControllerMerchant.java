package com.example.merchant.controller.merchant;


import com.example.common.util.ReturnJson;
import com.example.merchant.service.*;
import com.example.mybatis.dto.InvoiceApplicationDto;
import com.example.mybatis.dto.TobeinvoicedDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class InvoiceControllerMerchant {

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

    private static Logger logger = LoggerFactory.getLogger(InvoiceControllerMerchant.class);

    @ApiOperation("总包发票列表,待开票")
    @PostMapping(value = "/gettobeinvoiced")
    public ReturnJson gettobeinvoiced(TobeinvoicedDto tobeinvoicedDto) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        try {
            returnJson = invoiceService.selectTobeinvoiced(tobeinvoicedDto);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }

    @ApiOperation("总包发票列表,已开票")
    @PostMapping(value = "/getInvoiceList")
    public ReturnJson getInvoiceList(TobeinvoicedDto tobeinvoicedDto) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        try {
            returnJson = invoiceService.getInvoiceList(tobeinvoicedDto);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }

    @ApiOperation("总包发票列表,发票信息")
    @PostMapping(value = "/getInvInfoById")
    public ReturnJson getInvInfoById(String InvId) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        try {
            returnJson = invoiceService.getInvInfoById(InvId);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }

    @ApiOperation("支付信息,对应多个支付支付id用逗号隔开")
    @GetMapping(value = "/getPaymentOrderById")
    public ReturnJson getPaymentOrderById(String id) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        try {
            returnJson = paymentOrderService.getPaymentOrderById(id);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }

    @ApiOperation("开票信息，支付")
    @GetMapping(value = "/getBillingInfo")
    public ReturnJson getBillingInfo(String id) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        try {
            returnJson = paymentOrderService.getBillingInfo(id);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }


    @ApiOperation("开票信息，购买方,商户id")
    @GetMapping(value = "/getBuyerById")
    public ReturnJson getBuyerById(String id) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        try {
            returnJson = merchantService.getBuyerById(id);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }

    @ApiOperation("开票信息，销售方,税源地id")
    @GetMapping(value = "/getSellerById")
    public ReturnJson getSellerById(String id) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        try {
            returnJson = taxService.getSellerById(id);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }

    @ApiOperation("开票类目")
    @GetMapping(value = "/getListInv")
    public ReturnJson getListInv(String id) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        try {
            returnJson = invoiceCatalogService.getListInv(id);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }

    @ApiOperation("添加申请开票信息")
    @PostMapping(value = "/addInvApplication")
    public ReturnJson addInvApplication(InvoiceApplicationDto invoiceApplicationDto) {
        ReturnJson returnJson = new ReturnJson("添加失败", 300);
        try {
            returnJson = invoiceApplicationService.addInvApplication(invoiceApplicationDto);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }

}
