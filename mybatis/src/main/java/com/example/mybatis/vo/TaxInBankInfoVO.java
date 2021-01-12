package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "服务商银联余额详情")
public class TaxInBankInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "开户行名称")
    private String bankName;

    @ApiModelProperty(value = "银行卡号")
    private String bankCode;

    @ApiModelProperty(value = "账户名称")
    private String titleOfAccount;

}
