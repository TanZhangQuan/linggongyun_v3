package com.example.merchant.vo.platform;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/3
 */
@Data
public class QuerySalesmanVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 真实姓名
     */
    private String realName;
}
