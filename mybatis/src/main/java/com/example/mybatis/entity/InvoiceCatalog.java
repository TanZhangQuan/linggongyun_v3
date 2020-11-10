package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hzp
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_invoice_catalog")
public class InvoiceCatalog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    @JsonIgnore
    private String id;

    /**
     * 服务类型
     */
    private String serviceType;

    /**
     * 具体服务内容
     */
    private String serviceContent;

    /**
     * 开票类目
     */
    private String billingCategory;


}
