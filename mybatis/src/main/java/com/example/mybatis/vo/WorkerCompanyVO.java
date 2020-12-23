package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class WorkerCompanyVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创客ID")
    private String id;

    @ApiModelProperty(value = "创客姓名")
    private String accountName;

    @ApiModelProperty(value = "电话号码")
    private String mobileCode;

    @ApiModelProperty(value = "身份证号码")
    private String idcardCode;
}
