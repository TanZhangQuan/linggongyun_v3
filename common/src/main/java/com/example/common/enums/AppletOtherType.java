package com.example.common.enums;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2021/1/13
 */
@Getter
@AllArgsConstructor
@ApiModel(description = "上传类型")
public enum AppletOtherType {
    @ApiModelProperty(value = "图片")
    IMG("IMG","IMG"),

    @ApiModelProperty(value = "文本")
    TEXT("TEXT","TEXT");

    private final String value;
    private final String desc;
}
