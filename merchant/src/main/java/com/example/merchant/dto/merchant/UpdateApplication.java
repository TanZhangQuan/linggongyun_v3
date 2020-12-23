package com.example.merchant.dto.merchant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class UpdateApplication implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "申请开票ID")
    private String id;

    @ApiModelProperty(value = "开票类目")
    @NotNull(message = "开票类目不能为空")
    private String invoiceCatalogType;

    @ApiModelProperty(value = "申请说明")
    private String applicationDesc;

    @ApiModelProperty(value = "申请开票地址,引用地址表id")
    @NotNull(message = "收件地址不能为空")
    private String applicationAddress;

}
