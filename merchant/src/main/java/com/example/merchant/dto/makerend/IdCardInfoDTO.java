package com.example.merchant.dto.makerend;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(description = "创客身份证信息")
public class IdCardInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创客真实姓名")
    @NotBlank(message = "创客真实姓名不能为空")
    private String realName;

    @ApiModelProperty(value = "创客身份证号码")
    @NotBlank(message = "创客身份证号码不能为空")
    @Length(min = 18,max = 18, message = "身份证格式错误")
    private String IdCard;

    @ApiModelProperty(value = "身份证正面的访问地址")
    @NotBlank(message = "身份证正面的访问地址不能为空")
    private String IdCardFront;

    @ApiModelProperty(value = "身份证反面的访问地址")
    @NotBlank(message = "身份证反面的访问地址不能为空")
    private String IdCardBack;
}
