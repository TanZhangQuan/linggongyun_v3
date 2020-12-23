package com.example.merchant.vo.regulator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "创客流水信息统计")
public class CountRegulatorWorkerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "监管区入驻创客总数")
    private Integer countWorker;

    @ApiModelProperty(value = "创客总包单数")
    private Integer totalOrderCount;

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
