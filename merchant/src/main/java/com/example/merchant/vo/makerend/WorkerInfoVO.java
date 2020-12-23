package com.example.merchant.vo.makerend;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "创客个人信息")
public class WorkerInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创客ID")
    private String id;

    @ApiModelProperty(value = "微信关联ID")
    private String wxId;

    @ApiModelProperty(value = "用户名称")
    private String workerName;

    @ApiModelProperty(value = "实名认证")
    private String accountName;

    @ApiModelProperty(value = "身份证号码")
    private String idcardCode;

    @ApiModelProperty(value = "身份证正面")
    private String idcardFront;

    @ApiModelProperty(value = "身份证反面")
    private String idcardBack;

    @ApiModelProperty(value = "加盟合同")
    private String agreementUrl;

    @ApiModelProperty(value = "手机号码")
    private String mobileCode;

    @ApiModelProperty(value = "开户行")
    private String bankName;

    @ApiModelProperty(value = "银行卡号")
    private String bankCode;

    @ApiModelProperty(value = "活体视频")
    private String attestationVideo;

    @ApiModelProperty(value = "营业执照")
    private String businessLicense;

    @ApiModelProperty(value = "社会统一代码")
    private String creditCode;
}
