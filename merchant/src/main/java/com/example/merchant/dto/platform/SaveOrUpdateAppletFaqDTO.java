package com.example.merchant.dto.platform;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description 添加编辑小程序常见问题
 * @Author JWei <jwei0401@163.com>
 * @Date 2021/1/12
 */
@Data
@ApiModel(description = "添加编辑小程序常见问题DTO")
public class SaveOrUpdateAppletFaqDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "常见问题ID")
    private String id;

    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    @NotNull(message = "序号不能为空")
    private Integer serialNumber;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    @NotBlank(message = "内容不能为空")
    private String content;

}
