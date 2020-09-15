package com.example.paas.controller;

import com.example.common.util.ReturnJson;
import com.example.paas.service.FileOperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/paas/file")
@Api(value = "文件操作", tags = "文件上传下载的操作")
@Validated
public class FileOperationController {

    @Autowired
    private FileOperationService fileOperationService;

    @PostMapping("/uploadWorker")
    @ApiOperation(value = "导入创客Excel表", notes = "导入创客Excel表", httpMethod = "POST")
    public ReturnJson uploadWorker(@ApiParam(value = "导入创客Excel表",required = true) @RequestParam("workerExcel") MultipartFile workerExcel){
        return fileOperationService.getExcelWorker(workerExcel);
    }

    @PostMapping("/uploadInvoice")
    @ApiOperation(value = "上传支付清单", notes = "上传支付清单", httpMethod = "POST")
    public ReturnJson uploadInvoice(@ApiParam(value = "上传支付清单",required = true) @RequestParam("uploadInvoice") MultipartFile uploadInvoice) throws IOException{
        return fileOperationService.uploadInvoice(uploadInvoice);
    }


    @PostMapping("/uploadJpgOrPdf")
    @ApiOperation(value = "上传JPG或PDF", notes = "上传JPG或PDF", httpMethod = "POST")
    public ReturnJson uploadJpgOrPdf(@ApiParam(value = "上传文件",required = true) @RequestParam("uploadJpgOrPdf") MultipartFile uploadJpgOrPdf) throws IOException {
        return fileOperationService.uploadJpgOrPdf(uploadJpgOrPdf);
    }


}
