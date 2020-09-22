package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
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
@EqualsAndHashCode(callSuper = false)
@TableName("tb_invoice_ladder_price")
public class InvoiceLadderPrice implements Serializable ,Comparable<InvoiceLadderPrice>{

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;

    /**
     * 服务商ID
     */
    private String taxId;

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


    @Override
    public int compareTo(InvoiceLadderPrice o) {
            return this.startMoney.compareTo(o.getStartMoney());
    }
}
