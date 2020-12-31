package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
@ApiModel(description = "接收前端查询服务商参数")
public class TaxListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商名称")
    private String taxName;

    @ApiModelProperty(value = "开始时间")
    private String startDate;

    @ApiModelProperty(value = "结束时间")
    private String endDate;

    @ApiModelProperty(value = "当前页数", required = true)
    @Min(value = 1, message = "必须是大于0的整数")
    private Integer pageNo = 1;

    @ApiModelProperty(value = "一页的条数", required = true)
    @Min(value = 1, message = "必须是大于0的整数")
    private Integer pageSize = 10;
}
