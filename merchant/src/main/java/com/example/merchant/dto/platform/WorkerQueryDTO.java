package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "创客查询条件")
public class WorkerQueryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创客ID")
    private String workerId;

    @ApiModelProperty(value = "创客姓名")
    private String accountName;

    @ApiModelProperty(value = "创客手机号")
    private String mobileCode;

    @ApiModelProperty(value = "页码")
    private Integer pageNo = 1;

    @ApiModelProperty(value = "页大小")
    private Integer pageSize = 10;
}
