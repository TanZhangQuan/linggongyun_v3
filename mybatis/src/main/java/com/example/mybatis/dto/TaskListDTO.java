package com.example.mybatis.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;


/**
 * 任务列表查询dto
 */
@Data
@ApiModel(value = "任务列表查询")
public class TaskListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    private String merchantId;

    /**
     * 商户名称
     */
    private String merchantName;

    /**
     * 任务ID
     */
    private String taskCode;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String releaseDate;

    /**
     * 任务截至时间
     */
    private String deadlineDate;

    /**
     * 合作类型
     */
    private Integer cooperateMode;

    /**
     * 页码
     */
    private Integer pageNo = 1;

    /**
     * 页大小
     */
    private Integer pageSize = 10;
}
