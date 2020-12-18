package com.example.common.mybank.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ApiModel("银行卡绑定")
@Data
public class BankCardBind extends BaseRequest {

    @ApiModelProperty(value = "银行全称", required = true)
    @NotBlank(message = "请输入银行全称")
    private String bank_name;

    @ApiModelProperty(value = "支行名称")
    private String bank_branch;

    @ApiModelProperty(value = "联行号")
    private String branch_no;

    @ApiModelProperty(value = "银行账号/卡号", required = true)
    @NotBlank(message = "请输入银行账号/卡号")
    private String bank_account_no;

    @ApiModelProperty(value = "银行开户名", required = true)
    @NotBlank(message = "请输入银行开户名")
    private String account_name;

    @ApiModelProperty(value = "卡类型/仅支持借记", required = true, example = "DC")
    private String card_type;

    @ApiModelProperty(value = "卡属性/C:对私, B:对公", example = "C", required = true)
    private String card_attribute;

    @ApiModelProperty(value = "验证类型/3:三要素验证,4:四要素验证", example = "3")
    private String verify_type;

    @ApiModelProperty(value = "证件类型,暂时只支持身份证", example = "ID_CARD")
    private String certificate_type;

    @ApiModelProperty(value = "证件号")
    private String certificate_no;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "银行预留手机号")
    private String reserved_mobile;

    @ApiModelProperty(value = "支付属性/只支持NORMAL", example = "NORMAL")
    private String pay_attribute;

}
