package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class AddInvoiceCatalogDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务类型")
    @NotBlank(message = "服务类型不能为空")
    private String serviceType;

    @ApiModelProperty(value = "具体服务内容")
    @NotBlank(message = "具体服务内容不能为空")
    private String serviceContent;

    @ApiModelProperty(value = "开票类目")
    @NotBlank(message = "开票类目不能为空")
    private String billingCategory;
}
