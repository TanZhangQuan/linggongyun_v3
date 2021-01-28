package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
     * 公司全称
     */
    private String taxName;

    /**
     * 统一的社会信用代码
     */
    private String creditCode;

    /**
     * 法定人
     */
    private String taxMan;

    /**
     * 公司详细地址
     */
    private String taxAddress;

    /**
     * 公司的成立时间
     */
    private LocalDate taxCreateDate;

    /**
     * 联系人名称
     */
    private String linkMan;

    /**
     * 联系人职位
     */
    private String linkPosition;

    /**
     * 联系人手机号
     */
    private String linkMobile;

    /**
     * 联系人邮箱
     */
    private String linkEmail;

    /**
     * 开票资料-公司名称
     */
    private String invoiceEnterpriseName;

    /**
     * 开票资料-纳税识别号
     */
    private String invoiceTaxNo;

    /**
     * 开票资料-地址电话
     */
    private String invoiceAddressPhone;

    /**
     * 开票资料-开户行及账号
     */
    private String invoiceBankNameAccount;

    /**
     * 收款单位名称
     */
    private String titleOfAccount;

    /**
     * 收款单位账号
     */
    private String bankCode;

    /**
     * 开户行名称
     */
    private String bankName;

    /**
     * 收件人
     */
    private String receiptName;

    /**
     * 收件人手机号
     */
    private String receiptPhone;

    /**
     * 收件地址
     */
    private String receiptAddress;

    /**
     * 营业执照
     */
    private String businessLicense;

    /**
     * 法人身份证
     */
    private String taxManIdcard;

    /**
     * 平台加盟合同
     */
    private String joinContract;

    /**
     * 网商银行会员号
     */
    private String memberId;

    /**
     * 网商银行子账户唯一识别码
     */
    private String subAccountNo;

    /**
     * 公司状态 0正常，1停用
     */
    private Integer taxStatus;

}
