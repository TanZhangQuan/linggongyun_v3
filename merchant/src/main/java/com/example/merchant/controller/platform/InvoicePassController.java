package com.example.merchant.controller.platform;


import com.example.common.util.ReturnJson;
import com.example.merchant.dto.MakerInvoiceDto;
import com.example.merchant.dto.MakerTotalInvoiceDto;
import com.example.merchant.service.InvoiceService;
import com.example.merchant.service.MakerInvoiceService;
import com.example.merchant.service.MakerTotalInvoiceService;
import com.example.mybatis.dto.AddInvoiceDto;
import com.example.mybatis.dto.TobeinvoicedDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 发票相关 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(tags = "平台端总包+分包发票", value = "平台端总包+分包发票")
@RestController
@RequestMapping("/platform/invoice")
@Validated
public class InvoicePassController {

    @Resource
    private InvoiceService invoiceService;

    @Resource
    private MakerTotalInvoiceService makerTotalInvoiceService;

    @Resource
    private MakerInvoiceService makerInvoiceService;

    @ApiOperation("总包开票，待开票")
    @PostMapping("/getPlaInvoiceList")
    public ReturnJson getPlaInvoiceList(@Valid @RequestBody TobeinvoicedDto tobeinvoicedDto) {
        return invoiceService.getPlaInvoiceList(tobeinvoicedDto);
    }

    @ApiOperation("总包开票，详情数据")
    @PostMapping("/getPlaInvoiceInfo")
    public ReturnJson getPlaInvoiceInfo(String applicationID) {
        return invoiceService.getPlaInvoiceInfo(applicationID);
    }

    @ApiOperation("总包开票，编辑详情数据")
    @PostMapping("/queryInvoice")
    public ReturnJson queryInvoice(String invoiceId) {
        return invoiceService.queryInvoice(invoiceId);
    }

    @ApiOperation("总包开票")
    @PostMapping("/saveInvoice")
    public ReturnJson saveInvoice(@Valid @RequestBody AddInvoiceDto addInvoiceDto) {
        return invoiceService.saveInvoice(addInvoiceDto);
    }

    @ApiOperation("总包开票，以开票")
    @PostMapping("/listInvoiceQuery")
    public ReturnJson listInvoiceQuery(@Valid @RequestBody TobeinvoicedDto tobeinvoicedDto) {
        return invoiceService.getListInvoicequery(tobeinvoicedDto);
    }

    @ApiOperation("分包开票，待开票")
    @PostMapping("/listSubQuery")
    public ReturnJson listSubQuery(@Valid @RequestBody TobeinvoicedDto tobeinvoicedDto) {
        return invoiceService.getListSubQuery(tobeinvoicedDto);
    }

    @ApiOperation("分包开票，开票清单明细信息")
    @PostMapping("/invoiceListQuery")
    public ReturnJson invoiceListQuery(String invoiceId, String companySNames, String platformServiceProviders) {
        return invoiceService.getInvoiceListQuery(invoiceId, companySNames, platformServiceProviders);
    }

    @ApiOperation("汇总代开")
    @PostMapping("/saveOrUpdateMakerTotalInvoice")
    public ReturnJson saveOrUpdateMakerTotalInvoice(@Valid @RequestBody MakerTotalInvoiceDto makerTotalInvoiceDto) {
        return makerTotalInvoiceService.saveOrUpdateMakerTotalInvoice(makerTotalInvoiceDto);
    }

    @ApiOperation("门征单开,开票清单明细信息")
    @PostMapping("/getPaymentInventory")
    public ReturnJson getPaymentInventory(String invoiceId) {
        return makerInvoiceService.getPaymentInventory(invoiceId);
    }

    @ApiOperation("门征单开,开票如果未开票先上传发票进这里开一张发票")
    @PostMapping("/saveMakerInvoice")
    public ReturnJson saveMakerInvoice(@Valid @RequestBody MakerInvoiceDto makerInvoiceDto) {
        return makerInvoiceService.saveMakerInvoice(makerInvoiceDto);
    }


}