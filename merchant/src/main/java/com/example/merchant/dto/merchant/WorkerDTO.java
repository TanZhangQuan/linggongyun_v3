package com.example.merchant.dto.merchant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "查询创客所要的参数！")
public class WorkerDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创客ID")
    private String workerId;

    @ApiModelProperty(value = "创客的真实姓名")
    private String accountName;

    @ApiModelProperty(value = "创客的手机号")
    private String mobileCode;

    @ApiModelProperty(value = "当前页数")
    private Integer pageNo = 1;

    @ApiModelProperty(value = "每页的条数")
    private Integer pageSize = 10;
}
