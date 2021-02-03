package com.example.merchant.controller.merchant;

import com.example.common.util.ReturnJson;
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
import javax.validation.constraints.NotNull;

@Api(value = "商户端文件操作", tags = "商户端文件上传下载的操作")
@RestController
@RequestMapping("/merchant/file")
@Validated
public class FileOperationMerchantController {

    @Resource
    private FileOperationService fileOperationService;

    @PostMapping("/uploadWorker")
    @ApiOperation(value = "导入创客Excel表", notes = "导入创客Excel表")
    public ReturnJson uploadWorker(@ApiParam(value = "导入创客Excel表", required = true) @RequestParam("workerExcel") MultipartFile workerExcel) throws Exception {
        return fileOperationService.getExcelWorker(workerExcel);
    }

    @PostMapping("/uploadInvoice")
    @ApiOperation(value = "上传支付清单", notes = "上传支付清单")
    public ReturnJson uploadInvoice(@ApiParam(value = "上传支付清单", required = true) @RequestParam("uploadInvoice") MultipartFile uploadInvoice,
                                    @ApiParam(value = "区分：0总包或者1众包") @NotNull(message = "请区分总包或众包") @RequestParam("isNot") Integer isNot,
                                    HttpServletRequest request) throws Exception {
        return fileOperationService.uploadInvoice(uploadInvoice, isNot, request);
    }


    @PostMapping("/uploadJpgOrPdf")
    @ApiOperation(value = "上传JPG或PDF", notes = "上传JPG或PDF")
    public ReturnJson uploadJpgOrPdf(@ApiParam(value = "上传文件", required = true) @RequestParam("uploadJpgOrPdf") MultipartFile uploadJpgOrPdf, HttpServletRequest request) throws Exception {
        return fileOperationService.uploadJpgOrPdf(uploadJpgOrPdf, request);
    }

}
