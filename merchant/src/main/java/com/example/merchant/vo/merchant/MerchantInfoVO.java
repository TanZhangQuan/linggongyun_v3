package com.example.merchant.vo.merchant;

import com.example.mybatis.entity.Address;
import com.example.mybatis.entity.CompanyInvoiceInfo;
import com.example.mybatis.entity.Linkman;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel(description = "商户基本信息")
public class MerchantInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 公司全称
     */
    private String companyName;

    /**
     * 公司的法定人
     */
    private String companyMan;

    /**
     * 公司的注册资本
     */
    private BigDecimal registeredCapital;

    /**
     * 公司的成立时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime companyCreateDate;

    /**
     * 公司的营业执照
     */
    private String businessLicense;

    /**
     * 加盟合同
     */
    private String contract;

    /**
     * 服务商信息
     */
    private List<TaxVO> taxVOList;

    /**
     * 业务员名称
     */
    private String salesManNmae;

    /**
     * 代理商名称
     */
    private String agentName;

    /**
     * 开票信息
     */
    private CompanyInvoiceInfo companyInvoiceInfo;

    /**
     * 联系人信息
     */
    private List<Linkman> linkmanList;

    /**
     * 快递地址信息
     */
    private List<Address> addressList;

    /**
     * 商户编号
     */
    private String merchantId;

    /**
     * 登录账号
     */
    private String userName;

    /**
     * 登录时用的手机号码
     */
    private String loginMobile;
}
