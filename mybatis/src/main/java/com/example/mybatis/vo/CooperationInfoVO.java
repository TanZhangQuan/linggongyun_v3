package com.example.mybatis.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/10
 */
@Data
public class CooperationInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 合作信息ID
     */
    private String id;

    /**
     * 合作类型0总包1分包
     */
    private String packageStatus;

    /**
     * 服务商名称
     */
    private String taxName;

    /**
     * 费用类型0为一口价，1为梯度价
     */
    private String chargeStatus;

    /**
     * 合作合同地址
     */
    private String contract;

    /**
     * 账户
     */
    private String payee;

    /**
     * 银行号
     */
    private String bankCode;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 服务费率
     */
    private List<CompanyLadderServiceVO> companyLadderServiceVOS;
}
