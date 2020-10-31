package com.example.merchant.service;

import com.example.common.mybank.entity.BankCardBind;
import com.example.common.mybank.entity.Personal;
import com.example.common.util.ReturnJson;

public interface MyBankWorkerService {

    /**
     * 注册网商银行个人会员信息
     *
     * @param personal
     * @param userId
     * @return
     */
    ReturnJson personalRegister(Personal personal, String userId) throws Exception;

    /**
     * 修改网商银行个人会员信息
     *
     * @param personal
     * @param userId
     * @return
     * @throws Exception
     */
    ReturnJson personalInfoModify(Personal personal, String userId) throws Exception;

    /**
     * 查询网商银行个人会员信息
     *
     * @param userId
     * @return
     * @throws Exception
     */
    ReturnJson personalInfoQuery(String userId) throws Exception;

    /**
     * 网商银行绑定银行卡
     *
     * @param params
     * @param userId
     * @return
     * @throws Exception
     */
    ReturnJson bankCardBind(BankCardBind params, String userId) throws Exception;

    /**
     * 网商银行解绑银行卡
     *
     * @param bankId
     * @param userId
     * @return
     */
    ReturnJson bankCardUnBind(String bankId, String userId) throws Exception;

}
