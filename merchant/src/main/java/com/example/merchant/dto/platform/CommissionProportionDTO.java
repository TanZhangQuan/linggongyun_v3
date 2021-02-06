package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author jun.
 * @date 2021/1/28.
 * @time 9:37.
 */
@Data
@ApiModel(description = "业务员和代理商的流水结算梯度")
public class CommissionProportionDTO implements Serializable,Comparable<CommissionProportionDTO>{

    @ApiModelProperty(value = "流水结算梯度ID", required = true)
    private String id;

    @ApiModelProperty(value = "对象id,区分代理商和业务员", required = true)
    private String objectId;

    @ApiModelProperty(value = "0代表业务，1代表代理商", required = true)
    @Min(value = 0,message = "最小值为0")
    @Max(value = 1,message = "最大值为1")
    private Integer objectType;

    @ApiModelProperty(value = "0代表直客，1代表代理商的", required = true)
    @Min(value = 0,message = "最小值为0")
    @Max(value = 1,message = "最大值为1")
    private Integer customerType;

    @ApiModelProperty(value = "区间开始金额", required = true)
    @NotNull(message = "区间开始金额不能为空")
    private BigDecimal startMoney;

    @ApiModelProperty(value = "区间开始金额", required = true)
    @NotNull(message = "区间结束金额不能为空")
    private BigDecimal endMoney;

    @ApiModelProperty(value = "佣金结算汇率", required = true)
    @NotNull(message = "佣金结算汇率不能为空")
    private BigDecimal commissionRate;

    @Override
    public int compareTo(CommissionProportionDTO commissionProportionDTO) {
        BigDecimal i = this.getStartMoney().subtract(commissionProportionDTO.getStartMoney());
        return Integer.parseInt(i.toString());//i>=0则该元素排在前面
    }
}
