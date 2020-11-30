package com.example.merchant.dto.regulator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestAttribute;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "监管部门查询所监管的商户参数")
public class RegulatorMerchantDto {

    @ApiModelProperty(notes = "商户ID", value = "商户ID")
    private String companyId;

    @ApiModelProperty(notes = "商户名称", value = "商户名称")
    private String companyName;

    @ApiModelProperty(notes = "商户入驻时间，开始时间", value = "商户入驻时间，开始时间")
    private String startDate;

    @ApiModelProperty(notes = "商户入驻时间，结束时间", value = "商户入驻时间，结束时间")
    private String endDate;

    @ApiModelProperty(notes = "当前页数", value = "当前页数")
    private Integer pageNo = 1;

    @ApiModelProperty(notes = "每页的条数", value = "每页的条数")
    private Integer pageSize = 10;
}
