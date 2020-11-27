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

    @ApiModelProperty(notes = "商户id",value = "商户id")
    private String merchantId;

    @ApiModelProperty(notes = "商户名称，平台端使用",value = "商户名称，平台端使用")
    private String companySName;

    @ApiModelProperty(notes = "平台服务商",value = "平台服务商")
    private String platformServiceProvider;

    @ApiModelProperty(notes = "创建时间1,yyyy:HH:mm",value = "创建时间1,yyyy:HH:mm")
    private String applicationDateOne;

    @ApiModelProperty(notes = "创建时间2,yyyy:HH:mm",value = "创建时间2,yyyy:HH:mm")
    private String applicationDateTwo;

    @NotNull(message = "当前页不能为空")
    @ApiModelProperty(notes = "当前页默认第一页",value = "当前页默认第一页")
    @Min(value = 1,message = "当前页最小为1")
    private Integer pageNo=1;

    @ApiModelProperty(notes = "每页大小默认为10",value = "每页大小默认为10")
    private Integer pageSize=10;
}
