package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.HomePageService;
import com.example.merchant.util.AcquireMerchantID;
import com.example.merchant.vo.merchant.HomePageOV;
import com.example.merchant.vo.paas.HomePageVO;
import com.example.mybatis.entity.Agent;
import com.example.mybatis.entity.Managers;
import com.example.mybatis.entity.MerchantWorker;
import com.example.mybatis.mapper.*;
import com.example.mybatis.po.InvoicePO;
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
    private MerchantDao merchantDao;

    @Autowired
    private ManagersDao managersDao;

    @Autowired
    private AcquireMerchantID acquireMerchantID;

    @Autowired
    private AgentDao agentDao;

    @Autowired
    private SalesManDao salesManDao;

    @Autowired
    private TaxDao taxDao;

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

    @Override
    public ReturnJson getHomePageInofpaas(String managersId) {
        Managers managers = managersDao.selectById(managersId);
        List<String> merchantIds = acquireMerchantID.getMerchantIds(managersId);
        HomePageVO homePageVO = this.getHomePageOV(merchantIds);
        if (managers.getUserSign() == 1) { //当为代理商时可以查看代理商的所有商户及商户所拥有的创客
            Integer workerTotal = merchantWorkerDao.selectCount(new QueryWrapper<MerchantWorker>().in("merchant_id", merchantIds));
            //除去可能重复的商户ID
            Set merchantIdsSet = new HashSet();
            merchantIdsSet.addAll(merchantIds);
            homePageVO.setWorkerTotal(workerTotal);
            homePageVO.setMerchantTotal(merchantIdsSet.size());
            return ReturnJson.success(homePageVO);
        } else if (managers.getUserSign() == 2) { //当为业务员时以查看所拥有的代理商及代理商下的所以商户及商户所拥有的创客
            Integer agentTotal = agentDao.selectCount(new QueryWrapper<Agent>().eq("managers", managersId));
            Integer workerTotal = merchantWorkerDao.selectCount(new QueryWrapper<MerchantWorker>().in("merchant_id", merchantIds));
            //除去可能重复的商户ID
            Set merchantIdsSet = new HashSet();
            merchantIdsSet.addAll(merchantIds);
            homePageVO.setWorkerTotal(workerTotal);
            homePageVO.setMerchantTotal(merchantIdsSet.size());
            homePageVO.setAgentTotal(agentTotal);
            return ReturnJson.success(homePageVO);
        } else if (managers.getUserSign() == 3) { //当为服务商时查看
            Integer workerTotal = merchantWorkerDao.selectCount(new QueryWrapper<MerchantWorker>().in("merchant_id", merchantIds));
            //除去可能重复的商户ID
            Set merchantIdsSet = new HashSet();
            merchantIdsSet.addAll(merchantIds);
            homePageVO.setWorkerTotal(workerTotal);
            homePageVO.setMerchantTotal(merchantIdsSet.size());
            return ReturnJson.success(homePageVO);
        } else {// 管理员可以查询所有
            Integer workerTotal = workerDao.selectCount(new QueryWrapper<>());
            Integer merchantTotal = merchantDao.selectCount(new QueryWrapper<>());
            Integer agentTotal = agentDao.selectCount(new QueryWrapper<>());
            Integer salesManTotal = salesManDao.selectCount(new QueryWrapper<>());
            Integer taxTotal = taxDao.selectCount(new QueryWrapper<>());

            homePageVO.setWorkerTotal(workerTotal);
            homePageVO.setMerchantTotal(merchantTotal);
            homePageVO.setAgentTotal(agentTotal);
            homePageVO.setSalesManTotal(salesManTotal);
            homePageVO.setTaxTotal(taxTotal);
            return ReturnJson.success(homePageVO);
        }
    }
    private HomePageVO getHomePageOV(List<String> ids) {
        HomePageVO homePageVO = new HomePageVO();
        BigDecimal pay30Total = paymentOrderDao.selectBy30Daypaas(ids);
        BigDecimal pay30Many = paymentOrderManyDao.selectBy30Daypaas(ids);

        BigDecimal payTotal = paymentOrderDao.selectTotalpaas(ids);
        BigDecimal payMany = paymentOrderManyDao.selectTotalpaas(ids);

        homePageVO.setPayment30TotalMoney(pay30Total);
        homePageVO.setPayment30ManyMoney(pay30Many);
        homePageVO.setPaymentTotalMoney(payTotal);
        homePageVO.setPaymentManyMoney(payMany);
        return homePageVO;
    }
}
