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
@TableName("tb_achievement_statistics")
public class AchievementStatistics extends BaseEntity {
    /**
     * 对象id,区分代理商和业务员
     */
    private String objectId;
    /**
     *0代表业务，1代表代理商
     */
    private Integer objectType;
    /**
     *直属商户数
     */
    private Integer merchantCount;
    /**
     *直属商户流水
     */
    private BigDecimal merchantBusiness;

    /**
     *下级代理数
     */
    private Integer agentCount;
    /**
     *下级代理流水
     */
    private BigDecimal agentBusiness;

    /**
     *直属商户流水提成
     */
    private BigDecimal merchantCommission;
    /**
     * 直属商户流水提成汇率
     */
    private BigDecimal merchantRate;
    /**
     *代理商流水提成
     */
    private BigDecimal agentCommission;
    /**
     * 代理商流水提成汇率
     */
    private BigDecimal agentRate;
    /**
     *商户差价提成
     */
    private BigDecimal merchantDifference;
    /**
     *代理商差价提成
     */
    private BigDecimal agentDifference;
    /**
     *总提成
     */
    private BigDecimal totalCommission;
    /**
     *结算状态 0 未结算，1已结算
     */
    private Integer settlementState;
}
