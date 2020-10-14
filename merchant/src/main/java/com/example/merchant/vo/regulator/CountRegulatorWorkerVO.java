package com.example.merchant.vo.regulator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(description = "创客流水信息统计")
public class CountRegulatorWorkerVO {

    @ApiModelProperty(notes = "监管区入驻创客总数", value = "监管区入驻创客总数")
    private Integer countWorker;

    @ApiModelProperty(notes = "创客总包单数", value = "创客总包单数")
    private Integer totalOrderCount;

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

    public CountRegulatorWorkerVO() {
    }

    public CountRegulatorWorkerVO(Integer countWorker, Integer totalOrderCount, BigDecimal totalMoney, BigDecimal totalTaxMoney, Integer manyOrderCount, BigDecimal manyMoney, BigDecimal manyTaxMoney) {
        this.countWorker = countWorker;
        this.totalOrderCount = totalOrderCount;
        this.totalMoney = totalMoney;
        this.totalTaxMoney = totalTaxMoney;
        this.manyOrderCount = manyOrderCount;
        this.manyMoney = manyMoney;
        this.manyTaxMoney = manyTaxMoney;
    }
}
