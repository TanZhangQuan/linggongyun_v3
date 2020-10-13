package com.example.mybatis.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("接收查询服务商列表")
public class TaxListPO {
    @ApiModelProperty(notes = "服务商ID", value = "服务商ID")
    private String taxId;

    @ApiModelProperty(notes = "服务商名称", value = "服务商名称")
    private String taxName;

    @ApiModelProperty(notes = "总包状态0有总包，1没有总包", value = "总包状态0有总包，1没有总包")
    private Integer totalStatus;

    @ApiModelProperty(notes = "众包状态：0有众包，1没有众包", value = "众包状态0有众包，1没有众包")
    private Integer manyStatus;

    @ApiModelProperty(notes = "服务商状态：0正常1停用", value = "服务商状态：0正常1停用")
    private Integer taxStatus;

    @ApiModelProperty(notes = "创建时间", value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
}
