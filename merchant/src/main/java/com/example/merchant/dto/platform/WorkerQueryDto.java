package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "创客查询条件")
public class WorkerQueryDto {

    @ApiModelProperty("创客ID")
    private String workerId;

    @ApiModelProperty("创客姓名")
    private String accountName;

    @ApiModelProperty("创客手机号")
    private String mobileCode;

    private Integer page;

    private Integer pageSize;
}
