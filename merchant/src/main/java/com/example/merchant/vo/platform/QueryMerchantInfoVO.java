package com.example.merchant.vo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "商户信息")
public class QueryMerchantInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账户信息ID")
    private String id;

    @ApiModelProperty(value = "登录密码")
    private String passWord;

    @ApiModelProperty(value = "登录时用的手机号码")
    private String loginMobile;

    @ApiModelProperty(value = "商户状态")
    private Integer status;

    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 真实姓名
     */
    private String realName;
}
