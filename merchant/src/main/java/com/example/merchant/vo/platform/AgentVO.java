package com.example.merchant.vo.platform;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 代理商
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/7
 */
@Data
public class AgentVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 代理商ID
     */
    private String id;

    /**
     * 代理商名称
     */
    private String agentName;
}
