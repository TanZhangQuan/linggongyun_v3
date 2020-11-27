package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <p>
 * 服务商的总包众包信息
 * </p>
 *
 * @author hzp
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_tax_package")
public class TaxPackage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 对应的服务商
     */
    private String taxId;

    /**
     * 税号
     */
    private String invoiceTaxno;

    /**
     * 税费率成本
     */
    private BigDecimal taxPrice;

    /**
     * 建议市场价最小值
     */
    private BigDecimal taxMinPrice;

    /**
     * 建议市场价最大值
     */
    private BigDecimal taxMaxPrice;

    /**
     * 收款方户名
     */
    private String payee;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行账号
     */
    private String bankCode;

    /**
     * 0总包，1众包
     */
    private Integer packageStatus;

    /**
     * 支持的类目ID 逗号分隔 全量更新
     */
    private String supportCategory;

}
