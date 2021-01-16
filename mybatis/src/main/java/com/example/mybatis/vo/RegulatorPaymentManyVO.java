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
@ApiModel(description = "众包支付信息")
public class RegulatorPaymentManyVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "众包支付ID")
    private String paymentManyId;

    @ApiModelProperty(value = "商户名称")
    private String companySName;

    @ApiModelProperty(value = "服务商名称")
    private String platformServiceProvider;

    @ApiModelProperty(value = "项目合同")
    private String companyContract;

    @ApiModelProperty(value = "支付验收单")
    private String acceptanceCertificate;

    @ApiModelProperty(value = "支付状态")
    private String paymentOrderStatus;

    @ApiModelProperty(value = "支付清单")
    private String paymentInventory;

    @ApiModelProperty(value = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date paymentDate;

    @ApiModelProperty(value = "任务信息")
    private TaskVO taskVo;
}
