package com.example.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @author tzq
 * @description 文件本地存储配置
 * @date 2021-02-06
 */
@Component
@Validated
@ConfigurationProperties(prefix = "spring.file-storage")
public class FileStorageConfig {

    /**
     * 图片存储路径
     */
    @NotBlank
    private static String imagePath;

    /**
     * 图片访问路径
     */
    @NotBlank
    private static String imageAccessPath;

    /**
     * Excel存储路径
     */
    @NotBlank
    private static String excelPath;

    /**
     * Excel访问路径
     */
    @NotBlank
    private static String excelAccessPath;

    /**
     * video存储路径
     */
    @NotBlank
    private static String videoPath;

    /**
     * video访问路径
     */
    @NotBlank
    private static String videoAccessPath;


    public void setImagePath(String imagePath) {
        FileStorageConfig.imagePath = imagePath;
    }

    public void setImageAccessPath(String imageAccessPath) {
        FileStorageConfig.imageAccessPath = imageAccessPath;
    }

    public void setExcelPath(String excelPath) {
        FileStorageConfig.excelPath = excelPath;
    }

    public void setExcelAccessPath(String excelAccessPath) {
        FileStorageConfig.excelAccessPath = excelAccessPath;
    }

    public void setVideoPath(String videoPath) {
        FileStorageConfig.videoPath = videoPath;
    }

    public void setVideoAccessPath(String videoAccessPath) {
        FileStorageConfig.videoAccessPath = videoAccessPath;
    }


    public static String getImagePath() {
        return imagePath;
    }

    public static String getImageAccessPath() {
        return imageAccessPath;
    }

    public static String getExcelPath() {
        return excelPath;
    }

    public static String getExcelAccessPath() {
        return excelAccessPath;
    }

    public static String getVideoPath() {
        return videoPath;
    }

    public static String getVideoAccessPath() {
        return videoAccessPath;
    }

}
