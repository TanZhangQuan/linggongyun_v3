package com.example.mybatis.vo;

import com.example.common.enums.UnionpayBankType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "服务商银联列表")
public class TaxUnionpayListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商银联ID")
    private String id;

    @ApiModelProperty(value = "银联银行类型")
    private UnionpayBankType unionpayBankType;

    @ApiModelProperty(value = "商户号")
    private String merchno;

    @ApiModelProperty(value = "平台帐户账号")
    private String acctno;

    @ApiModelProperty(value = "清分子账户")
    private String clearNo;

    @ApiModelProperty(value = "手续费子账户")
    private String serviceChargeNo;

    @ApiModelProperty(value = "是否启用")
    private Boolean boolEnable;

    @ApiModelProperty(value = "创建时间")
    private String createDate;

}
