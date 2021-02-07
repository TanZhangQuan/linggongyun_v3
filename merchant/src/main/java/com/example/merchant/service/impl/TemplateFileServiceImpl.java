package com.example.merchant.service.impl;

import com.example.common.config.TemplateConfig;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.TemplateFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class TemplateFileServiceImpl implements TemplateFileService {

    @Override
    public ReturnJson getTotalPayInventory(HttpServletRequest request) {
        String fileStaticAccesspath = this.getFileStaticAccesspath(TemplateConfig.getTotalPayInventoryPath(), request);
        return ReturnJson.success("总包+分包支付清单模板URL获取成功！", fileStaticAccesspath);
    }

    @Override
    public ReturnJson getTotalAcceptanceCertificate(HttpServletRequest request) {
        String fileStaticAccesspath = this.getFileStaticAccesspath(TemplateConfig.getTotalAcceptanceCertificatePath(), request);
        return ReturnJson.success("总包+分包交付支付验收单URL获取成功！", fileStaticAccesspath);
    }

    @Override
    public ReturnJson getManyPayInventory(HttpServletRequest request) {
        String fileStaticAccesspath = this.getFileStaticAccesspath(TemplateConfig.getManyPayInventoryPath(), request);
        return ReturnJson.success("获取众包清单模板URL！", fileStaticAccesspath);
    }

    @Override
    public ReturnJson getManyAcceptanceCertificate(HttpServletRequest request) {
        String fileStaticAccesspath = this.getFileStaticAccesspath(TemplateConfig.getManyAcceptanceCertificatePath(), request);
        return ReturnJson.success("众包交付支付验收单URL获取成功！", fileStaticAccesspath);
    }

    @Override
    public ReturnJson getImportWorker(HttpServletRequest request) {
        String fileStaticAccesspath = this.getFileStaticAccesspath(TemplateConfig.getImportWorkerPath(), request);
        return ReturnJson.success("导入创客模板URL获取成功！", fileStaticAccesspath);
    }

    /**
     * 获取模板文件的URL
     *
     * @param templateFilePath 模板文件的存储路径
     * @param request
     * @return
     */
    private String getFileStaticAccesspath(String templateFilePath, HttpServletRequest request) {
        String substring = templateFilePath.substring(templateFilePath.lastIndexOf("/") + 1);
        String fileStaticAccesspath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
                request.getContextPath() + TemplateConfig.getAccessPath() + substring;
        return fileStaticAccesspath;
    }
}
