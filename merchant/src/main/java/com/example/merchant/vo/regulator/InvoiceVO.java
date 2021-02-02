package com.example.merchant.vo.regulator;

import com.example.common.util.ExpressLogisticsInfo;
import com.example.mybatis.vo.SendAndReceiveVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2021/2/2
 */
@Data
@ApiModel("发票信息")
public class InvoiceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "众包或总包发票")
    private String invoiceUrl;

    @ApiModelProperty(value = "分包发票")
    private String subInvoiceUrl;

    @ApiModelProperty(value="物流信息")
    private SendAndReceiveVO sendAndReceiveVo;

    @ApiModelProperty(value = "快递信息")
    private List<ExpressLogisticsInfo> expressLogisticsInfoList;
}
