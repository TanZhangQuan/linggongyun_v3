package com.example.mybatis.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/2
 */
@Data
public class QueryPassRolemenuVo {
    private String managersId;

    private String realNmae;

    private String roleName;

    private String userName;

    private String loginMoblie;

    private List<MenuVo> list;
}
