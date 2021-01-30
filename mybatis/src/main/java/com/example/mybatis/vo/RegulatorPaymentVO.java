package com.example.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2021/1/16
 */
@Data
@ApiModel(description = "支付信息")
public class RegulatorPaymentVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付ID")
    private String paymentId;

    @ApiModelProperty(value = "商户名称")
    private String companySName;

    @ApiModelProperty(value = "服务商名称")
    private String platformServiceProvider;

    @ApiModelProperty(value = "商户ID")
    private String companyId;

    @ApiModelProperty(value = "服务商ID")
    private String taxId;

    @ApiModelProperty(value = "项目合同")
    private String companyContract;

    @ApiModelProperty(value = "支付验收单")
    private String acceptanceCertificate;

    @ApiModelProperty(value = "支付状态")
    private String paymentOrderStatus;

    @ApiModelProperty(value = "支付清单")
    private String paymentInventory;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @ApiModelProperty(value = "任务信息")
    private TaskVO taskVo;
}
