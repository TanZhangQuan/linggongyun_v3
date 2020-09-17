package com.example.paas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.paas.dto.PaymentOrderDto;
import com.example.paas.service.MerchantService;
import com.example.paas.service.PaymentInventoryService;
import com.example.paas.service.PaymentOrderManyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    private MerchantDao merchantDao;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private TaxDao taxDao;

    @Autowired
    private PaymentInventoryService paymentInventoryService;

    @Autowired
    private ManagersDao managersDao;

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

    @Override
    public ReturnJson confirmPaymentMany(String id) {
        PaymentOrderMany paymentOrderMany = new PaymentOrderMany();
        paymentOrderMany.setId(id);
        paymentOrderMany.setPaymentOrderStatus(3);
        paymentOrderManyDao.updateById(paymentOrderMany);
        return ReturnJson.success("已成功确认收款");
    }

    /**
     * 查询众包的支付订单
     * @param paymentOrderDto
     * @return
     */
    @Override
    public ReturnJson getPaymentOrderMany(PaymentOrderDto paymentOrderDto) {
        Managers managers = managersDao.selectById(paymentOrderDto.getManagersId());
        List<String> merchantIds = this.getMerchantIds(managers);
        paymentOrderDto.setMerchantIds(merchantIds);
        return this.getPaymentOrderData(paymentOrderDto);
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

    /**
     * 创建或修改众包订单
     * @param paymentOrderMany
     * @param paymentInventories
     * @return
     */
    @Override
    public ReturnJson saveOrUpdataPaymentOrderMany(PaymentOrderMany paymentOrderMany, List<PaymentInventory> paymentInventories) {

        String id = paymentOrderMany.getId();
        if (id != null && paymentOrderMany.getPaymentOrderStatus() == 0) {
            List<PaymentInventory> paymentInventoryList = paymentInventoryDao.selectList(new QueryWrapper<PaymentInventory>().eq("payment_order_id", id));
            List<String> ids = new ArrayList<>();
            for (PaymentInventory paymentInventory : paymentInventoryList) {
                ids.add(paymentInventory.getId());
            }
            paymentInventoryDao.delete(new QueryWrapper<PaymentInventory>().eq("payment_order_id",id));
            this.removeById(id);
        }

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
            countMoney = countMoney.add(paymentInventory.getServiceMoney());
        }
        paymentOrderMany.setRealMoney(countMoney);
        boolean b = this.saveOrUpdate(paymentOrderMany);
        if (!b){
            return ReturnJson.error("订单创建失败！");
        }
        for (PaymentInventory paymentInventory : paymentInventories) {
            paymentInventory.setPaymentOrderId(paymentOrderMany.getId());
            try {
                paymentInventoryService.saveOrUpdate(paymentInventory);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ReturnJson.success("支付订单创建成功！");
    }


    private List<String> getMerchantIds(Managers managers) {
        List<String> merchantIds = new ArrayList<>();
        Integer userSign = managers.getUserSign();
        if (userSign == 1) {//管理人员为代理商
            List<Merchant> merchants = merchantDao.selectList(new QueryWrapper<Merchant>().eq("agent_id", managers.getId()));
            for (Merchant merchant : merchants) {
                merchantIds.add(merchant.getId());
            }
        } else if (userSign == 2) {//管理人员为业务员
            List<Merchant> merchants = merchantDao.selectList(new QueryWrapper<Merchant>().eq("sales_man_id", managers.getId()));
            for (Merchant merchant : merchants) {
                merchantIds.add(merchant.getId());
            }
        } else if (userSign == 3) {//管理人员为服务商
            Tax tax = taxDao.selectOne(new QueryWrapper<Tax>().eq("managers_id", managers.getId()));
            List<MerchantTax> merchantTaxes = merchantTaxDao.selectList(new QueryWrapper<MerchantTax>().eq("tax_id", tax.getId()));
            for (MerchantTax merchantTax : merchantTaxes) {
                merchantIds.add(merchantTax.getId());
            }
        } else {
            List<Merchant> merchants = merchantService.list();
            for (Merchant merchant : merchants) {
                merchantIds.add(merchant.getId());
            }
        }
        return merchantIds;
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

        Integer total = paymentOrderManyDao.selectManyCountPaas(merchantIds, merchantName, paymentOrderId, taxId, beginDate, endDate);
        Integer totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;

        List<PaymentOrderMany> list = paymentOrderManyDao.selectManyPaas(merchantIds, merchantName, paymentOrderId, taxId, beginDate, endDate, page, pageSize);
        ReturnJson returnJson = new ReturnJson();
        returnJson.setState("success");
        returnJson.setPageCount(totalPage);
        returnJson.setPageSize(pageSize);
        returnJson.setItemsCount(total);
        returnJson.setData(list);
        returnJson.setCode(200);
        return returnJson;
    }
}
