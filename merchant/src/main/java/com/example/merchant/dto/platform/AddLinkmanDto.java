package com.example.merchant.dto.platform;

import lombok.Data;

/**
 * @Description 联系人信息
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/2
 */
@Data
public class AddLinkmanDto {
    /**
     * 联系人
     */
    private String linkName;

    /**
     * 联系电话
     */
    private String linkMobile;

    /**
     * 职位
     */
    private String post;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否默认：0为默认，1为不默认
     */
    private Integer isNot;

}
