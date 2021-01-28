package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Description
 * @Author tzq
 * @Date 2021/1/13
 */
@Data
@ApiModel("服务商总包或众包合作信息")
public class TaxPackageDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "一口价综合税费率")
    private BigDecimal taxPrice;

    @ApiModelProperty(value = "梯度价税费率")
    private List<InvoiceLadderPriceDetailVO> invoiceLadderPriceDetailVOList;

}
