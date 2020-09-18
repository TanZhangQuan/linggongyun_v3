package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.PaymentOrderDto;
import com.example.merchant.service.PaymentInventoryService;
import com.example.merchant.service.PaymentOrderService;
import com.example.mybatis.entity.PaymentInventory;
import com.example.mybatis.entity.PaymentOrder;
import com.example.mybatis.entity.PaymentOrderSubpackage;
import com.example.mybatis.entity.Tax;
import com.example.mybatis.mapper.PaymentInventoryDao;
import com.example.mybatis.mapper.PaymentOrderDao;
import com.example.mybatis.mapper.PaymentOrderSubpackageDao;
import com.example.mybatis.mapper.TaxDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 获取今天的支付总额
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getDay(String merchantId) {
        List<PaymentOrder> list = paymentOrderDao.selectDay(merchantId);
        return ReturnJson.success(list);
    }

    /**
     * 获取本周的支付总额
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getWeek(String merchantId) {
        List<PaymentOrder> list = paymentOrderDao.selectWeek(merchantId);
        return ReturnJson.success(list);
    }

    /**
     * 获取本月的支付总额
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getMonth(String merchantId) {
        List<PaymentOrder> list = paymentOrderDao.selectMonth(merchantId);
        return ReturnJson.success(list);
    }

    /**
     * 获取今年的支付总额
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getYear(String merchantId) {
        List<PaymentOrder> list = paymentOrderDao.selectYear(merchantId);
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
        BigDecimal compositeTax = paymentOrder.getCompositeTax().divide(BigDecimal.valueOf(100));
        BigDecimal countMoney = new BigDecimal("0");
        BigDecimal countWorkerMoney = new BigDecimal("0");
        if (!compositeTax.equals(receviceTax.add(merchantTax))){
            return ReturnJson.error("综合税率应该等于商户+创客的税率");
        }
        for (PaymentInventory paymentInventory : paymentInventories) {
            paymentInventory.setCompositeTax(paymentOrder.getCompositeTax());
            Integer taxStatus = paymentOrder.getTaxStatus();
            BigDecimal realMoney = paymentInventory.getRealMoney();
            if (taxStatus == 0){
                paymentInventory.setMerchantPaymentMoney(realMoney.multiply(merchantTax.add(BigDecimal.valueOf(1))));
                paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
            } else if (taxStatus == 1){
                paymentInventory.setMerchantPaymentMoney(realMoney);
                paymentInventory.setServiceMoney(realMoney.multiply(receviceTax));
                paymentInventory.setRealMoney(realMoney.subtract(realMoney.multiply(compositeTax)));
            } else {
                paymentInventory.setMerchantPaymentMoney(realMoney.multiply(merchantTax.add(BigDecimal.valueOf(1))));
                paymentInventory.setRealMoney(realMoney.subtract(realMoney.multiply(receviceTax)));
                paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
            }
            countMoney = countMoney.add(paymentInventory.getMerchantPaymentMoney());
            countWorkerMoney = countWorkerMoney.add(paymentInventory.getRealMoney());
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
            paymentOrderSubpackage.setMerchantId(paymentOrder.getMerchantId());
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
}
