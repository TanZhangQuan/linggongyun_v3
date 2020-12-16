package com.example.mybatis.vo;

import lombok.Data;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/16
 */
@Data
public class TaskInfoVo {
    /**
     * 任务ID
     */
    private String id;
    /**
     * 商户名称
     */
    private String companyName;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务上限人数
     */
    private String upperLimit;
    /**
     * 任务起始金额
     */
    private String taskCostMin;
    /**
     * 任务结束金额
     */
    private String taskCostMax;
    /**
     * 合作类型0,总包+分包  1众包
     */
    private String cooperateMode;
    /**
     * 发布时间
     */
    private String releaseDate;
    /**
     * 任务模式0派单，1抢单，2混合
     */
    private String taskMode;
}
