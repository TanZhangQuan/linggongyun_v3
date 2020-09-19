package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.Merchant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 商户信息
 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface MerchantService extends IService<Merchant> {

    Merchant findByID(String id);
    ReturnJson getIdAndName();
    String getNameById(String id);
    ReturnJson merchantLogin(String username, String password, HttpServletResponse response);
    String getId(HttpServletRequest request);
    ReturnJson senSMS(String mobileCode);
    ReturnJson loginMobile(String loginMobile, String checkCode, HttpServletResponse resource);
    ReturnJson merchantInfo(String merchantId);
    ReturnJson updataPassWord(String loginMobile, String checkCode, String newPassWord);

    ReturnJson getMerchantList(String managersId, String merchantId, String merchantName, String linkMobile, Integer auditStatus, Integer page,Integer pageSize);

    ReturnJson removeMerchant(String merchantId);
    ReturnJson auditMerchant(String merchantId);
}
