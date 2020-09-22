package com.example.mybatis.vo;

import lombok.Data;

import java.util.List;

/**
 * 分包待开显示
 */
@Data
public class SubpackageVo {
    //发票id
    private String id;
    //发票编号
    private String invoiceCode;
    //商户名称
    private String companySName;
    //服务商名称
    private String platformServiceProvider;
    //总包发票url
    private String invoiceUrl;
    //总包税票url
    private String taxReceiptUrl;
    //分包发票url
    private String makerInvoiceUrl;
    //分包税票url
    private String makerTaxUrl;
    //是否申请开分包
    private Integer isSubpackage;
    //开票时间
    private String invoiceDate;
    //支付状态
    private Integer paymentOrderStatus;
    //支付时间
    private String paymentDate;
    //税价合计
    private Double totalAmount;

    //对应的支付清单
    private List<OrderSubpackageVo> list;

    //关联的任务
    private List<TaskVo> taskVoList;
}
