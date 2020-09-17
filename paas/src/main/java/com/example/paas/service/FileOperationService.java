package com.example.paas.service;

import com.example.common.util.ReturnJson;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface FileOperationService {
    ReturnJson getExcelWorker(MultipartFile workerExcel);
    ReturnJson uploadJpgOrPdf(MultipartFile uploadJpgOrPdf, HttpServletRequest request) throws IOException;
    ReturnJson uploadInvoice(MultipartFile uploadInvoice, HttpServletRequest request)throws IOException;
}
