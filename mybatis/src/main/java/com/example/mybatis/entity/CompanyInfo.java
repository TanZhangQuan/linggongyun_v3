package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
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
@EqualsAndHashCode(callSuper = false)
@TableName("tb_company_info")
public class CompanyInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
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
     * 公司的详细地址
     */
    private String companyAddress;

    /**
     * 公司的注册资本
     */
    private BigDecimal registeredCapital;

    /**
     * 公司的成立时间
     */
    private LocalDateTime companyCreateDate;

    /**
     * 开户行(银行名称)
     */
    private String bankName;

    /**
     * 银行账号
     */
    private String bankCode;

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
     * 公司电话(对外公共)
     */
    private String telephones;

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

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 用户修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;

}
