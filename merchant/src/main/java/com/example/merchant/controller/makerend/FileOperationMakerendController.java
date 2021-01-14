package com.example.merchant.controller.makerend;

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
import java.io.IOException;

@Api(value = "小程序文件操作", tags = "小程序文件上传下载的操作")
@RestController
@RequestMapping("/makerend/file")
@Validated
public class FileOperationMakerendController {

    @Resource
    private FileOperationService fileOperationService;

    @PostMapping("/uploadJpgOrPdf")
    @ApiOperation(value = "上传JPG或PDF", notes = "上传JPG或PDF")
    public ReturnJson uploadJpgOrPdf(@ApiParam(value = "上传文件",required = true) @RequestParam("uploadJpgOrPdf") MultipartFile uploadJpgOrPdf, HttpServletRequest request) throws IOException {
        return fileOperationService.uploadJpgOrPdf(uploadJpgOrPdf, request);
    }

    @PostMapping("/uploadVideo")
    @ApiOperation(value = "上传活体视频", notes = "上传活体视频")
    public ReturnJson uploadVideo(@ApiParam(value = "上传文件",required = true) @RequestParam("uploadVideo") MultipartFile uploadVideo, HttpServletRequest request) throws IOException {
        return fileOperationService.uploadVideo(uploadVideo, request);
    }

    @PostMapping("/uploadTaxReceipt")
    @ApiOperation(value = "税票or发票门征单开", notes = "税票or发票门征单开")
    public ReturnJson uploadInvoiceOrTaxReceipt(@ApiParam(value = "判断税票or发票,0为发票,1为税票",required = true) @RequestParam("state") String state,
                                                @ApiParam(value = "文件",required = true) @RequestParam("uploadTaxReceipt") MultipartFile uploadTaxReceipt,
                                                @ApiParam(value = "支付明细id",required = true) @RequestParam("paymentInventoryId")String paymentInventoryId ,HttpServletRequest request) throws IOException {
        return fileOperationService.uploadInvoiceOrTaxReceipt(state, uploadTaxReceipt, paymentInventoryId, request);
    }
}
