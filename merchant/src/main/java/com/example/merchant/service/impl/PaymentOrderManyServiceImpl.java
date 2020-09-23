package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.PaymentOrderDto;
import com.example.merchant.service.PaymentInventoryService;
import com.example.merchant.service.PaymentOrderManyService;
import com.example.merchant.util.AcquireID;
import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.vo.CrowdSourcingInvoiceVo;
import com.example.mybatis.vo.InvoiceDetailsVo;
import com.example.mybatis.vo.PaymentOrderManyVo;
import org.apache.ibatis.session.RowBounds;
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
    private CompanyTaxDao companyTaxDao;

    @Autowired
    private TaxDao taxDao;

    @Autowired
    private PaymentInventoryService paymentInventoryService;

    @Autowired
    private CompanyLadderServiceDao companyLadderServiceDao;

    @Autowired
    private AcquireID acquireID;

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
     * 根据商户id查众包待开票数据
     * @param tobeinvoicedDto
     * @return
     */
    @Override
    public ReturnJson getListCSIByID(TobeinvoicedDto tobeinvoicedDto) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        RowBounds rowBounds=new RowBounds((tobeinvoicedDto.getPageNo()-1)*3,3);
        List<CrowdSourcingInvoiceVo> list = paymentOrderManyDao.getListCSIByID(tobeinvoicedDto,rowBounds);
        if (list != null && list.size() > 0) {
            returnJson = new ReturnJson("查询成功", list, 200);
        }
        return returnJson;
    }

    /**
     * 根据支付id查询众包支付信息
     * @param id
     * @return
     */
    @Override
    public ReturnJson getPayOrderManyById(String id) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        PaymentOrderManyVo paymentOrderManyVo = paymentOrderManyDao.getPayOrderManyById(id);
        if (paymentOrderManyVo != null) {
            returnJson = new ReturnJson("查询成功", paymentOrderManyVo, 200);
        }
        return returnJson;
    }

    /**
     * 根据支付id查找发票信息详情
     * @param id
     * @param pageNo
     * @return
     */
    @Override
    public ReturnJson getInvoiceDetailsByPayId(String id,Integer pageNo) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        RowBounds rowBounds=new RowBounds((pageNo-1)*3,3);
        List<InvoiceDetailsVo> list=paymentOrderManyDao.getInvoiceDetailsByPayId(id,rowBounds);
        if (list != null && list.size() > 0) {
            returnJson = new ReturnJson("查询成功", list, 200);
        }
        return returnJson;
    }

    /**
     * 查询众包的支付订单
     * @param paymentOrderDto
     * @return
     */
    @Override
    public ReturnJson getPaymentOrderMany(PaymentOrderDto paymentOrderDto) {
        String merchantId = acquireID.getCompanyId(paymentOrderDto.getMerchantId());
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

    /**
     * 创建或修改众包支付订单
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
        BigDecimal compositeTax = new BigDecimal("0");
        BigDecimal countMoney = new BigDecimal("0");
        Integer taxStatus = paymentOrderMany.getTaxStatus();

        CompanyTax companyTax = companyTaxDao.selectOne(new QueryWrapper<CompanyTax>().eq("tax_id", paymentOrderMany.getTaxId()).eq("company_id", paymentOrderMany.getCompanyId()));

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
            }
        }
        paymentOrderMany.setRealMoney(countMoney);
        boolean b = this.saveOrUpdate(paymentOrderMany);
        if (!b){
            return ReturnJson.error("订单创建失败！");
        }
        for (PaymentInventory paymentInventory : paymentInventories) {
            paymentInventory.setPaymentOrderId(paymentOrderMany.getId());
            paymentInventory.setPackageStatus(1);
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


    /**
     * 众包今天的支付金额
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getDayPaas(String merchantId) {
        List<String> merchantIds = acquireID.getMerchantIds(merchantId);
        List<PaymentOrderMany> list = paymentOrderManyDao.selectDaypaas(merchantIds);
        return ReturnJson.success(list);
    }

    /**
     * 众包本周的支付金额
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getWeekPaas(String merchantId) {
        List<String> merchantIds = acquireID.getMerchantIds(merchantId);
        List<PaymentOrderMany> list = paymentOrderManyDao.selectWeekpaas(merchantIds);
        return ReturnJson.success(list);
    }


    /**
     * 众包本月的支付金额
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getMonthPaas(String merchantId) {
        List<String> merchantIds = acquireID.getMerchantIds(merchantId);
        List<PaymentOrderMany> list = paymentOrderManyDao.selectMonthpaas(merchantIds);
        return ReturnJson.success(list);
    }

    /**
     * 众包今年的支付金额
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getYearPaas(String merchantId) {
        List<String> merchantIds = acquireID.getMerchantIds(merchantId);
        List<PaymentOrderMany> list = paymentOrderManyDao.selectYearpaas(merchantIds);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson confirmPaymentManyPaas(String id) {
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
    public ReturnJson getPaymentOrderManyPaas(PaymentOrderDto paymentOrderDto) {
        List<String> merchantIds = acquireID.getMerchantIds(paymentOrderDto.getManagersId());
        paymentOrderDto.setMerchantIds(merchantIds);
        return this.getPaymentOrderData(paymentOrderDto);
    }

    /**
     * 众包支付订单详情
     * @param id
     * @return
     */
    @Override
    public ReturnJson getPaymentOrderManyInfoPaas(String id) {
        PaymentOrderMany paymentOrderMany = paymentOrderManyDao.selectById(id);
        List<PaymentInventory> paymentInventories = paymentInventoryDao.selectList(new QueryWrapper<PaymentInventory>().eq("payment_order_id", id));
        Tax tax = taxDao.selectById(paymentOrderMany.getTaxId());
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("paymentInventories", paymentInventories);
        map.put("tax", tax);
        list.add(map);
        return ReturnJson.success(paymentOrderMany, list);
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
