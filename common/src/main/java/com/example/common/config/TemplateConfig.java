package com.example.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @author tzq
 * @description 模板文件配置
 * @date 2021-02-06
 */
@Component
@Validated
@ConfigurationProperties(prefix = "spring.template")
public class TemplateConfig {

    /**
     * 模板存储路径
     */
    @NotBlank
    private static String path;

    /**
     * 模板访问路径
     */
    @NotBlank
    private static String accessPath;

    /**
     * 合同模板路径
     */
    @NotBlank
    private static String contractPdfPath;

    /**
     * 合同模板路径
     */
    @NotBlank
    private static String contractHtmlPath;

    /**
     * 总包+分包支付明细模板路径
     */
    @NotBlank
    private static String totalPayInventoryPath;

    /**
     * 众包支付明细模板路径
     */
    @NotBlank
    private static String manyPayInventoryPath;

    /**
     * 总包+分包交付支付验收单路径
     */
    @NotBlank
    private static String totalAcceptanceCertificatePath;

    /**
     * 众包交付支付验收单路径
     */
    @NotBlank
    private static String manyAcceptanceCertificatePath;

    /**
     * 导入创客的模板路径
     */
    @NotBlank
    private static String importWorkerPath;


    public void setPath(String path) {
        TemplateConfig.path = path;
    }

    public void setAccessPath(String accessPath) {
        TemplateConfig.accessPath = accessPath;
    }

    public void setContractPdfPath(String contractPdfPath) {
        TemplateConfig.contractPdfPath = contractPdfPath;
    }

    public void setContractHtmlPath(String contractHtmlPath) {
        TemplateConfig.contractHtmlPath = contractHtmlPath;
    }

    public void setTotalPayInventoryPath(String totalPayInventoryPath) {
        TemplateConfig.totalPayInventoryPath = totalPayInventoryPath;
    }

    public void setManyPayInventoryPath(String manyPayInventoryPath) {
        TemplateConfig.manyPayInventoryPath = manyPayInventoryPath;
    }

    public void setTotalAcceptanceCertificatePath(String totalAcceptanceCertificatePath) {
        TemplateConfig.totalAcceptanceCertificatePath = totalAcceptanceCertificatePath;
    }

    public void setManyAcceptanceCertificatePath(String manyAcceptanceCertificatePath) {
        TemplateConfig.manyAcceptanceCertificatePath = manyAcceptanceCertificatePath;
    }

    public void setImportWorkerPath(String importWorkerPath) {
        TemplateConfig.importWorkerPath = importWorkerPath;
    }


    public static String getPath() {
        return path;
    }

    public static String getAccessPath() {
        return accessPath;
    }

    public static String getContractPdfPath() {
        return contractPdfPath;
    }

    public static String getContractHtmlPath() {
        return contractHtmlPath;
    }

    public static String getTotalPayInventoryPath() {
        return totalPayInventoryPath;
    }

    public static String getManyPayInventoryPath() {
        return manyPayInventoryPath;
    }

    public static String getTotalAcceptanceCertificatePath() {
        return totalAcceptanceCertificatePath;
    }

    public static String getManyAcceptanceCertificatePath() {
        return manyAcceptanceCertificatePath;
    }

    public static String getImportWorkerPath() {
        return importWorkerPath;
    }

}
