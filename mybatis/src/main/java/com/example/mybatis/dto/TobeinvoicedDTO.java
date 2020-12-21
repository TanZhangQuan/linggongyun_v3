package com.example.mybatis.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "开票列表查询")
public class TobeinvoicedDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    private String merchantId;

    /**
     * 商户名称
     */
    private String companySName;

    /**
     * 平台服务商
     */
    private String platformServiceProvider;

    /**
     * 创建时间1
     */
    private String applicationDateOne;

    /**
     * 创建时间2
     */
    private String applicationDateTwo;

    /**
     * 页码
     */
    private Integer pageNo = 1;

    /**
     * 页大小
     */
    private Integer pageSize = 10;
}
