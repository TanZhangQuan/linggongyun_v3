package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "物流信息")
public class SendAndReceiveVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "物流公司")
    private String logisticsCompany;

    @ApiModelProperty(value = "物流单号")
    private String logisticsOrderNo;

    @ApiModelProperty(value = "收件地址")
    private String toAddress;

    @ApiModelProperty(value = "收件人")
    private String addressee;

    @ApiModelProperty(value = "收件人电话")
    private String addresseeTelephone;

    @ApiModelProperty(value = "发件地址")
    private String  sendingAddress;

    @ApiModelProperty(value = "发件人")
    private String from;

    @ApiModelProperty(value = "发件人电话")
    private String fromTelephone;
}