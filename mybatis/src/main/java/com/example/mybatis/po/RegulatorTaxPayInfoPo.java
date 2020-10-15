package com.example.mybatis.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "支付信息")
public class RegulatorTaxPayInfoPo {

    @ApiModelProperty(notes = "支付Id",value = "支付Id")
    private String id;

    @ApiModelProperty(notes = "平台服务商名称",value = "平台服务商名称")
    private String platformServiceProvider;

    @ApiModelProperty(notes = "商户名称",value = "商户名称")
    private String companySName;

    @ApiModelProperty(notes = "合作类型",value = "合作类型")
    private String type;

    @ApiModelProperty(notes = "项目合同",value = "项目合同")
    private String companyContract;

    @ApiModelProperty(notes = "支付清单",value = "支付清单")
    private String paymentInventory;

    @ApiModelProperty(notes = "交易流水",value = "交易流水")
    private String realMoney;

    @ApiModelProperty(notes = "支付状态",value = "支付状态")
    private Integer paymentOrderStatus;

    @ApiModelProperty(notes = "导出使用到",value = "导出使用到")
    private String paymentOrderZNameStatus;

    @ApiModelProperty(notes = "完成时间",value = "完成时间")
    private String paymentDate;
}
