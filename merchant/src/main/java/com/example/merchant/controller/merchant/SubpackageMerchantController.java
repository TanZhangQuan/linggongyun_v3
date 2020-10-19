package com.example.merchant.controller.merchant;


import com.example.common.util.ReturnJson;
import com.example.merchant.service.SubpackageService;
import com.example.mybatis.dto.TobeinvoicedDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "商户端分包发票关操作接口", tags = {"商户端分包发票关操作接口"})
@RestController
@RequestMapping("/merchant/subpackage")
public class SubpackageMerchantController {

    @Resource
    private SubpackageService subpackageService;

    @ApiOperation("汇总代开已开票")
    @PostMapping(value = "/getSummaryList")
    public ReturnJson getSummaryList(TobeinvoicedDto tobeinvoicedDto) {
        return subpackageService.getSummaryList(tobeinvoicedDto);
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
    public ReturnJson getListByInvoiceId(String invoicedId, Integer PageNo,Integer pageSize) {
        return subpackageService.getListByInvoiceId(invoicedId, PageNo,pageSize);
    }


}
