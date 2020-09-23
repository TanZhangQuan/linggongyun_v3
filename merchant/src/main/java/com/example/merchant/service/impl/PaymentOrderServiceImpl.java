package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.PaymentOrderDto;
import com.example.merchant.service.MerchantService;
import com.example.merchant.service.PaymentInventoryService;
import com.example.merchant.service.PaymentOrderService;
import com.example.merchant.util.AcquireID;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.vo.BillingInfoVo;
import com.example.mybatis.vo.PaymentOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 支付单信息
 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class PaymentOrderServiceImpl extends ServiceImpl<PaymentOrderDao, PaymentOrder> implements PaymentOrderService {

    @Autowired
    private PaymentOrderDao paymentOrderDao;

    @Autowired
    private PaymentInventoryDao paymentInventoryDao;

    @Autowired
    private PaymentInventoryService paymentInventoryService;

    @Autowired
    private PaymentOrderSubpackageDao paymentOrderSubpackageDao;

    @Autowired
    private TaxDao taxDao;

    @Autowired
    private ManagersDao managersDao;

    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private CompanyTaxDao companyTaxDao;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private CompanyLadderServiceDao companyLadderServiceDao;

    /**
     * 获取今天的支付总额
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getDay(String merchantId) {
        List<PaymentOrder> list = paymentOrderDao.selectDay(acquireID.getCompanyId(merchantId));
        return ReturnJson.success(list);
    }

    /**
     * 获取本周的支付总额
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getWeek(String merchantId) {
        List<PaymentOrder> list = paymentOrderDao.selectWeek(acquireID.getCompanyId(merchantId));
        return ReturnJson.success(list);
    }

    /**
     * 获取本月的支付总额
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getMonth(String merchantId) {
        List<PaymentOrder> list = paymentOrderDao.selectMonth(acquireID.getCompanyId(merchantId));
        return ReturnJson.success(list);
    }

    /**
     * 获取今年的支付总额
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getYear(String merchantId) {
        List<PaymentOrder> list = paymentOrderDao.selectYear(acquireID.getCompanyId(merchantId));
        return ReturnJson.success(list);
    }

    /**
     * 查询总包+分包的支付订单
     * @param paymentOrderDto
     * @return
     */
    @Override
    public ReturnJson getPaymentOrder(PaymentOrderDto paymentOrderDto) {
        String merchantId = paymentOrderDto.getMerchantId();
        String paymentOrderId = paymentOrderDto.getPaymentOrderId();
        String taxId = paymentOrderDto.getTaxId();
        Integer pageSize = paymentOrderDto.getPageSize();
        Integer page = (paymentOrderDto.getPage()-1)*pageSize;
        String beginDate = paymentOrderDto.getBeginDate();
        String endDate = paymentOrderDto.getEndDate();

        Integer total = paymentOrderDao.selectManyCount(merchantId, paymentOrderId, taxId, beginDate, endDate);
        Integer totalPage = total%pageSize == 0 ? total/pageSize:total/pageSize+1;

        List<PaymentOrder> list = paymentOrderDao.selectMany(merchantId, paymentOrderId, taxId, beginDate, endDate, page, pageSize);
        ReturnJson returnJson = new ReturnJson();
        returnJson.setState("success");
        returnJson.setPageCount(totalPage);
        returnJson.setPageSize(pageSize);
        returnJson.setItemsCount(total);
        returnJson.setData(list);
        returnJson.setCode(200);
        return returnJson;
    }

    /**
     * 查询支付订单详情
     * @param id
     * @return
     */
    @Override
    public ReturnJson getPaymentOrderInfo(String id) {
        PaymentOrder paymentOrder = paymentOrderDao.selectById(id);
        List<PaymentInventory> paymentInventories = paymentInventoryDao.selectList(new QueryWrapper<PaymentInventory>().eq("payment_order_id", id));
        List<PaymentOrderSubpackage> paymentOrderSubpackages = paymentOrderSubpackageDao.selectList(new QueryWrapper<PaymentOrderSubpackage>().eq("payment_order_id", id));
        Tax tax = taxDao.selectById(paymentOrder.getTaxId());
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("paymentInventories",paymentInventories);
        map.put("paymentOrderSubpackages",paymentOrderSubpackages);
        map.put("tax",tax);
        list.add(map);
        return ReturnJson.success(paymentOrder,list);
    }

    /**
     * 插入或更新数据
     * @param paymentOrder
     * @param paymentInventories
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson saveOrUpdataPaymentOrder(PaymentOrder paymentOrder, List<PaymentInventory> paymentInventories) {
        String id = paymentOrder.getId();
        if (id != null && paymentOrder.getPaymentOrderStatus() == 0) {
            List<PaymentInventory> paymentInventoryList = paymentInventoryDao.selectList(new QueryWrapper<PaymentInventory>().eq("payment_order_id", id));
            List<String> ids = new ArrayList<>();
            for (PaymentInventory paymentInventory : paymentInventoryList) {
                ids.add(paymentInventory.getId());
            }
            paymentOrderSubpackageDao.delete(new QueryWrapper<PaymentOrderSubpackage>().in("payment_inventory_id",ids));
            paymentInventoryDao.delete(new QueryWrapper<PaymentInventory>().eq("payment_order_id",id));
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
            compositeTax = companyTax.getServiceCharge();
            for (PaymentInventory paymentInventory : paymentInventories) {
                BigDecimal realMoney = paymentInventory.getRealMoney();
                if (taxStatus == 0){
                    paymentInventory.setMerchantPaymentMoney(realMoney.multiply(compositeTax));
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                } else if (taxStatus == 1){
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
            for (PaymentInventory paymentInventory : paymentInventories) {
                BigDecimal realMoney = paymentInventory.getRealMoney();
                compositeTax = this.getCompositeTax(companyLadderServices,realMoney);
                if (taxStatus == 0){
                    paymentInventory.setMerchantPaymentMoney(realMoney.multiply(compositeTax));
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                } else if (taxStatus == 1){
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
        }
        paymentOrder.setRealMoney(countMoney);
        paymentOrder.setWorkerMoney(countWorkerMoney);
        //生成总包支付订单
        boolean b = this.saveOrUpdate(paymentOrder);
        if (!b){
            return ReturnJson.error("订单创建失败！");
        }
        for (PaymentInventory paymentInventory : paymentInventories) {
            paymentInventory.setPaymentOrderId(paymentOrder.getId());
            //生成支付明细
            paymentInventory.setPackageStatus(0);
            paymentInventoryService.saveOrUpdate(paymentInventory);
            //生成分包订单
            PaymentOrderSubpackage paymentOrderSubpackage = new PaymentOrderSubpackage();
            paymentOrderSubpackage.setCompanyId(paymentOrder.getCompanyId());
            paymentOrderSubpackage.setPaymentInventoryId(paymentInventory.getId());
            paymentOrderSubpackage.setRealMoney(paymentInventory.getRealMoney());
            paymentOrderSubpackage.setTaskId(paymentOrder.getTaskId());
            paymentOrderSubpackage.setTaxId(paymentOrder.getTaxId());
            paymentOrderSubpackageDao.insert(paymentOrderSubpackage);
        }
        return ReturnJson.success("支付订单创建成功！");
    }

    /**
     * 线下支付
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


    @Autowired
    private AcquireID acquireID;


    /**
     * 获取今天的支付总额
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getDayPaas(String merchantId) {
        List<String> merchantIds = acquireID.getMerchantIds(merchantId);
        List<PaymentOrder> list = paymentOrderDao.selectDaypaas(merchantIds);
        return ReturnJson.success(list);
    }

    /**
     * 获取本周的支付总额
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getWeekPaas(String merchantId) {
        List<String> merchantIds = acquireID.getMerchantIds(merchantId);
        List<PaymentOrder> list = paymentOrderDao.selectWeekpaas(merchantIds);
        return ReturnJson.success(list);
    }

    /**
     * 获取本月的支付总额
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getMonthPaas(String merchantId) {
        List<String> merchantIds = acquireID.getMerchantIds(merchantId);
        List<PaymentOrder> list = paymentOrderDao.selectMonthpaas(merchantIds);
        return ReturnJson.success(list);
    }

    /**
     * 获取今年的支付总额
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getYearPaas(String merchantId) {
        List<String> merchantIds = acquireID.getMerchantIds(merchantId);
        List<PaymentOrder> list = paymentOrderDao.selectYearpaas(merchantIds);
        return ReturnJson.success(list);
    }

    /**
     * 查询总包+分包的支付订单
     *
     * @param paymentOrderDto
     * @return
     */
    @Override
    public ReturnJson getPaymentOrderPaas(PaymentOrderDto paymentOrderDto) {
        List<String> merchantIds = acquireID.getMerchantIds(paymentOrderDto.getManagersId());
        paymentOrderDto.setMerchantIds(merchantIds);
        return this.getPaymentOrderData(paymentOrderDto);
    }

    /**
     * 查询支付订单详情
     *
     * @param id
     * @return
     */
    @Override
    public ReturnJson getPaymentOrderInfoPaas(String id) {
        PaymentOrder paymentOrder = paymentOrderDao.selectById(id);
        List<PaymentInventory> paymentInventories = paymentInventoryDao.selectList(new QueryWrapper<PaymentInventory>().eq("payment_order_id", id));
        List<PaymentOrderSubpackage> paymentOrderSubpackages = paymentOrderSubpackageDao.selectList(new QueryWrapper<PaymentOrderSubpackage>().eq("payment_order_id", id));
        Tax tax = taxDao.selectById(paymentOrder.getTaxId());
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("paymentInventories", paymentInventories);
        map.put("paymentOrderSubpackages", paymentOrderSubpackages);
        map.put("tax", tax);
        list.add(map);
        return ReturnJson.success(paymentOrder, list);
    }

    /**
     * 线下支付
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
        List<Merchant> merchants = null;
        if (userSign == 1) {//管理人员为代理商
            merchants = merchantDao.selectList(new QueryWrapper<Merchant>().eq("agent_id", managers.getId()));
        } else if (userSign == 2) {//管理人员为业务员
            merchants = merchantDao.selectList(new QueryWrapper<Merchant>().eq("sales_man_id", managers.getId()));
        } else {
            merchants = merchantService.list();
        }
        return ReturnJson.success(merchants);
    }

    private ReturnJson getPaymentOrderData(PaymentOrderDto paymentOrderDto) {
        List<String> merchantIds = paymentOrderDto.getMerchantIds();
        String merchantName = paymentOrderDto.getMerchantName();
        String paymentOrderId = paymentOrderDto.getPaymentOrderId();
        String taxId = paymentOrderDto.getTaxId();
        Integer pageSize = paymentOrderDto.getPageSize();
        Integer page = (paymentOrderDto.getPage() - 1) * pageSize;
        String beginDate = paymentOrderDto.getBeginDate();
        String endDate = paymentOrderDto.getEndDate();

        Integer total = paymentOrderDao.selectManyCountPaas(merchantIds, merchantName, paymentOrderId, taxId, beginDate, endDate);
        Integer totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;

        List<PaymentOrder> list = paymentOrderDao.selectManyPaas(merchantIds, merchantName, paymentOrderId, taxId, beginDate, endDate, page, pageSize);
        ReturnJson returnJson = new ReturnJson();
        returnJson.setState("success");
        returnJson.setPageCount(totalPage);
        returnJson.setPageSize(pageSize);
        returnJson.setItemsCount(total);
        returnJson.setData(list);
        returnJson.setCode(200);
        return returnJson;
    }

    /**
     * 获取综合费率
     * @param companyLadderServices
     * @param realMoney
     * @return
     */
    private BigDecimal getCompositeTax(List<CompanyLadderService> companyLadderServices,BigDecimal realMoney){
        BigDecimal compositeTax = null;
        for (CompanyLadderService companyLadderService : companyLadderServices) {
            BigDecimal startMoney = companyLadderService.getStartMoney();
            if (realMoney.compareTo(startMoney) >= 0){
                compositeTax = companyLadderService.getServiceCharge();
            }
        }
        return compositeTax;
    }

}
