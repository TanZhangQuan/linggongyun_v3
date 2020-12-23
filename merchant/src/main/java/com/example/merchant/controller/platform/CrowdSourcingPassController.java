package com.example.merchant.controller.platform;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.AddCrowdSourcingInvoiceDTO;
import com.example.merchant.service.CrowdSourcingInvoiceService;
import com.example.mybatis.dto.TobeinvoicedDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(value = "平台端众包发票关操作接口", tags = {"平台端众包发票关操作接口"})
@RestController
@RequestMapping("/platform/crowdSourcing")
public class CrowdSourcingPassController {

    @Resource
    private CrowdSourcingInvoiceService crowdSourcingInvoiceService;

    @PostMapping("/getTobeCrowdSourcingInvoice")
    @ApiOperation(value = "众包待开票")
    public ReturnJson getTobeCrowdSourcingInvoice(TobeinvoicedDTO tobeinvoicedDto) {
        return crowdSourcingInvoiceService.getTobeCrowdSourcingInvoice(tobeinvoicedDto);
    }

    @PostMapping("/getPaymentOrderMany")
    @ApiOperation(value = "众包开票详情页，支付信息")
    public ReturnJson getPaymentOrderMany(String payId) {
        return crowdSourcingInvoiceService.getPaymentOrderMany(payId);
    }

    @PostMapping("/getPaymentInventory")
    @ApiOperation(value = "众包开票详情页，支付清单信息")
    public ReturnJson getPaymentInventory(@RequestParam String invoiceId, @RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        return crowdSourcingInvoiceService.getPaymentInventoryPass(invoiceId, pageNo, pageSize);
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
    public ReturnJson saveCrowdSourcingInvoice(@Valid @RequestBody AddCrowdSourcingInvoiceDTO addCrowdSourcingInvoiceDto) {
        return crowdSourcingInvoiceService.saveCrowdSourcingInvoice(addCrowdSourcingInvoiceDto);
    }

    @PostMapping("/getCrowdSourcingInfo")
    @ApiOperation(value = "众包开票详情页，以开票")
    public ReturnJson getCrowdSourcingInfo(TobeinvoicedDTO tobeinvoicedDto) {
        return crowdSourcingInvoiceService.getCrowdSourcingInfoPass(tobeinvoicedDto);
    }

    @PostMapping("/getPaymentInventoryInfo")
    @ApiOperation(value = "众包开票详情页，已开票支付清单详情")
    public ReturnJson getPaymentInventoryInfo(@RequestParam String invoiceId, @RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        return crowdSourcingInvoiceService.getPaymentInventoryInfoPass(invoiceId, pageNo, pageSize);
    }


    @PostMapping("/queryNotInvoiced")
    @ApiOperation(value = "众包未开票详情")
    public ReturnJson queryNotInvoiced(String invoiceId) {
        return crowdSourcingInvoiceService.queryNotInvoiced(invoiceId);
    }

    @PostMapping("/queryInvoiced")
    @ApiOperation(value = "众包以开票详情")
    public ReturnJson queryInvoiced(String invoiceId) {
        return crowdSourcingInvoiceService.queryInvoiced(invoiceId);
    }

}
