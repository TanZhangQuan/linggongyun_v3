package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.merchant.exception.CommonException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface FileOperationService {
    /**
     * 获取Excel表中的创客信息
     *
     * @param workerExcel
     * @return
     */
    ReturnJson getExcelWorker(MultipartFile workerExcel) throws IOException;

    /**
     * 上传JPG或PDF文件
     *
     * @param uploadJpgOrPdf
     * @return
     * @throws IOException
     */
    ReturnJson uploadJpgOrPdf(MultipartFile uploadJpgOrPdf, HttpServletRequest request) throws IOException;

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
     * @return
     * @throws IOException
     */
    ReturnJson uploadInvoice(MultipartFile uploadInvoice, HttpServletRequest request) throws IOException, CommonException;

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
    ReturnJson uploadInvoiceOrTaxReceipt(String state, MultipartFile uploadTaxReceipt, String paymentInventoryId, HttpServletRequest request) throws IOException;

    /**
     * 上传视频
     *
     * @param uploadVideo
     * @param request
     * @return
     */
    ReturnJson uploadVideo(MultipartFile uploadVideo, HttpServletRequest request);
}
