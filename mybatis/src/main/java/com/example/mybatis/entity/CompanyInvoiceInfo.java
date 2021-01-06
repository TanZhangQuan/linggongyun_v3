package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 公司的开票信息
 * </p>
 *
 * @author hzp
 * @since 2020-09-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_company_invoice_info")
public class CompanyInvoiceInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

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
    private String addressAndTelephone;

    /**
     * 纳税识别号
     */
    private String taxCode;

    /**
     * 开户行
     */
    private String bankAndAccount;
}
