package com.example.merchant.vo.regulator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "所监管的商户的详情")
public class RegulatorMerchantParticularsVO {

    @ApiModelProperty("公司流水统计")
    private CountSingleRegulatorMerchantVO countSingleRegulatorMerchantVO;

    @ApiModelProperty("公司基本信息")
    private RegulatorMerchantInfoVO regulatorMerchantInfoVO;

    @ApiModelProperty("公司的支付订单")
    List<RegulatorMerchantPaymentOrderVO> regulatorMerchantPaymentOrderVOS;
}
