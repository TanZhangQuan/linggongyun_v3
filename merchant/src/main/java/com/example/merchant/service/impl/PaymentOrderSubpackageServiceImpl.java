package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.PaymentOrderSubpackageService;
import com.example.mybatis.entity.PaymentInventory;
import com.example.mybatis.entity.PaymentOrder;
import com.example.mybatis.entity.PaymentOrderSubpackage;
import com.example.mybatis.mapper.PaymentInventoryDao;
import com.example.mybatis.mapper.PaymentOrderDao;
import com.example.mybatis.mapper.PaymentOrderSubpackageDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 支付单信息
 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-08
 */
@Service
public class PaymentOrderSubpackageServiceImpl extends ServiceImpl<PaymentOrderSubpackageDao, PaymentOrderSubpackage> implements PaymentOrderSubpackageService {
    @Resource
    private PaymentInventoryDao paymentInventoryDao;

    @Resource
    private PaymentOrderDao paymentOrderDao;

    @Override
    public ReturnJson subpackagePayPaas(String paymentOrderId, String subpackagePayment) {
        List<PaymentInventory> paymentInventories = paymentInventoryDao.selectList(new QueryWrapper<PaymentInventory>().eq("payment_order_id", paymentOrderId));
        List<String> ids = new ArrayList<>();
        for (PaymentInventory paymentInventory : paymentInventories) {
            ids.add(paymentInventory.getId());
        }
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setId(paymentOrderId);
        paymentOrder.setSubpackagePayment(subpackagePayment);
        PaymentOrderSubpackage paymentOrderSubpackage = new PaymentOrderSubpackage();
        paymentOrderSubpackage.setPaymentOrderStatus(2);
        paymentOrderSubpackage.setPaymentDate(LocalDateTime.now());
        paymentOrderSubpackage.setSubpackagePayment(subpackagePayment);
        paymentOrderDao.updateById(paymentOrder);
        this.update(paymentOrderSubpackage,new QueryWrapper<PaymentOrderSubpackage>().in("payment_inventory_id",ids));
        return ReturnJson.success("分包支付成功");
    }
}
