package com.example.merchant.controller.platform;

import com.example.common.util.ReturnJson;
import com.example.merchant.controller.merchant.InvoiceControllerMerchant;
import com.example.merchant.service.CrowdSourcingInvoiceService;
import com.example.merchant.service.PaymentOrderManyService;
import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.entity.ApplicationCrowdSourcing;
import com.example.mybatis.entity.CrowdSourcingInvoice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "平台端众包发票关操作接口", tags = {"平台端众包发票关操作接口"})
@RestController
@RequestMapping("/platform/crowdSourcing")
public class CrowdSourcingControllerPass {

    @Resource
    private CrowdSourcingInvoiceService crowdSourcingInvoiceService;


    private static Logger logger = LoggerFactory.getLogger(InvoiceControllerMerchant.class);

    @PostMapping("/getTobeCrowdSourcingInvoice")
    @ApiOperation(value = "众包待开票")
    public ReturnJson getTobeCrowdSourcingInvoice(TobeinvoicedDto tobeinvoicedDto) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        try {
            returnJson = crowdSourcingInvoiceService.getTobeCrowdSourcingInvoice(tobeinvoicedDto);
        } catch (Exception err) {
            logger.error("出现错误异常", err);
        }
        return returnJson;
    }

    @PostMapping("/getPaymentOrderMany")
    @ApiOperation(value = "众包开票详情页，支付信息")
    public ReturnJson getPaymentOrderMany(String payId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        try {
            returnJson = crowdSourcingInvoiceService.getPaymentOrderMany(payId);
        } catch (Exception err) {
            logger.error("出现错误异常", err);
        }
        return returnJson;
    }

    @PostMapping("/getPaymentInventory")
    @ApiOperation(value = "众包开票详情页，支付清单信息")
    public ReturnJson getPaymentInventory(String payId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        try {
            returnJson = crowdSourcingInvoiceService.getPaymentInventoryPass(payId);
        } catch (Exception err) {
            logger.error("出现错误异常", err);
        }
        return returnJson;
    }

    @PostMapping("/getBuyer")
    @ApiOperation(value = "众包开票详情页，购买方信息")
    public ReturnJson getBuyer(String companyId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        try {
            returnJson = crowdSourcingInvoiceService.getBuyer(companyId);
        } catch (Exception err) {
            logger.error("出现错误异常", err);
        }
        return returnJson;
    }

    @PostMapping("/getApplicationInfo")
    @ApiOperation(value = "众包开票详情页，申请开票信息")
    public ReturnJson getApplicationInfo(String applicationId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        try {
            returnJson = crowdSourcingInvoiceService.getApplicationInfo(applicationId);
        } catch (Exception err) {
            logger.error("出现错误异常", err);
        }
        return returnJson;
    }

    @PostMapping("/saveCrowdSourcingInvoice")
    @ApiOperation(value = "众包开票详情页，开票")
    public ReturnJson saveCrowdSourcingInvoice(CrowdSourcingInvoice crowdSourcingInvoice) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        try {
            returnJson = crowdSourcingInvoiceService.saveCrowdSourcingInvoice(crowdSourcingInvoice);
        } catch (Exception err) {
            logger.error("出现错误异常", err);
        }
        return returnJson;
    }

    @PostMapping("/getCrowdSourcingInfo")
    @ApiOperation(value = "众包开票详情页，以开票")
    public ReturnJson getCrowdSourcingInfo(TobeinvoicedDto tobeinvoicedDto) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        try {
            returnJson = crowdSourcingInvoiceService.getCrowdSourcingInfoPass(tobeinvoicedDto);
        } catch (Exception err) {
            logger.error("出现错误异常", err);
        }
        return returnJson;
    }

    @PostMapping("/getPaymentInventoryInfo")
    @ApiOperation(value = "众包开票详情页，已开票支付清单详情")
    public ReturnJson getPaymentInventoryInfo(String invoiceId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        try {
            returnJson = crowdSourcingInvoiceService.getPaymentInventoryInfoPass(invoiceId);
        } catch (Exception err) {
            logger.error("出现错误异常", err);
        }
        return returnJson;
    }
}
