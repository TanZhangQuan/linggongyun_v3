package com.example.merchant.dto.regulator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "监管部门查询所监管的创客参数")
public class RegulatorWorkerDto {

    @ApiModelProperty(notes = "创客ID", value = "创客ID")
    private String workerId;

    @ApiModelProperty(notes = "创客名称", value = "创客名称")
    private String workerName;

    @ApiModelProperty(notes = "创客身份证号码", value = "创客身份证号码")
    private String idCardCode;

    @ApiModelProperty(notes = "创客入驻时间，开始时间", value = "创客入驻时间，开始时间")
    private String startDate;

    @ApiModelProperty(notes = "创客入驻时间，结束时间", value = "创客入驻时间，结束时间")
    private String endDate;

    @ApiModelProperty(notes = "当前页数", value = "当前页数")
    private Integer page = 1;

    @ApiModelProperty(notes = "每页的条数", value = "每页的条数")
    private Integer pageSize = 10;
}
