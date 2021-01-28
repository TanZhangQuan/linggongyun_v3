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
public class CompanyInvoiceLadderPriceDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "开始的金额")
    private BigDecimal startMoney;

    @ApiModelProperty(value = "结束的金额")
    private BigDecimal endMoney;

    @ApiModelProperty(value = "0分包汇总代开，1分包单人单开，2众包单人单开")
    private Integer packaegStatus;

    @ApiModelProperty(value = "0月度，1季度")
    private Integer status;

    @ApiModelProperty(value = "服务商服务费（如7.5，不需把百分数换算成小数）")
    private BigDecimal rate;

    @ApiModelProperty(value = "商户服务费（如7.5，不需把百分数换算成小数）")
    private BigDecimal companyRate;

}
