package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 公司信息

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

    @TableId(value = "id", type = IdType.UUID)
    private String id;

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
     * 公司的成立时间
     */
    private LocalDateTime companyCreateDate;

    /**
     * 税源地ID(用来获取税源地信息)
     */
    private String taxId;

    /**
     * 开户行(银行名称)
     */
    private String bankName;

    /**
     * 银行账号
     */
    private String bankCode;

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
     * 公司状态
     */
    private Integer companyStatus;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;


}
