package com.example.merchant.vo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2021/1/13
 */
@Data
@ApiModel("小程序轮播图VO")
public class AppletBannerInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "轮播图ID")
    private String id;

    @ApiModelProperty(value = "序号")
    private Integer serialNumber;

    @ApiModelProperty(value = "图片")
    private String picture;

}
