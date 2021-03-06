package com.example.merchant.vo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "商户开票信息")
public class QueryInvoiceInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户开票信息ID")
    private String id;

    @ApiModelProperty(value = "公司全称")
    private String companyName;

    @ApiModelProperty(value = "纳税识别号")
    private String taxCode;

    @ApiModelProperty(value = "地址及电话")
    private String addressAndTelephone;

    @ApiModelProperty(value = "开户行及账号")
    private String bankAndAccount;

}
