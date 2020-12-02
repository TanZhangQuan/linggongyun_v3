package com.example.merchant.dto.platform;

import lombok.Data;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/2
 */
@Data
public class AddressDto {

    /**
     * 联系人
     */
    private String linkName;

    /**
     * 联系电话
     */
    private String linkMobile;

    /**
     * 详细地址
     */
    private String addressName;

    /**
     * 是否默认：0为默认，1为不默认
     */
    private Integer isNot;
}
