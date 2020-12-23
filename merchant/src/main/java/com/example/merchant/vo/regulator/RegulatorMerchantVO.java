package com.example.merchant.vo.regulator;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "导出商户的信息")
public class RegulatorMerchantVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户编号")
    @Excel(name = "商户编号")
    private String companyId;

    @ApiModelProperty(value = "商户姓名")
    @Excel(name = "商户姓名")
    private String companyName;

    @ApiModelProperty(value = "加盟合同")
    @Excel(name = "加盟合同")
    private String contract;

    @ApiModelProperty(value = "商户总包单数")
    @Excel(name = "总包支付单数/众包支付单数")
    private String orderCount;

    @ApiModelProperty(value = "总包支付金额")
    @Excel(name = "总包+分包交易流水")
    private BigDecimal countTotalMoney;

    @ApiModelProperty(value = "众包支付金额")
    @Excel(name = "众包交易流水")
    private BigDecimal countManyMoney;

    @ApiModelProperty(value = "状态")
    @Excel(name = "状态")
    private String auditStatus;

    @ApiModelProperty(value = "商户的入驻时间")
    @Excel(name = "入驻时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
}
