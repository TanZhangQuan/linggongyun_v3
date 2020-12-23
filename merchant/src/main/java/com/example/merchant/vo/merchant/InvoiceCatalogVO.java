package com.example.merchant.vo.merchant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class InvoiceCatalogVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "开票类目ID")
    private String id;

    @ApiModelProperty(value = "服务类型")
    private String serviceType;

    @ApiModelProperty(value = "具体服务内容")
    private String serviceContent;

    @ApiModelProperty(value = "开票类目")
    private String billingCategory;
}
