package com.example.merchant.dto.regulator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "查询支付信息")
public class PayInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商Id")
    @NotNull(message = "服务商Id不能为空")
    private String taxId;

    @ApiModelProperty(value = "商户名称")
    private String companySName;

    @ApiModelProperty(value = "支付时间，开始时间")
    private String startDate;

    @ApiModelProperty(value = "支付时间，结束时间")
    private String endDate;

    @ApiModelProperty(value = "当前页数", required = true)
    private Integer pageNo = 1;

    @ApiModelProperty(value = "每页的条数", required = true)
    private Integer pageSize = 10;
}
