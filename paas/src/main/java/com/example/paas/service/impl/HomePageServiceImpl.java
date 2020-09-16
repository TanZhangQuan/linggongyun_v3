package com.example.paas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.po.InvoicePO;
import com.example.paas.ov.HomePageOV;
import com.example.paas.service.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Autowired
    private ManagersDao managersDao;

    @Autowired
    private AgentDao agentDao;

    @Autowired
    private SalesManDao salesManDao;

    @Autowired
    private MerchantDao merchantDao;

    /**
     * 获取首页基本信息
     *
     * @param managersId
     * @return
     */
    @Override
    public ReturnJson getHomePageInof(String managersId) {
        Managers managers = managersDao.selectById(managersId);
        HomePageOV homePageOV = null;
        if (managers.getUserSign() == 1) { //当为代理商时可以查看代理商的所有商户及商户所拥有的创客
            Agent agent = agentDao.selectOne(new QueryWrapper<Agent>().eq("managers_id", managersId));
            List<Merchant> merchants = merchantDao.selectList(new QueryWrapper<Merchant>().eq("agent_id", agent.getId()));
            List<String> merchantIds = new ArrayList<>();
            for (Merchant merchant : merchants) {
                merchantIds.add(merchant.getId());
            }
            homePageOV = this.getHomePageOV(merchantIds);
            homePageOV.setMerchantTotal(merchants.size());
            return ReturnJson.success(homePageOV);
        } else if (managers.getUserSign() == 2) { //当为业务员时以查看所拥有的代理商及代理商下的所以商户及商户所拥有的创客
            SalesMan salesMan = salesManDao.selectById(managersId);
            //  Integer agentCount = agentDao.selectCount(new QueryWrapper<Agent>().eq("sales_man_id", salesMan.getId()));
            List<Merchant> merchants = merchantDao.selectList(new QueryWrapper<Merchant>().eq("salesMan_id", salesMan.getId()));
            List<String> merchantIds = new ArrayList<>();
            for (Merchant merchant : merchants) {
                merchantIds.add(merchant.getId());
            }
            homePageOV = this.getHomePageOV(merchantIds);
            homePageOV.setMerchantTotal(merchants.size());
            //homePageOV.setAgentTotal(agentCount);

        } else if (managers.getUserSign() == 3) { //当为服务商时查看

        } else if (managers.getUserSign() == 4) {// 管理员可以查询所有
            List<Merchant> merchants = merchantDao.selectList(new QueryWrapper<>());
            List<String> merchantIds = new ArrayList<>();
            for (Merchant merchant : merchants) {
                merchantIds.add(merchant.getId());
            }
            homePageOV = this.getHomePageOV(merchantIds);
            homePageOV.setMerchantTotal(merchants.size());
        } else {
            return ReturnJson.success(homePageOV);
        }
        return ReturnJson.success(homePageOV);
    }

    private HomePageOV getHomePageOV(List<String> merchantIds) {
        HomePageOV homePageOV = new HomePageOV();
        BigDecimal payment30TotalMoney = paymentOrderDao.selectBy30Daypaas(merchantIds);
        homePageOV.setPayment30TotalMoney(payment30TotalMoney);

        BigDecimal paymentTotalMoney = paymentOrderDao.selectTotalpaas(merchantIds);
        homePageOV.setPaymentTotalMoney(paymentTotalMoney);

        BigDecimal payment30ManyMoney = paymentOrderManyDao.selectBy30Daypaas(merchantIds);
        homePageOV.setPayment30ManyMoney(payment30ManyMoney);

        BigDecimal paymentManyMoney = paymentOrderManyDao.selectTotalpaas(merchantIds);

        homePageOV.setPaymentManyMoney(paymentManyMoney);


        List<InvoicePO> invoicePOS = invoiceDao.selectTotalpaas(merchantIds);
        for (InvoicePO invoicePO : invoicePOS) {
            if (invoicePO.getPackageStatus() == 0) {
                homePageOV.setInvoiceTotalCount(invoicePO.getCount());
                homePageOV.setInvoiceTotalMoney(invoicePO.getTotalMoney());
            } else {
                homePageOV.setInvoiceManyCount(invoicePO.getCount());
                homePageOV.setInvoiceManyMoney(invoicePO.getTotalMoney());
            }
        }
        Integer workeCount = merchantWorkerDao.selectCount(new QueryWrapper<MerchantWorker>().in("merchant_id", merchantIds));
        homePageOV.setWorkerTotal(workeCount);
        return homePageOV;
    }
}
