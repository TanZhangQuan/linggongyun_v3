package com.example.merchant.vo.regulator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "单个创客流水信息统计")
public class CountSingleRegulatorWorkerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创客总包单数")
    private Integer totalOrderCount;

    @ApiModelProperty(value = "创客实名认证：0未认证，1已认证")
    private Integer attestation;

    @ApiModelProperty(value = "创客头像")
    private String headPortraits;

    @ApiModelProperty(value = "创客姓名")
    private String workerName;

    @ApiModelProperty(value = "创客总包的总金额")
    private BigDecimal totalMoney;

    @ApiModelProperty(value = "总纳税金额")
    private BigDecimal totalTaxMoney;

    @ApiModelProperty(value = "创客众包单数")
    private Integer manyOrderCount;

    @ApiModelProperty(value = "创客众包总金额")
    private BigDecimal manyMoney;

    @ApiModelProperty(value = "总纳税金额")
    private BigDecimal manyTaxMoney;
}
