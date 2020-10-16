package com.example.merchant.service;

import com.example.common.util.ReturnJson;

import javax.servlet.http.HttpServletRequest;

public interface TemplateFileService {
    ReturnJson getTotalPayInventory(HttpServletRequest request);

    ReturnJson getTotalAcceptanceCertificate(HttpServletRequest request);

    ReturnJson getManyPayInventory(HttpServletRequest request);

    ReturnJson getManyAcceptanceCertificate(HttpServletRequest request);

    ReturnJson getImportWorker(HttpServletRequest request);
}
