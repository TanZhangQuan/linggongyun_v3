package com.example.mybatis.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/8
 */
@Data
public class PlaInvoiceListVo {

    /**
     * 申请ID
     */
    private String invoiceApplicationId;
    private String companySName;
    private String platformServiceProvider;
    private String applicationDesc;
    private String applicationState;
    private String applicationDate;
    private String isInvoice;
    private List<PayVo> payVo;

}
