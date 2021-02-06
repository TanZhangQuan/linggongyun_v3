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
 * @time 15:17.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_agent_tax")
public class AgentTax extends BaseEntity{

    /**
     * 服务商id
     */
    private String taxId;
    /**
     * 代理商
     */
    private String agentId;
    /**
     * 费用类型0为一口价，1为梯度价
     */
    private Integer chargeStatus;
    /**
     * 一口价的服务费(如果为梯度价这为空)
     */
    private BigDecimal serviceCharge;
    /**
     * 合作类型
     */
    private Integer packageStatus;
    /**
     * 合作合同地址
     */
    private String contract;
}
