package com.example.mybatis.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "所监管的商户信息")
public class RegulatorMerchantInfoPO {

    @ApiModelProperty("商户编号")
    private String companyId;

    @ApiModelProperty("商户姓名")
    private String companyName;

    @ApiModelProperty("加盟合同")
    private String contract;

    @ApiModelProperty("总包支付订单数")
    private Integer countTotalOrder;

    @ApiModelProperty("总包支付金额")
    private BigDecimal countTotalMoney;

    @ApiModelProperty("众包支付订单数")
    private Integer countManyOrder;

    @ApiModelProperty("众包支付金额")
    private BigDecimal countManyMoney;

    @ApiModelProperty("状态")
    private Integer auditStatus;

    @ApiModelProperty("入驻时间")
    private LocalDateTime createDate;
}
