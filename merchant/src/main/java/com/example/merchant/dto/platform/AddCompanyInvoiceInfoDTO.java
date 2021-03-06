package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(description = "商户公司的开票信息DTO")
public class AddCompanyInvoiceInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公司全称")
    @NotBlank(message = "公司全称不能为空")
    private String companyName;

    @ApiModelProperty(value = "纳税识别号")
    @NotBlank(message = "纳税识别号不能为空")
    private String taxCode;

    @ApiModelProperty(value = "公司地址及电话")
    @NotBlank(message = "公司地址及电话不能为空")
    private String addressAndTelephone;

    @ApiModelProperty(value = "开户行及账号")
    @NotBlank(message = "开户行及账号不能为空")
    private String bankAndAccount;


}
