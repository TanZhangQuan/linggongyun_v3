package com.example.merchant.controller.platform;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.AddCrowdSourcingInvoiceDto;
import com.example.merchant.service.CrowdSourcingInvoiceService;
import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.entity.CrowdSourcingInvoice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value = "平台端众包发票关操作接口", tags = {"平台端众包发票关操作接口"})
@RestController
@RequestMapping("/platform/crowdSourcing")
public class CrowdSourcingPassController {

    @Resource
    private CrowdSourcingInvoiceService crowdSourcingInvoiceService;

    @PostMapping("/getTobeCrowdSourcingInvoice")
    @ApiOperation(value = "众包待开票")
    public ReturnJson getTobeCrowdSourcingInvoice(TobeinvoicedDto tobeinvoicedDto) {
        return crowdSourcingInvoiceService.getTobeCrowdSourcingInvoice(tobeinvoicedDto);
    }

    @PostMapping("/getPaymentOrderMany")
    @ApiOperation(value = "众包开票详情页，支付信息")
    public ReturnJson getPaymentOrderMany(String payId) {
        return crowdSourcingInvoiceService.getPaymentOrderMany(payId);
    }

    @PostMapping("/getPaymentInventory")
    @ApiOperation(value = "众包开票详情页，支付清单信息")
    public ReturnJson getPaymentInventory(String payId) {
        return crowdSourcingInvoiceService.getPaymentInventoryPass(payId);
    }

    @PostMapping("/getBuyer")
    @ApiOperation(value = "众包开票详情页，购买方信息")
    public ReturnJson getBuyer(String companyId) {
        return crowdSourcingInvoiceService.getBuyer(companyId);
    }

    @PostMapping("/getApplicationInfo")
    @ApiOperation(value = "众包开票详情页，申请开票信息")
    public ReturnJson getApplicationInfo(String applicationId) {
        return crowdSourcingInvoiceService.getApplicationInfo(applicationId);
    }

    @PostMapping("/saveCrowdSourcingInvoice")
    @ApiOperation(value = "众包开票详情页，开票")
    public ReturnJson saveCrowdSourcingInvoice(@RequestBody AddCrowdSourcingInvoiceDto addCrowdSourcingInvoiceDto) {
        return crowdSourcingInvoiceService.saveCrowdSourcingInvoice(addCrowdSourcingInvoiceDto);
    }

    @PostMapping("/getCrowdSourcingInfo")
    @ApiOperation(value = "众包开票详情页，以开票")
    public ReturnJson getCrowdSourcingInfo(TobeinvoicedDto tobeinvoicedDto) {
        return crowdSourcingInvoiceService.getCrowdSourcingInfoPass(tobeinvoicedDto);
    }

    @PostMapping("/getPaymentInventoryInfo")
    @ApiOperation(value = "众包开票详情页，已开票支付清单详情")
    public ReturnJson getPaymentInventoryInfo(String invoiceId) {
        return crowdSourcingInvoiceService.getPaymentInventoryInfoPass(invoiceId);
    }
}
