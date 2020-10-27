package com.example.merchant.dto.myBank;

import lombok.Data;

/**
 * 创建会员用户
 */
@Data
public class MemberAccountDto {

    /**
     * 合作方业务平台用户ID
     */
    private String uid;

    /**
     * 账户类型
     */
    private String account_type;

    /**
     * 账户别名
     */
    private String alias;

    /**
     * 不必填
     */
    private String account_identity;
}
