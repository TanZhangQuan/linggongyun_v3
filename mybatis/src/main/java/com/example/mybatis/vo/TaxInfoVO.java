package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/25
 */
@Data
@ApiModel(description = "服务商信息")
public class TaxInfoVO {

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "服务商名称")
    private String taxName;

    @ApiModelProperty(value = "社会同统一代码")
    private String creditCode;

    @ApiModelProperty(value = "法人")
    private String taxMan;

    @ApiModelProperty(value = "联系号码")
    private String linkMobile;

    @ApiModelProperty(value = "开户行")
    private String bankName;

    @ApiModelProperty(value = "银行号码")
    private String bankCode;

    @ApiModelProperty(value = "创建时间")
    private String createDate;

    @ApiModelProperty(value = "状态")
    private String taxStatus;
}
