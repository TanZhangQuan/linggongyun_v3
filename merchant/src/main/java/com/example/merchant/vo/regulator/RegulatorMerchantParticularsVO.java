package com.example.merchant.vo.regulator;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "所监管的商户的详情")
public class RegulatorMerchantParticularsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 公司流水统计
     */
    private CountSingleRegulatorMerchantVO countSingleRegulatorMerchantVO;

    /**
     * 公司基本信息
     */
    private RegulatorMerchantInfoVO regulatorMerchantInfoVO;

    /**
     * 公司的支付订单
     */
    List<RegulatorMerchantPaymentOrderVO> regulatorMerchantPaymentOrderVOS;
}
