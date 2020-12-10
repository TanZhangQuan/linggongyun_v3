package com.example.merchant.dto.platform;

import lombok.Data;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/10
 */
@Data
public class UpdateCooperationDto {

    /**
     * 业务员ID
     */
    private String salesManId;

    /**
     * 代理商ID
     */
    private String agentId;
}
