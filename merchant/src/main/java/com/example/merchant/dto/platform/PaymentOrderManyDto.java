package com.example.merchant.dto.platform;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/4
 */
@Data
public class PaymentOrderManyDto {

    private String id;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务类型（行业类型）
     */
    private String taskType;

    /**
     * 平台服务商ID
     */
    @NotBlank(message = "平台服务商ID不能为空")
    private String taxId;

    /**
     * 项目合同（存储位置）
     */
    @NotBlank(message = "项目合同（存储位置）")
    private String companyContract;

    /**
     * 支付清单（存储位置）
     */
    @NotBlank(message = "支付清单（存储位置）")
    private String paymentInventory;

    /**
     * 关联的任务(可以不关联)
     */
    private String taskId;

    /**
     * 支付验收单（存储位置）
     */
    @NotBlank(message = "支付验收单（存储位置）")
    private String acceptanceCertificate;

    @ApiParam(hidden = true)
    private Integer taxStatus=0;

}
