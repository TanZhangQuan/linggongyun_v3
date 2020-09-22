package com.example.mybatis.vo;

import lombok.Data;

import java.util.List;


/**
 * 查询总包待开票数据
 */
@Data
public class TobeinvoicedVo {

    private String invoiceApplicationId;
    private String companySName;
    private String platformServiceProvider;
    private String applicationDesc;
    /**
     * 状态为空的话就是为申请
     */
    private String isInvoice;
    private String applicationDate;
    private List<OrderSubpackageVo> list;
}
