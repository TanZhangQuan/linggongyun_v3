package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_invoice_application")
public class InvoiceApplication  implements Serializable {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;
    /**
     * 申请时间
     */
    private String applicationDate;
    /**
     * 申请时人
     */
    private String applicationPerson;
    /**
     * 开票总额
     */
    private String invoiceTotalAmount;
    /**
     * 开票类目
     */
    private String invoiceCatalogType;
    /**
     * 申请说明
     */
    private String applicationDesc;
    /**
     * 地址
     */
    private String applicationAddress;
    /**
     * 申请状态：0.未申请；1.申请中；2.已拒绝；3.已开票，4未开票
     */
    private Integer applicationState;
    /**
     * 处理说明
     */
    private String applicationHandleDesc;
}
