package com.example.merchant.dto.makerend;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(description = "创客的银行卡信息")
public class WorkerBankDto {

    @NotBlank(message = "户名不能为空！")
    @ApiModelProperty(notes = "户名", value = "户名", required = true)
    private String realName;

    @NotBlank(message = "开户行不能为空！")
    @ApiModelProperty(notes = "开户行", value = "开户行", required = true)
    private String bankName;

    @NotBlank(message = "银行卡号不能为空！")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的银行卡号")
    @ApiModelProperty(notes = "银行卡号", value = "银行卡号", required = true)
    private String bankCode;

    @ApiModelProperty(notes = "支付顺序", value = "支付顺序", hidden = true)
    private Integer sort = 0;
}
