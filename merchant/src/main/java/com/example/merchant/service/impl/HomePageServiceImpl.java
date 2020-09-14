package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.util.ReturnJson;
import com.example.merchant.ov.HomePageOV;
import com.example.merchant.service.HomePageService;
import com.example.mybatis.entity.MerchantWorker;
import com.example.mybatis.mapper.*;
import com.example.mybatis.po.InvoicePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class HomePageServiceImpl implements HomePageService {


    @Autowired
    private PaymentOrderDao paymentOrderDao;

    @Autowired
    private PaymentOrderManyDao paymentOrderManyDao;

    @Autowired
    private InvoiceDao invoiceDao;

    @Autowired
    private WorkerDao workerDao;

    @Autowired
    private MerchantWorkerDao merchantWorkerDao;

    /**
     * 获取首页基本信息
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getHomePageInof(String merchantId) {
        HomePageOV homePageOV = new HomePageOV();
        Double payment30TotalMoney = paymentOrderDao.selectBy30Day(merchantId);
        if (payment30TotalMoney != null){
            homePageOV.setPayment30TotalMoney(BigDecimal.valueOf(payment30TotalMoney));
        }

        Double paymentTotalMoney = paymentOrderDao.selectTotal(merchantId);
        if (paymentTotalMoney != null){
            homePageOV.setPaymentTotalMoney(BigDecimal.valueOf(paymentTotalMoney));
        }

        Double payment30ManyMoney = paymentOrderManyDao.selectBy30Day(merchantId);
        if (payment30ManyMoney != null){
            homePageOV.setPayment30ManyMoney(BigDecimal.valueOf(payment30ManyMoney));
        }

        Double paymentManyMoney = paymentOrderManyDao.selectTotal(merchantId);
        if (paymentManyMoney != null){
            homePageOV.setPaymentManyMoney(BigDecimal.valueOf(paymentManyMoney));
        }


        List<InvoicePO> invoicePOS = invoiceDao.selectTotal(merchantId);
        for (InvoicePO invoicePO : invoicePOS) {
            if (invoicePO.getPackageStatus() == 0) {
                homePageOV.setInvoiceTotalCount(invoicePO.getCount());
                homePageOV.setInvoiceTotalMoney(invoicePO.getTotalMoney());
            } else {
                homePageOV.setInvoiceManyCount(invoicePO.getCount());
                homePageOV.setInvoiceManyMoney(invoicePO.getTotalMoney());
            }
        }
        Integer workeCount = merchantWorkerDao.selectCount(new QueryWrapper<MerchantWorker>().eq("merchant_id", merchantId));
        homePageOV.setWorkerTotal(workeCount);
        return ReturnJson.success(homePageOV);
    }
}
