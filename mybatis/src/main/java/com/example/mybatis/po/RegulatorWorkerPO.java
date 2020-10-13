package com.example.mybatis.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
/**
 * 监管部门所监管的创客信息
 */
public class RegulatorWorkerPO {
    @ApiModelProperty(notes = "创客ID", value = "创客ID")
    private String workerId;

    @ApiModelProperty(notes = "创客姓名", value = "创客姓名")
    private String workerName;

    @ApiModelProperty(notes = "创客电话号码", value = "创客电话号码")
    private String mobileCode;

    @ApiModelProperty(notes = "创客的身份证号码", value = "创客的身份证号码")
    private String idCardCode;

    @ApiModelProperty(notes = "创客总包单数", value = "创客总包单数")
    private Integer totalOrderCount;

    @ApiModelProperty(notes = "创客总包的总金额", value = "创客总包的总金额")
    private BigDecimal totalMoney;

    @ApiModelProperty(notes = "总包纳税金额", value = "总纳税金额")
    private BigDecimal totalTaxMoney;

    @ApiModelProperty(notes = "创客众包单数", value = "创客众包单数")
    private Integer manyOrderCount;

    @ApiModelProperty(notes = "创客众包总金额", value = "创客众包总金额")
    private BigDecimal manyMoney;

    @ApiModelProperty(notes = "众包纳税金额", value = "总纳税金额")
    private BigDecimal manyTaxMoney;

    @ApiModelProperty(notes = "创客实名认证：0未认证，1已认证", value = "创客实名认证：0未认证，1已认证")
    private Integer attestation;

    @ApiModelProperty(notes = "创客的加盟合同", value = "创客的加盟合同")
    private String agreementUrl;

    @ApiModelProperty(notes = "创客的入驻时间", value = "创客的入驻时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
}
