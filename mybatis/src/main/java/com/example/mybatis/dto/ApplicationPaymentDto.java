package com.example.mybatis.dto;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class ApplicationPaymentDto {

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;


    @ApiModelProperty(value = "申请开票id")
    private String invoiceApplicationId;

    @ApiModelProperty(value = "总包支付id,多个支付id以逗号隔开")
    private String paymentOrderId;
}
