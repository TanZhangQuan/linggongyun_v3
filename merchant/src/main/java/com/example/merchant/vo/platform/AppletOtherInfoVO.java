package com.example.merchant.vo.platform;

import com.example.common.enums.AppletOtherType;
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
@ApiModel("小程序其他问题VO")
public class AppletOtherInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "其他问题ID")
    private String id;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "上传类型")
    private AppletOtherType type;

}
