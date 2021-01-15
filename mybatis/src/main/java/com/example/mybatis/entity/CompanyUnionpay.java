package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
     * 会员标识
     */
    private String uid;

    /**
     * 子账户账号
     */
    private String subAccountCode;

    /**
     * 子账号户名
     */
    private String subAccountName;

}
