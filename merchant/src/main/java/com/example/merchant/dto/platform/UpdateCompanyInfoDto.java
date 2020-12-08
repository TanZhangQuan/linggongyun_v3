package com.example.merchant.dto.platform;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/12/7
 */
@Data
public class UpdateCompanyInfoDto {

    /**
     * 商户id
     */
    private String id;

    /**
     * 公司名称
     */
    private String companySName;

    /**
     * 法人
     */
    private String companyMan;

    /**
     * 注册资本
     */
    private BigDecimal registeredCapital;

    /**
     * 成立日期
     */
    private LocalDateTime companyCreateDate;

    /**
     *统一的社会信用代码
     */
    private String creditCode;

    /**
     * 公司的营业执照
     */
    private String businessLicense;
}
