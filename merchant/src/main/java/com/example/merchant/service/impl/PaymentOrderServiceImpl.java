package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ExpressLogisticsInfo;
import com.example.common.util.KdniaoTrackQueryAPI;
import com.example.common.util.ReturnJson;
import com.example.common.util.VerificationCheck;
import com.example.merchant.dto.merchant.AddPaymentOrderDto;
import com.example.merchant.dto.merchant.PaymentOrderMerchantDto;
import com.example.merchant.dto.platform.PaymentOrderDto;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.PaymentInventoryService;
import com.example.merchant.service.PaymentOrderService;
import com.example.merchant.util.AcquireID;
import com.example.merchant.util.JwtUtils;
import com.example.merchant.vo.ExpressInfoVO;
import com.example.merchant.vo.PaymentOrderInfoVO;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.po.InvoiceInfoPO;
import com.example.mybatis.po.PaymentOrderInfoPO;
import com.example.mybatis.vo.BillingInfoVo;
import com.example.mybatis.vo.PaymentOrderVo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private TaxDao taxDao;

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
    private JwtUtils jwtUtils;

    /**
     * 获取今天的支付总额
     *
     * @param request
     * @return
     */
    @Override
    public ReturnJson getDay(HttpServletRequest request) {
        String token = request.getHeader(TOKEN);
        Claims claim = jwtUtils.getClaimByToken(token);
        String merchantId = null;
        if (claim != null) {
            merchantId = claim.getSubject();
        }
        List<PaymentOrder> list = paymentOrderDao.selectDay(merchantId);
        return ReturnJson.success(list);
    }

    /**
     * 获取本周的支付总额
     *
     * @param request
     * @return
     */
    @Override
    public ReturnJson getWeek(HttpServletRequest request) {
        String token = request.getHeader(TOKEN);
        Claims claim = jwtUtils.getClaimByToken(token);
        String merchantId = null;
        if (claim != null) {
            merchantId = claim.getSubject();
        }
        List<PaymentOrder> list = paymentOrderDao.selectWeek(merchantId);
        return ReturnJson.success(list);
    }

    /**
     * 获取本月的支付总额
     *
     * @param request
     * @return
     */
    @Override
    public ReturnJson getMonth(HttpServletRequest request) {
        String token = request.getHeader(TOKEN);
        Claims claim = jwtUtils.getClaimByToken(token);
        String merchantId = null;
        if (claim != null) {
            merchantId = claim.getSubject();
        }
        List<PaymentOrder> list = paymentOrderDao.selectMonth(merchantId);
        return ReturnJson.success(list);
    }

    /**
     * 获取今年的支付总额
     *
     * @param request
     * @return
     */
    @Override
    public ReturnJson getYear(HttpServletRequest request) {
        String token = request.getHeader(TOKEN);
        Claims claim = jwtUtils.getClaimByToken(token);
        String merchantId = null;
        if (claim != null) {
            merchantId = claim.getSubject();
        }
        List<PaymentOrder> list = paymentOrderDao.selectYear(merchantId);
        return ReturnJson.success(list);
    }

    /**
     * 查询总包+分包的支付订单
     *
     * @param paymentOrderMerchantDto
     * @return
     */
    @Override
    public ReturnJson getPaymentOrder(PaymentOrderMerchantDto paymentOrderMerchantDto) {
        String paymentOrderId = paymentOrderMerchantDto.getPaymentOrderId();
        String taxId = paymentOrderMerchantDto.getTaxId();
        String beginDate = paymentOrderMerchantDto.getBeginDate();
        String endDate = paymentOrderMerchantDto.getEndDate();
        Page<PaymentOrder> paymentOrderPage = new Page<>(paymentOrderMerchantDto.getPage(), paymentOrderMerchantDto.getPageSize());

        IPage<PaymentOrder> paymentOrderIPage = paymentOrderDao.selectMany(paymentOrderPage, paymentOrderMerchantDto.getCompanyId(), paymentOrderId, taxId, beginDate, endDate);
        return ReturnJson.success(paymentOrderIPage);
    }

    /**
     * 查询支付订单详情
     *
     * @param id
     * @return
     */
    @Override
    public ReturnJson getPaymentOrderInfo(String id) {
        PaymentOrderInfoPO paymentOrderInfoPO = null;
        PaymentOrderInfoVO paymentOrderInfoVO = new PaymentOrderInfoVO();
        ExpressInfoVO expressInfoVO = new ExpressInfoVO();
        //为总包订单
        paymentOrderInfoPO = paymentOrderDao.selectPaymentOrderInfo(id);
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
        List<PaymentInventory> paymentInventories = paymentInventoryDao.selectPaymentInventoryList(id, null);
        paymentOrderInfoVO.setPaymentInventories(paymentInventories);
        paymentOrderInfoVO.setPaymentOrderInfoPO(paymentOrderInfoPO);
        paymentOrderInfoVO.setExpressInfoVO(expressInfoVO);
        return ReturnJson.success(paymentOrderInfoVO);
    }

    /**
     * 插入或更新数据
     *
     * @param addPaymentOrderDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson saveOrUpdataPaymentOrder(AddPaymentOrderDto addPaymentOrderDto) {
        PaymentOrder paymentOrder = addPaymentOrderDto.getPaymentOrder();
        List<PaymentInventory> paymentInventories = addPaymentOrderDto.getPaymentInventories();
        String id = paymentOrder.getId();
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

        CompanyTax companyTax = companyTaxDao.selectOne(new QueryWrapper<CompanyTax>().eq("tax_id", paymentOrder.getTaxId()).eq("company_id", paymentOrder.getCompanyId()));
        Integer taxStatus = paymentOrder.getTaxStatus();
        //判断服务费是一口价还是梯度价
        if (companyTax.getChargeStatus() == 0) {
            compositeTax = companyTax.getServiceCharge().divide(BigDecimal.valueOf(100));
            paymentOrder.setCompositeTax(compositeTax.multiply(BigDecimal.valueOf(100)));
            for (PaymentInventory paymentInventory : paymentInventories) {
                BigDecimal realMoney = paymentInventory.getRealMoney();
                paymentInventory.setCompositeTax(compositeTax.multiply(BigDecimal.valueOf(100)));
                if (taxStatus == 0) {
                    paymentInventory.setMerchantPaymentMoney(realMoney.multiply(compositeTax.add(new BigDecimal(1))));
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                } else if (taxStatus == 1) {
                    paymentInventory.setMerchantPaymentMoney(realMoney);
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                    paymentInventory.setRealMoney(realMoney.subtract(realMoney.multiply(compositeTax)));
                } else {
                    paymentInventory.setMerchantPaymentMoney(realMoney.multiply(compositeTax.subtract(merchantTax).add(new BigDecimal("1"))));
                    paymentInventory.setRealMoney(realMoney.subtract(realMoney.multiply(compositeTax.subtract(receviceTax))));
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
                if (taxStatus == 0) {
                    paymentInventory.setMerchantPaymentMoney(realMoney.multiply(compositeTax.add(new BigDecimal(1))));
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                } else if (taxStatus == 1) {
                    paymentInventory.setMerchantPaymentMoney(realMoney);
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                    paymentInventory.setRealMoney(realMoney.subtract(realMoney.multiply(compositeTax)));
                } else {
                    paymentInventory.setMerchantPaymentMoney(realMoney.multiply(compositeTax.subtract(merchantTax).add(new BigDecimal("1"))));
                    paymentInventory.setRealMoney(realMoney.subtract(realMoney.multiply(compositeTax.subtract(receviceTax))));
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
            return ReturnJson.error("订单创建失败！");
        }
        for (PaymentInventory paymentInventory : paymentInventories) {
            paymentInventory.setPaymentOrderId(paymentOrder.getId());
            //生成支付明细
            paymentInventory.setPackageStatus(0);
            paymentInventoryService.saveOrUpdate(paymentInventory);
        }
        return ReturnJson.success("支付订单创建成功！");
    }

    /**
     * 线下支付
     *
     * @param paymentOrderId
     * @param turnkeyProjectPayment
     * @return
     */
    @Override
    public ReturnJson offlinePayment(String paymentOrderId, String turnkeyProjectPayment) {
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

    /**
     * 总包支付信息
     *
     * @param id
     * @return
     */
    @Override
    public ReturnJson getPaymentOrderById(String id) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        List<PaymentOrderVo> paymentOrderVoList = new ArrayList<>();
        String[] ids = id.split(",");
        for (int i = 0; i < ids.length; i++) {
            PaymentOrderVo paymentOrder = paymentOrderDao.getPaymentOrderById(ids[i]);
            if (paymentOrder != null) {
                paymentOrderVoList.add(paymentOrder);
                returnJson = new ReturnJson("查询成功", paymentOrder, 200);
            }
        }
        return returnJson;
    }

    /**
     * 开票信息，支付
     *
     * @param id
     * @return
     */
    @Override
    public ReturnJson getBillingInfo(String id) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        BillingInfoVo billingInfo = paymentOrderDao.getBillingInfo(id);
        if (billingInfo != null) {
            returnJson = new ReturnJson("查询成功", billingInfo, 200);
        }
        return returnJson;
    }

    /**
     * 获取今天的支付总额
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getDayPaas(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getMerchantIds(merchantId);
        List<PaymentOrder> list = null;
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success(list);
        }
        list = paymentOrderDao.selectDaypaas(merchantIds);
        return ReturnJson.success(list);
    }

    /**
     * 获取本周的支付总额
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getWeekPaas(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getMerchantIds(merchantId);
        List<PaymentOrder> list = null;
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success(list);
        }
        list = paymentOrderDao.selectWeekpaas(merchantIds);
        return ReturnJson.success(list);
    }

    /**
     * 获取本月的支付总额
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getMonthPaas(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getMerchantIds(merchantId);
        List<PaymentOrder> list = null;
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success(list);
        }
        list = paymentOrderDao.selectMonthpaas(merchantIds);
        return ReturnJson.success(list);
    }

    /**
     * 获取今年的支付总额
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getYearPaas(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getMerchantIds(merchantId);
        List<PaymentOrder> list = null;
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success(list);
        }
        list = paymentOrderDao.selectYearpaas(merchantIds);
        return ReturnJson.success(list);
    }

    /**
     * 查询总包+分包的支付订单
     *
     * @param paymentOrderDto
     * @return
     */
    @Override
    public ReturnJson getPaymentOrderPaas(PaymentOrderDto paymentOrderDto,String managersId) throws CommonException {
        List<String> merchantIds = acquireID.getMerchantIds(managersId);
        List<PaymentOrder> list = null;
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success(list);
        }
        String merchantName = paymentOrderDto.getMerchantName();
        String paymentOrderId = paymentOrderDto.getPaymentOrderId();
        String taxId = paymentOrderDto.getTaxId();
        Integer pageSize = paymentOrderDto.getPageSize();
        Integer page = paymentOrderDto.getPage();
        String beginDate = paymentOrderDto.getBeginDate();
        String endDate = paymentOrderDto.getEndDate();
        Page<PaymentOrder> paymentOrderPage = new Page<>(page, pageSize);
        IPage<PaymentOrder> paymentOrderIPage = paymentOrderDao.selectManyPaas(paymentOrderPage, merchantIds, merchantName, paymentOrderId, taxId, beginDate, endDate);
        return ReturnJson.success(paymentOrderIPage);
    }

    /**
     * 线下支付
     *
     * @param paymentOrderId
     * @param turnkeyProjectPayment
     * @return
     */
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

    /**
     * 确认支付
     *
     * @param paymentOrderId
     * @return
     */
    @Override
    public ReturnJson confirmReceiptPaas(String paymentOrderId) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setId(paymentOrderId);
        paymentOrder.setPaymentOrderStatus(3);
        boolean flag = this.updateById(paymentOrder);
        if (flag) {
            return ReturnJson.success("确认收款成功！");
        }
        return ReturnJson.error("确认收款失败！");
    }

    /**
     * 查询商户
     *
     * @param managersId
     * @return
     */
    @Override
    public ReturnJson findMerchantPaas(String managersId) {
        Managers managers = managersDao.selectById(managersId);
        Integer userSign = managers.getUserSign();
        List<CompanyInfo> companyInfos = null;
        if (userSign == 1) {//管理人员为代理商
            companyInfos = companyInfoDao.selectList(new QueryWrapper<CompanyInfo>().eq("agent_id", managers.getId()));
        } else if (userSign == 2) {//管理人员为业务员
            companyInfos = companyInfoDao.selectList(new QueryWrapper<CompanyInfo>().eq("sales_man_id", managers.getId()));
        } else {
            companyInfos = companyInfoDao.selectList(new QueryWrapper<>());
        }
        return ReturnJson.success(companyInfos);
    }

    @Override
    public ReturnJson subpackagePayPaas(String paymentOrderId, String subpackagePayment) {
        PaymentOrder paymentOrder = this.getById(paymentOrderId);
        if (paymentOrder == null) {
            log.error("订单号不存在!");
            return ReturnJson.error("你输入的订单号不存在！");
        }
        paymentOrder.setSubpackagePayment(subpackagePayment);
        return null;
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
