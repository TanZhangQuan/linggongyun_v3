package com.example.merchant.dto.merchant;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/2
 */
@Data
public class UpdateApplication {

    @ApiModelProperty(value = "申请开票ID", notes = "申请开票ID")
    private String id;

    @NotNull(message = "开票类目不能为空")
    @ApiModelProperty(value = "开票类目", notes = "开票类目")
    private String invoiceCatalogType;

    @ApiModelProperty(value = "申请说明", notes = "申请说明")
    private String applicationDesc;

    @NotNull(message = "收件地址不能为空")
    @ApiModelProperty(value = "申请开票地址,引用地址表id", notes = "申请开票地址,引用地址表id")
    private String applicationAddress;

}
