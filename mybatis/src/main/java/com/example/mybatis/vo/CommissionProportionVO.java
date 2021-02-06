package com.example.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author jun.
 * @date 2021/1/28.
 * @time 11:24.
 */
@Data
@ApiModel(description = "业务员和代理商的流水结算梯度Vo")
public class CommissionProportionVO implements Serializable {
    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "对象id,区分代理商和业务员")
    private String objectId;

    @ApiModelProperty(value = "0代表业务，1代表代理商")
    private Integer objectType;

    @ApiModelProperty(value = "0代表直客，1代表代理商的")
    private Integer customerType;

    @ApiModelProperty(value = "区间开始金额")
    private BigDecimal startMoney;

    @ApiModelProperty(value = "区间结束金额")
    private BigDecimal endMoney;

    @ApiModelProperty(value = "佣金结算汇率")
    private String commissionRate;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateDate;
}
