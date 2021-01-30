package com.example.mybatis.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("接收查询服务商列表")
public class TaxListPO {

    @ApiModelProperty("服务商ID")
    private String taxId;

    @ApiModelProperty("服务商名称")
    private String taxName;

    @ApiModelProperty("联系人")
    private String linkMan;

    @ApiModelProperty("联系人手机号")
    private String linkMobile;

    @ApiModelProperty("总包状态 0没有总包，1有总包")
    private Integer totalStatus;

    @ApiModelProperty("众包状态 0没有众包，1有众包")
    private Integer manyStatus;

    @ApiModelProperty("服务商状态：0正常 1停用")
    private Integer taxStatus;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
}
