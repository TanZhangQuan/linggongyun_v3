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
 * 商户银联信息表
 * </p>
 *
 * @author hzp
 * @since 2021-01-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_company_unionpay")
public class CompanyUnionpay extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    private String companyId;

    /**
     * 服务商银联ID
     */
    private String taxUnionpayId;

    /**
     * 子账户账号
     */
    private String subAccountCode;

    /**
     * 子账号户名
     */
    private String subAccountName;

    /**
     * 来款银行账号
     */
    private String inBankNo;

}
