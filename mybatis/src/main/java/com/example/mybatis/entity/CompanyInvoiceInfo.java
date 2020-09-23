package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 公司的开票信息
 * </p>
 *
 * @author hzp
 * @since 2020-09-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_company_invoice_info")
public class CompanyInvoiceInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 公司ID
     */
    private String companyId;

    /**
     * 公司全称
     */
    private String companyName;

    /**
     * 公司地址
     */
    private String companyAddress;

    /**
     * 纳税识别号
     */
    private String taxCode;

    /**
     * 开户行
     */
    private String bankName;

    /**
     * 银行账号
     */
    private String bankCode;

    /**
     * 电话
     */
    private String mobile;


}
