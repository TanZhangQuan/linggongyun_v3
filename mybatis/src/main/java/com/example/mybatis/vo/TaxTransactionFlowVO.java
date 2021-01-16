package com.example.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author .
 * @date 2021/1/12.
 * @time 17:53.
 */
@Data
@ApiModel(description = "支付订单")
public class TaxTransactionFlowVO implements Serializable {

    @ApiModelProperty(value = "商户ID")
    private String companyId;

    @ApiModelProperty(value = "商户ID")
    private String merchantId;

    @ApiModelProperty(value = "服务商ID")
    private String taxId;

    @ApiModelProperty(value = "商户名称")
    private String merchantName;

    @ApiModelProperty(value = "服务商名称")
    private String taxName;

    @ApiModelProperty(value = "总包交易流水")
    private BigDecimal totalPackageMoney;

    @ApiModelProperty(value = "众包交易流水")
    private BigDecimal manyPackageMoney;

    @ApiModelProperty(value = "总包交易次数")
    private Integer totalPackageCount;

    @ApiModelProperty(value = "众包交易次数")
    private Integer manyPackageCount;

    @ApiModelProperty(value = "合作的状态 1 合作中，0 未合作")
    private Integer cooperationState;

    @ApiModelProperty(value = "开始合作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startCooperationDate;

    @ApiModelProperty(value = "最近合作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime latelyCooperationDate;
}
