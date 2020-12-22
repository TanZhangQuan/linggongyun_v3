package com.example.merchant.vo.regulator;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "导出创客的信息")
public class RegulatorWorkerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客ID
     */
    @Excel(name = "创客编号")
    private String workerId;

    /**
     * 创客姓名
     */
    @Excel(name = "姓名")
    private String workerName;

    /**
     * 创客电话号码
     */
    @Excel(name = "电话")
    private String mobileCode;

    /**
     * 创客的身份证号码
     */
    @Excel(name = "身份证号")
    private String idCardCode;

    /**
     * 创客总包单数
     */
    @Excel(name = "总包支付单数/众包支付单数")
    private String orderCount;

    /**
     * 创客总包的总金额
     */
    @Excel(name = "总包+分包交易流水")
    private BigDecimal totalMoney;

    /**
     * 创客众包总金额
     */
    @Excel(name = "众包交易流水")
    private BigDecimal manyMoney;

    /**
     * 创客实名认证
     */
    @Excel(name = "实名认证")
    private String attestation;

    /**
     * 创客的加盟合同
     */
    @Excel(name = "加盟合同")
    private String agreementUrl;

    /**
     * 创客的入驻时间
     */
    @Excel(name = "创建时间", exportFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
}
