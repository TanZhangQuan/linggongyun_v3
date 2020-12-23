package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class WorkerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创客总赚钱金额")
    private String realMoney ;

    @ApiModelProperty(value = "创客头像")
    private String headPortraits;

    @ApiModelProperty(value = "创客姓名")
    private String accountName;
}
