package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * tb_invoice_list
 *
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_invoice_list")
public class InvoiceList extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 总包发票
     */
    private String invoiceId;

    /**
     * 汇总代开发票id
     */
    private String makerTotalInvoiceId;

}