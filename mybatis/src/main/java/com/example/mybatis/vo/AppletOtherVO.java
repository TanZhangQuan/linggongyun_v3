package com.example.mybatis.vo;

import com.example.common.enums.AppletOtherType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2021/1/13
 */
@Data
@ApiModel("小程序其他问题VO")
public class AppletOtherVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "其他问题ID")
    private String id;

    @ApiModelProperty(value = "项目名称")
    private String entryName;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "上传类型")
    private AppletOtherType type;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDate;
}
