package com.example.merchant.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * MakerExcel
 *
 * @author xjw
 * @since 2020/12/30 11:34
 */
@Data
public class MakerPanymentExcel implements Serializable {
    private static final long serialVersionUID = 1L;

    @ExcelProperty("收款方账户名")
    private String payeeName;

    @ExcelProperty("实名手机")
    private String phoneNumber;

    @ExcelProperty("身份证号")
    private String idCardCode;

    @ExcelProperty("开户行")
    private String bankName;

    @ExcelProperty("收款方账号")
    private String bankCardNo;

    @ExcelProperty("发放金额")
    private String realMoney;

}
