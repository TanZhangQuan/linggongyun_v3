package com.example.merchant.dto.platform;

import com.example.mybatis.entity.InvoiceLadderPrice;
import com.example.mybatis.entity.Tax;
import com.example.mybatis.entity.TaxPackage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
@ApiModel(description = "添加服务商")
public class TaxDto extends Tax {
    @ApiModelProperty(notes = "总包信息", value = "总包信息")
    private TaxPackage totalTaxPackage;

    @ApiModelProperty(notes = "众包信息", value = "众包信息")
    private TaxPackage manyTaxPackage;

    @ApiModelProperty(notes = "总包税率梯度价", value = "总包税率梯度价")
    private List<InvoiceLadderPrice> totalLadders;

    @ApiModelProperty(notes = "众包税率梯度价", value = "众包税率梯度价")
    private List<InvoiceLadderPrice> manyLadders;
}
