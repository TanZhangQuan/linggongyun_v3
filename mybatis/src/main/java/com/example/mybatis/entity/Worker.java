package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 创客表
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_worker")
public class Worker extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 微信openid
     */
    private String wxId;

    /**
     * 微信名称
     */
    private String wxName;

    /**
     * 创客姓名
     */
    private String workerName;

    /**
     * 创客注册状态
     */
    private Integer workerStatus;

    /**
     * 真实姓名
     */
    private String accountName;

    /**
     * 创客性别
     */
    private Integer workerSex;

    /**
     * 创客电话号码
     */
    private String mobileCode;

    /**
     * 创客身份证号码
     */
    private String idcardCode;

    /**
     * 开户行
     */
    private String bankName;

    /**
     * 银行卡号
     */
    private String bankCode;

    /**
     *  网商银行会员号。
     */
    private String memberId;

    /**
     * 子账户（智能识别码）
     */
    private String subAccountNo;

    private String bankId;

    /**
     * 实名认证状态（0未认证，1已认证,-1已上传图片）
     */
    private Integer attestation;

    /**
     * 身份证正面
     */
    private String idcardFront;

    /**
     * 身份证反面
     */
    private String idcardBack;

    /**
     * 认证视频
     */
    private String attestationVideo;

    /**
     * 登录用户名
     */
    private String userName;

    /**
     * 登陆密码
     */
    private String userPwd;

    /**
     * 主要技能
     */
    private String skill;

    /**
     * 加盟合同(签约状态)
     */
    private Integer agreementSign;

    /**
     * 合同地址
     */
    private String agreementUrl;

    /**
     * 头像
     */
    private String headPortraits;

    /**
     * 照片
     */
    private String picture;

    /**
     * 营业执照
     */
    private String businessLicense;

    /**
     * 社会统一代码
     */
    private String creditCode;

}
