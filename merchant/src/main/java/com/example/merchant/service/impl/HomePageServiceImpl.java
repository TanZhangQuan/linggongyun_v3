package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.util.ReturnJson;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.HomePageService;
import com.example.merchant.util.AcquireID;
import com.example.merchant.vo.merchant.HomePageMerchantVO;
import com.example.merchant.vo.platform.HomePageVO;
import com.example.mybatis.entity.Agent;
import com.example.mybatis.entity.CompanyWorker;
import com.example.mybatis.entity.Managers;
import com.example.mybatis.mapper.*;
import com.example.mybatis.po.InvoicePO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
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
        String companyId = merchantId;
        HomePageMerchantVO homePageMerchantVO = new HomePageMerchantVO();
        BigDecimal payment30TotalMoney = paymentOrderDao.selectBy30Day(companyId);
        homePageMerchantVO.setPayment30TotalMoney(payment30TotalMoney);

        BigDecimal paymentTotalMoney = paymentOrderDao.selectTotal(companyId);
        homePageMerchantVO.setPaymentTotalMoney(paymentTotalMoney);

        BigDecimal payment30ManyMoney = paymentOrderManyDao.selectBy30Day(companyId);
        homePageMerchantVO.setPayment30ManyMoney(payment30ManyMoney);

        BigDecimal paymentManyMoney = paymentOrderManyDao.selectTotal(companyId);
        homePageMerchantVO.setPaymentManyMoney(paymentManyMoney);


        InvoicePO invoicePO = invoiceDao.selectInvoiceMoney(companyId);

        try {
            homePageMerchantVO.setInvoiceTotalCount(invoicePO.getCount());
        } catch (Exception e) {
            log.info(e.toString() + ":" + e.getMessage());
        }
        try {
            homePageMerchantVO.setInvoiceTotalMoney(invoicePO.getTotalMoney());
        } catch (Exception e) {
            log.info(e.toString() + ":" + e.getMessage());
        }


        InvoicePO invoicePOCrow = crowdSourcingInvoiceDao.selectCrowdInvoiceMoney(companyId);

        try {
            homePageMerchantVO.setInvoiceManyCount(invoicePOCrow.getCount());
        } catch (Exception e) {
            log.info(e.toString() + ":" + e.getMessage());
        }
        try {
            homePageMerchantVO.setInvoiceManyMoney(invoicePOCrow.getTotalMoney());
        } catch (Exception e) {
            log.info(e.toString() + ":" + e.getMessage());
        }

        Integer workeCount = companyWorkerDao.selectCount(new QueryWrapper<CompanyWorker>().eq("company_id", companyId));
        homePageMerchantVO.setWorkerTotal(workeCount);
        return ReturnJson.success(homePageMerchantVO);
    }

    @Override
    public ReturnJson getHomePageInofpaas(String managersId) throws CommonException {
        Managers managers = managersDao.selectById(managersId);
        HomePageVO homePageVO = null;
        List<String> merchantIds = acquireID.getMerchantIds(managersId);
        if (merchantIds == null || merchantIds.size() == 0) {
            return ReturnJson.success(homePageVO);
        }
        homePageVO = this.getHomePageOV(merchantIds);
        if (managers.getUserSign() == 1) { //当为代理商时可以查看代理商的所有商户及商户所拥有的创客
            Integer workerTotal = companyWorkerDao.selectCount(new QueryWrapper<CompanyWorker>().in("company_id", merchantIds));
            //除去可能重复的商户ID
            Set merchantIdsSet = new HashSet();
            merchantIdsSet.addAll(merchantIds);
            homePageVO.setWorkerTotal(workerTotal);
            homePageVO.setMerchantTotal(merchantIdsSet.size());
            return ReturnJson.success(homePageVO);
        } else if (managers.getUserSign() == 2) { //当为业务员时以查看所拥有的代理商及代理商下的所以商户及商户所拥有的创客
            Integer agentTotal = agentDao.selectCount(new QueryWrapper<Agent>().eq("sales_man_id", managersId));
            Integer workerTotal = companyWorkerDao.selectCount(new QueryWrapper<CompanyWorker>().in("company_id", merchantIds));
            //除去可能重复的商户ID
            Set merchantIdsSet = new HashSet();
            merchantIdsSet.addAll(merchantIds);
            homePageVO.setWorkerTotal(workerTotal);
            homePageVO.setMerchantTotal(merchantIdsSet.size());
            homePageVO.setAgentTotal(agentTotal);
            return ReturnJson.success(homePageVO);
        } else {// 管理员可以查询所有
            Integer workerTotal = workerDao.selectCount(new QueryWrapper<>());
            Integer merchantTotal = merchantDao.selectCount(new QueryWrapper<>());
            Integer agentTotal = agentDao.selectCount(new QueryWrapper<>());
            Integer salesManTotal = managersDao.selectCount(new QueryWrapper<Managers>().eq("user_sign", 2).eq("status", 0));
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
        try {
            homePageVO.setInvoiceTotalCount(invoicePO.getCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            homePageVO.setInvoiceTotalMoney(invoicePO.getTotalMoney());
        } catch (Exception e) {
            e.printStackTrace();
        }

        InvoicePO invoicePOCrow = crowdSourcingInvoiceDao.selectCrowdInvoiceMoneyPaas(ids);
        try {
            homePageVO.setInvoiceManyCount(invoicePOCrow.getCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            homePageVO.setInvoiceManyMoney(invoicePOCrow.getTotalMoney());
        } catch (Exception e) {
            e.printStackTrace();
        }

        homePageVO.setPayment30TotalMoney(pay30Total);
        homePageVO.setPayment30ManyMoney(pay30Many);
        homePageVO.setPaymentTotalMoney(payTotal);
        homePageVO.setPaymentManyMoney(payMany);
        return homePageVO;
    }
}
