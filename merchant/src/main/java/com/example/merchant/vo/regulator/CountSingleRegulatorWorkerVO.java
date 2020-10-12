package com.example.merchant.vo.regulator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(description = "单个创客流水信息统计")
public class CountSingleRegulatorWorkerVO {
    @ApiModelProperty(notes = "创客总包单数", value = "创客总包单数")
    private Integer totalOrderCount;

    @ApiModelProperty(notes = "创客实名认证：0未认证，1已认证", value = "创客实名认证：0未认证，1已认证")
    private Integer attestation;

    @ApiModelProperty(notes = "创客头像", value = "创客头像")
    private String headPortraits;

    @ApiModelProperty(notes = "创客姓名", value = "创客姓名")
    private String workerName;

    @ApiModelProperty(notes = "创客总包的总金额", value = "创客总包的总金额")
    private BigDecimal totalMoney;

    @ApiModelProperty(notes = "总包纳税金额", value = "总纳税金额")
    private BigDecimal totalTaxMoney;

    @ApiModelProperty(notes = "创客众包单数", value = "创客众包单数")
    private Integer manyOrderCount;

    @ApiModelProperty(notes = "创客众包总金额", value = "创客众包总金额")
    private BigDecimal manyMoney;

    @ApiModelProperty(notes = "众包纳税金额", value = "总纳税金额")
    private BigDecimal manyTaxMoney;
}
