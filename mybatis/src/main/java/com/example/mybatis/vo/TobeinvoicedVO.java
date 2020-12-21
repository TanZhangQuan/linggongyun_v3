package com.example.mybatis.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 查询总包待开票数据
 */
@Data
public class TobeinvoicedVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String invoiceApplicationId;

    private String companySName;

    private String platformServiceProvider;

    private String applicationDesc;

    /**
     * 状态为空的话就是为申请
     */
    private String isInvoice;

    private String applicationDate;

    private List<OrderSubpackageVO> list;
}
