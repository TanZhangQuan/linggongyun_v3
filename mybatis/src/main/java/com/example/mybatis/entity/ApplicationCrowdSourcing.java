package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 中包开票申请
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_crowd_sourcing_application")
public class ApplicationCrowdSourcing extends BaseEntity {
    private static final long serialVersionUID = 1L;

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
