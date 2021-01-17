package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ExcelUtils;
import com.example.common.util.ReturnJson;
import com.example.common.util.VerificationCheck;
import com.example.merchant.dto.regulator.PayInfoDTO;
import com.example.merchant.exception.CommonException;
import com.example.mybatis.dto.RegulatorTaxDTO;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.merchant.service.RegulatorTaxService;
import com.example.mybatis.po.PaymentOrderInfoPO;
import com.example.mybatis.po.RegulatorTaxPayInfoPo;
import com.example.mybatis.vo.TaxInfoVO;
import com.example.mybatis.vo.TaxVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

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

    @Override
    public ReturnJson homeFourData(String regulatorId) {
        Map<String, Object> map = new HashMap<>();
        List<RegulatorTax> taxIds = regulatorTaxDao.selectList(
                new QueryWrapper<RegulatorTax>().select("tax_id")
                        .eq("regulator_id", regulatorId)
                        .eq("status",0));//监管区服务商数量

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
                List<String> paymentOrderIds = new ArrayList<>();
                List<PaymentOrder> paymentOrders = paymentOrderDao.selectList(
                        new QueryWrapper<PaymentOrder>()
                                .in("tax_id", taxList.get(i).getId())
                                .ge("payment_order_status", 2));
                paymentOrderCount = paymentOrders.size();
                for (PaymentOrder paymentOrder : paymentOrders) {
                    paymentOrderNum = paymentOrderNum.add(paymentOrder.getRealMoney());
                    paymentOrderIds.add(paymentOrder.getId());
                }

                List<PaymentOrderMany> paymentOrderManies = paymentOrderManyDao.selectList(
                        new QueryWrapper<PaymentOrderMany>()
                                .in("tax_id", taxList.get(i).getId())
                                .ge("payment_order_status", 2));
                paymentOrderManyCount = paymentOrderManies.size();
                for (PaymentOrderMany paymentOrderMany : paymentOrderManies) {
                    paymentOrderManyNum = paymentOrderManyNum.add(paymentOrderMany.getRealMoney());
                    paymentOrderIds.add(paymentOrderMany.getId());
                }

                if (!VerificationCheck.listIsNull(paymentOrderIds)) {
                    List<PaymentInventory> paymentInventories = paymentInventoryDao.selectList(
                            new QueryWrapper<PaymentInventory>()
                                    .in("payment_order_id", paymentOrderIds));
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
            map.put("taxCount", taxCount);
            map.put("paymentOrderCount", paymentOrderCount);
            map.put("paymentOrderManyCount", paymentOrderManyCount);
            map.put("paymentOrderNum", paymentOrderNum);
            map.put("paymentOrderManyNum", paymentOrderManyNum);
            map.put("paymentOrderTaxAmount", paymentOrderTaxAmount);
            map.put("paymentOrderManyTaxAmount", paymentOrderManyTaxAmount);
            return ReturnJson.success(map);
        }
        return ReturnJson.error("没有签约平台服务商");
    }

    @Override
    public ReturnJson listTax(RegulatorTaxDTO regulatorTaxDto, String regulatorId) {
        Page page = new Page(regulatorTaxDto.getPageNo(), regulatorTaxDto.getPageSize());
        IPage<TaxVO> taxIPage = regulatorTaxDao.selServiceProviders(page, regulatorTaxDto, regulatorId);
        List<TaxVO> voList = taxIPage.getRecords();
        for (int i = 0; i < voList.size(); i++) {
            voList.get(i).setPaymentOrderCount(voList.get(i).getPaymentOrderNum() + "/" + voList.get(i).getPaymentOrderManyNum());
            voList.get(i).setStatus(voList.get(i).getTaxStatus() == 0 ? "未认证" : "已认证");
        }
        taxIPage.setRecords(voList);
        return ReturnJson.success(taxIPage);
    }

    @Override
    public ReturnJson getTax(String taxId) {
        TaxInfoVO tax = taxDao.queryTaxInfoById(taxId);
        Map<String, Object> map = new HashMap<>();
        BigDecimal paymentOrderNum = new BigDecimal("0.00");
        BigDecimal paymentOrderManyNum = new BigDecimal("0.00");
        BigDecimal paymentOrderTaxAmount = new BigDecimal("0.00");
        BigDecimal paymentOrderManyTaxAmount = new BigDecimal("0.00");
        Integer paymentOrderCount = 0;
        Integer paymentOrderManyCount = 0;
        List<String> paymentOrderIds = new ArrayList<>();
        List<PaymentOrder> paymentOrders = paymentOrderDao.selectList(new QueryWrapper<PaymentOrder>()
                .in("tax_id", tax.getId())
                .ge("payment_order_status", 2));
        paymentOrderCount = paymentOrders.size();
        for (PaymentOrder paymentOrder : paymentOrders) {
            paymentOrderNum = paymentOrderNum.add(paymentOrder.getRealMoney());
            paymentOrderIds.add(paymentOrder.getId());
        }

        List<PaymentOrderMany> paymentOrderManies = paymentOrderManyDao.selectList(new QueryWrapper<PaymentOrderMany>()
                .in("tax_id", tax.getId())
                .ge("payment_order_status", 2));
        paymentOrderManyCount = paymentOrderManies.size();
        for (PaymentOrderMany paymentOrderMany : paymentOrderManies) {
            paymentOrderManyNum = paymentOrderManyNum.add(paymentOrderMany.getRealMoney());
            paymentOrderIds.add(paymentOrderMany.getId());
        }

        if (!VerificationCheck.listIsNull(paymentOrderIds)) {
            List<PaymentInventory> paymentInventories = paymentInventoryDao.selectList(new QueryWrapper<PaymentInventory>()
                    .in("payment_order_id", paymentOrderIds));
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
        map.put("taxName", tax.getTaxName());
        map.put("paymentOrderCount", paymentOrderCount);
        map.put("paymentOrderManyCount", paymentOrderManyCount);
        map.put("paymentOrderNum", paymentOrderNum);
        map.put("paymentOrderManyNum", paymentOrderManyNum);
        map.put("paymentOrderTaxAmount", paymentOrderTaxAmount);
        map.put("paymentOrderManyTaxAmount", paymentOrderManyTaxAmount);
        map.put("tax", tax);
        return ReturnJson.success(map);
    }

    @Override
    public ReturnJson batchExportTax(String taxIds, HttpServletResponse response) throws CommonException {
        List list = Arrays.asList(taxIds.split(","));
        List<TaxVO> taxVOS = regulatorTaxDao.selTaxListByIds(list);
        for (int i = 0; i < taxVOS.size(); i++) {
            taxVOS.get(i).setPaymentOrderCount(taxVOS.get(i).getPaymentOrderNum() + "/" + taxVOS.get(i).getPaymentOrderManyNum());
            taxVOS.get(i).setStatus(taxVOS.get(i).getTaxStatus() == 0 ? "未认证" : "已认证");
        }
        if (!VerificationCheck.listIsNull(taxVOS)) {
            try {
                ExcelUtils.exportExcel(taxVOS, "平台服务商信息", "服务商信息", TaxVO.class, "RegulatorTax", true, response);
                return ReturnJson.success("服务商导出成功！");
            } catch (IOException e) {
                log.error(e.toString() + ":" + e.getMessage());
                throw new CommonException(300, "创客导出失败！");
            }
        }
        throw new CommonException(300, "创客导出失败！");
    }

    @Override
    public ReturnJson getPayInfo(PayInfoDTO payInfoDto) {
        Page page = new Page(payInfoDto.getPageNo(), payInfoDto.getPageSize());
        IPage<RegulatorTaxPayInfoPo> infoPoIPage = taxDao.selectPayInfo(page, payInfoDto.getTaxId(), payInfoDto.getCompanySName(), payInfoDto.getStartDate(), payInfoDto.getEndDate());
        List<RegulatorTaxPayInfoPo> list = infoPoIPage.getRecords();
        for (int i = 0; i < list.size(); i++) {
            switch (list.get(i).getPaymentOrderStatus()) {
                case -1:
                    list.get(i).setPaymentOrderZNameStatus("支付失败");
                    break;
                case 0:
                    list.get(i).setPaymentOrderZNameStatus("申请中");
                    break;
                case 1:
                    list.get(i).setPaymentOrderZNameStatus("待支付");
                    break;
                case 2:
                    list.get(i).setPaymentOrderZNameStatus("已支付");
                    break;
                case 3:
                    list.get(i).setPaymentOrderZNameStatus("已确认收款");
                    break;
                case 4:
                    list.get(i).setPaymentOrderZNameStatus("支付中");
                    break;
                case 5:
                    list.get(i).setPaymentOrderZNameStatus("已驳回");
                    break;
                case 6:
                    list.get(i).setPaymentOrderZNameStatus("已完成");
                    break;
                default:
                    return ReturnJson.error("支付状态有误");
            }
        }
        infoPoIPage.setRecords(list);
        return ReturnJson.success(infoPoIPage);
    }

    @Override
    public ReturnJson batchExportPayInfo(@NotNull String paymentOrderIds, HttpServletResponse response) throws CommonException {
        List<RegulatorTaxPayInfoPo> voList = taxDao.getPayInfoByIds(Arrays.asList(paymentOrderIds.split(",")));
        for (int i = 0; i < voList.size(); i++) {
            switch (voList.get(i).getPaymentOrderStatus()) {
                case -1:
                    voList.get(i).setPaymentOrderZNameStatus("支付失败");
                    break;
                case 0:
                    voList.get(i).setPaymentOrderZNameStatus("申请中");
                    break;
                case 1:
                    voList.get(i).setPaymentOrderZNameStatus("待支付");
                    break;
                case 2:
                    voList.get(i).setPaymentOrderZNameStatus("已支付");
                    break;
                case 3:
                    voList.get(i).setPaymentOrderZNameStatus("已确认收款");
                    break;
                case 4:
                    voList.get(i).setPaymentOrderZNameStatus("支付中");
                    break;
                case 5:
                    voList.get(i).setPaymentOrderZNameStatus("已驳回");
                    break;
                case 6:
                    voList.get(i).setPaymentOrderZNameStatus("已完成");
                    break;
                default:
                    throw new CommonException(300, "支付状态有误！");
            }
        }
        if (!VerificationCheck.listIsNull(voList)) {
            try {
                ExcelUtils.exportExcel(voList, "支付订单信息", "支付订单信息", RegulatorTaxPayInfoPo.class, "RegulatorTaxPayInfoPo", true, response);
                return ReturnJson.success("支付订单导出成功！");
            } catch (IOException e) {
                log.error(e.toString() + ":" + e.getMessage());
                throw new CommonException(300, "创客导出失败！");
            }
        }
        throw new CommonException(300, "创客导出失败！");
    }

    @Override
    public ReturnJson getPaymentInventoryInfo(String paymentOrderId, Integer page, Integer pageSize) {
        Page<PaymentInventory> paymentInventoryPage = new Page<>(page, pageSize);
        paymentInventoryPage = paymentInventoryDao.selectPage(paymentInventoryPage, new QueryWrapper<PaymentInventory>()
                .eq("payment_order_id", paymentOrderId));
        return ReturnJson.success(paymentInventoryPage);
    }

    @Override
    public ReturnJson getPaymentOrderInfo(String paymentOrderId, Integer type) {
        PaymentOrderInfoPO paymentOrderInfoPO;
        if (type == 0) {
            paymentOrderInfoPO = paymentOrderDao.selectPaymentOrderInfo(paymentOrderId);
        } else {
            paymentOrderInfoPO = paymentOrderManyDao.selectPaymentOrderInfo(paymentOrderId);
        }
        return ReturnJson.success(paymentOrderInfoPO);
    }


}
