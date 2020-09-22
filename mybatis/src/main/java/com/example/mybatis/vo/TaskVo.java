package com.example.mybatis.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 关联任务
 */
@Data
public class TaskVo {

    private String taskCode;

    private String taskName;

    private LocalDateTime releaseDate;

    private LocalDateTime deadlineDate;

    private String industryType;
}
