package com.example.mybatis.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 平台端查询任务列表
 */
@Data
public class PlatformTaskListVo {

    private String id;

    private String taskCode;

    private String merchantName;

    private String taskName;

    private String releaseDate;

    private String industryType;

    private String releaseTime;

    private Integer cooperateMode;

    private Integer taskMode;

    private Integer state;

}
