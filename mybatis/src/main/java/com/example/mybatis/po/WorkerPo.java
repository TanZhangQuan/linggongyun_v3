package com.example.mybatis.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "已接创客明细")
public class WorkerPo {

    @ApiModelProperty(value = "创客id")
    private String wId;

    @ApiModelProperty(value = "创客名称")
    private String workerName;

    @ApiModelProperty(value = "创客电话")
    private String mobileCode;

    @ApiModelProperty(value = "创客生份证")
    private String idcardCode;

    @ApiModelProperty(value = "是否实名认证")
    private String attestation;

    @ApiModelProperty(value = "合同url")
    private String agreementUrl;

    @ApiModelProperty(value = "接单时间")
    private String createDate;

    @ApiModelProperty(value = "是否交付")
    private String isDeliver;

    @ApiModelProperty(value ="验收金额")
    private String checkMoney;

    @ApiModelProperty(value = "提交工作成果时间")
    private String achievementDate;

    @ApiModelProperty(value = "提交工作成果说明文字")
    private String achievementDesc;

    @ApiModelProperty(value = "工作成果附件,可以多个文件")
    private String achievementFiles;
}
