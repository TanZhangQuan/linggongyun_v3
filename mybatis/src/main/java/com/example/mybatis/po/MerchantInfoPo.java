package com.example.mybatis.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "商户基本资料")
public class MerchantInfoPo {
    @ApiModelProperty(value = "商户ID")
    private String merchantId;

    @ApiModelProperty(value = "商户名称")
    private String merchantName;

    @ApiModelProperty(value = "联系人名称")
    private String linkName;

    @ApiModelProperty(value = "联系人电话")
    private String linkMobile;

    @ApiModelProperty(value = "组织结构")
    private String structure;

    @ApiModelProperty(value = "加盟合同")
    private String contract;

    @ApiModelProperty(value = "状态0启用，1停用")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
}
