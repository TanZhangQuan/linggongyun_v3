package com.example.merchant.vo.regulator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "商户的信息")
public class RegulatorMerchantInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户编号")
    private String id;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "统一信用代码")
    private String creditCode;

    @ApiModelProperty(value = "法人姓名")
    private String companyMan;

    @ApiModelProperty(value = "联系方式")
    private String linkMobile;

    @ApiModelProperty(value = "银行账号")
    private String bankCode;

    @ApiModelProperty(value = "开户行")
    private String bankName;

    @ApiModelProperty(value = "加盟合同")
    private String contract;

    @ApiModelProperty(value = "入驻时间")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "公司状态")
    private String companyStatus;

}
