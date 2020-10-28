package com.example.merchant.dto.myBank;

import lombok.Data;

/**
 * 创建修个个人会员信息
 */
@Data
public class PersonalDto {

    /**
     * UID
     */
    private String uId;

    /**
     * 真实姓名
     */
    private String real_name;

    /**
     * 会员名称
     */
    private String member_name;

    /**
     * 身份验证类型
     */
    private String certificate_type;

    /**
     * 身份证编号
     */
    private String certificate_no;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;
}
