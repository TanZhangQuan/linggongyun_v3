package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ReturnJson;
import com.example.common.util.VerificationCheck;
import com.example.merchant.dto.TaxListDTO;
import com.example.merchant.dto.platform.*;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.InvoiceLadderPriceService;
import com.example.merchant.service.TaxService;
import com.example.merchant.vo.merchant.CompanyFlowInfoVO;
import com.example.merchant.vo.platform.HomePageVO;
import com.example.merchant.vo.platform.TaxPlatformVO;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.po.InvoicePO;
import com.example.mybatis.po.MerchantPaymentListPO;
import com.example.mybatis.po.TaxListPO;
import com.example.mybatis.vo.SellerVO;
import com.example.mybatis.vo.TaxBriefVO;
import com.example.mybatis.vo.TaxInBankInfoVO;
import com.example.mybatis.vo.TaxListVO;
import com.example.mybatis.vo.TaxTransactionFlowVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 合作园区信息 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class TaxServiceImpl extends ServiceImpl<TaxDao, Tax> implements TaxService {

    @Resource
    private TaxDao taxDao;

    @Resource
    private CompanyTaxDao companyTaxDao;

    @Resource
    private InvoiceCatalogDao invoiceCatalogDao;

    @Resource
    private TaxPackageDao taxPackageDao;

    @Resource
    private InvoiceLadderPriceService invoiceLadderPriceService;

    @Resource
    private PaymentOrderDao paymentOrderDao;

    @Resource
    private PaymentOrderManyDao paymentOrderManyDao;

    @Resource
    private InvoiceDao invoiceDao;

    @Resource
    private CrowdSourcingInvoiceDao crowdSourcingInvoiceDao;

    @Resource
    private MerchantDao merchantDao;

    @Resource
    private WorkerDao workerDao;


    @Override
    public ReturnJson getTaxAll(String merchantId, Integer packageStatus) {
        Merchant merchant = merchantDao.selectById(merchantId);
        List<CompanyTax> companyTaxes = companyTaxDao.selectList(new QueryWrapper<CompanyTax>()
                .eq("company_id", merchant.getCompanyId()).eq("package_status", packageStatus));
        List<String> ids = new LinkedList<>();
        for (CompanyTax companyTax : companyTaxes) {
            ids.add(companyTax.getTaxId());
        }
        List<Tax> taxes = null;
        if (!VerificationCheck.listIsNull(ids)) {
            taxes = taxDao.selectList(new QueryWrapper<Tax>().in("id", ids).eq("tax_status", 0));
        }
        List<TaxBriefVO> taxBriefVOS = new ArrayList<>();
        if (taxes != null) {
            taxes.forEach(tax -> {
                TaxBriefVO taxBriefVO = new TaxBriefVO();
                BeanUtils.copyProperties(tax, taxBriefVO);
                taxBriefVOS.add(taxBriefVO);
            });
        }
        if (taxes == null) {
            return ReturnJson.success("没有可用的平台服务商");
        }
        return ReturnJson.success(taxBriefVOS);
    }

    @Override
    public ReturnJson getTaxPaasAll(String companyId, Integer packageStatus) {
        List<CompanyTax> companyTaxes = companyTaxDao.selectList(new QueryWrapper<CompanyTax>()
                .eq("company_id", companyId).eq("package_status", packageStatus));
        List<String> ids = new LinkedList<>();
        for (CompanyTax companyTax : companyTaxes) {
            ids.add(companyTax.getTaxId());
        }
        List<Tax> taxes = null;
        if (!VerificationCheck.listIsNull(ids)) {
            taxes = taxDao.selectList(new QueryWrapper<Tax>().in("id", ids).eq("tax_status", 0));
        }
        List<TaxBriefVO> taxBriefVOS = new ArrayList<>();
        taxes.forEach(tax -> {
            TaxBriefVO taxBriefVO = new TaxBriefVO();
            BeanUtils.copyProperties(tax, taxBriefVO);
            taxBriefVOS.add(taxBriefVO);
        });
        return ReturnJson.success(taxBriefVOS);
    }

    @Override
    public ReturnJson getCatalogAll() {
        List<InvoiceCatalog> invoiceCatalogs = invoiceCatalogDao.selectList(new QueryWrapper<>());
        return ReturnJson.success(invoiceCatalogs);
    }

    @Override
    public ReturnJson saveCatalog(AddInvoiceCatalogDTO addInvoiceCatalogDto) {
        InvoiceCatalog invoiceCatalog = new InvoiceCatalog();
        BeanUtils.copyProperties(addInvoiceCatalogDto, invoiceCatalog);
        int i = invoiceCatalogDao.insert(invoiceCatalog);
        if (i == 1) {
            return ReturnJson.success("添加类目成功！");
        }
        return ReturnJson.error("添加类目失败！");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson saveTax(TaxDTO taxDto) throws Exception {
        if (taxDto.getId() != null) {
            invoiceLadderPriceService.remove(new QueryWrapper<InvoiceLadderPrice>().eq("tax_id", taxDto.getId()));
            taxPackageDao.delete(new QueryWrapper<TaxPackage>().eq("tax_id", taxDto.getId()));
            this.removeById(taxDto.getId());
        }
        Tax tax = new Tax();
        BeanUtils.copyProperties(taxDto, tax);
        log.error(tax.toString());
        taxDao.insert(tax);
        TaxPackageDTO totalTaxPackageDTO = taxDto.getTotalTaxPackage();
        //判断是否有总包，有总包就添加
        if (totalTaxPackageDTO != null) {

            TaxPackage totalTaxPackage = new TaxPackage();
            BeanUtils.copyProperties(totalTaxPackageDTO, totalTaxPackage);

            totalTaxPackage.setTaxId(tax.getId());
            taxPackageDao.insert(totalTaxPackage);
            List<InvoiceLadderPriceDTO> totalLaddersDto = taxDto.getTotalLadders();
            List<InvoiceLadderPrice> totalLadders = new ArrayList<>();
            for (int i = 0; i < totalLaddersDto.size(); i++) {
                InvoiceLadderPrice invoiceLadderPrice = new InvoiceLadderPrice();
                BeanUtils.copyProperties(totalLaddersDto.get(i), invoiceLadderPrice);
                totalLadders.add(invoiceLadderPrice);
            }
            //判断是否有梯度价
            if (!VerificationCheck.listIsNull(totalLadders)) {
                //判断梯度价是否合理
                for (int i = 0; i < totalLadders.size(); i++) {
                    if (i != totalLadders.size() - 1) {
                        InvoiceLadderPrice invoiceLadderPrice = totalLadders.get(i);
                        if (invoiceLadderPrice.getEndMoney().compareTo(invoiceLadderPrice.getStartMoney()) < 0) {
                            throw new CommonException(300, "结束金额应该大于起始金额");
                        }
                        InvoiceLadderPrice invoiceLadderPriceNext = totalLadders.get(i + 1);
                        invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney());
                        if (invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney()) < 0) {
                            throw new CommonException(300, "上梯度结束金额应小于下梯度起始金额");
                        }
                    }
                    totalLadders.get(i).setTaxId(tax.getId());
                    totalLadders.get(i).setTaxPackageId(totalTaxPackage.getId());
                }
                invoiceLadderPriceService.saveBatch(totalLadders);
            }

        }
        TaxPackageDTO manyTaxPackageDTO = taxDto.getManyTaxPackage();
        //判断是否有众包，有众包就添加
        if (manyTaxPackageDTO != null) {

            TaxPackage manyTaxPackage = new TaxPackage();
            BeanUtils.copyProperties(manyTaxPackageDTO, manyTaxPackage);

            manyTaxPackage.setTaxId(tax.getId());
            taxPackageDao.insert(manyTaxPackage);
            List<InvoiceLadderPriceDTO> manyLaddersDto = taxDto.getManyLadders();
            List<InvoiceLadderPrice> manyLadders = new ArrayList<>();
            for (int i = 0; i < manyLaddersDto.size(); i++) {
                InvoiceLadderPrice invoiceLadderPrice = new InvoiceLadderPrice();
                BeanUtils.copyProperties(manyLaddersDto.get(i), invoiceLadderPrice);
                manyLadders.add(invoiceLadderPrice);
            }
            //判断是否有梯度价
            if (!VerificationCheck.listIsNull(manyLadders)) {
                //判断梯度价是否合理
                for (int i = 0; i < manyLadders.size(); i++) {
                    if (i != manyLadders.size() - 1) {
                        InvoiceLadderPrice invoiceLadderPrice = manyLadders.get(i);
                        if (invoiceLadderPrice.getEndMoney().compareTo(invoiceLadderPrice.getStartMoney()) < 0) {
                            throw new CommonException(300, "结束金额应该大于起始金额");
                        }
                        InvoiceLadderPrice invoiceLadderPriceNext = manyLadders.get(i + 1);
                        invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney());
                        if (invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney()) < 0) {
                            throw new CommonException(300, "上梯度结束金额应小于下梯度起始金额");
                        }
                    }
                    manyLadders.get(i).setTaxId(tax.getId());
                    manyLadders.get(i).setTaxPackageId(manyTaxPackage.getId());
                }
                invoiceLadderPriceService.saveBatch(manyLadders);
            }
        }
        return ReturnJson.success("操作成功！");
    }

    @Override
    public ReturnJson getTaxList(TaxListDTO taxListDto, String merchantId) {
        if (merchantId != null) {
            Merchant merchant = merchantDao.selectById(merchantId);
            Page<TaxListPO> taxListPOPage = new Page<>(taxListDto.getPageNo(), taxListDto.getPageSize());
            IPage<TaxListPO> taxListPage = taxDao.selectTaxList(taxListPOPage, taxListDto.getTaxName(), taxListDto.getStartDate(), taxListDto.getEndDate(), merchant.getCompanyId());
            return ReturnJson.success(taxListPage);
        } else {
            Page<TaxListPO> taxListPOPage = new Page<>(taxListDto.getPageNo(), taxListDto.getPageSize());
            IPage<TaxListPO> taxListPage = taxDao.selectTaxList(taxListPOPage, taxListDto.getTaxName(), taxListDto.getStartDate(), taxListDto.getEndDate(), null);
            return ReturnJson.success(taxListPage);
        }
    }

    @Override
    public ReturnJson getTaxInfo(String taxId) {
        Tax tax = taxDao.selectById(taxId);
        TaxPlatformVO taxPlatformVO = new TaxPlatformVO();
        BeanUtils.copyProperties(tax, taxPlatformVO);
        TaxPackage totalTaxPackage = taxPackageDao.selectOne(new QueryWrapper<TaxPackage>().eq("tax_id", taxId).eq("package_status", 0));
        if (totalTaxPackage != null) {
            List<InvoiceLadderPrice> totalLadder = invoiceLadderPriceService.list(new QueryWrapper<InvoiceLadderPrice>().eq("tax_package_id", totalTaxPackage.getId()));
            taxPlatformVO.setTotalTaxPackage(totalTaxPackage);
            taxPlatformVO.setTotalLadders(totalLadder);
        }
        TaxPackage manyTaxPackage = taxPackageDao.selectOne(new QueryWrapper<TaxPackage>().eq("tax_id", taxId).eq("package_status", 1));
        if (manyTaxPackage != null) {
            List<InvoiceLadderPrice> manyLadder = invoiceLadderPriceService.list(new QueryWrapper<InvoiceLadderPrice>().eq("tax_package_id", manyTaxPackage.getId()));
            taxPlatformVO.setManyTaxPackage(manyTaxPackage);
            taxPlatformVO.setManyLadders(manyLadder);
        }
        return ReturnJson.success(taxPlatformVO);
    }

    @Override
    public ReturnJson transactionRecordCount(String taxId) {
        HomePageVO homePageVO = new HomePageVO();
        BigDecimal total30DayMoney = paymentOrderDao.selectBy30DayPaasTax(taxId);
        homePageVO.setPayment30TotalMoney(total30DayMoney);
        BigDecimal totalMoney = paymentOrderDao.selectTotalPaasTax(taxId);
        homePageVO.setPaymentTotalMoney(totalMoney);

        BigDecimal many30DayMoney = paymentOrderManyDao.selectBy30DayPaasTax(taxId);
        homePageVO.setPayment30ManyMoney(many30DayMoney);
        BigDecimal manyMoney = paymentOrderManyDao.selectTotalPaasTax(taxId);
        homePageVO.setPaymentManyMoney(manyMoney);

        InvoicePO totalInvoice = invoiceDao.selectInvoiceMoneyPaasTax(taxId);
        if (totalInvoice != null) {
            homePageVO.setInvoiceTotalCount(totalInvoice.getCount());
            homePageVO.setInvoiceTotalMoney(totalInvoice.getTotalMoney());
        }

        InvoicePO manyInvoice = crowdSourcingInvoiceDao.selectCrowdInvoiceMoneyPaasTax(taxId);
        homePageVO.setInvoiceManyCount(manyInvoice.getCount());
        homePageVO.setInvoiceManyMoney(manyInvoice.getTotalMoney());

        //商户数
        Integer count = companyTaxDao.selectCount(new QueryWrapper<CompanyTax>().eq("tax_id", taxId));
        homePageVO.setMerchantTotal(count);
        //创客数
        Integer countWorker = workerDao.queryWorkerCount(taxId, null);
        homePageVO.setWorkerTotal(countWorker);

        //获取10条具体的交易流水
        ReturnJson returnJson = this.transactionRecord(taxId, null, 1, 10);
        returnJson.setObj(homePageVO);
        return returnJson;
    }

    @Override
    public ReturnJson transactionRecord(String taxId, String merchantId, Integer page, Integer pageSize) {
        List<String> ids = new ArrayList<>();
        QueryWrapper<PaymentOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentOrder::getTaxId, taxId).eq(StringUtils.isNotBlank(merchantId), PaymentOrder::getMerchantId, merchantId);
        List<PaymentOrder> paymentOrders = paymentOrderDao.selectList(queryWrapper);
        for (PaymentOrder paymentOrder : paymentOrders) {
            ids.add(paymentOrder.getId());
        }

        QueryWrapper<PaymentOrderMany> queryWrapperMany = new QueryWrapper<>();
        queryWrapperMany.lambda().eq(PaymentOrderMany::getTaxId, taxId).eq(StringUtils.isNotBlank(merchantId), PaymentOrderMany::getMerchantId, merchantId);
        List<PaymentOrderMany> paymentOrderManies = paymentOrderManyDao.selectList(queryWrapperMany);
        for (PaymentOrderMany paymentOrderMany : paymentOrderManies) {
            ids.add(paymentOrderMany.getId());
        }
        if (VerificationCheck.listIsNull(ids)) {
            return ReturnJson.success(ids);
        }
        Page<MerchantPaymentListPO> taxPage = new Page<>(page, pageSize);
        IPage<MerchantPaymentListPO> taxPaymentListPage = taxDao.selectTaxPaymentList(taxPage, ids);
        return ReturnJson.success(taxPaymentListPage);
    }

    @Override
    public ReturnJson getSellerById(String id) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        SellerVO sellerVo = taxDao.getSellerById(id);
        if (sellerVo != null) {
            returnJson = new ReturnJson("查询成功", sellerVo, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getTaxPaasList() {
        List<Tax> taxList = taxDao.selectList(new QueryWrapper<Tax>().eq("tax_status", 0));
        List<TaxListVO> taxListVOS = new ArrayList<>();
        for (int i = 0; i < taxList.size(); i++) {
            TaxListVO taxListVo = new TaxListVO();
            BeanUtils.copyProperties(taxList.get(i), taxListVo);
            taxListVOS.add(taxListVo);
        }
        return ReturnJson.success(taxListVOS);
    }

    @Override
    public ReturnJson getTaxList(Integer packageStatus) {
        List<TaxListVO> taxListVOS = taxDao.getTaxPaasList(packageStatus);
        return ReturnJson.success(taxListVOS);
    }

    @Override
    public ReturnJson queryTaxTransactionFlow(String taxId, Integer page, Integer pageSize) {
        Page taxPage = new Page(page, pageSize);
        IPage<TaxTransactionFlowVO> taxTransactionFlowVOS = taxDao.queryTaxTransactionFlow(taxId, taxPage);
        return ReturnJson.success(taxTransactionFlowVOS);
    }

    @Override
    public ReturnJson queryTaxCompanyList(String taxId, String userId, Integer pageNo, Integer pageSize) {
        Merchant merchant = merchantDao.selectById(userId);
        if (merchant == null) {
            return ReturnJson.error("不存在此商户，请重新登录！");
        }
        Page page = new Page(pageNo, pageSize);
        IPage iPage = taxDao.getTaxCompanyFlow(page, merchant.getCompanyId(), taxId);
        return ReturnJson.success(iPage);
    }

    @Override
    public ReturnJson queryCompanyFlowInfo(String userId, String taxId) {
        Merchant merchant = merchantDao.selectById(userId);
        if (merchant == null) {
            return ReturnJson.error("商户不存在，请重新登录");
        }
        String companyId=merchant.getCompanyId();
        //获取众包三十天支付流水
        BigDecimal payment30ManyMoney = paymentOrderManyDao.getFlowInfo(companyId, taxId, 30);
        //获取总包三十天支付流水
        BigDecimal payment30TotalMoney = paymentOrderDao.getFlowInfo(companyId, taxId, 30);
        //获取众包总支付流水
        BigDecimal paymentManyMoney = paymentOrderManyDao.getFlowInfo(companyId, taxId, null);
        //获取总包总支付流水
        BigDecimal paymentTotalMoney = paymentOrderDao.getFlowInfo(companyId, taxId, null);
        //获取总包发票数
        Integer invoiceTotalCount = paymentOrderDao.getInvoiceTotalCount(companyId, taxId);
        //获取总包发票金额
        BigDecimal invoiceTotalMoney = paymentOrderDao.getInvoiceTotalMoney(companyId, taxId);
        //获取众包发票金额
        BigDecimal invoiceManyMoney = paymentOrderManyDao.getPaymentManyMoney(companyId, taxId);
        //获取众包发票数
        Integer invoiceManyCount = paymentOrderManyDao.getPaymentManyCount(companyId, taxId);

        CompanyFlowInfoVO companyFlowInfoVO = new CompanyFlowInfoVO();
        companyFlowInfoVO.setPayment30ManyMoney(payment30ManyMoney);
        companyFlowInfoVO.setPayment30TotalMoney(payment30TotalMoney);
        companyFlowInfoVO.setPaymentManyMoney(paymentManyMoney);
        companyFlowInfoVO.setPaymentTotalMoney(paymentTotalMoney);
        companyFlowInfoVO.setInvoiceManyCount(invoiceManyCount);
        companyFlowInfoVO.setInvoiceTotalCount(invoiceTotalCount);
        companyFlowInfoVO.setInvoiceManyMoney(invoiceManyMoney);
        companyFlowInfoVO.setInvoiceTotalMoney(invoiceTotalMoney);
        return ReturnJson.success(companyFlowInfoVO);
    }

    @Override
    public ReturnJson queryTaxInBankInfo(String taxId) {
        TaxInBankInfoVO taxInBankInfoVO = taxDao.queryTaxInBankInfo(taxId);
        return ReturnJson.success(taxInBankInfoVO);
    }

}
