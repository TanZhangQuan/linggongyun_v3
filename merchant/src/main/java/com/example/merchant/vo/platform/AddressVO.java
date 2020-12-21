package com.example.merchant.vo.platform;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/8
 */
@Data
public class AddressVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 地址ID
     */
    private String id;
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
}