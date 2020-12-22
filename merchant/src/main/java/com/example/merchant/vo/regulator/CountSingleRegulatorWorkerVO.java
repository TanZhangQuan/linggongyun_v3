package com.example.merchant.vo.regulator;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "单个创客流水信息统计")
public class CountSingleRegulatorWorkerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客总包单数
     */
    private Integer totalOrderCount;

    /**
     * 创客实名认证：0未认证，1已认证
     */
    private Integer attestation;

    /**
     * 创客头像
     */
    private String headPortraits;

    /**
     * 创客姓名
     */
    private String workerName;

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
