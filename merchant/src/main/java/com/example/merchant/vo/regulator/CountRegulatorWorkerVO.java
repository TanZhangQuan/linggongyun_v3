package com.example.merchant.vo.regulator;

import io.swagger.annotations.ApiModel;
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

    /**
     * 监管区入驻创客总数
     */
    private Integer countWorker;

    /**
     * 创客总包单数
     */
    private Integer totalOrderCount;

    /**
     * 创客总包的总金额
     */
    private BigDecimal totalMoney;

    /**
     * 总包纳税金额
     */
    private BigDecimal totalTaxMoney;

    /**
     * 创客众包单数
     */
    private Integer manyOrderCount;

    /**
     * 创客众包总金额
     */
    private BigDecimal manyMoney;

    /**
     * 众包纳税金额
     */
    private BigDecimal manyTaxMoney;

}
