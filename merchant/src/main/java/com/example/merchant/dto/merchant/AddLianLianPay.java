package com.example.merchant.dto.merchant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(description = "添加连连支付参数")
public class AddLianLianPay implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("连连商户号")
    @NotBlank(message = "连连商户号不能为空")
    private String oidPartner;

    @ApiModelProperty("连连商户号的私钥")
    @NotBlank(message = "连连商户号的私钥不能为空")
    private String privateKey;
}
