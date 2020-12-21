package com.example.merchant.vo.makerend;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "创客个人信息")
public class WorkerInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客ID
     */
    private String id;

    /**
     * 微信openid
     */
    private String wxId;

    /**
     * 创客姓名
     */
    private String workerName;

    /**
     * 真实姓名
     */
    private String accountName;

    /**
     * 创客身份证
     */
    private String idcardCode;


    /**
     * 身份证正面
     */
    private String idcardFront;

    /**
     * 身份证反面
     */
    private String idcardBack;


    /**
     * 合同地址
     */
    private String agreementUrl;

    /**
     * 创客电话号码
     */
    private String mobileCode;

    /**
     * 开户银行行
     */
    private String bankName;

    /**
     * 银行卡号
     */
    private String bankCode;

    /**
     * 认证视频
     */
    private String attestationVideo;

    /**
     * 营业执照
     */
    private String businessLicense;

    /**
     * 社会统一代码
     */
    private String creditCode;
}
