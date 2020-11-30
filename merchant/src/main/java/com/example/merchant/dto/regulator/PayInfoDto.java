package com.example.merchant.dto.regulator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "查询支付信息")
public class PayInfoDto {
    @ApiModelProperty(notes = "服务商Id",value = "服务商Id")
    @NotNull(message = "服务商Id不能为空")
    private String taxId;

    @ApiModelProperty(notes = "商户名称",value = "商户名称")
    private String companySName;

    @ApiModelProperty(notes = "支付时间，开始时间", value = "支付时间，开始时间")
    private String startDate;

    @ApiModelProperty(notes = "支付时间，结束时间", value = "支付时间，结束时间")
    private String endDate;

    @ApiModelProperty(notes = "当前页数", value = "当前页数", required = true)
    private Integer pageNo = 1;

    @ApiModelProperty(notes = "每页的条数", value = "每页的条数", required = true)
    private Integer pageSize = 10;
}
