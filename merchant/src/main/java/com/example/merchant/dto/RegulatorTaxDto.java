package com.example.merchant.dto;


import com.example.mybatis.entity.RegulatorTax;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "监管服务商")
public class RegulatorTaxDto {
    /**
     * 服务商ID
     */
    @ApiModelProperty(notes = "服务商ID", value = "服务商ID", required = true)
    private String taxId;

    /**
     * 监管部门ID
     */
    @ApiModelProperty(notes = "监管部门ID", value = "监管部门ID", required = true)
    private Long regulatorId;

    /**
     * 状态0开启监管，1关闭监管
     */
    @ApiModelProperty(notes = "状态0开启监管，1关闭监管", value = "状态0开启监管，1关闭监管")
    private Integer status = 0;

}
