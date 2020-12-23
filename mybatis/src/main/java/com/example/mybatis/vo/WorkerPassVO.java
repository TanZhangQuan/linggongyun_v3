package com.example.mybatis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "已接创客明细--平台端")
public class WorkerPassVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创客ID")
    private String wId;

    @ApiModelProperty(value = "创客名称")
    private String workerName;

    @ApiModelProperty(value = "创客电话")
    private String mobileCode;

    @ApiModelProperty(value = "创客身份证")
    private String idcardCode;

    @ApiModelProperty(value = "是否实名认证")
    private String attestation;

    @ApiModelProperty(value = "接单时间")
    private String createDate;

    @ApiModelProperty(value = "合同")
    private String agreementUrl;

    @ApiModelProperty(value = "是否交付")
    private String isDeliver;

    @ApiModelProperty(value = "验收金额")
    private BigDecimal acceptanceAmount;

    @ApiModelProperty(value = "创客完成状态")
    private String status;

    @ApiModelProperty(value = "提交工作成果时间")
    private String achievementDate;

    @ApiModelProperty(value = "提交工作成果说明文字")
    private String achievementDesc;

    @ApiModelProperty(value = "工作成果附件,可以多个文件")
    private String achievementFiles;
}
