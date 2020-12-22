package com.example.mybatis.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;


@Data
@ApiModel(value = "平台任务搜索")
public class PlatformTaskDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户名称
     */
    private String merchantName;

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
     * 合作类型 0总包+分包,1众包
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
