package com.example.merchant.vo.regulator;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel("公司的支付订单")
public class RegulatorMerchantPaymentOrderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 支付ID
     */
    @Excel(name = "支付编号")
    private String paymentOrderId;

    /**
     * 公司名称
     */
    @Excel(name = "商户")
    private String companyName;

    /**
     * 服务商名称
     */
    @Excel(name = "平台服务商")
    private String taxName;

    /**
     * 合作类型
     */
    @Excel(name = "合作类型")
    private String packageStatus;

    /**
     * 项目合同
     */
    @Excel(name = "项目合同")
    private String companyContract;

    /**
     * 支付清单
     */
    @Excel(name = "支付清单")
    private String paymentInventory;

    /**
     * 交易流水
     */
    @Excel(name = "交易流水")
    private BigDecimal realMoney;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String isInvoice;

    /**
     * 完成时间
     */
    @Excel(name = "完成时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;

}
