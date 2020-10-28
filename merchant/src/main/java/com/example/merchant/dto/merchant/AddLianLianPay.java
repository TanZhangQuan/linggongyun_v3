package com.example.merchant.dto.merchant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "添加连连支付参数")
public class AddLianLianPay {
    /**
     * 连连商户号
     */
    @NotBlank(message = "连连商户号不能为空！")
    @ApiModelProperty("连连商户号")
    private String oidPartner;

    /**
     * 连连商户号的私钥
     */
    @NotBlank(message = "连连商户号的私钥不能为空！")
    @ApiModelProperty("连连商户号的私钥")
    private String privateKey;
}
