package com.example.merchant.dto.merchant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "查询创客所要的参数！")
public class WorkerDto {
    @ApiModelProperty("创客ID")
    private String workerId;

    @ApiModelProperty("创客的真实姓名")
    private String accountName;

    @ApiModelProperty("创客的手机号")
    private String mobileCode;

    @ApiModelProperty("当前页数")
    private Integer page = 1;

    @ApiModelProperty("每页的条数")
    private Integer pageSize = 10;
}
