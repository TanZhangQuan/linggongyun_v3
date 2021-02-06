package com.example.mybatis.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author .
 * @date 2021/2/1.
 * @time 15:58.
 */
@Data
public class TotalAchievementStatisticsVO  implements Serializable {
    /**
     * 总直属商户流水提成
     */
    private String totalMerchantCommission;
    /**
     * 总代理商流水提成
     */
    private String totalAgentCommission;
    /**
     * 总直属商户差额提成
     */
    private String totalMerchantDifference;
    /**
     * 总直属商户差额提成
     */
    private String totalAgentDifference;
    /**
     * 总提成
     */
    private String totalCommission;
    /**
     *个数
     */
    private String num;
}
