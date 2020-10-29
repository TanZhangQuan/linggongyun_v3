package com.example.merchant.dto.myBank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ApiModel("银行卡绑定")
@ToString
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

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_branch() {
        return bank_branch;
    }

    public void setBank_branch(String bank_branch) {
        this.bank_branch = bank_branch;
    }

    public String getBranch_no() {
        return branch_no;
    }

    public void setBranch_no(String branch_no) {
        this.branch_no = branch_no;
    }

    public String getBank_account_no() {
        return bank_account_no;
    }

    public void setBank_account_no(String bank_account_no) {
        this.bank_account_no = bank_account_no;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getCard_attribute() {
        return card_attribute;
    }

    public void setCard_attribute(String card_attribute) {
        this.card_attribute = card_attribute;
    }

    public String getVerify_type() {
        return verify_type;
    }

    public void setVerify_type(String verify_type) {
        this.verify_type = verify_type;
    }

    public String getCertificate_type() {
        return certificate_type;
    }

    public void setCertificate_type(String certificate_type) {
        this.certificate_type = certificate_type;
    }

    public String getCertificate_no() {
        return certificate_no;
    }

    public void setCertificate_no(String certificate_no) {
        this.certificate_no = certificate_no;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getReserved_mobile() {
        return reserved_mobile;
    }

    public void setReserved_mobile(String reserved_mobile) {
        this.reserved_mobile = reserved_mobile;
    }

    public String getPay_attribute() {
        return pay_attribute;
    }

    public void setPay_attribute(String pay_attribute) {
        this.pay_attribute = pay_attribute;
    }
}
