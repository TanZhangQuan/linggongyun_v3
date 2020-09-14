package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileOperationService {
    ReturnJson getExcelWorker(MultipartFile workerExcel);
    ReturnJson uploadJpgOrPdf(MultipartFile uploadJpgOrPdf) throws IOException;
    ReturnJson uploadInvoice(@RequestParam("uploadInvoice") MultipartFile uploadInvoice)throws IOException;
}
