package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 创客表
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_worker")
public class Worker implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 创客id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 商户创建的创客（为空表示没有与商户所关联）
     */
    private String merchantId;

    /**
     * 创客姓名
     */
    private String workerName;

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
     * 创客身份证
     */
    private String idcardCode;

    /**
     * 银行卡号
     */
    private String bankCode;

    /**
     * 实名认证状态（0未认证，1已认证）
     */
    private Integer attestation;

    /**
     * 身份证正面
     */
    @TableField("idcardFront")
    private String idcardFront;

    /**
     * 身份证反面
     */
    @TableField("idcardBack")
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
    @TableField("agreementSign")
    private Integer agreementSign;

    /**
     * 合同地址
     */
    @TableField("agreementUrl")
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
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;


}
