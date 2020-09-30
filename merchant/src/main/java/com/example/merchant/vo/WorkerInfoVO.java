package com.example.merchant.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "创客个人信息")
public class WorkerInfoVO {
    /**
     * 创客id
     */
    @ApiModelProperty(notes = "创客id", value = "创客id")
    private String id;
    /**
     * 微信openid
     */
    @ApiModelProperty(notes = "微信关联ID", value = "微信关联ID")
    private String wxId;

    /**
     * 创客姓名
     */
    @ApiModelProperty(notes = "用户名称", value = "用户名称")
    private String workerName;

    /**
     * 真实姓名
     */
    @ApiModelProperty(notes = "实名认证", value = "实名认证")
    private String accountName;

    /**
     * 创客身份证
     */
    @ApiModelProperty(notes = "身份证号码", value = "身份证号码")
    private String idcardCode;


    /**
     * 身份证正面
     */
    @ApiModelProperty(notes = "身份证正面", value = "身份证正面")
    private String idcardFront;

    /**
     * 身份证反面
     */
    @ApiModelProperty(notes = "身份证反面", value = "身份证反面")
    private String idcardBack;


    /**
     * 合同地址
     */
    @ApiModelProperty(notes = "加盟合同", value = "加盟合同")
    @TableField("agreementUrl")
    private String agreementUrl;

    /**
     * 创客电话号码
     */
    @ApiModelProperty(notes = "手机号码", value = "手机号码")
    private String mobileCode;


    /**
     * 开户银行行
     */
    @ApiModelProperty(notes = "开户行", value = "开户行")
    private String bankName;

    /**
     * 银行卡号
     */
    @ApiModelProperty(notes = "银行卡号", value = "银行卡号")
    private String bankCode;

    /**
     * 认证视频
     */
    @ApiModelProperty(notes = "活体视频", value = "活体视频")
    private String attestationVideo;

    /**
     * 认证视频
     */
    @ApiModelProperty(notes = "营业执照", value = "营业执照")
    private String businessLicense;
}
