package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2021/2/5
 */
@Data
@ApiModel("快递信息")
public class ReceiptVO {

    @ApiModelProperty(value = "收件人")
    private String receiptName;

    @ApiModelProperty(value = "收件人手机号")
    private String receiptPhone;

    @ApiModelProperty(value = "收件人地址")
    private String receiptAddress;
}
