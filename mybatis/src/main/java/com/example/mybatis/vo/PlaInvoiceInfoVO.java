package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "平台端发票申请信息")
public class PlaInvoiceInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "开票类目")
    private String invoiceCatalogType;

    @ApiModelProperty(value = "申请说明")
    private String applicationDesc;

    @ApiModelProperty(value = "申请地址")
    private String applicationAddress;
    
}
