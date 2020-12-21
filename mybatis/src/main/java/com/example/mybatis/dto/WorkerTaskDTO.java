package com.example.mybatis.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 派单dto
 */
@Data
@ApiModel
public class WorkerTaskDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 工单ID
     */
    private String id;

    /**
     * 创客ID
     */
    private String workerId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 派单状态
     */
    private String taskStatus;

    /**
     * 任务接取时间
     */
    private String createDate;

}
