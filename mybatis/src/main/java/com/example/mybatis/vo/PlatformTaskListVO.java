package com.example.mybatis.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 平台端查询任务列表
 */
@Data
public class PlatformTaskListVO implements Serializable {
    private static final long serialVersionUID = 1L;

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
