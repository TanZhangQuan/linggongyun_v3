package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ExpressLogisticsInfo;
import com.example.common.util.KdniaoTrackQueryAPI;
import com.example.common.util.ReturnJson;
import com.example.common.util.VerificationCheck;
import com.example.merchant.dto.AssociatedTasksDTO;
import com.example.merchant.dto.merchant.AddPaymentOrderDTO;
import com.example.merchant.dto.merchant.PaymentDTO;
import com.example.merchant.dto.merchant.PaymentOrderMerchantDTO;
import com.example.merchant.dto.platform.PaymentOrderDTO;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.PaymentInventoryService;
import com.example.merchant.service.PaymentOrderService;
import com.example.merchant.util.AcquireID;
import com.example.merchant.vo.ExpressInfoVO;
import com.example.merchant.vo.PaymentOrderInfoVO;
import com.example.merchant.vo.platform.CompanyBriefVO;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.po.InvoiceInfoPO;
import com.example.mybatis.po.PaymentOrderInfoPO;
import com.example.mybatis.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 支付单信息
 * 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class PaymentOrderServiceImpl extends ServiceImpl<PaymentOrderDao, PaymentOrder> implements PaymentOrderService {

    @Value("${TOKEN}")
    private String TOKEN;

    @Resource
    private PaymentOrderDao paymentOrderDao;

    @Resource
    private PaymentInventoryDao paymentInventoryDao;

    @Resource
    private PaymentInventoryService paymentInventoryService;

    @Resource
    private ManagersDao managersDao;

    @Resource
    private CompanyInfoDao companyInfoDao;

    @Resource
    private CompanyTaxDao companyTaxDao;

    @Resource
    private CompanyLadderServiceDao companyLadderServiceDao;

    @Resource
    private InvoiceDao invoiceDao;

    @Resource
    private AcquireID acquireID;

    @Resource
    private TaxDao taxDao;

    @Resource
    private MerchantDao merchantDao;

    @Resource
    private TaskDao taskDao;

    @Override
    public ReturnJson getPaymentOrder(String merchantId, PaymentOrderMerchantDTO paymentOrderMerchantDto) {
        Merchant merchant = merchantDao.selectById(merchantId);
        String paymentOrderId = paymentOrderMerchantDto.getPaymentOrderId();
        String taxId = paymentOrderMerchantDto.getTaxId();
        String beginDate = paymentOrderMerchantDto.getBeginDate();
        String endDate = paymentOrderMerchantDto.getEndDate();
        Page<PaymentOrder> paymentOrderPage = new Page<>(paymentOrderMerchantDto.getPageNo(), paymentOrderMerchantDto.getPageSize());
        IPage<PaymentOrder> paymentOrderIPage = paymentOrderDao.selectMany(paymentOrderPage, merchant.getCompanyId(), paymentOrderId, taxId, beginDate, endDate);
        return ReturnJson.success(paymentOrderIPage);
    }

    @Override
    public ReturnJson getPaymentOrderInfo(String id) {
        PaymentOrderInfoVO paymentOrderInfoVO = new PaymentOrderInfoVO();
        ExpressInfoVO expressInfoVO = new ExpressInfoVO();
        // 为总包订单
        PaymentOrderInfoPO paymentOrderInfoPO = paymentOrderDao.selectPaymentOrderInfo(id);
        if (paymentOrderInfoPO == null) {
            return ReturnJson.error("订单编号有误，请重新输入！");
        }
        InvoiceInfoPO invoiceInfoPO = invoiceDao.selectInvoiceInfoPO(id);
        if (invoiceInfoPO != null) {
            //总包发票信息
            paymentOrderInfoVO.setInvoice(invoiceInfoPO.getInvoiceUrl());
            paymentOrderInfoVO.setSubpackageInvoice(invoiceInfoPO.getMakerInvoiceUrl());
            expressInfoVO.setExpressCompanyName(invoiceInfoPO.getExpressCompanyName());
            expressInfoVO.setExpressCode(invoiceInfoPO.getExpressSheetNo());
            List<ExpressLogisticsInfo> expressLogisticsInfos = KdniaoTrackQueryAPI.getExpressInfo(invoiceInfoPO.getExpressCompanyName(), invoiceInfoPO.getExpressSheetNo());
            expressInfoVO.setExpressLogisticsInfos(expressLogisticsInfos);
        }
        List<PaymentInventoryVO> paymentInventories = paymentInventoryDao.selectPaymentInventoryList(id, null);
        paymentOrderInfoVO.setPaymentInventories(paymentInventories);
        paymentOrderInfoVO.setPaymentOrderInfoPO(paymentOrderInfoPO);
        paymentOrderInfoVO.setExpressInfoVO(expressInfoVO);
        return ReturnJson.success(paymentOrderInfoVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson saveOrUpdataPaymentOrder(AddPaymentOrderDTO addPaymentOrderDto, String merchantId) throws CommonException {
        if (addPaymentOrderDto.getPaymentInventories() == null) {
            throw new CommonException(300,"支付清单不能为空！");
        }
        PaymentDTO paymentDto = addPaymentOrderDto.getPaymentDto();
        PaymentOrder paymentOrder = new PaymentOrder();
        BeanUtils.copyProperties(paymentDto, paymentOrder);
        Tax tax = taxDao.selectById(paymentDto.getTaxId());
        paymentOrder.setPlatformServiceProvider(tax.getTaxName());
        List<PaymentInventory> paymentInventories = addPaymentOrderDto.getPaymentInventories();
        String id = paymentOrder.getId();
        paymentOrder.setMerchantId(merchantId);
        CompanyInfo companyInfo = companyInfoDao.selectById(merchantId);
        if (companyInfo == null) {
            Merchant merchant = merchantDao.selectById(merchantId);
            paymentOrder.setCompanyId(merchant.getCompanyId());
            paymentOrder.setCompanySName(merchant.getCompanyName());
        }
        if (companyInfo != null) {
            paymentOrder.setCompanyId(companyInfo.getId());
            paymentOrder.setCompanySName(companyInfo.getCompanyName());
        }
        if (id != null && paymentOrder.getPaymentOrderStatus() == 0) {
            List<PaymentInventory> paymentInventoryList = paymentInventoryDao.selectList(new QueryWrapper<PaymentInventory>().eq("payment_order_id", id));
            List<String> ids = new ArrayList<>();
            for (PaymentInventory paymentInventory : paymentInventoryList) {
                ids.add(paymentInventory.getId());
            }
            paymentInventoryDao.delete(new QueryWrapper<PaymentInventory>().eq("payment_order_id", id));
            this.removeById(id);
        }
        BigDecimal receviceTax = paymentOrder.getReceviceTax().divide(BigDecimal.valueOf(100));
        BigDecimal merchantTax = paymentOrder.getMerchantTax().divide(BigDecimal.valueOf(100));
        BigDecimal compositeTax = new BigDecimal("0");
        BigDecimal countMoney = new BigDecimal("0");
        BigDecimal countWorkerMoney = new BigDecimal("0");


        CompanyTax companyTax = companyTaxDao.selectOne(new QueryWrapper<CompanyTax>()
                .eq("tax_id", paymentOrder.getTaxId())
                .eq("company_id", paymentOrder.getCompanyId())
                .eq("package_status", 0));
        Integer taxStatus = paymentOrder.getTaxStatus();
        //判断服务费是一口价还是梯度价
        if (companyTax.getChargeStatus() == 0) {
            compositeTax = companyTax.getServiceCharge().divide(BigDecimal.valueOf(100));
            paymentOrder.setCompositeTax(compositeTax.multiply(BigDecimal.valueOf(100)));
            for (PaymentInventory paymentInventory : paymentInventories) {
                BigDecimal realMoney = paymentInventory.getRealMoney();
                paymentInventory.setTaskMoney(realMoney);
                paymentInventory.setCompositeTax(compositeTax.multiply(BigDecimal.valueOf(100)));
                if (taxStatus == 0) {
                    paymentInventory.setMerchantPaymentMoney(realMoney.multiply(compositeTax.add(new BigDecimal(1))));
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                } else if (taxStatus == 1) {
                    paymentInventory.setMerchantPaymentMoney(realMoney);
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                    paymentInventory.setRealMoney(realMoney.subtract(realMoney.multiply(compositeTax)));
                } else {
                    paymentInventory.setMerchantPaymentMoney(realMoney.multiply(compositeTax.multiply(merchantTax).add(new BigDecimal("1"))));
                    paymentInventory.setRealMoney(realMoney.subtract(realMoney.multiply(compositeTax.multiply(receviceTax))));
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                }
                countMoney = countMoney.add(paymentInventory.getMerchantPaymentMoney());
                countWorkerMoney = countWorkerMoney.add(paymentInventory.getRealMoney());
            }
        } else {
            List<CompanyLadderService> companyLadderServices = companyLadderServiceDao.selectList(new QueryWrapper<CompanyLadderService>().eq("company_tax_id", companyTax.getId()).orderByAsc("start_money"));
            BigDecimal compositeTaxCount = new BigDecimal(0);
            for (PaymentInventory paymentInventory : paymentInventories) {
                BigDecimal realMoney = paymentInventory.getRealMoney();
                compositeTax = this.getCompositeTax(companyLadderServices, realMoney);
                paymentInventory.setCompositeTax(compositeTax.multiply(BigDecimal.valueOf(100)));
                compositeTaxCount = compositeTaxCount.add(compositeTax);
                paymentInventory.setTaskMoney(realMoney);
                if (taxStatus == 0) {
                    paymentInventory.setMerchantPaymentMoney(realMoney.multiply(compositeTax.add(new BigDecimal(1))));
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                } else if (taxStatus == 1) {
                    paymentInventory.setMerchantPaymentMoney(realMoney);
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                    paymentInventory.setRealMoney(realMoney.subtract(realMoney.multiply(compositeTax)));
                } else {
                    paymentInventory.setMerchantPaymentMoney(realMoney.multiply(compositeTax.multiply(merchantTax).add(new BigDecimal("1"))));
                    paymentInventory.setRealMoney(realMoney.subtract(realMoney.multiply(compositeTax.multiply(receviceTax))));
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                }
                countMoney = countMoney.add(paymentInventory.getMerchantPaymentMoney());
                countWorkerMoney = countWorkerMoney.add(paymentInventory.getRealMoney());
            }
            //算平均税率
            paymentOrder.setCompositeTax(compositeTaxCount.multiply(new BigDecimal(100).divide(new BigDecimal(paymentInventories.size()))));
        }
        paymentOrder.setRealMoney(countMoney);
        paymentOrder.setWorkerMoney(countWorkerMoney);
        //生成总包支付订单
        boolean b = this.saveOrUpdate(paymentOrder);
        if (!b) {
            throw new CommonException(300,"订单创建失败！");
        }
        for (PaymentInventory paymentInventory : paymentInventories) {
            paymentInventory.setPaymentOrderId(paymentOrder.getId());
            //生成支付明细
            paymentInventory.setPackageStatus(0);
            paymentInventoryService.saveOrUpdate(paymentInventory);
        }
        return ReturnJson.success("支付订单创建成功！");
    }

    @Override
    public ReturnJson offlinePayment(String paymentOrderId, String turnkeyProjectPayment) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setId(paymentOrderId);
        paymentOrder.setTurnkeyProjectPayment(turnkeyProjectPayment);
        paymentOrder.setPaymentDate(LocalDateTime.now());
        paymentOrder.setPaymentOrderStatus(4);
        int i = paymentOrderDao.updateById(paymentOrder);
        if (i == 1) {
            return ReturnJson.success("支付成功！");
        }
        return ReturnJson.error("支付失败，请重试！");
    }

    @Override
    public ReturnJson getPaymentOrderById(String id) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        List<PaymentOrderVO> paymentOrderVOList = new ArrayList<>();
        String[] ids = id.split(",");
        for (int i = 0; i < ids.length; i++) {
            PaymentOrderVO paymentOrder = paymentOrderDao.getPaymentOrderById(ids[i]);
            if (paymentOrder != null) {
                paymentOrderVOList.add(paymentOrder);
                returnJson = new ReturnJson("查询成功", paymentOrder, 200);
            }
        }
        return returnJson;
    }

    @Override
    public ReturnJson getBillingInfo(String id) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        BillingInfoVO billingInfo = paymentOrderDao.getBillingInfo(id);
        if (billingInfo != null) {
            returnJson = new ReturnJson("查询成功", billingInfo, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getDayPaas(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        List<PaymentOrder> list;
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success((List) null);
        }
        list = paymentOrderDao.selectDaypaas(merchantIds);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getWeekPaas(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        List<PaymentOrder> list;
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success((List) null);
        }
        list = paymentOrderDao.selectWeekpaas(merchantIds);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getMonthPaas(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        List<PaymentOrder> list;
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success((List) null);
        }
        list = paymentOrderDao.selectMonthpaas(merchantIds);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getYearPaas(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        List<PaymentOrder> list;
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success((List) null);
        }
        list = paymentOrderDao.selectYearpaas(merchantIds);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getPaymentOrderPaas(PaymentOrderDTO paymentOrderDto, String managersId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(managersId);
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success((List) null);
        }
        String merchantName = paymentOrderDto.getMerchantName();
        String paymentOrderId = paymentOrderDto.getPaymentOrderId();
        String taxId = paymentOrderDto.getTaxId();
        Integer pageSize = paymentOrderDto.getPageSize();
        Integer page = paymentOrderDto.getPageNo();
        String beginDate = paymentOrderDto.getBeginDate();
        String endDate = paymentOrderDto.getEndDate();
        Page<PaymentOrder> paymentOrderPage = new Page<>(page, pageSize);
        IPage<PaymentOrder> paymentOrderIPage = paymentOrderDao.selectManyPaas(paymentOrderPage, merchantIds, merchantName, paymentOrderId, taxId, beginDate, endDate);
        return ReturnJson.success(paymentOrderIPage);
    }

    @Override
    public ReturnJson offlinePaymentPaas(String paymentOrderId, String turnkeyProjectPayment) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setId(paymentOrderId);
        paymentOrder.setTurnkeyProjectPayment(turnkeyProjectPayment);
        paymentOrder.setPaymentDate(LocalDateTime.now());
        paymentOrder.setPaymentOrderStatus(2);
        int i = paymentOrderDao.updateById(paymentOrder);
        if (i == 1) {
            return ReturnJson.success("支付成功！");
        }
        return ReturnJson.error("支付失败，请重试！");
    }

    @Override
    public ReturnJson confirmReceiptPaas(String paymentOrderId) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setId(paymentOrderId);
        paymentOrder.setPaymentOrderStatus(2);
        boolean flag = this.updateById(paymentOrder);
        if (flag) {
            return ReturnJson.success("确认收款成功！");
        }
        return ReturnJson.error("确认收款失败！");
    }

    @Override
    public ReturnJson findMerchantPaas(String managersId) {
        Managers managers = managersDao.selectById(managersId);
        Integer userSign = managers.getUserSign();
        List<CompanyInfo> companyInfos;
        //管理人员为代理商
        if (userSign == 1) {
            companyInfos = companyInfoDao.selectList(new QueryWrapper<CompanyInfo>().eq("agent_id", managers.getId()));
        } else if (userSign == 2) {
            //管理人员为业务员
            companyInfos = companyInfoDao.selectList(new QueryWrapper<CompanyInfo>().eq("sales_man_id", managers.getId()));
        } else {
            companyInfos = companyInfoDao.selectList(new QueryWrapper<>());
        }
        List<CompanyBriefVO> companyBriefVOS = new ArrayList<>();
        companyInfos.forEach(companyInfo -> {
            CompanyBriefVO companyBriefVO = new CompanyBriefVO();
            BeanUtils.copyProperties(companyInfo, companyBriefVO);
            companyBriefVOS.add(companyBriefVO);
        });
        return ReturnJson.success(companyBriefVOS);
    }

    @Override
    public ReturnJson subpackagePayPaas(String paymentOrderId, String subpackagePayment) {
        PaymentOrder paymentOrder = this.getById(paymentOrderId);
        if (paymentOrder == null) {
            log.error("订单号不存在!");
            return ReturnJson.error("你输入的订单号不存在！");
        }
        paymentOrder.setPaymentOrderStatus(6);
        paymentOrder.setSubpackagePayment(subpackagePayment);
        paymentOrderDao.updateById(paymentOrder);
        return ReturnJson.success(paymentOrder);
    }

    @Override
    public ReturnJson getDaypaa(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        List<PaymentOrder> list;
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success((List) null);
        }
        TodayVO todayVO = paymentOrderDao.selectDaypaa(merchantIds);
        return ReturnJson.success(todayVO);
    }

    @Override
    public ReturnJson getWeekPaa(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        List<PaymentOrder> list;
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success((List) null);
        }
        WeekTradeVO weekTradeVO = paymentOrderDao.selectWeekpaa(merchantIds);
        return ReturnJson.success(weekTradeVO);
    }

    @Override
    public ReturnJson getMonthPaa(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        List<PaymentOrder> list;
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success((List) null);
        }
        MonthTradeVO monthTradeVO = paymentOrderDao.selectMonthpaa(merchantIds);
        return ReturnJson.success(monthTradeVO);
    }

    @Override
    public ReturnJson getYearPaa(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        List<PaymentOrder> list;
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success((List) null);
        }
        YearTradeVO yearTradeVO = paymentOrderDao.selectYearpaa(merchantIds);
        return ReturnJson.success(yearTradeVO);
    }

    @Override
    public ReturnJson associatedTasks(String merchantId, AssociatedTasksDTO associatedTasksDto) {
        Page page = new Page(associatedTasksDto.getPageNo(), associatedTasksDto.getPageSize());
        IPage<AssociatedTasksVO> voiPage = taskDao.getAssociatedTask(page, merchantId, associatedTasksDto.getCooperateMode());
        return ReturnJson.success(voiPage);
    }

    @Override
    public ReturnJson reject(String paymentOrderId, String reasonsForRejection) {
        PaymentOrder paymentOrder = paymentOrderDao.selectById(paymentOrderId);
        if (paymentOrder == null) {
            return ReturnJson.error("支付信息错误，请重新选择！");
        }
        paymentOrder.setReasonsForRejection(reasonsForRejection);
        paymentOrderDao.updateById(paymentOrder);
        return ReturnJson.success("驳回成功");
    }

    @Override
    public ReturnJson gradientPrice(String merchantId, String taxId, Integer packageStatus) {
        CompanyInfo companyInfo = companyInfoDao.selectById(merchantId);
        if (companyInfo == null) {
            Merchant merchant = merchantDao.selectById(merchantId);
            companyInfo = companyInfoDao.selectById(merchant.getCompanyId());
        }
        if (companyInfo == null) {
            return ReturnJson.error("商户信息错误请重新选择");
        }
        CompanyTax companyTax = companyTaxDao.selectOne(new QueryWrapper<CompanyTax>()
                .eq("company_id", companyInfo.getId())
                .eq("tax_id", taxId)
                .eq("package_status",packageStatus));
        if (companyTax == null) {
            return ReturnJson.error("此商户还未和此服务商取得合作！");
        }
        if (companyTax.getChargeStatus() == 0) {
            Double serviceCharge = companyTaxDao.getCompanyTax(companyInfo.getId(), taxId, packageStatus);
            return ReturnJson.success(serviceCharge);
        } else {
            List<CompanyTaxMoneyVO> list = companyTaxDao.getCompanyTaxMoney(companyInfo.getId(), taxId, packageStatus);
            return ReturnJson.success(list);
        }
    }

    @Override
    public ReturnJson associatedTask(String merchantId, AssociatedTasksDTO associatedTasksDto) {
        Page page = new Page(associatedTasksDto.getPageNo(), associatedTasksDto.getPageSize());
        IPage<AssociatedTasksVO> voiPage = taskDao.getAssociatedTasks(page, merchantId, associatedTasksDto.getCooperateMode());
        return ReturnJson.success(voiPage);
    }

    /**
     * 获取综合费率
     *
     * @param companyLadderServices
     * @param realMoney
     * @return
     */
    private BigDecimal getCompositeTax(List<CompanyLadderService> companyLadderServices, BigDecimal realMoney) {
        BigDecimal compositeTax = null;
        for (CompanyLadderService companyLadderService : companyLadderServices) {
            BigDecimal startMoney = companyLadderService.getStartMoney();
            if (realMoney.compareTo(startMoney) >= 0) {
                compositeTax = companyLadderService.getServiceCharge().divide(new BigDecimal(100));
            }
        }
        return compositeTax;
    }

}
