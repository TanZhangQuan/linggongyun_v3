package com.example.merchant.dto.regulator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(description = "监管部门查询所监管创客的支付明细查询参数")
public class RegulatorWorkerPaymentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "创客ID不能为空！")
    @ApiModelProperty(value = "创客ID", required = true)
    private String workerId;

    @ApiModelProperty(value = "商户公司名称")
    private String companyName;

    @ApiModelProperty(value = "服务商名称")
    private String taxName;

    @ApiModelProperty(value = "支付时间，开始时间")
    private String startDate;

    @ApiModelProperty(value = "支付时间，结束时间")
    private String endDate;

    @ApiModelProperty(value = "当前页数", required = true)
    private Integer pageNo = 1;

    @ApiModelProperty(value = "每页的条数", required = true)
    private Integer pageSize = 10;

}
