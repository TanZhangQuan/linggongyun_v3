package com.example.merchant.controller.platform;

import com.example.common.util.ReturnJson;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.FileOperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(value = "平台端文件操作", tags = "平台端文件上传下载的操作")
@RestController
@RequestMapping("/platform/file")
@Validated
public class FileOperationPaasController {

    @Resource
    private FileOperationService fileOperationService;

    @PostMapping("/uploadWorker")
    @ApiOperation(value = "导入创客Excel表", notes = "导入创客Excel表")
    public ReturnJson uploadWorker(@ApiParam(value = "导入创客Excel表", required = true) @RequestParam("workerExcel") MultipartFile workerExcel) throws Exception {
        return fileOperationService.getExcelWorker(workerExcel);
    }

    @PostMapping("/uploadInvoice")
    @ApiOperation(value = "上传支付清单", notes = "上传支付清单")
    public ReturnJson uploadInvoice(@ApiParam(value = "上传支付清单", required = true) @RequestParam("uploadInvoice") MultipartFile uploadInvoice, HttpServletRequest request) throws Exception {
        return fileOperationService.uploadInvoice(uploadInvoice, request);
    }

    @PostMapping("/uploadJpgOrPdf")
    @ApiOperation(value = "上传JPG或PDF", notes = "上传JPG或PDF")
    public ReturnJson uploadJpgOrPdf(@ApiParam(value = "上传文件", required = true) @RequestParam("uploadJpgOrPdf") MultipartFile uploadJpgOrPdf, HttpServletRequest request) throws Exception {
        return fileOperationService.uploadJpgOrPdf(uploadJpgOrPdf, request);
    }

    @PostMapping("/uploadTaxReceipt")
    @ApiOperation(value = "税票or发票门征单开", notes = "税票or发票门征单开")
    public ReturnJson uploadInvoiceOrTaxReceipt(@ApiParam(value = "判断税票or发票,0为发票,1为税票", required = true) @RequestParam("state") String state,
                                                @ApiParam(value = "文件", required = true) @RequestParam("uploadTaxReceipt") MultipartFile uploadTaxReceipt,
                                                @ApiParam(value = "支付明细id", required = true) @RequestParam("paymentInventoryId") String paymentInventoryId, HttpServletRequest request) throws Exception {
        return fileOperationService.uploadInvoiceOrTaxReceipt(state, uploadTaxReceipt, paymentInventoryId, request);
    }
}
