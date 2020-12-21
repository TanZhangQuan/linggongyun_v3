package com.example.merchant.vo.regulator;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "导出商户的信息")
public class RegulatorMerchantVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    @Excel(name = "商户编号")
    private String companyId;

    /**
     * 商户姓名
     */
    @Excel(name = "商户姓名")
    private String companyName;

    /**
     * 加盟合同
     */
    @Excel(name = "加盟合同")
    private String contract;

    /**
     * 总包支付单数/众包支付单数
     */
    @Excel(name = "总包支付单数/众包支付单数")
    private String orderCount;

    /**
     * 总包+分包交易流水
     */
    @Excel(name = "总包+分包交易流水")
    private BigDecimal countTotalMoney;

    /**
     * 众包交易流水
     */
    @Excel(name = "众包交易流水")
    private BigDecimal countManyMoney;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String auditStatus;

    /**
     * 商户的入驻时间
     */
    @Excel(name = "入驻时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
}
