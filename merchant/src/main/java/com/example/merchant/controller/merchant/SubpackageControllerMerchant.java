package com.example.merchant.controller.merchant;


import com.example.common.util.ReturnJson;
import com.example.merchant.service.SubpackageService;
import com.example.mybatis.dto.TobeinvoicedDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "商户端分包发票关操作接口", tags = {"商户端分包发票关操作接口"})
@RestController
@RequestMapping("/merchant/subpackage")
public class SubpackageControllerMerchant {

    @Resource
    private SubpackageService subpackageService;

    private static Logger logger = LoggerFactory.getLogger(InvoiceControllerMerchant.class);

    @ApiOperation("汇总代开已开票")
    @PostMapping(value = "/getSummaryList")
    public ReturnJson getSummaryList(TobeinvoicedDto tobeinvoicedDto) {
        ReturnJson returnJson = new ReturnJson("添加失败", 300);
        try {
            returnJson = subpackageService.getSummaryList(tobeinvoicedDto);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }

    @ApiOperation("汇总代开,支付信息，税价总和")
    @PostMapping(value = "/getSummary")
    public ReturnJson getSummary(String invoicedId) {
        ReturnJson returnJson = new ReturnJson("添加失败", 300);
        try {
            returnJson = subpackageService.getSummary(invoicedId);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }

    @ApiOperation("汇总代开,发票信息")
    @PostMapping(value = "/getSubpackageInfo")
    public ReturnJson getSubpackageInfo(String invoicedId) {
        ReturnJson returnJson = new ReturnJson("添加失败", 300);
        try {
            returnJson = subpackageService.getSubpackageInfoById(invoicedId);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }

    @ApiOperation("汇总代开,发票信息,创客到手明细")
    @PostMapping(value = "/getListByInvoiceId")
    public ReturnJson getListByInvoiceId(String invoicedId, Integer PageNo) {
        ReturnJson returnJson = new ReturnJson("添加失败", 300);
        try {
            returnJson = subpackageService.getListByInvoiceId(invoicedId, PageNo);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }


}
