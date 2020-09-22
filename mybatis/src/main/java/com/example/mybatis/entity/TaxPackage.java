package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 服务商的总包众包信息
 * </p>
 *
 * @author hzp
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_tax_package")
public class TaxPackage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 税源地公司id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 对应的管理人员
     */
    private String taxId;

    /**
     * 税源地公司名称
     */
    private String taxName;

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

    /**
     * 修改时间
     */

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;


}
