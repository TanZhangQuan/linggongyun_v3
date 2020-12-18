package com.example.common.mybank.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("个人会员")
public class Personal extends BaseRequest {
//    @ApiModelProperty("用户UserGUID")
//    @NotBlank(message = "请传入用户UserGUID")
//    private String uid;
    @ApiModelProperty("用户手机号")
    private String mobile;
    @ApiModelProperty("用户名")
    private String member_name;
    @ApiModelProperty("真实姓名")
    private String real_name;
    @ApiModelProperty(value = "证件类型,只支持ID_CARD", example = "ID_CARD")
    private String certificate_type;
    @ApiModelProperty(value = "身份证号码", required = true)
    @NotBlank(message = "请输入用户身份证号码")
    private String certificate_no;
    @ApiModelProperty(value = "是否验证, T/F")
    private String is_verify;

}
