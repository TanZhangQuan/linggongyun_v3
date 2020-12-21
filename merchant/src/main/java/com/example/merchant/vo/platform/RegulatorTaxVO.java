package com.example.merchant.vo.platform;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "监管服务商信息")
public class RegulatorTaxVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商ID
     */
    private String taxId;

    /**
     * 服务商名称
     */
    private String taxName;

    /**
     * 总包+分包交易流水
     */
    private BigDecimal totalTab;

    /**
     * 众包交易流水
     */
    private BigDecimal manyTba;

    /**
     * 监管状态
     */
    private Integer status;

    /**
     * 开始监管时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startRegulatorDate;
}
