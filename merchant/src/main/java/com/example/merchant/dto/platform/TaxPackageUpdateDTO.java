package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "服务商总包众包合作信息")
public class TaxPackageUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商ID")
    @NotBlank(message = "请选择服务商")
    private String taxId;

    @ApiModelProperty(value = "总包信息")
    private TaxPackageDTO totalTaxPackage;

    @ApiModelProperty(value = "众包信息")
    private TaxPackageDTO manyTaxPackage;

    @ApiModelProperty(value = "分包汇总代开（开票）税率梯度价")
    @Valid
    private List<InvoiceLadderPriceDTO> totalCollectLadders;

    @ApiModelProperty(value = "分包单人单开（开票）税率梯度价")
    @Valid
    private List<InvoiceLadderPriceDTO> totalSingleLadders;

    @ApiModelProperty(value = "总包（手续费）税率梯度价")
    @Valid
    private List<InvoiceLadderPriceDTO> totalServiceLadders;

    @ApiModelProperty(value = "众包（开票）税率梯度价")
    @Valid
    private List<InvoiceLadderPriceDTO> manyLadders;

    @ApiModelProperty(value = "众包（手续费）税率梯度价")
    @Valid
    private List<InvoiceLadderPriceDTO> manyServiceLadders;
}
