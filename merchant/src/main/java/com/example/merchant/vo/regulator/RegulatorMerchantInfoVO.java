package com.example.merchant.vo.regulator;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "商户的信息")
public class RegulatorMerchantInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    private String id;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 统一信用代码
     */
    private String creditCode;

    /**
     * 法人姓名
     */
    private String companyMan;

    /**
     * 联系方式
     */
    private String linkMobile;

    /**
     * 银行账号
     */
    private String bankCode;

    /**
     * 开户行
     */
    private String bankName;

    /**
     * 加盟合同
     */
    private String contract;

    /**
     * 入驻时间
     */
    private LocalDateTime createDate;

    /**
     * 公司状态
     */
    private String companyStatus;

}
