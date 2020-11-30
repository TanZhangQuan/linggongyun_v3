package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ExcelUtils;
import com.example.common.util.ReturnJson;
import com.example.common.util.VerificationCheck;
import com.example.merchant.dto.regulator.PayInfoDto;
import com.example.merchant.vo.PaymentOrderInfoVO;
import com.example.merchant.vo.regulator.RegulatorWorkerPaymentInfoVO;
import com.example.mybatis.dto.RegulatorTaxDto;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.merchant.service.RegulatorTaxService;
import com.example.mybatis.po.PaymentOrderInfoPO;
import com.example.mybatis.po.RegulatorTaxPayInfoPo;
import com.example.mybatis.vo.TaxVo;
import com.sun.org.apache.bcel.internal.generic.LSTORE;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 监管部门监管的服务商 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-25
 */
@Service
public class RegulatorTaxServiceImpl extends ServiceImpl<RegulatorTaxDao, RegulatorTax> implements RegulatorTaxService {

    @Resource
    private RegulatorTaxDao regulatorTaxDao;

    @Resource
    private TaxDao taxDao;

    @Resource
    private PaymentInventoryDao paymentInventoryDao;

    @Resource
    private PaymentOrderDao paymentOrderDao;

    @Resource
    private PaymentOrderManyDao paymentOrderManyDao;

    /**
     * 监管服务商上方的四个数据
     *
     * @param regulatorId
     * @return
     */
    @Override
    public ReturnJson homeFourData(String regulatorId) {
        Map<String, Object> map = new HashMap<>();
        List<RegulatorTax> taxIds = regulatorTaxDao.selectList(new QueryWrapper<RegulatorTax>().select("tax_id").eq("regulator_id", regulatorId));//监管区服务商数量

        List<String> ids = new ArrayList<>();

        for (int i = 0; i < taxIds.size(); i++) {
            ids.add(taxIds.get(i).getTaxId());
        }

        List<Tax> taxList = taxDao.selectList(new QueryWrapper<Tax>().in("id", ids));

        BigDecimal paymentOrderNum = new BigDecimal("0.00");
        BigDecimal paymentOrderManyNum = new BigDecimal("0.00");
        BigDecimal paymentOrderTaxAmount = new BigDecimal("0.00");
        BigDecimal paymentOrderManyTaxAmount = new BigDecimal("0.00");
        Integer paymentOrderCount = 0;
        Integer paymentOrderManyCount = 0;
        Integer taxCount = 0;
        if (taxList.size() > 0) {
            taxCount = taxList.size();
            for (int i = 0; i < taxList.size(); i++) {
                System.out.println(111 + "---------------------------1111--------------" + i);
                List<String> paymentOrderIds = new ArrayList<>();
                List<PaymentOrder> paymentOrders = paymentOrderDao.selectList(new QueryWrapper<PaymentOrder>().in("tax_id", taxList.get(i).getId()).ge("payment_order_status", 2));
                paymentOrderCount = paymentOrders.size();
                for (PaymentOrder paymentOrder : paymentOrders) {
                    paymentOrderNum = paymentOrderNum.add(paymentOrder.getRealMoney());
                    paymentOrderIds.add(paymentOrder.getId());
                }

                List<PaymentOrderMany> paymentOrderManies = paymentOrderManyDao.selectList(new QueryWrapper<PaymentOrderMany>().in("tax_id", taxList.get(i).getId()).ge("payment_order_status", 2));
                paymentOrderManyCount = paymentOrderManies.size();
                for (PaymentOrderMany paymentOrderMany : paymentOrderManies) {
                    paymentOrderManyNum = paymentOrderManyNum.add(paymentOrderMany.getRealMoney());
                    paymentOrderIds.add(paymentOrderMany.getId());
                }

                if (!VerificationCheck.listIsNull(paymentOrderIds)) {
                    List<PaymentInventory> paymentInventories = paymentInventoryDao.selectList(new QueryWrapper<PaymentInventory>().in("payment_order_id", paymentOrderIds));
                    if (!VerificationCheck.listIsNull(paymentInventories)) {
                        for (PaymentInventory paymentInventory : paymentInventories) {
                            if (paymentInventory.getPackageStatus() == 0) {
                                paymentOrderTaxAmount = paymentOrderTaxAmount.add(paymentInventory.getTaxAmount());
                            } else {
                                paymentOrderManyTaxAmount = paymentOrderManyTaxAmount.add(paymentInventory.getTaxAmount());
                            }
                        }
                    }
                }
            }
            map.put("监管服务商数量", taxCount);
            map.put("总包支付单数", paymentOrderCount);
            map.put("众包支付单数", paymentOrderManyCount);
            map.put("总包支付流水", paymentOrderNum);
            map.put("众包支付流水", paymentOrderManyNum);
            map.put("总包总纳税金额", paymentOrderTaxAmount);
            map.put("众包总纳税金额", paymentOrderManyTaxAmount);
            return ReturnJson.success(map);
        }
        return ReturnJson.success("没有签约平台服务商");
    }

    /**
     * 查询服务商列表
     *
     * @param regulatorTaxDto
     * @return
     */
    @Override
    public ReturnJson listTax(RegulatorTaxDto regulatorTaxDto, String regulatorId) {
        Page page = new Page(regulatorTaxDto.getPageNo(), regulatorTaxDto.getPageSize());
        IPage<TaxVo> taxIPage = regulatorTaxDao.selServiceProviders(page, regulatorTaxDto,regulatorId);
        List<TaxVo> voList = taxIPage.getRecords();
        for (int i = 0; i < voList.size(); i++) {
            voList.get(i).setPaymentOrderCount(voList.get(i).getPaymentOrderNum() + "/" + voList.get(i).getPaymentOrderManyNum());
            voList.get(i).setStatus(voList.get(i).getTaxStatus() == 0 ? "未认证" : "已认证");
        }
        taxIPage.setRecords(voList);
        return ReturnJson.success(taxIPage);
    }

    /**
     * 查询服务商信息
     *
     * @param taxId
     * @return
     */
    @Override
    public ReturnJson getTax(String taxId) {
        Tax tax = taxDao.selectById(taxId);
        Map<String, Object> map = new HashMap<>();
        BigDecimal paymentOrderNum = new BigDecimal("0.00");
        BigDecimal paymentOrderManyNum = new BigDecimal("0.00");
        BigDecimal paymentOrderTaxAmount = new BigDecimal("0.00");
        BigDecimal paymentOrderManyTaxAmount = new BigDecimal("0.00");
        Integer paymentOrderCount = 0;
        Integer paymentOrderManyCount = 0;
        List<String> paymentOrderIds = new ArrayList<>();
        List<PaymentOrder> paymentOrders = paymentOrderDao.selectList(new QueryWrapper<PaymentOrder>().in("tax_id", tax.getId()).ge("payment_order_status", 2));
        paymentOrderCount = paymentOrders.size();
        for (PaymentOrder paymentOrder : paymentOrders) {
            paymentOrderNum = paymentOrderNum.add(paymentOrder.getRealMoney());
            paymentOrderIds.add(paymentOrder.getId());
        }

        List<PaymentOrderMany> paymentOrderManies = paymentOrderManyDao.selectList(new QueryWrapper<PaymentOrderMany>().in("tax_id", tax.getId()).ge("payment_order_status", 2));
        paymentOrderManyCount = paymentOrderManies.size();
        for (PaymentOrderMany paymentOrderMany : paymentOrderManies) {
            paymentOrderManyNum = paymentOrderManyNum.add(paymentOrderMany.getRealMoney());
            paymentOrderIds.add(paymentOrderMany.getId());
        }

        if (!VerificationCheck.listIsNull(paymentOrderIds)) {
            List<PaymentInventory> paymentInventories = paymentInventoryDao.selectList(new QueryWrapper<PaymentInventory>().in("payment_order_id", paymentOrderIds));
            if (!VerificationCheck.listIsNull(paymentInventories)) {
                for (PaymentInventory paymentInventory : paymentInventories) {
                    if (paymentInventory.getPackageStatus() == 0) {
                        paymentOrderTaxAmount = paymentOrderTaxAmount.add(paymentInventory.getTaxAmount());
                    } else {
                        paymentOrderManyTaxAmount = paymentOrderManyTaxAmount.add(paymentInventory.getTaxAmount());
                    }
                }
            }
        }
        map.put("服务商名称", tax.getTaxName());
        map.put("总包支付单数", paymentOrderCount);
        map.put("众包支付单数", paymentOrderManyCount);
        map.put("总包支付流水", paymentOrderNum);
        map.put("众包支付流水", paymentOrderManyNum);
        map.put("总包总纳税金额", paymentOrderTaxAmount);
        map.put("众包总纳税金额", paymentOrderManyTaxAmount);
        map.put("服务商信息", tax);
        return ReturnJson.success(map);
    }

    /**
     * 批量导出服务商信息
     *
     * @param taxIds 服务商id
     * @return
     */
    @Override
    public ReturnJson batchExportTax(String taxIds, HttpServletResponse response) {
        List list = Arrays.asList(taxIds.split(","));
        List<TaxVo> taxVos = regulatorTaxDao.selTaxListByIds(list);
        for (int i = 0; i < taxVos.size(); i++) {
            taxVos.get(i).setPaymentOrderCount(taxVos.get(i).getPaymentOrderNum() + "/" + taxVos.get(i).getPaymentOrderManyNum());
            taxVos.get(i).setStatus(taxVos.get(i).getTaxStatus() == 0 ? "未认证" : "已认证");
        }
        if (!VerificationCheck.listIsNull(taxVos)) {
            try {
                ExcelUtils.exportExcel(taxVos, "平台服务商信息", "服务商信息", TaxVo.class, "RegulatorTax", true, response);
                return ReturnJson.success("服务商导出成功！");
            } catch (IOException e) {
                log.error(e.toString() + ":" + e.getMessage());
            }
        }
        return ReturnJson.error("创客导出失败！");
    }

    /**
     * 支付订单信息
     *
     * @return
     */
    @Override
    public ReturnJson getPayInfo(PayInfoDto payInfoDto) {
        Page page = new Page(payInfoDto.getPageNo(), payInfoDto.getPageSize());
        IPage<RegulatorTaxPayInfoPo> infoPoIPage = taxDao.selectPayInfo(page, payInfoDto.getTaxId(), payInfoDto.getCompanySName(), payInfoDto.getStartDate(), payInfoDto.getEndDate());
        List<RegulatorTaxPayInfoPo> list = infoPoIPage.getRecords();
        for (int i = 0; i < list.size(); i++) {
            switch (list.get(i).getPaymentOrderStatus()) {
                case 0:
                    list.get(i).setPaymentOrderZNameStatus("申请中");
                    break;
                case 1:
                    list.get(i).setPaymentOrderZNameStatus("待支付");
                    break;
                case 3:
                    list.get(i).setPaymentOrderZNameStatus("已支付");
                    break;
                case 4:
                    list.get(i).setPaymentOrderZNameStatus("已确认收款");
                    break;
            }
        }
        infoPoIPage.setRecords(list);
        return ReturnJson.success(infoPoIPage);
    }

    /**
     * 导出支付订单信息
     *
     * @param paymentOrderIds
     * @param response
     * @return
     */
    @Override
    public ReturnJson batchExportPayInfo(@NotNull String paymentOrderIds, HttpServletResponse response) {
        List<RegulatorTaxPayInfoPo> voList = taxDao.getPayInfoByIds(Arrays.asList(paymentOrderIds.split(",")));
        for (int i = 0; i < voList.size(); i++) {
            switch (voList.get(i).getPaymentOrderStatus()) {
                case 0:
                    voList.get(i).setPaymentOrderZNameStatus("申请中");
                    break;
                case 1:
                    voList.get(i).setPaymentOrderZNameStatus("待支付");
                    break;
                case 3:
                    voList.get(i).setPaymentOrderZNameStatus("已支付");
                    break;
                case 4:
                    voList.get(i).setPaymentOrderZNameStatus("已确认收款");
                    break;
            }
        }
        if (!VerificationCheck.listIsNull(voList)) {
            try {
                ExcelUtils.exportExcel(voList, "支付订单信息", "支付订单信息", RegulatorTaxPayInfoPo.class, "RegulatorTaxPayInfoPo", true, response);
                return ReturnJson.success("支付订单导出成功！");
            } catch (IOException e) {
                log.error(e.toString() + ":" + e.getMessage());
            }
        }
        return ReturnJson.error("创客导出失败！");
    }

    /**
     * 支付清单明细查询
     *
     * @param paymentOrderId
     * @return
     */
    @Override
    public ReturnJson getPaymentInventoryInfo(String paymentOrderId, Integer page, Integer pageSize) {
        Page<PaymentInventory> paymentInventoryPage = new Page<>(page, pageSize);
        paymentInventoryPage = paymentInventoryDao.selectPage(paymentInventoryPage, new QueryWrapper<PaymentInventory>().eq("payment_order_id", paymentOrderId));
        return ReturnJson.success(paymentInventoryPage);
    }

    /**
     * 支付信息  以及  支付方信息
     *
     * @param paymentOrderId 支付id
     * @param type           合作类型
     * @return
     */
    @Override
    public ReturnJson getPaymentOrderInfo(String paymentOrderId, Integer type) {
        Map<String, Object> map = new HashMap();
        PaymentOrderInfoPO paymentOrderInfoPO = null;
        if (type == 0) {
            paymentOrderInfoPO = paymentOrderDao.selectPaymentOrderInfo(paymentOrderId);
        } else {
            paymentOrderInfoPO = paymentOrderManyDao.selectPaymentOrderInfo(paymentOrderId);
        }
        return ReturnJson.success(paymentOrderInfoPO);
    }


}
