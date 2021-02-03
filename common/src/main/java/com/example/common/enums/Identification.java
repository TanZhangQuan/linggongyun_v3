package com.example.common.enums;

import com.example.common.annotation.SwaggerDisplayEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2021/1/13
 */
@Getter
@AllArgsConstructor
@ApiModel(description = "标识")
public enum Identification {

    @ApiModelProperty(value = "客服电话")
    KFDH("KFDH","客服电话"),

    @ApiModelProperty(value = "隐私协议")
    YSXY("YSXY","隐私协议"),

    @ApiModelProperty(value = "银行卡信息协议")
    YHKXXXY("YHKXXXY","银行卡信息协议"),

    @ApiModelProperty(value = "小程序操作指南")
    XCXCZZN("XCXCZZN","小程序操作指南"),

    @ApiModelProperty(value = "海报")
    HB("HB","海报"),

    @ApiModelProperty(value = "小程序分享")
    XCXFX("XCXFX","小程序分享");

    private final String value;
    private final String desc;

//    //不使用@ToString，手动重写，让swagger显示更好看
//    @Override
//    public String toString() {
//        return value + ":" + desc;
//    }

}
