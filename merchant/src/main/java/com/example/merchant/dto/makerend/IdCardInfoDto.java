package com.example.merchant.dto.makerend;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.RequestAttribute;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "创客身份证信息")
public class IdCardInfoDto {

    @NotBlank(message = "创客真实姓名不能为空！")
    @ApiModelProperty(notes = "创客真实姓名", value = "创客真实姓名")
    private String realName;

    @NotBlank(message = "创客身份证号码不能为空！")
    @Length(min = 18,max = 18, message = "身份证格式错误！")
    @ApiModelProperty(notes = "创客身份证号码", value = "创客身份证号码")
    private String IdCard;

    @NotBlank(message = "身份证正面的访问地址不能为空！")
    @ApiModelProperty(notes = "身份证正面的访问地址", value = "身份证正面的访问地址")
    private String IdCardFront;

    @NotBlank(message = "身份证反面的访问地址不能为空！")
    @ApiModelProperty(notes = "身份证反面的访问地址", value = "身份证反面的访问地址")
    private String IdCardBack;
}
