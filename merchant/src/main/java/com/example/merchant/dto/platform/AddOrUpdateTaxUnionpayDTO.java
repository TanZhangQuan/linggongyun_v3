package com.example.merchant.dto.platform;

import com.example.common.enums.UnionpayBankType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "添加或编辑服务商银联")
public class AddOrUpdateTaxUnionpayDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商银联ID(编辑必传)")
    private String taxUnionpayId;

    @ApiModelProperty(value = "服务商ID")
    private String taxId;

    @ApiModelProperty(value = "银行类型")
    @NotNull(message = "请选择银行类型")
    private UnionpayBankType unionpayBankType;

    @ApiModelProperty(value = "平台公钥")
    private String pfmpubkey;

    @ApiModelProperty(value = "合作方私钥")
    private String prikey;

    @ApiModelProperty(value = "商户号")
    @NotBlank(message = "请输入商户号")
    private String merchno;

    @ApiModelProperty(value = "平台帐户账号")
    @NotBlank(message = "请输入平台帐户账号")
    private String acctno;

    @ApiModelProperty(value = "清分子账户")
    @NotBlank(message = "请输入清分子账户")
    private String clearNo;
    
    @ApiModelProperty(value = "手续费子账户")
    @NotBlank(message = "请输入手续费子账户")
    private String serviceChargeNo;
    
}
