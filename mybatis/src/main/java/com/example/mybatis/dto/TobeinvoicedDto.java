package com.example.mybatis.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "开票列表查询")
public class TobeinvoicedDto {

    @ApiModelProperty(value = "商户id")
    private String merchantId;

    @ApiModelProperty(value = "商户名称，平台端使用")
    private String companySName;

    @ApiModelProperty(value = "平台服务商")
    private String platformServiceProvider;

    @ApiModelProperty(value = "创建时间1")
    private String applicationDateOne;

    @ApiModelProperty(value = "创建时间2")
    private String applicationDateTwo;

    @NotNull(message = "当前页不能为空")
    @ApiModelProperty(value = "当前页")
    @Min(value = 1,message = "当前页最小为1")
    private Integer pageNo=1;

    @ApiModelProperty(value = "每页大小")
    private Integer pageSize=10;
}
