package com.example.paas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ReturnJson;
import com.example.paas.dto.PaymentOrderDto;
import com.example.paas.service.PaymentInventoryService;
import com.example.paas.service.PaymentOrderManyService;
import com.example.mybatis.entity.PaymentInventory;
import com.example.mybatis.entity.PaymentOrderMany;
import com.example.mybatis.entity.Tax;
import com.example.mybatis.mapper.MerchantTaxDao;
import com.example.mybatis.mapper.PaymentInventoryDao;
import com.example.mybatis.mapper.PaymentOrderManyDao;
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
 * 众包支付单操作
 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-08
 */
@Service
public class PaymentOrderManyServiceImpl extends ServiceImpl<PaymentOrderManyDao, PaymentOrderMany> implements PaymentOrderManyService {

    @Autowired
    private PaymentOrderManyDao paymentOrderManyDao;

    @Autowired
    private PaymentInventoryDao paymentInventoryDao;

    @Autowired
    private MerchantTaxDao merchantTaxDao;

    @Autowired
    private TaxDao taxDao;

    @Autowired
    private PaymentInventoryService paymentInventoryService;

    /**
     * 众包今天的支付金额
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getDay(String merchantId) {
        List<PaymentOrderMany> list = paymentOrderManyDao.selectDay(merchantId);
        return ReturnJson.success(list);
    }

    /**
     * 众包本周的支付金额
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getWeek(String merchantId) {
        List<PaymentOrderMany> list = paymentOrderManyDao.selectWeek(merchantId);
        return ReturnJson.success(list);
    }


    /**
     * 众包本月的支付金额
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getMonth(String merchantId) {
        List<PaymentOrderMany> list = paymentOrderManyDao.selectMonth(merchantId);
        return ReturnJson.success(list);
    }

    /**
     * 众包今年的支付金额
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getYear(String merchantId) {
        List<PaymentOrderMany> list = paymentOrderManyDao.selectYear(merchantId);
        return ReturnJson.success(list);
    }

    /**
     * 查询众包的支付订单
     * @param paymentOrderDto
     * @return
     */
    @Override
    public ReturnJson getPaymentOrderMany(PaymentOrderDto paymentOrderDto) {
        String merchantId = paymentOrderDto.getMerchantId();
        String paymentOrderId = paymentOrderDto.getPaymentOrderId();
        String taxId = paymentOrderDto.getTaxId();
        Integer pageSize = paymentOrderDto.getPageSize();
        Integer page = (paymentOrderDto.getPage()-1)*pageSize;
        String beginDate = paymentOrderDto.getBeginDate();
        String endDate = paymentOrderDto.getEndDate();

        Integer total = paymentOrderManyDao.selectManyCount(merchantId, paymentOrderId, taxId, beginDate, endDate);
        Integer totalPage = total%pageSize == 0 ? total/pageSize:total/pageSize+1;

        List<PaymentOrderMany> list = paymentOrderManyDao.selectMany(merchantId, paymentOrderId, taxId, beginDate, endDate, page, pageSize);
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
     * 众包支付订单详情
     * @param id
     * @return
     */
    @Override
    public ReturnJson getPaymentOrderManyInfo(String id) {
        PaymentOrderMany paymentOrderMany = paymentOrderManyDao.selectById(id);
        List<PaymentInventory> paymentInventories = paymentInventoryDao.selectList(new QueryWrapper<PaymentInventory>().eq("payment_order_id", id));
        Tax tax = taxDao.selectById(paymentOrderMany.getTaxId());
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("paymentInventories",paymentInventories);
        map.put("tax",tax);
        list.add(map);
        return ReturnJson.success(paymentOrderMany,list);
    }

    @Override
    public ReturnJson saveOrUpdataPaymentOrderMany(PaymentOrderMany paymentOrderMany, List<PaymentInventory> paymentInventories) {
        BigDecimal receviceTax = paymentOrderMany.getReceviceTax().divide(BigDecimal.valueOf(100));
        BigDecimal merchantTax = paymentOrderMany.getMerchantTax().divide(BigDecimal.valueOf(100));
        BigDecimal compositeTax = paymentOrderMany.getCompositeTax().divide(BigDecimal.valueOf(100));
        BigDecimal countMoney = new BigDecimal("0");

        if (!compositeTax.equals(receviceTax.add(merchantTax))){
            return ReturnJson.error("综合税率应该等于商户+创客的税率");
        }
        for (PaymentInventory paymentInventory : paymentInventories) {
            paymentInventory.setCompositeTax(paymentOrderMany.getCompositeTax());
            Integer taxStatus = paymentOrderMany.getTaxStatus();
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
        }
        paymentOrderMany.setRealMoney(countMoney);
        boolean b = this.saveOrUpdate(paymentOrderMany);
        if (!b){
            return ReturnJson.error("订单创建失败！");
        }
        for (PaymentInventory paymentInventory : paymentInventories) {
            paymentInventory.setPaymentOrderId(paymentOrderMany.getId());
        }
        paymentInventoryService.saveOrUpdateBatch(paymentInventories);
        return ReturnJson.success("支付订单创建成功！");
    }

    @Override
    public ReturnJson offlinePayment(String id, String manyPayment) {
        PaymentOrderMany paymentOrder = new PaymentOrderMany();
        paymentOrder.setId(id);
        paymentOrder.setManyPayment(manyPayment);
        paymentOrder.setPaymentDate(LocalDateTime.now());
        paymentOrder.setPaymentOrderStatus(2);
        int i = paymentOrderManyDao.updateById(paymentOrder);
        if (i == 1) {
            return ReturnJson.success("支付成功！");
        }
        return ReturnJson.error("支付失败，请重试！");
    }
}
