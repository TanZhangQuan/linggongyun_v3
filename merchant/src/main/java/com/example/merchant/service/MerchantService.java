package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.*;
import com.example.merchant.exception.CommonException;
import com.example.mybatis.entity.Merchant;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 商户信息
 * 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface MerchantService extends IService<Merchant> {

    Merchant findByID(String id);

    ReturnJson getIdAndName();

    String getNameById(String id);

    /**
     * 根据用户名和密码进行登录
     *
     * @param username 用户名
     * @param password 密码
     * @param response
     * @return
     */
    ReturnJson merchantLogin(String username, String password, HttpServletResponse response);

    /**
     * 发送验证码码
     *
     * @param mobileCode
     * @return
     */
    ReturnJson senSMS(String mobileCode);

    /**
     * 根据手机号和验证码进行登录
     *
     * @param loginMobile 手机号码
     * @param checkCode   验证码
     * @param resource
     * @return
     */
    ReturnJson loginMobile(String loginMobile, String checkCode, HttpServletResponse resource);

    /**
     * 获取商户信息
     * @param merchantId
     * @return
     */
    ReturnJson getmerchantCustomizedInfo(String merchantId);

    /**
     * 获取商户信息
     *
     * @param merchantId
     * @return
     */
    ReturnJson merchantInfo(String merchantId);

    /**
     * 修改密码或忘记密码
     *
     * @param loginMobile
     * @param checkCode
     * @param newPassWord
     * @return
     */
    ReturnJson updataPassWord(String loginMobile, String checkCode, String newPassWord);

    /**
     * 购买方信息
     *
     * @param id
     * @return
     */
    ReturnJson getBuyerById(String id); //购买方

    /*---------平台端----------*/

    /**
     * 获取的商户
     *
     * @param managersId
     * @param merchantId
     * @param merchantName
     * @param linkMobile
     * @param auditStatus
     * @return
     */
    ReturnJson getMerchantList(String managersId, String merchantId, String merchantName, String linkMobile, Integer auditStatus, Integer page, Integer pageSize) throws CommonException;

    /**
     * 删除商户
     *
     * @param merchantId
     * @return
     */
    ReturnJson removeMerchant(String merchantId);

    /**
     * 审核商户
     *
     * @param merchantId
     * @return
     */
    ReturnJson auditMerchant(String merchantId);

    /**
     * 获取商户的支付流水
     *
     * @param merchantId
     * @return
     */
    ReturnJson merchantInfoPaas(String merchantId);

    /**
     * 获取商户的支付列表
     *
     * @param merchantId
     * @param page
     * @param pageSize
     * @return
     */
    ReturnJson getMerchantPaymentList(String merchantId, Integer page, Integer pageSize);

    /**
     * 获取支付详情
     *
     * @param paymentOrderId
     * @param packgeStatus
     * @return
     */
    ReturnJson getMerchantPaymentInfo(String paymentOrderId, Integer packgeStatus);

    /**
     * 获取支付清单
     *
     * @param paymentOrderId
     * @param page
     * @param pageSize
     * @return
     */
    ReturnJson getMerchantPaymentInventory(String paymentOrderId, Integer page, Integer pageSize);

    /**
     * 添加商户
     *
     * @param companyDto
     * @return
     * @throws CommonException
     */
    ReturnJson addMerchant(CompanyDto companyDto) throws Exception;

    /**
     * 退出登录
     *
     * @param merchantId
     * @return
     */
    ReturnJson logout(String merchantId);

    /**
     * 查询所有的代理员
     *
     * @return
     */
    ReturnJson queryAgent();

    /**
     * 公司基本信息查询
     *
     * @param companyId
     * @return
     */
    ReturnJson queryCompanyInfo(String companyId);

    /**
     * 修改公司基本信息
     *
     * @param updateCompanyInfoDto
     * @return
     */
    ReturnJson updateCompanyInfo(UpdateCompanyInfoDto updateCompanyInfoDto);

    /**
     * 公司的开票信息
     *
     * @param companyId
     * @return
     */
    ReturnJson queryInvoiceInfo(String companyId);

    /**
     * 修改公司开票信息
     *
     * @param updetaInvoiceInfoDto
     * @return
     */
    ReturnJson updateInvoiceInfo(UpdetaInvoiceInfoDto updetaInvoiceInfoDto);

    /**
     * 公司的账户信息
     *
     * @param companyId
     * @return
     */
    ReturnJson queryMerchantInfo(String companyId);

    /**
     * 修改公司的账户信息
     *
     * @param updateMerchantInfDto
     * @return
     */
    ReturnJson updateMerchantInfo(UpdateMerchantInfDto updateMerchantInfDto);

    /**
     * 公司的合作信息
     *
     * @param companyId
     * @return
     */
    ReturnJson queryCooperationInfo(String companyId);

    /**
     * 修改商户合作信息
     *
     * @param companyId
     * @return
     */
    ReturnJson updateCooperationInfo(String companyId);

    /**
     * 查看商户信息
     *
     * @param companyId
     * @return
     */
    ReturnJson queryCompanyInfoById(String companyId);

    /**
     * 修改商户信息
     *
     * @param updateCompanyDto
     * @return
     */
    ReturnJson updateCompanyInfo(UpdateCompanyDto updateCompanyDto);
}
