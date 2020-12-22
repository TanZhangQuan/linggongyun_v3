package com.example.mybatis.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/10
 */
@Data
public class CompanyLadderServiceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务费率Id
     */
    private String companyLadderServiceId;

    /**
     * 起始金额
     */
    private String startMoney;

    /**
     * 结束金额
     */
    private String endMoney;

    /**
     * 服务费率
     */
    private String serviceCharge;
}
