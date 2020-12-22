package com.example.mybatis.dto;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
public class InvoiceApplicationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 申请人,商户名称
     */
    private String applicationPerson;

    /**
     * 开票总额
     */
    private Double invoiceTotalAmount;

    /**
     * 开票类目
     */
    @NotNull(message = "开票类目不能为空")
    private String invoiceCatalogType;

    /**
     * 申请说明
     */
    private String applicationDesc;

    /**
     * 申请开票地址,引用地址表ID
     */
    @NotNull(message = "收件地址不能为空")
    private String applicationAddress;

    /**
     * 支付id,多个支付id以逗号隔开
     */
    private String paymentOrderId;
}
