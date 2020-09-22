package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ReturnJson;
import com.example.common.util.VerificationCheck;
import com.example.merchant.dto.TaxDto;
import com.example.merchant.dto.TaxListDto;
import com.example.merchant.service.InvoiceLadderPriceService;
import com.example.merchant.service.TaxService;
import com.example.merchant.vo.HomePageVO;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.po.InvoicePO;
import com.example.mybatis.po.MerchantPaymentListPO;
import com.example.mybatis.po.TaxListPO;
import com.example.mybatis.vo.SellerVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private TaxDao taxDao;

    @Autowired
    private MerchantTaxDao merchantTaxDao;

    @Autowired
    private InvoiceCatalogDao invoiceCatalogDao;

    @Autowired
    private TaxPackageDao taxPackageDao;

    @Autowired
    private InvoiceLadderPriceService invoiceLadderPriceService;

    @Autowired
    private PaymentOrderDao paymentOrderDao;

    @Autowired
    private PaymentOrderManyDao paymentOrderManyDao;

    @Autowired
    private InvoiceDao invoiceDao;

    @Autowired
    private CrowdSourcingInvoiceDao crowdSourcingInvoiceDao;


    /**
     * 查询商户可用使用的平台服务商
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getTaxAll(String merchantId, Integer packageStatus) {
        List<MerchantTax> merchantTaxes = merchantTaxDao.selectList(new QueryWrapper<MerchantTax>()
                .eq("merchant_id", merchantId).eq("package_status",packageStatus));
        List<String> ids = new LinkedList<>();
        for (MerchantTax merchantTax : merchantTaxes ) {
            ids.add(merchantTax.getTaxId());
        }
        List<Tax> taxes = taxDao.selectList(new QueryWrapper<Tax>().in("id", ids).eq("tax_status", 1));
        return ReturnJson.success(taxes);
    }

    /**
     * 查询所以开票类目
     * @return
     */
    @Override
    public ReturnJson getCatalogAll() {
        List<InvoiceCatalog> invoiceCatalogs = invoiceCatalogDao.selectList(new QueryWrapper<>());
        return ReturnJson.success(invoiceCatalogs);
    }

    /**
     * 添加开票类目
     * @param invoiceCatalog
     * @return
     */
    @Override
    public ReturnJson saveCatalog(InvoiceCatalog invoiceCatalog) {
        int i = invoiceCatalogDao.insert(invoiceCatalog);
        if (i == 1) {
            return ReturnJson.success("添加类目成功！");
        }
        return ReturnJson.error("添加类目失败！");
    }

    /**
     * 添加服务商
     * @param taxDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson saveTax(TaxDto taxDto) {
        if (taxDto.getId() != null){
            invoiceLadderPriceService.remove(new QueryWrapper<InvoiceLadderPrice>().eq("tax_id",taxDto.getId()));
            taxPackageDao.delete(new QueryWrapper<TaxPackage>().eq("tax_id",taxDto.getId()));
            this.removeById(taxDto.getId());
        }
        Tax tax = new Tax();
        BeanUtils.copyProperties(taxDto,tax);
        log.error(tax.toString());
        taxDao.insert(tax);
        TaxPackage totalTaxPackage = taxDto.getTotalTaxPackage();
        //判断是否有总包，有总包就添加
        if (totalTaxPackage != null){
            totalTaxPackage.setTaxId(tax.getId());
            taxPackageDao.insert(totalTaxPackage);
            List<InvoiceLadderPrice> totalLadders = taxDto.getTotalLadders();
            //判断是否有梯度价
            if (!VerificationCheck.listIsNull(totalLadders)){
                //判断梯度价是否合理
                for (int i = 0; i < totalLadders.size(); i++) {
                    if (i != totalLadders.size()-1){
                        InvoiceLadderPrice invoiceLadderPrice = totalLadders.get(i);
                        if (invoiceLadderPrice.getEndMoney().compareTo(invoiceLadderPrice.getStartMoney()) < 0){
                            return ReturnJson.success("结束金额应该大于起始金额！");
                        }
                        InvoiceLadderPrice invoiceLadderPriceNext = totalLadders.get(i+1);
                        invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney());
                        if (invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney()) < 0 ){
                            return ReturnJson.error("上梯度结束金额应小于下梯度起始金额");
                        }
                    }
                    totalLadders.get(i).setTaxId(tax.getId());
                    totalLadders.get(i).setTaxPackageId(totalTaxPackage.getId());
                }
                invoiceLadderPriceService.saveBatch(totalLadders);
            }

        }

        TaxPackage manyTaxPackage = taxDto.getManyTaxPackage();
        //判断是否有众包，有众包就添加
        if (manyTaxPackage != null) {
            manyTaxPackage.setTaxId(tax.getId());
            taxPackageDao.insert(manyTaxPackage);
            List<InvoiceLadderPrice> manyLadders = taxDto.getManyLadders();
            //判断是否有梯度价
            if (!VerificationCheck.listIsNull(manyLadders)){
                //判断梯度价是否合理
                for (int i = 0; i < manyLadders.size(); i++) {
                    if (i != manyLadders.size()-1){
                        InvoiceLadderPrice invoiceLadderPrice = manyLadders.get(i);
                        if (invoiceLadderPrice.getEndMoney().compareTo(invoiceLadderPrice.getStartMoney()) < 0){
                            return ReturnJson.success("结束金额应该大于起始金额！");
                        }
                        InvoiceLadderPrice invoiceLadderPriceNext = manyLadders.get(i+1);
                        invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney());
                        if (invoiceLadderPriceNext.getStartMoney().compareTo(invoiceLadderPrice.getEndMoney()) < 0 ){
                            return ReturnJson.error("上梯度结束金额应小于下梯度起始金额");
                        }
                    }
                    manyLadders.get(i).setTaxId(tax.getId());
                    manyLadders.get(i).setTaxPackageId(manyTaxPackage.getId());
                }
                invoiceLadderPriceService.saveBatch(manyLadders);
            }
        }
        return ReturnJson.success("添加成功！");
    }

    /**
     * 查询服务商列表
     * @param taxListDto
     * @return
     */
    @Override
    public ReturnJson getTaxList(TaxListDto taxListDto) {
        Page<TaxListPO> taxListPOPage = new Page<>(taxListDto.getPage(),taxListDto.getPageSize());
        IPage<TaxListPO> taxListPage = taxDao.selectTaxList(taxListPOPage, taxListDto.getTaxName(), taxListDto.getStartDate(), taxListDto.getEndDate());
        return ReturnJson.success(taxListPage);
    }

    /**
     * 查询服务商详情
     * @param taxId
     * @return
     */
    @Override
    public ReturnJson getTaxInfo(String taxId) {
        Tax tax = taxDao.selectById(taxId);
        TaxDto taxDto = new TaxDto();
        BeanUtils.copyProperties(tax,taxDto);
        TaxPackage totalTaxPackage = taxPackageDao.selectOne(new QueryWrapper<TaxPackage>().eq("tax_id", taxId).eq("package_status", 0));
        if (totalTaxPackage != null) {
            List<InvoiceLadderPrice> totalLadder = invoiceLadderPriceService.list(new QueryWrapper<InvoiceLadderPrice>().eq("tax_package_id", totalTaxPackage.getId()));
            taxDto.setTotalTaxPackage(totalTaxPackage);
            taxDto.setTotalLadders(totalLadder);
        }
        TaxPackage manyTaxPackage = taxPackageDao.selectOne(new QueryWrapper<TaxPackage>().eq("tax_id", taxId).eq("package_status", 1));
        if (manyTaxPackage != null) {
            List<InvoiceLadderPrice> manyLadder = invoiceLadderPriceService.list(new QueryWrapper<InvoiceLadderPrice>().eq("tax_package_id", manyTaxPackage.getId()));
            taxDto.setManyTaxPackage(manyTaxPackage);
            taxDto.setManyLadders(manyLadder);
        }
        return ReturnJson.success(taxDto);
    }

    /**
     * 查询交易流水统计
     * @param taxId
     * @return
     */
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
        homePageVO.setInvoiceTotalCount(totalInvoice.getCount());
        homePageVO.setInvoiceTotalMoney(totalInvoice.getTotalMoney());

        InvoicePO manyInvoice = crowdSourcingInvoiceDao.selectCrowdInvoiceMoneyPaasTax(taxId);
        homePageVO.setInvoiceManyCount(manyInvoice.getCount());
        homePageVO.setInvoiceManyMoney(manyInvoice.getTotalMoney());
        //获取10条具体的交易流水
        ReturnJson returnJson = this.transactionRecord(taxId, 1, 10);
        returnJson.setObj(homePageVO);
        return returnJson;
    }

    /**
     * 查询具体的交易流水
     * @param taxId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public ReturnJson transactionRecord(String taxId, Integer page, Integer pageSize) {
        List<String> ids = new ArrayList<>();
        List<PaymentOrder> paymentOrders = paymentOrderDao.selectList(new QueryWrapper<PaymentOrder>().eq("tax_id", taxId));
        for (PaymentOrder paymentOrder : paymentOrders) {
            ids.add(paymentOrder.getId());
        }

        List<PaymentOrderMany> paymentOrderManies = paymentOrderManyDao.selectList(new QueryWrapper<PaymentOrderMany>().eq("tax_id", taxId));
        for (PaymentOrderMany paymentOrderMany : paymentOrderManies) {
            ids.add(paymentOrderMany.getId());
        }
        if (VerificationCheck.listIsNull(ids)){
            return ReturnJson.success(ids);
        }
        Page<MerchantPaymentListPO> taxPage = new Page<>(page,pageSize);
        IPage<MerchantPaymentListPO> taxPaymentListPage = taxDao.selectTaxPaymentList(taxPage,ids);
        return ReturnJson.success(taxPaymentListPage);
    }
    /**
     * 销售方信息
     * @param id
     * @return
     */
    @Override
    public ReturnJson getSellerById(String id) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        SellerVo sellerVo = taxDao.getSellerById(id);
        if (sellerVo != null) {
            returnJson = new ReturnJson("查询成功", sellerVo, 200);
        }
        return returnJson;
    }

}
