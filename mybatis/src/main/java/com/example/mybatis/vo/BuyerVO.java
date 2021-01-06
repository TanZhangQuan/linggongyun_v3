package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "购买方信息")
public class BuyerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String companyName;

    @ApiModelProperty(value = "纳税人识别号")
    private String creditCode;

    @ApiModelProperty(value = "地址and电话")
    private String addressAndTelephone;

    @ApiModelProperty(value = "开户行and卡号")
    private String bankAndAccount;
}
