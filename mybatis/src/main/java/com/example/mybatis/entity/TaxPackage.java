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
     * 服务商
     */
    private String taxId;

    /**
     * 一口价综合税费率
     */
    private BigDecimal taxPrice;

    /**
     * 合作类型 0总包 1众包
     */
    private Integer packageStatus;

    /**
     * 支持的类目ID 逗号分隔 全量更新
     */
    private String supportCategory;

}
