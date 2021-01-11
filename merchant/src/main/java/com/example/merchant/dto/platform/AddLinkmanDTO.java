package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(description = "商户公司的联系人DTO")
public class AddLinkmanDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "联系人")
    @NotBlank(message = "联系人名称不能为空")
    private String linkName;

    @ApiModelProperty(value = "联系电话")
    @NotBlank(message = "联系电话不能为空")
    private String linkMobile;

    @ApiModelProperty(value = "职位")
    private String post;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "是否默认：0为默认，1为不默认")
    private Integer isNot;

}
