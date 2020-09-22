package com.example.mybatis.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * tb_invoice_list
 * @author 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_invoice_list")
public class InvoiceList implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 总包发票
     */
    private String invoiceId;

    /**
     * 汇总代开发票id
     */
    private String makerTotalInvoiceId;

    private static final long serialVersionUID = 1L;
}