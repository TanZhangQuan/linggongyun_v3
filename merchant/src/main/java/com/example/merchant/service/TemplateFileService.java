package com.example.merchant.service;

import com.example.common.util.ReturnJson;

import javax.servlet.http.HttpServletRequest;

public interface TemplateFileService {
    /**
     * 获取总包+分包支付清单模板URL
     *
     * @param request
     * @return
     */
    ReturnJson getTotalPayInventory(HttpServletRequest request);

    /**
     * 获取总包+分包交付支付验收单URL
     *
     * @param request
     * @return
     */
    ReturnJson getTotalAcceptanceCertificate(HttpServletRequest request);

    /**
     * 获取众包清单模板URL
     *
     * @param request
     * @return
     */
    ReturnJson getManyPayInventory(HttpServletRequest request);

    /**
     * 获取众包交付支付验收单URL
     *
     * @param request
     * @return
     */
    ReturnJson getManyAcceptanceCertificate(HttpServletRequest request);

    /**
     * 获取导入创客模板URL
     *
     * @param request
     * @return
     */
    ReturnJson getImportWorker(HttpServletRequest request);
}
