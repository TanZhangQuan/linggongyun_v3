package com.example.merchant.vo.regulator;

import com.example.mybatis.vo.PayInfoVO;
import com.example.mybatis.vo.RegulatorPayInfoVO;
import com.example.mybatis.vo.RegulatorPaymentVO;
import com.example.mybatis.vo.RegulatorSubpackageInfoVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2021/1/16
 */
@Data
@ApiModel(description = "支付信息")
@AllArgsConstructor
@NoArgsConstructor
public class QueryPaymentInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "总包+分包 或 众包的支付信息")
    private PayInfoVO payInfoVO;

    @ApiModelProperty(value = "支付清单")
    private List<RegulatorPayInfoVO> regulatorPayInfoVO;

    @ApiModelProperty(value = "分包支付信息")
    private RegulatorPaymentVO regulatorPaymentVO;

    @ApiModelProperty(value = "分包支付信息")
    private RegulatorSubpackageInfoVO regulatorSubpackageInfoVO;

    @ApiModelProperty(value = "发票信息")
    private InvoiceVO invoiceVO;
}
