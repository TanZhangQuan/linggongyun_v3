package com.example.merchant.dto.merchant;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/7
 */
@Data
public class AddApplicationCrowdSourcingDto {

    @ApiModelProperty("申请id，修改用")
    private String id;

    @ApiModelProperty("众包支付id")
    private String paymentOrderManyId;

    @ApiModelProperty("开票类目id")
    @NotBlank(message = "开票类目id不能为空")
    private String invoiceCatalogType;

    @ApiModelProperty("申请开票的地址对应地址表中id")
    @NotBlank(message = "申请开票的地址不能为空")
    private String applicationAddressId;

    @ApiModelProperty("申请说明")
    private String applicationDesc;

}
