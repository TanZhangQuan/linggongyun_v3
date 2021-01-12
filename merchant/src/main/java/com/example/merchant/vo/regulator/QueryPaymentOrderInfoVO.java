package com.example.merchant.vo.regulator;

import com.example.merchant.vo.ExpressInfoVO;
import com.example.mybatis.vo.PayInfoVO;
import com.example.mybatis.vo.PaymentInventoryVO;
import com.example.mybatis.vo.PaymentOrderVO;
import com.example.mybatis.vo.QuerySubpackageInfoVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "支付订单详情")
public class QueryPaymentOrderInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付订单信息")
    private PaymentOrderVO paymentOrderVo;

    @ApiModelProperty(value = "分包支付信息")
    private QuerySubpackageInfoVO querySubpackageInfoVo;

    @ApiModelProperty(value = "支付明细")
    private List<PaymentInventoryVO> paymentInventories;

    @ApiModelProperty(value = "支付信息")
    private PayInfoVO payInfoVo;

    @ApiModelProperty(value = "总包或众包发票")
    private String invoice;

    @ApiModelProperty(value = "分包发票，当订单为众包时分包发票为空")
    private String SubpackageInvoice;

    @ApiModelProperty(value = "发票的快递信息")
    private ExpressInfoVO expressInfoVO;

}
