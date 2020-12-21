package com.example.merchant.vo.platform;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/2
 */
@Data
public class TaxListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String taxName;
}
