package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 服务商银联信息表
 * </p>
 *
 * @author hzp
 * @since 2021-01-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_tax_unionpay")
public class TaxUnionpay extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商ID
     */
    private String taxId;

    /**
     * 银联银行类型
     */
    private String unionpayBankType;

    /**
     * 商户号
     */
    private String merchno;

    /**
     * 平台帐户账号
     */
    private String acctno;

    /**
     * 清分子账户
     */
    private String clearNo;

    /**
     * 手续费子账户
     */
    private String serviceChargeNo;

}
