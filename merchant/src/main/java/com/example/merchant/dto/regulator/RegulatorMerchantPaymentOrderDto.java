package com.example.merchant.dto.regulator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "查询所监管商户的支付订单参数")
public class RegulatorMerchantPaymentOrderDto {

    @ApiModelProperty(value = "监管部门ID", required = true)
    @NotBlank(message = "监管部门ID不能为空！")
    private String regulatorId;

    @ApiModelProperty(value = "公司ID", required = true)
    @NotBlank(message = "公司ID不能为空！")
    private String companyId;

    @ApiModelProperty("服务商名称")
    private String taxName;

    @ApiModelProperty("完成时间，开始时间")
    private String startDate;

    @ApiModelProperty("完成时间，结束时间")
    private String endDate;

    @ApiModelProperty(notes = "当前页数", value = "当前页数", required = true)
    private Integer page = 1;

    @ApiModelProperty(notes = "每页的条数", value = "每页的条数", required = true)
    private Integer pageSize = 10;
}
