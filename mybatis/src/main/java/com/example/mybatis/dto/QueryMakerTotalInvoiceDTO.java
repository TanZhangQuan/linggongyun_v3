package com.example.mybatis.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class QueryMakerTotalInvoiceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户id")
    private String companyId;

    @ApiModelProperty(value = "平台服务商")
    private String platformServiceProvider;

    @ApiModelProperty(value = "创建时间1,yyyy:HH:mm")
    private String applicationDateOne;

    @ApiModelProperty(value = "创建时间2,yyyy:HH:mm")
    private String applicationDateTwo;

    @ApiModelProperty(value = "当前页默认第一页")
    @Min(value = 1, message = "当前页最小为1")
    private Integer pageNo = 1;

    @ApiModelProperty(value = "每页大小默认为10")
    private Integer pageSize = 10;
}
