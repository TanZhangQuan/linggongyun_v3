package com.example.merchant.dto.makerend;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@ApiModel(description = "创客的银行卡信息")
public class WorkerBankDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "户名", required = true)
    @NotBlank(message = "户名不能为空")
    private String realName;

    @ApiModelProperty(value = "开户行", required = true)
    @NotBlank(message = "开户行不能为空")
    private String bankName;

    @ApiModelProperty(value = "银行卡号", required = true)
    @NotBlank(message = "银行卡号不能为空")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的银行卡号")
    private String bankCode;

    @ApiModelProperty(value = "支付顺序", hidden = true)
    private Integer sort = 0;
}
