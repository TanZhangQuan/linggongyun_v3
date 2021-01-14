package com.example.merchant.controller;

import com.example.common.util.ReturnJson;
import com.example.merchant.service.TemplateFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(value = "模板下载", tags = "模板下载")
@RestController
@RequestMapping("/templateFile")
public class TemplateFileController {

    @Resource
    private TemplateFileService templateFileService;

    @GetMapping("/getTotalPayInventory")
    @ApiOperation(value = "下载总包+分包支付清单模板", notes = "下载总包+分包支付清单模板")
    public ReturnJson getTotalPayInventory(HttpServletRequest request) {
        return templateFileService.getTotalPayInventory(request);
    }

    @GetMapping("/getTotalAcceptanceCertificate")
    @ApiOperation(value = "下载总包+分包交付支付验收单", notes = "下载总包+分包交付支付验收单")
    public ReturnJson getTotalAcceptanceCertificate(HttpServletRequest request) {
        return templateFileService.getTotalAcceptanceCertificate(request);
    }

    @GetMapping("/getManyPayInventory")
    @ApiOperation(value = "下载众包支付清单模板", notes = "下载众包支付清单模板")
    public ReturnJson getManyPayInventory(HttpServletRequest request) {
        return templateFileService.getManyPayInventory(request);
    }

    @GetMapping("/getManyAcceptanceCertificate")
    @ApiOperation(value = "下载众包交付支付验收单", notes = "下载众包交付支付验收单")
    public ReturnJson getManyAcceptanceCertificate(HttpServletRequest request) {
        return templateFileService.getManyAcceptanceCertificate(request);
    }

    @GetMapping("/getImportWorker")
    @ApiOperation(value = "下载导入创客模板", notes = "下载导入创客模板")
    public ReturnJson getImportWorker(HttpServletRequest request) {
        return templateFileService.getImportWorker(request);
    }
}
