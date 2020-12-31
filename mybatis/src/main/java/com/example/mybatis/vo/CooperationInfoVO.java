package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "XXXXX")
public class CooperationInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "合作信息ID")
    private String id;

    @ApiModelProperty(value = "合作类型: 0 总包 1 分包")
    private String packageStatus;

    @ApiModelProperty(value = "服务商ID")
    private String taxId;

    @ApiModelProperty(value = "服务商名称")
    private String taxName;

    @ApiModelProperty(value = "费用类型：0为一口价，1为梯度价")
    private String chargeStatus;

    @ApiModelProperty(value = "一口价费率")
    private String serviceCharge;

    @ApiModelProperty(value = "合作合同地址")
    private String contract;

    @ApiModelProperty(value = "账户")
    private String payee;

    @ApiModelProperty(value = "银行号")
    private String bankCode;

    @ApiModelProperty(value = "银行名称")
    private String bankName;

    @ApiModelProperty(value = "服务费率")
    private List<CompanyLadderServiceVO> companyLadderServiceVos;
}
