package com.example.common.mybank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("网上银行公用请求参数")
public class BaseRequest {
    @ApiModelProperty(value = "用户UserGUID", required = true)
    @NotBlank(message = "请传入用户UserGUID")
    private String uid;
    @ApiModelProperty(hidden=true)
    @JsonIgnore
    private String version;
    private String partner_id;
    private String charset;
    private String sign;
    private String sign_type;
    private String service;
    private String memo;

}
