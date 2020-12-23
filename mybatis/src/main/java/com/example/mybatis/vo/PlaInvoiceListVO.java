package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "XXXXX")
public class PlaInvoiceListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 申请ID
     */
    @ApiModelProperty(value = "XXXXXX")
    private String invoiceApplicationId;

    @ApiModelProperty(value = "XXXXXX")
    private String companySName;

    @ApiModelProperty(value = "XXXXXX")
    private String platformServiceProvider;

    @ApiModelProperty(value = "XXXXXX")
    private String applicationDesc;

    @ApiModelProperty(value = "XXXXXX")
    private String applicationState;

    @ApiModelProperty(value = "XXXXXX")
    private String applicationDate;

    @ApiModelProperty(value = "XXXXXX")
    private String isInvoice;

    @ApiModelProperty(value = "XXXXXX")
    private List<PayVO> payVo;

}
