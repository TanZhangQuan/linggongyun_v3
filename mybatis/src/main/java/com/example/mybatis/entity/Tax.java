package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务商公司信息
 *
 * </p>
 *
 * @author hzp
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_tax")
public class Tax extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 公司的简称
     */
    private String taxSName;

    /**
     * 公司的法定人
     */
    private String taxMan;

    /**
     * 公司的营业执照
     */
    private String businessLicense;

    /**
     * 公司全称
     */
    private String taxName;

    /**
     * 公司的详细地址
     */
    private String taxAddress;

    /**
     * 公司的成立时间
     */
    private LocalDateTime taxCreateDate;

    /**
     * 公司联系人
     */
    private String linkMan;

    /**
     * 公司联系电话
     */
    private String linkMobile;

    /**
     * 统一的社会信用代码
     */
    private String creditCode;

    /**
     * 网商银行会员号
     */
    @TableField("member_id")
    private String memberId;

    /**
     * 网商银行子账户唯一识别码
     */
    @TableField("sub_account_no")
    private String subAccountNo;

    /**
     * 公司状态0正常，1停用
     */
    private Integer taxStatus;

}
