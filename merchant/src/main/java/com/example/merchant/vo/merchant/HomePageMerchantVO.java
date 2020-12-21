package com.example.merchant.vo.merchant;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "首页5个数据统计返还对象")
public class HomePageMerchantVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 30天的众包支付总金额
     */
    private BigDecimal payment30ManyMoney;

    /**
     * 30天的总包+分包支付总金额
     */
    private BigDecimal payment30TotalMoney;

    /**
     * 众包支付总金额
     */
    private BigDecimal paymentManyMoney;

    /**
     * 总包+分包支付总金额
     */
    private BigDecimal paymentTotalMoney;

    /**
     * 众包的发票数量
     */
    private Integer invoiceManyCount;

    /**
     * 众包的发票总金额
     */
    private BigDecimal invoiceManyMoney;

    /**
     * 总包+分包的发票数量
     */
    private Integer invoiceTotalCount;

    /**
     * 总包+分包的发票总金额
     */
    private BigDecimal invoiceTotalMoney;

    /**
     * 所拥有的创客数量
     */
    private Integer workerTotal;
}
