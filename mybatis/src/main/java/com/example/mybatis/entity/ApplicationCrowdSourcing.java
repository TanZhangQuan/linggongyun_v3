package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 中包开票申请
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_crowd_sourcing_application")
@ApiModel("众包开票申请")
public class ApplicationCrowdSourcing {


    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    @ApiModelProperty("众包支付id")
    private String paymentOrderManyId;

    @ApiModelProperty("开票类目id")
    private String invoiceCatalogType;

    private LocalDateTime applicationDate;

    @ApiModelProperty("申请开票的地址对应地址表中id")
    private String applicationAddressId;

    @ApiModelProperty("申请状态：0 and null.未申请；1.申请中；2.已拒绝；3.已开票，4未开票")
    private Integer applicationState;

    @ApiModelProperty("申请说明")
    private String applicationDesc;

    @ApiModelProperty("处理说明")
    private String auditDesc;
}
