package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2021/1/13
 */
@Data
@ApiModel(description = "添加编辑小程序轮播图DTO")
public class SaveOrUpdateAppletBannerDTO implements Serializable {
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
    @ApiModelProperty(value = "图片")
    @NotBlank(message = "图片不能为空")
    private String picture;

}
