package com.example.mybatis.vo;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author .
 * @date 2021/2/1.
 * @time 14:27.
 */
@Data
public class AchievementStatisticsVO  implements Serializable {

    private String achievementStatisticsId;
    /**
     * 对象id,区分代理商和业务员
     */
    private String objectId;
    /**
     *0代表业务，1代表代理商
     */
    private Integer objectType;

    /**
     *名称
     */
    private String objectName;
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
     *代理商流水提成
     */
    private BigDecimal agentCommission;
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

    /**
     * 创建时间
     */
    private String createDate;


}
