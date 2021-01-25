package com.example.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2021/1/25
 */
@Data
@ApiModel("商户与服务商合作信息")
public class TaxCompanyPaymentVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付ID")
    private String id;

    @ApiModelProperty(value = "服务商名称")
    private String platformServiceProvider;

    @ApiModelProperty(value = "商户名称")
    private String companySName;

    @ApiModelProperty(value = "合作类型")
    private Integer cooperateMode;

    @ApiModelProperty(value = "交易流水")
    private BigDecimal transactionFlow;

    @ApiModelProperty(value = "是否开票")
    private Integer isNotInvoice;

    @ApiModelProperty(value = "完成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;

}
