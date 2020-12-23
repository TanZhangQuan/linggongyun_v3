package com.example.mybatis.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "支付清单")
public class PaymentOrderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "XXXXXX")
    private String id;

    @ApiModelProperty(value = "XXXXXX")
    private String companySName;

    @ApiModelProperty(value = "XXXXXX")
    private String platformServiceProvider;

    @ApiModelProperty(value = "XXXXXX")
    private String acceptanceCertificate;

    @ApiModelProperty(value = "XXXXXX")
    private String paymentOrderStatus;

    @ApiModelProperty(value = "XXXXXX")
    private String paymentInventory;

    @ApiModelProperty(value = "XXXXXX")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date paymentDate;

    @ApiModelProperty(value = "XXXXXX")
    private String subpackagePayment;

    @ApiModelProperty(value = "XXXXXX")
    private TaskVO taskVo;
}
