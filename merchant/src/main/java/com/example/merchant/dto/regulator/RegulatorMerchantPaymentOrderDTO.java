package com.example.merchant.dto.regulator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(description = "查询所监管商户的支付订单参数")
public class RegulatorMerchantPaymentOrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公司ID", required = true)
    @NotBlank(message = "公司ID不能为空！")
    private String companyId;

    @ApiModelProperty(value = "服务商名称")
    private String taxName;

    @ApiModelProperty(value = "完成时间，开始时间")
    private String startDate;

    @ApiModelProperty(value = "完成时间，结束时间")
    private String endDate;

    @ApiModelProperty(value = "当前页数", required = true)
    private Integer pageNo = 1;

    @ApiModelProperty(value = "每页的条数", required = true)
    private Integer pageSize = 10;
}
