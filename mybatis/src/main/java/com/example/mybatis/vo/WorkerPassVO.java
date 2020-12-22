package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(value = "已接创客明细--平台端")
public class WorkerPassVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客ID
     */
    private String wId;

    /**
     * 创客名称
     */
    private String workerName;

    /**
     * 创客电话
     */
    private String mobileCode;

    /**
     * 创客身份证
     */
    private String idcardCode;

    /**
     * 是否实名认证
     */
    private String attestation;

    /**
     * 接单时间
     */
    private String createDate;

    /**
     * 合同
     */
    private String agreementUrl;

    /**
     * 是否交付
     */
    private String isDeliver;

    /**
     * 验收金额
     */
    private BigDecimal acceptanceAmount;

    /**
     * 创客完成状态
     */
    private String status;

    /**
     * 提交工作成果时间
     */
    private String achievementDate;

    /**
     * 提交工作成果说明文字
     */
    private String achievementDesc;

    /**
     * 工作成果附件,可以多个文件
     */
    private String achievementFiles;
}
