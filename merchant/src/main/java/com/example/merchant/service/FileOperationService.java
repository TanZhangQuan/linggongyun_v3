package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

public interface FileOperationService {
    /**
     * 获取Excel表中的创客信息
     *
     * @param workerExcel
     * @return
     */
    ReturnJson getExcelWorker(MultipartFile workerExcel) throws Exception;

    /**
     * 上传JPG或PDF文件
     *
     * @param uploadJpgOrPdf
     * @return
     * @throws IOException
     */
    ReturnJson uploadJpgOrPdf(MultipartFile uploadJpgOrPdf, HttpServletRequest request) throws Exception;

    /**
     * 上传JpgOrPdf
     *
     * @param fileUrl
     * @param request
     * @return
     */
    String uploadJpgOrPdf(String fileUrl, HttpServletRequest request);

    /**
     * 上传支付清单
     *
     * @param uploadInvoice
     * @param isNot
     * @param request
     * @return
     * @throws Exception
     */
    ReturnJson uploadInvoice(MultipartFile uploadInvoice, Integer isNot, HttpServletRequest request) throws Exception;

    /**
     * 上传门征单开发票或税票
     *
     * @param state
     * @param uploadTaxReceipt
     * @param paymentInventoryId
     * @param request
     * @return
     * @throws IOException
     */
    ReturnJson uploadInvoiceOrTaxReceipt(String state, MultipartFile uploadTaxReceipt, String paymentInventoryId, HttpServletRequest request) throws Exception;

    /**
     * 上传视频
     *
     * @param uploadVideo
     * @param request
     * @return
     */
    ReturnJson uploadVideo(MultipartFile uploadVideo, HttpServletRequest request);


}
