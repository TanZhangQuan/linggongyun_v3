package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description
 * @Author tzq
 * @Date 2021/1/13
 */
@Data
@ApiModel("服务商总包或众包梯度价合作信息")
public class InvoiceLadderPriceDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "开始的金额")
    private BigDecimal startMoney;

    @ApiModelProperty(value = "结束的金额")
    private BigDecimal endMoney;

    @ApiModelProperty(value = "服务费（如7.5，不需把百分数换算成小数）")
    private BigDecimal rate;

}
