package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "销售方信息")
public class SellerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公司名称")
    private String invoiceEnterpriseName;

    @ApiModelProperty(value = "纳税识别号")
    private String invoiceTaxNo;

    @ApiModelProperty(value = "地址电话")
    private String invoiceAddressPhone;

    @ApiModelProperty(value = "开户银行及账号")
    private String invoiceBankNameAccount;

}
