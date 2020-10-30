package com.example.merchant.service;

import com.example.common.mybank.entity.Enterprise;
import com.example.common.util.ReturnJson;

import javax.servlet.http.HttpServletRequest;

public interface MyBankPayService {

    /**
     * 网商银行  总包支付
     * 假如商户和服务商都没有网商银行账户则直接注册然后提醒商户进行充值
     *
     * @param paymentOrderId
     * @return
     */
    ReturnJson myBankPayByPayId(String paymentOrderId) throws Exception;

    /**
     * 总包回调
     *
     * @param request
     */
    void myBankPayNotifyUrl(HttpServletRequest request);

    /**
     * 网商银行  众包支付
     * 假如商户和服务商都没有网商银行账户则直接注册然后提醒商户进行充值
     *
     * @param paymentOrderManyId
     * @return
     */
    ReturnJson myBankPayManyByPayId(String paymentOrderManyId) throws Exception;

    /**
     * 众包回调
     *
     * @param request
     */
    void myBankPayManyNotifyUrl(HttpServletRequest request);

    /**
     * 商户注册网商银行子账户
     *
     * @param enterprise
     * @return
     */
    ReturnJson enterpriseRegister(Enterprise enterprise, String userId) throws Exception;

    /**
     * 修改网商银行企业信息
     * @param enterprise
     * @param userId
     * @return
     * @throws Exception
     */
    ReturnJson enterpriseInfoModify(Enterprise enterprise, String userId) throws Exception;
}
