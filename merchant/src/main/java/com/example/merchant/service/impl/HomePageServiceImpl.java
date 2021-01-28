package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.util.ReturnJson;
import com.example.common.util.VerificationCheck;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.HomePageService;
import com.example.merchant.util.AcquireID;
import com.example.merchant.vo.merchant.HomePageMerchantVO;
import com.example.merchant.vo.platform.HomePageVO;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.po.InvoicePO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class HomePageServiceImpl implements HomePageService {

    @Value("${TOKEN}")
    private String TOKEN;

    @Resource
    private PaymentOrderDao paymentOrderDao;

    @Resource
    private PaymentOrderManyDao paymentOrderManyDao;

    @Resource
    private InvoiceDao invoiceDao;

    @Resource
    private WorkerDao workerDao;

    @Resource
    private CompanyWorkerDao companyWorkerDao;

    @Resource
    private MerchantDao merchantDao;

    @Resource
    private ManagersDao managersDao;

    @Resource
    private AcquireID acquireID;

    @Resource
    private AgentDao agentDao;

    @Resource
    private TaxDao taxDao;

    @Resource
    private CrowdSourcingInvoiceDao crowdSourcingInvoiceDao;

    /**
     * 获取首页基本信息
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getHomePageInfo(String merchantId) {
        Merchant merchant = merchantDao.selectById(merchantId);
        String companyId = merchant.getCompanyId();
        HomePageMerchantVO homePageMerchantVO = new HomePageMerchantVO();
        BigDecimal payment30TotalMoney = paymentOrderDao.selectBy30Day(companyId);
        homePageMerchantVO.setPayment30TotalMoney(payment30TotalMoney);

        BigDecimal paymentTotalMoney = paymentOrderDao.selectTotal(companyId);
        homePageMerchantVO.setPaymentTotalMoney(paymentTotalMoney);

        BigDecimal payment30ManyMoney = paymentOrderManyDao.selectBy30Day(companyId);
        homePageMerchantVO.setPayment30ManyMoney(payment30ManyMoney);

        BigDecimal paymentManyMoney = paymentOrderManyDao.selectTotal(companyId);
        homePageMerchantVO.setPaymentManyMoney(paymentManyMoney);

        BigDecimal payTotalServiceMoney = paymentOrderDao.getTotalServiceMoney(companyId);
        homePageMerchantVO.setPaymentTotalServiceMoney(payTotalServiceMoney);

        BigDecimal payManyServiceMoney = paymentOrderManyDao.getTotalServiceMoney(companyId);
        homePageMerchantVO.setPaymentManyServiceMoney(payManyServiceMoney);

        BigDecimal invoiceManyDKMoney=paymentOrderManyDao.getInvoiceManyDKMoney(companyId);
        homePageMerchantVO.setInvoiceManyDKMoney(invoiceManyDKMoney);


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

        Integer workerCount = companyWorkerDao.selectCount(new QueryWrapper<CompanyWorker>().lambda()
                .eq(CompanyWorker::getCompanyId, companyId));
        homePageMerchantVO.setWorkerTotal(workerCount);
        return ReturnJson.success(homePageMerchantVO);
    }

    @Override
    public ReturnJson getHomePageInofpaas(String userId) throws CommonException {
        String managersId = userId;
        Managers managers = managersDao.selectById(managersId);
        HomePageVO homePageVO;
        List<String> merchantIds = acquireID.getCompanyIds(managersId);
        if (merchantIds == null || merchantIds.size() == 0) {
            return ReturnJson.success((Object) null);
        }
        homePageVO = this.getHomePageOV(merchantIds);
        //当为代理商时可以查看代理商的所有商户及商户所拥有的创客
        if (managers.getUserSign() == 1) {
            Integer workerTotal = companyWorkerDao.selectWorkerCount(merchantIds, 1);
            //除去可能重复的商户ID
            Set merchantIdsSet = new HashSet();
            merchantIdsSet.addAll(merchantIds);
            homePageVO.setWorkerTotal(workerTotal);
            homePageVO.setMerchantTotal(merchantIdsSet.size());
            return ReturnJson.success(homePageVO);
            //当为业务员时以查看所拥有的代理商及代理商下的所以商户及商户所拥有的创客
        } else if (managers.getUserSign() == 2) {
            Integer agentTotal = agentDao.selectCount(new QueryWrapper<Agent>().lambda()
                    .eq(Agent::getSalesManId, managersId));
            Integer workerTotal = companyWorkerDao.selectWorkerCount(merchantIds, 1);
            //除去可能重复的商户ID
            Set merchantIdsSet = new HashSet();
            merchantIdsSet.addAll(merchantIds);
            homePageVO.setWorkerTotal(workerTotal);
            homePageVO.setMerchantTotal(merchantIdsSet.size());
            homePageVO.setAgentTotal(agentTotal);
            return ReturnJson.success(homePageVO);
        } else {// 管理员可以查询所有
            Integer workerTotal = workerDao.selectCount(new QueryWrapper<Worker>().lambda().eq(Worker::getAttestation, 1).eq(Worker::getAgreementSign, 2));
            Integer merchantTotal = merchantDao.selectCount(new QueryWrapper<>());
            Integer agentTotal = agentDao.selectCount(new QueryWrapper<>());
            Integer salesManTotal = managersDao.selectCount(new QueryWrapper<Managers>().lambda()
                    .eq(Managers::getUserSign, 2).eq(Managers::getStatus, 0));
            Integer taxTotal = taxDao.selectCount(new QueryWrapper<>());

            homePageVO.setWorkerTotal(workerTotal);
            homePageVO.setMerchantTotal(merchantTotal);
            homePageVO.setAgentTotal(agentTotal);
            homePageVO.setSalesManTotal(salesManTotal);
            homePageVO.setTaxTotal(taxTotal);
            return ReturnJson.success(homePageVO);
        }
    }

    @Override
    public ReturnJson getTodayById(String merchantId) {
        Merchant merchant = merchantDao.selectById(merchantId);
        if (merchant == null) {
            return ReturnJson.error("您输入的信息有误！");
        }
        return ReturnJson.success((merchantDao.getTodayById(merchant.getCompanyId())));
    }

    @Override
    public ReturnJson getWeekTradeById(String merchantId) {
        Merchant merchant = merchantDao.selectById(merchantId);
        if (merchant == null) {
            return ReturnJson.error("您输入的信息有误！");
        }
        return ReturnJson.success((merchantDao.getWeekTradeById(merchant.getCompanyId())));
    }

    @Override
    public ReturnJson getMonthTradeById(String merchantId) {
        Merchant merchant = merchantDao.selectById(merchantId);
        if (merchant == null) {
            return ReturnJson.error("您输入的信息有误！");
        }
        return ReturnJson.success((merchantDao.getMonthTradeById(merchant.getCompanyId())));
    }

    @Override
    public ReturnJson getYearTradeById(String merchantId) {
        Merchant merchant = merchantDao.selectById(merchantId);
        if (merchant == null) {
            return ReturnJson.error("您输入的信息有误！");
        }
        return ReturnJson.success((merchantDao.getYearTradeById(merchant.getCompanyId())));
    }


    private HomePageVO getHomePageOV(List<String> ids) {
        HomePageVO homePageVO = new HomePageVO();
        if (VerificationCheck.listIsNull(ids)) {
            return homePageVO;
        }
        BigDecimal pay30Total = paymentOrderDao.selectBy30Daypaas(ids);
        BigDecimal pay30Many = paymentOrderManyDao.selectBy30Daypaas(ids);


        BigDecimal payTotalServiceMoney = paymentOrderDao.selectTotalServiceMoney(ids);
        BigDecimal payManyServiceMoney = paymentOrderManyDao.selectTotalServiceMoneyPaas(ids);

        BigDecimal payTotal = paymentOrderDao.selectTotalpaas(ids);
        BigDecimal payMany = paymentOrderManyDao.selectTotalpaas(ids);

        BigDecimal invoiceManyDKMoney=paymentOrderManyDao.selectInvoiceManyDKMoneyPaas(ids);

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
        homePageVO.setInvoiceManyDKMoney(invoiceManyDKMoney);
        homePageVO.setPaymentTotalServiceMoney(payTotalServiceMoney);
        homePageVO.setPaymentManyServiceMoney(payManyServiceMoney);
        homePageVO.setPayment30TotalMoney(pay30Total);
        homePageVO.setPayment30ManyMoney(pay30Many);
        homePageVO.setPaymentTotalMoney(payTotal);
        homePageVO.setPaymentManyMoney(payMany);
        return homePageVO;
    }
}
