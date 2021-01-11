package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 公司信息
 *
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_company_info")
public class CompanyInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 业务员ID(必填)
     */
    private String salesManId;

    /**
     * 代理商ID(可以为空)
     */
    private String agentId;

    /**
     * 公司的简称
     */
    private String companySName;

    /**
     * 公司logo
     */
    private String companyLogo;

    /**
     * 公司规模
     */
    private String companySize;

    /**
     * 公司所属省份
     */
    private String companyProvice;

    /**
     * 公司所属城市
     */
    private String companyCity;

    /**
     * 公司的法定人
     */
    private String companyMan;

    /**
     * 公司的法定人的身份证
     */
    private String companyManIdCard;

    /**
     * 公司的营业执照
     */
    private String businessLicense;

    /**
     * 公司的简介
     */
    private String companyDesc;

    /**
     * 公司全称
     */
    private String companyName;

    /**
     * 开户行and账号
     */
    private String bankAndAccount;

    /**
     * 公司地址and电话
     */
    private String addressAndTelephone;

    /**
     * 开户行名称
     */
    private String bankName;

    /**
     * 银行卡号
     */
    private String bankCode;

    /**
     * 公司的注册资本
     */
    private BigDecimal registeredCapital;

    /**
     * 公司的成立时间
     */
    private LocalDate companyCreateDate;

    /**
     * 网商银行会员号
     */
    private String memberId;

    /**
     * 网商银行子账户(智能试别码)
     */
    private String subAccountNo;

    /**
     * 网商银行绑定银行卡
     */
    private String bankId;

    /**
     * 公司联系人
     */
    private String linkMan;

    /**
     * 公司联系电话
     */
    private String linkMobile;

    /**
     * 加盟合同
     */
    private String contract;

    /**
     * 社会统一信用代码
     */
    private String creditCode;

    /**
     * 审核状态0未审核，1已审核
     */
    private Integer auditStatus;

    /**
     * 公司状态
     */
    private Integer companyStatus;

}
