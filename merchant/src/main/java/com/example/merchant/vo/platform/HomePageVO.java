package com.example.merchant.vo.platform;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "首页5个数据统计返还对象")
public class HomePageVO implements Serializable {
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

    /**
     * 所拥有的商户数量
     */
    private Integer merchantTotal;

    /**
     * 所拥有的代理商数量
     */
    private Integer agentTotal;

    /**
     * 所拥有的业务员数量
     */
    private Integer salesManTotal;

    /**
     * 所拥有的服务商数量
     */
    private Integer taxTotal;

}
