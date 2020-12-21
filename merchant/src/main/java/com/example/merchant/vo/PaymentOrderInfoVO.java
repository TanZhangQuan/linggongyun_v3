package com.example.merchant.vo;

import com.example.mybatis.entity.PaymentInventory;
import com.example.mybatis.po.PaymentOrderInfoPO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "支付订单详情")
public class PaymentOrderInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 支付订单信息
     */
    private PaymentOrderInfoPO paymentOrderInfoPO;

    /**
     * 支付明细
     */
    private List<PaymentInventory> paymentInventories;

    /**
     * 总包或众包发票
     */
    private String invoice;

    /**
     * 分包发票，当订单为众包时分包发票为空
     */
    private String SubpackageInvoice;

    /**
     * 发票的快递信息
     */
    private ExpressInfoVO expressInfoVO;

}
