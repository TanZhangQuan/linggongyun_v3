package com.example.mybatis.vo;

import lombok.Data;

/**
 * 我的任务
 */
@Data
public class WorkerTaskVo {

    /**
     *任务id
     */
    private String taskId;
    /**
     * 任务对应创客id
     */
    private String workerTaskId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务最小金额
     */
    private String taskCostMin;
    /**
     * 任务最高金额
     */
    private String taskCostMax;
    /**
     * 合作类型
     */
    private String cooperateMode;
    /**
     * 开始时间
     */
    private String releaseDate;
    /**
     * 截至时间
     */
    private String deadlineDate;
    /**
     * 行业类型
     */
    private String industryType;
    /**
     * 状态
     */
    private String status;
}
