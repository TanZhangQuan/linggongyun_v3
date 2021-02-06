package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author jun.
 * @date 2021/1/29.
 * @time 15:23.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_agent_ladder_service")
public class AgentLadderService extends BaseEntity {
    /**
     * 给代理商的服务商ID
     */
    private String agentTaxId;
    /**
     * 开始的金额
     */
    private BigDecimal startMoney;
    /**
     * 结束的金额
     */
    private BigDecimal endMoney;
    /**
     * 服务费（如7.5，不需把百分数换算成小数）
     */
    private BigDecimal serviceCharge;
}
