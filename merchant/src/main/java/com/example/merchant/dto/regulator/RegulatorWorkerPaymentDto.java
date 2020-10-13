package com.example.merchant.dto.regulator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "监管部门查询所监管创客的支付明细查询参数")
public class RegulatorWorkerPaymentDto {
    @NotBlank(message = "监管部门ID不能为空！")
    @ApiModelProperty(notes = "监管部门ID", value = "监管部门ID", required = true)
    private String regulatorId;

    @NotBlank(message = "创客ID不能为空！")
    @ApiModelProperty(notes = "创客ID", value = "创客ID", required = true)
    private String workerId;

    @ApiModelProperty(notes = "商户公司名称", value = "商户公司名称")
    private String companyName;

    @ApiModelProperty(notes = "服务商名称", value = "服务商名称")
    private String taxName;

    @ApiModelProperty(notes = "支付时间，开始时间", value = "支付时间，开始时间")
    private String startDate;

    @ApiModelProperty(notes = "支付时间，结束时间", value = "支付时间，结束时间")
    private String endDate;

    @ApiModelProperty(notes = "当前页数", value = "当前页数", required = true)
    private Integer page = 1;

    @ApiModelProperty(notes = "每页的条数", value = "每页的条数", required = true)
    private Integer pageSize = 10;

}
