package com.example.paas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.Agent;
import com.example.mybatis.entity.Managers;
import com.example.mybatis.entity.MerchantWorker;
import com.example.mybatis.mapper.*;
import com.example.paas.ov.HomePageOV;
import com.example.paas.service.HomePageService;
import com.example.paas.util.AcquireMerchantID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private ManagersDao managersDao;

    @Autowired
    private AgentDao agentDao;

    @Autowired
    private SalesManDao salesManDao;

    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private AcquireMerchantID acquireMerchantID;

    @Autowired
    private TaxDao taxDao;

    /**
     * 获取首页基本信息
     *
     * @param managersId
     * @return
     */
    @Override
    public ReturnJson getHomePageInof(String managersId) {
        Managers managers = managersDao.selectById(managersId);
        List<String> merchantIds = acquireMerchantID.getMerchantIds(managersId);
        HomePageOV homePageOV = this.getHomePageOV(merchantIds);
        if (managers.getUserSign() == 1) { //当为代理商时可以查看代理商的所有商户及商户所拥有的创客
            Integer workerTotal = merchantWorkerDao.selectCount(new QueryWrapper<MerchantWorker>().in("merchant_id", merchantIds));
            //除去可能重复的商户ID
            Set merchantIdsSet = new HashSet();
            merchantIdsSet.addAll(merchantIds);
            homePageOV.setWorkerTotal(workerTotal);
            homePageOV.setMerchantTotal(merchantIdsSet.size());
            return ReturnJson.success(homePageOV);
        } else if (managers.getUserSign() == 2) { //当为业务员时以查看所拥有的代理商及代理商下的所以商户及商户所拥有的创客
            Integer agentTotal = agentDao.selectCount(new QueryWrapper<Agent>().eq("managers", managersId));
            Integer workerTotal = merchantWorkerDao.selectCount(new QueryWrapper<MerchantWorker>().in("merchant_id", merchantIds));
            //除去可能重复的商户ID
            Set merchantIdsSet = new HashSet();
            merchantIdsSet.addAll(merchantIds);
            homePageOV.setWorkerTotal(workerTotal);
            homePageOV.setMerchantTotal(merchantIdsSet.size());
            homePageOV.setAgentTotal(agentTotal);
            return ReturnJson.success(homePageOV);
        } else if (managers.getUserSign() == 3) { //当为服务商时查看
            Integer workerTotal = merchantWorkerDao.selectCount(new QueryWrapper<MerchantWorker>().in("merchant_id", merchantIds));
            //除去可能重复的商户ID
            Set merchantIdsSet = new HashSet();
            merchantIdsSet.addAll(merchantIds);
            homePageOV.setWorkerTotal(workerTotal);
            homePageOV.setMerchantTotal(merchantIdsSet.size());
            return ReturnJson.success(homePageOV);
        } else {// 管理员可以查询所有
            Integer workerTotal = workerDao.selectCount(new QueryWrapper<>());
            Integer merchantTotal = merchantDao.selectCount(new QueryWrapper<>());
            Integer agentTotal = agentDao.selectCount(new QueryWrapper<>());
            Integer salesManTotal = salesManDao.selectCount(new QueryWrapper<>());
            Integer taxTotal = taxDao.selectCount(new QueryWrapper<>());

            homePageOV.setWorkerTotal(workerTotal);
            homePageOV.setMerchantTotal(merchantTotal);
            homePageOV.setAgentTotal(agentTotal);
            homePageOV.setSalesManTotal(salesManTotal);
            homePageOV.setTaxTotal(taxTotal);
            return ReturnJson.success(homePageOV);
        }
    }
    private HomePageOV getHomePageOV(List<String> ids) {
        HomePageOV homePageOV = new HomePageOV();
        BigDecimal pay30Total = paymentOrderDao.selectBy30Daypaas(ids);
        BigDecimal pay30Many = paymentOrderManyDao.selectBy30Daypaas(ids);

        BigDecimal payTotal = paymentOrderDao.selectTotalpaas(ids);
        BigDecimal payMany = paymentOrderManyDao.selectTotalpaas(ids);

        homePageOV.setPayment30TotalMoney(pay30Total);
        homePageOV.setPayment30ManyMoney(pay30Many);
        homePageOV.setPaymentTotalMoney(payTotal);
        homePageOV.setPaymentManyMoney(payMany);
        return homePageOV;
    }
}
