package com.example.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "支付信息")
public class BillingInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "XXXXXX")
    private String id;

    @ApiModelProperty(value = "XXXXXX")
    private String companySName;

    @ApiModelProperty(value = "XXXXXX")
    private String platformServiceProvider;

    @ApiModelProperty(value = "XXXXXX")
    private Double realMoney;

    @ApiModelProperty(value = "XXXXXX")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime paymentDate;
}
