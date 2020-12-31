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
public class MakerExcel implements Serializable {
    private static final long serialVersionUID = 1L;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("身份证号码")
    private String idcardNo;

    @ExcelProperty("手机号码")
    private String phoneNumber;

    @ExcelProperty("开户行全名")
    private String bankName;

    @ExcelProperty("卡号")
    private String bankCardNo;

}
