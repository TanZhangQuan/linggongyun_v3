package com.example.mybatis.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "查询服务商列表")
public class RegulatorTaxDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商名称
     */
    private String serviceProviderName;

    /**
     * 服务商ID
     */
    private String serviceProvideId;

    /**
     * 入驻时间，开始时间
     */
    private String startDate;

    /**
     * 入驻时间，结束时间
     */
    private String endDate;

    /**
     * 页码
     */
    private Integer pageNo = 1;

    /**
     * 页大小
     */
    private Integer pageSize = 10;
}
