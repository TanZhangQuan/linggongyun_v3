package com.example.merchant.dto.regulator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "监管部门查询所监管的商户参数")
public class RegulatorMerchantDto {

    @NotBlank(message = "监管部门ID不能为空！")
    @ApiModelProperty(notes = "监管部门ID", value = "监管部门ID", required = true)
    private String regulatorId;

    @ApiModelProperty(notes = "商户ID", value = "商户ID")
    private String companyId;

    @ApiModelProperty(notes = "商户名称", value = "商户名称")
    private String companyName;

    @ApiModelProperty(notes = "商户入驻时间，开始时间", value = "商户入驻时间，开始时间")
    private String startDate;

    @ApiModelProperty(notes = "商户入驻时间，结束时间", value = "商户入驻时间，结束时间")
    private String endDate;

    @ApiModelProperty(notes = "当前页数", value = "当前页数")
    private Integer page = 1;

    @ApiModelProperty(notes = "每页的条数", value = "每页的条数")
    private Integer pageSize = 10;
}