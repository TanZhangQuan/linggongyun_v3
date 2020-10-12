package com.example.merchant.vo.regulator;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "导出创客的信息")
public class ExportRegulatorWorkerVO {
    @Excel(name = "创客编号")
    @ApiModelProperty(notes = "创客ID", value = "创客ID")
    private String workerId;

    @Excel(name = "姓名")
    @ApiModelProperty(notes = "创客姓名", value = "创客姓名")
    private String workerName;

    @Excel(name = "电话")
    @ApiModelProperty(notes = "创客电话号码", value = "创客电话号码")
    private String mobileCode;

    @Excel(name = "身份证号")
    @ApiModelProperty(notes = "创客的身份证号码", value = "创客的身份证号码")
    private String idCardCode;

    @Excel(name = "总包支付单数/众包支付单数")
    @ApiModelProperty(notes = "创客总包单数", value = "创客总包单数")
    private String orderCount;

    @Excel(name = "总包+分包交易流水")
    @ApiModelProperty(notes = "创客总包的总金额", value = "创客总包的总金额")
    private BigDecimal totalMoney;

    @Excel(name = "众包交易流水")
    @ApiModelProperty(notes = "创客众包总金额", value = "创客众包总金额")
    private BigDecimal manyMoney;

    @Excel(name = "实名认证")
    @ApiModelProperty(notes = "创客实名认证：0未认证，1已认证", value = "创客实名认证：0未认证，1已认证")
    private String attestation;

    @Excel(name = "加盟合同")
    @ApiModelProperty(notes = "创客的加盟合同", value = "创客的加盟合同")
    private String agreementUrl;

    @Excel(name = "创建时间",exportFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(notes = "创客的入驻时间", value = "创客的入驻时间")
    private LocalDateTime createDate;
}
