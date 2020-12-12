package com.example.merchant.controller.platform;


import com.example.common.util.ReturnJson;
import com.example.merchant.dto.MakerInvoiceDto;
import com.example.merchant.dto.MakerTotalInvoiceDto;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.InvoiceService;
import com.example.merchant.service.MakerInvoiceService;
import com.example.merchant.service.MakerTotalInvoiceService;
import com.example.mybatis.dto.AddInvoiceDto;
import com.example.mybatis.dto.QueryMakerTotalInvoiceDto;
import com.example.mybatis.dto.TobeinvoicedDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
    public ReturnJson invoiceListQuery(String invoiceId) {
        return invoiceService.getInvoiceListQuery(invoiceId);
    }

    @ApiOperation("汇总代开")
    @PostMapping("/saveOrUpdateMakerTotalInvoice")
    @LoginRequired
    public ReturnJson saveOrUpdateMakerTotalInvoice(@Valid @RequestBody MakerTotalInvoiceDto makerTotalInvoiceDto,
                                                    @RequestAttribute("userId") @ApiParam(hidden = true) String managerId) {
        return makerTotalInvoiceService.saveOrUpdateMakerTotalInvoice(makerTotalInvoiceDto, managerId);
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

    @ApiOperation("汇总代开详情")
    @PostMapping("/queryMakerTotalInvoiceInfo")
    public ReturnJson queryMakerTotalInvoiceInfo(String invoiceIds) {
        return makerTotalInvoiceService.queryMakerTotalInvoiceInfo(invoiceIds);
    }

    @ApiOperation("分包已开票，汇总代开列表")
    @PostMapping("/queryMakerTotalInvoice")
    public ReturnJson queryMakerTotalInvoice(@RequestBody QueryMakerTotalInvoiceDto queryMakerTotalInvoiceDto) {
        return makerTotalInvoiceService.queryMakerTotalInvoice(queryMakerTotalInvoiceDto);
    }

    @ApiOperation("分包已开票，汇总代开详情")
    @PostMapping("/queryMakerTotalInvoiceDetails")
    public ReturnJson queryMakerTotalInvoiceDetails(String invoiceId) {
        return makerTotalInvoiceService.queryMakerTotalInvoiceDetails(invoiceId);
    }

    @ApiOperation("分包已开票，汇总代开支付清单详情")
    @PostMapping("/getMakerTotalInvoicePayList")
    public ReturnJson getMakerTotalInvoicePayList(@RequestParam @NotNull(message = "发票ID不能为空") String invoiceId,
                                                  @RequestParam(defaultValue = "1") Integer pageNo,
                                                  @RequestParam(defaultValue = "10") Integer pageSize) {
        return makerTotalInvoiceService.getMakerTotalInvoicePayList(invoiceId, pageNo, pageSize);
    }

}