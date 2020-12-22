package com.example.merchant.vo.platform;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/7
 */
@Data
public class QueryCooperationInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户Id
     */
    private String companyInfoId;

    /**
     * 业务员ID
     */
    private String salesManId;

    /**
     * 业务员名称
     */
    private String salesManName;

    /**
     * 代理商ID
     */
    private String agentId;

    /**
     * 代理商名称
     */
    private String agentName;
}
