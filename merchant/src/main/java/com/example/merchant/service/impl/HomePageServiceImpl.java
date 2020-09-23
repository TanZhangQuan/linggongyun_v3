package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.HomePageService;
import com.example.merchant.util.AcquireID;
import com.example.merchant.vo.HomePageVO;
import com.example.mybatis.entity.Agent;
import com.example.mybatis.entity.CompanyWorker;
import com.example.mybatis.entity.Managers;
import com.example.mybatis.entity.Merchant;
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
    private CompanyWorkerDao companyWorkerDao;

    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private ManagersDao managersDao;

    @Autowired
    private AcquireID acquireID;

    @Autowired
    private AgentDao agentDao;

    @Autowired
    private SalesManDao salesManDao;

    @Autowired
    private TaxDao taxDao;

    @Autowired
    private CrowdSourcingInvoiceDao crowdSourcingInvoiceDao;

    /**
     * 获取首页基本信息
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getHomePageInof(String merchantId) {
        Merchant merchant = merchantDao.selectById(merchantId);
        HomePageVO homePageVO = new HomePageVO();
        BigDecimal payment30TotalMoney = paymentOrderDao.selectBy30Day(merchantId);
        homePageVO.setPayment30TotalMoney(payment30TotalMoney);

        BigDecimal paymentTotalMoney = paymentOrderDao.selectTotal(merchantId);
        homePageVO.setPaymentTotalMoney(paymentTotalMoney);

        BigDecimal payment30ManyMoney = paymentOrderManyDao.selectBy30Day(merchantId);
        homePageVO.setPayment30ManyMoney(payment30ManyMoney);

        BigDecimal paymentManyMoney = paymentOrderManyDao.selectTotal(merchantId);
        homePageVO.setPaymentManyMoney(paymentManyMoney);


        InvoicePO invoicePO = invoiceDao.selectInvoiceMoney(merchantId);
        homePageVO.setInvoiceTotalCount(invoicePO.getCount());
        homePageVO.setInvoiceTotalMoney(invoicePO.getTotalMoney());

        InvoicePO invoicePOCrow = crowdSourcingInvoiceDao.selectCrowdInvoiceMoney(merchantId);
        homePageVO.setInvoiceManyCount(invoicePOCrow.getCount());
        homePageVO.setInvoiceManyMoney(invoicePOCrow.getTotalMoney());

        Integer workeCount = companyWorkerDao.selectCount(new QueryWrapper<CompanyWorker>().eq("company_id", merchant.getCompanyId()));
        homePageVO.setWorkerTotal(workeCount);
        return ReturnJson.success(homePageVO);
    }

    @Override
    public ReturnJson getHomePageInofpaas(String managersId) {
        Managers managers = managersDao.selectById(managersId);
        List<String> merchantIds = acquireID.getMerchantIds(managersId);
        HomePageVO homePageVO = this.getHomePageOV(merchantIds);
        if (managers.getUserSign() == 1) { //当为代理商时可以查看代理商的所有商户及商户所拥有的创客
            Integer workerTotal = companyWorkerDao.selectCount(new QueryWrapper<CompanyWorker>().in("company_id", merchantIds));
            //除去可能重复的商户ID
            Set merchantIdsSet = new HashSet();
            merchantIdsSet.addAll(merchantIds);
            homePageVO.setWorkerTotal(workerTotal);
            homePageVO.setMerchantTotal(merchantIdsSet.size());
            return ReturnJson.success(homePageVO);
        } else if (managers.getUserSign() == 2) { //当为业务员时以查看所拥有的代理商及代理商下的所以商户及商户所拥有的创客
            Integer agentTotal = agentDao.selectCount(new QueryWrapper<Agent>().eq("managers", managersId));
            Integer workerTotal = companyWorkerDao.selectCount(new QueryWrapper<CompanyWorker>().in("company_id", merchantIds));
            //除去可能重复的商户ID
            Set merchantIdsSet = new HashSet();
            merchantIdsSet.addAll(merchantIds);
            homePageVO.setWorkerTotal(workerTotal);
            homePageVO.setMerchantTotal(merchantIdsSet.size());
            homePageVO.setAgentTotal(agentTotal);
            return ReturnJson.success(homePageVO);
        } else if (managers.getUserSign() == 3) { //当为服务商时查看
            Integer workerTotal = companyWorkerDao.selectCount(new QueryWrapper<CompanyWorker>().in("company_id", merchantIds));
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

        InvoicePO invoicePO = invoiceDao.selectInvoiceMoneyPaas(ids);
        homePageVO.setInvoiceTotalCount(invoicePO.getCount());
        homePageVO.setInvoiceTotalMoney(invoicePO.getTotalMoney());

        InvoicePO invoicePOCrow = crowdSourcingInvoiceDao.selectCrowdInvoiceMoneyPaas(ids);
        homePageVO.setInvoiceManyCount(invoicePOCrow.getCount());
        homePageVO.setInvoiceManyMoney(invoicePOCrow.getTotalMoney());

        homePageVO.setPayment30TotalMoney(pay30Total);
        homePageVO.setPayment30ManyMoney(pay30Many);
        homePageVO.setPaymentTotalMoney(payTotal);
        homePageVO.setPaymentManyMoney(payMany);
        return homePageVO;
    }
}
