package com.example.merchant.dto.merchant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class AddApplicationCrowdSourcingDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("申请ID，修改用")
    private String id;

    @ApiModelProperty("众包支付ID")
    @NotBlank(message = "众包支付ID能为空")
    private String paymentOrderManyId;

    @ApiModelProperty("开票类目ID")
    @NotBlank(message = "开票类目ID不能为空")
    private String invoiceCatalogType;

    @ApiModelProperty("申请开票的地址对应地址表中ID")
    @NotBlank(message = "申请开票的地址不能为空")
    private String applicationAddressId;

    @ApiModelProperty("申请说明")
    private String applicationDesc;

}
