package com.example.merchant.dto.platform;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "监管服务商")
public class AddRegulatorTaxDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商ID", required = true)
    private String taxId;

    @ApiModelProperty(value = "监管部门ID", required = true)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long regulatorId;

    @ApiModelProperty(value = "状态0开启监管，1关闭监管")
    private Integer status = 0;

}
