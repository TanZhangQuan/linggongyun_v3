package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <p>
 * 服务商发票税率梯度价
 * </p>
 *
 * @author hzp
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_invoice_ladder_price")
public class InvoiceLadderPrice extends BaseEntity{
    private static final long serialVersionUID = 1L;

    /**
     * 服务商ID
     */
    private String taxId;

    /**
     * 对于的合作类型ID
     */
    private String taxPackageId;

    /**
     * 开始的金额
     */
    private BigDecimal startMoney;

    /**
     * 结束的金额
     */
    private BigDecimal endMoney;

    /**
     * 0分包汇总代开，1分包单人单开，2众包单人单开
     */
    private Integer packaegStatus;

    /**
     * 0月度，1季度
     */
    private Integer status;

    /**
     * 服务费（如7.5，不需把百分数换算成小数）
     */
    private BigDecimal rate;

}
