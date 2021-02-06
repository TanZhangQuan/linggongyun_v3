package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


/**
 * 业务员和代理商的流水结算梯度表
 * @author .
 * @date 2021/1/27.
 * @time 19:47.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_commission_proportion")
public class CommissionProportion extends BaseEntity  {

    /**
     * 对象id,区分代理商和业务员
     */
    private String objectId;
    /**
     *0代表业务，1代表代理商
     */
    private Integer objectType;
    /**
     * 0代表直客，1代表代理商的
     */
    private Integer customerType;
    /**
     * 区间开始金额
     */
    private BigDecimal startMoney;
    /**
     *区间结束金额
     */
    private BigDecimal endMoney;
    /**
     * 佣金结算汇率
     */
    private BigDecimal commissionRate;
}
