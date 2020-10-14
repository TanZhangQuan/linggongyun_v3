package com.example.merchant.vo.regulator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "商户的信息")
public class RegulatorMerchantInfoVO {

    @ApiModelProperty("商户编号")
    private String id;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("统一信用代码")
    private String creditCode;

    @ApiModelProperty("法人姓名")
    private String companyMan;

    @ApiModelProperty("联系方式")
    private String linkMobile;

    @ApiModelProperty("银行账号")
    private String bankCode;

    @ApiModelProperty("开户行")
    private String bankName;

    @ApiModelProperty("加盟合同")
    private String contract;

    @ApiModelProperty("入驻时间")
    private LocalDateTime createDate;

    @ApiModelProperty("公司状态")
    private Integer companyStatus;

}
