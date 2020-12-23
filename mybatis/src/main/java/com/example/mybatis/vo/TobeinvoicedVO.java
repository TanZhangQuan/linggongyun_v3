package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "总包待开票数据")
public class TobeinvoicedVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "XXXXXX")
    private String invoiceApplicationId;

    @ApiModelProperty(value = "XXXXXX")
    private String companySName;

    @ApiModelProperty(value = "XXXXXX")
    private String platformServiceProvider;

    @ApiModelProperty(value = "XXXXXX")
    private String applicationDesc;

    @ApiModelProperty(value = "状态为空的话就是为申请")
    private String isInvoice;

    @ApiModelProperty(value = "XXXXXX")
    private String applicationDate;

    @ApiModelProperty(value = "XXXXXX")
    private List<OrderSubpackageVO> list;
}
