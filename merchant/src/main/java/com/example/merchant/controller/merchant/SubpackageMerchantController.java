package com.example.merchant.controller.merchant;


import com.example.common.util.ReturnJson;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.SubpackageService;
import com.example.mybatis.dto.QuerySubpackageDto;
import com.example.mybatis.dto.TobeinvoicedDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(value = "商户端分包发票关操作接口", tags = {"商户端分包发票关操作接口"})
@RestController
@RequestMapping("/merchant/subpackage")
public class SubpackageMerchantController {

    @Resource
    private SubpackageService subpackageService;

    @ApiOperation("汇总代开已开票")
    @PostMapping(value = "/getSummaryList")
    @LoginRequired
    public ReturnJson getSummaryList(@Valid @RequestBody QuerySubpackageDto querySubpackageDto,
                                     @RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return subpackageService.getSummaryList(querySubpackageDto, merchantId);
    }

    @ApiOperation("汇总代开,支付信息，税价总和")
    @PostMapping(value = "/getSummary")
    public ReturnJson getSummary(String invoicedId) {
        return subpackageService.getSummary(invoicedId);
    }

    @ApiOperation("汇总代开,发票信息")
    @PostMapping(value = "/getSubpackageInfo")
    public ReturnJson getSubpackageInfo(String invoicedId) {
        return subpackageService.getSubpackageInfoById(invoicedId);
    }

    @ApiOperation("汇总代开,发票信息,创客到手明细")
    @PostMapping(value = "/getListByInvoiceId")
    public ReturnJson getListByInvoiceId(String invoicedId, Integer pageNo, Integer pageSize) {
        return subpackageService.getListByInvoiceId(invoicedId, pageNo, pageSize);
    }

    @ApiOperation("汇总代开,详细信息")
    @PostMapping(value = "/getSummaryInfo")
    @LoginRequired
    public ReturnJson getSummaryInfo(@RequestParam String invoicedId, @RequestAttribute("userId") @ApiParam(hidden = true) String merchantId) {
        return subpackageService.getSummaryInfo(invoicedId, merchantId);
    }


}
