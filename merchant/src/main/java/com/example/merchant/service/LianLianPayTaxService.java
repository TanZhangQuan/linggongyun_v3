package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.AddLianLianPay;
import com.example.merchant.exception.CommonException;
import com.example.mybatis.entity.Lianlianpay;
import com.example.mybatis.entity.LianlianpayTax;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface LianLianPayTaxService extends IService<LianlianpayTax> {
    /**
     * 添加连连支付商户号和私钥
     * @param taxId
     * @param addLianLianPay
     * @return
     */
    ReturnJson addLianlianPay(String taxId, AddLianLianPay addLianLianPay);

    /**
     * 支付创客的回调接口
     * @param request
     */
    void workerNotifyUrl(HttpServletRequest request);

    /**
     * 支付给创客
     * @param paymentInventoryIds
     * @return
     */
    ReturnJson taxPay(String paymentInventoryIds);
}
