package com.example.mybatis.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/2
 */
@Data
public class QueryPassRolemenuVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String managersId;

    private String realNmae;

    private String roleName;

    private String userName;

    private String loginMoblie;

    private List<MenuVO> list;
}
