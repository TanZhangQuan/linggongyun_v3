package com.example.merchant.vo.regulator;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "导出商户的信息")
public class RegulatorMerchantVO {
    @Excel(name = "商户编号")
    @ApiModelProperty("商户编号")
    private String companyId;

    @Excel(name = "商户姓名")
    @ApiModelProperty("商户姓名")
    private String companyName;

    @Excel(name = "加盟合同")
    @ApiModelProperty("加盟合同")
    private String contract;

    @Excel(name = "总包支付单数/众包支付单数")
    @ApiModelProperty(notes = "商户总包单数", value = "商户总包单数")
    private String orderCount;

    @Excel(name = "总包+分包交易流水")
    @ApiModelProperty("总包支付金额")
    private BigDecimal countTotalMoney;

    @Excel(name = "众包交易流水")
    @ApiModelProperty("众包支付金额")
    private BigDecimal countManyMoney;

    @Excel(name = "状态")
    @ApiModelProperty("状态")
    private String auditStatus;

    @Excel(name = "入驻时间",exportFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(notes = "商户的入驻时间", value = "商户的入驻时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
}
