package com.example.mybatis.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author .
 * @date 2021/1/30.
 * @time 16:15.
 */
@Data
public class TimerStatisticsVO implements Serializable {
    private String id;
    private String realName;
    private Integer userSign;
    private Integer merchantCount;
    private BigDecimal totalMerchantBusiness;
    private BigDecimal manyMerchantBusiness;
    private Integer agentCount;
    private BigDecimal totalAgentBusiness;
    private BigDecimal manyAgentBusiness;
    private BigDecimal totalMerchantCommission;
    private BigDecimal manyMerchantCommission;
    private BigDecimal totalAgentCommission;
    private BigDecimal manyAgentCommission;
}
