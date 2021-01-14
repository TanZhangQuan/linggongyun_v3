package com.example.merchant.dto.platform;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "监管服务商")
public class AddRegulatorTaxDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商ID", required = true)
    @NotBlank(message = "服务商ID不能为空")
    private String taxId;

    @ApiModelProperty(value = "监管部门ID", required = true)
    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "监管部门ID不能为空")
    private Long regulatorId;

    @ApiModelProperty(value = "状态0开启监管，1关闭监管")
    private Integer status = 0;

}
