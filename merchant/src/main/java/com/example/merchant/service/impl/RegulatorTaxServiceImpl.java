package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ExcelUtils;
import com.example.common.util.ReturnJson;
import com.example.common.util.VerificationCheck;
import com.example.merchant.dto.regulator.PayInfoDto;
import com.example.merchant.vo.regulator.RegulatorWorkerPaymentInfoVO;
import com.example.mybatis.dto.RegulatorTaxDto;
import com.example.mybatis.entity.PaymentInventory;
import com.example.mybatis.entity.RegulatorTax;
import com.example.mybatis.entity.Tax;
import com.example.mybatis.mapper.PaymentInventoryDao;
import com.example.mybatis.mapper.RegulatorTaxDao;
import com.example.merchant.service.RegulatorTaxService;
import com.example.mybatis.mapper.TaxDao;
import com.example.mybatis.po.RegulatorTaxPayInfoPo;
import com.example.mybatis.vo.TaxVo;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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


    /**
     * 监管服务商上方的四个数据
     *
     * @param regulatorId
     * @return
     */
    @Override
    public ReturnJson homeFourData(String regulatorId) {
        Map<String, Object> map = new HashMap<>();
        int serviceProvidernum = regulatorTaxDao.selectCount(new QueryWrapper<RegulatorTax>().eq("regulator_id", regulatorId));//监管区服务商数量
        map.put("监管区服务商数量", serviceProvidernum);

        return null;
    }

    /**
     * 查询服务商列表
     *
     * @param regulatorTaxDto
     * @return
     */
    @Override
    public ReturnJson listTax(RegulatorTaxDto regulatorTaxDto) {
        Page page = new Page(regulatorTaxDto.getPage(), regulatorTaxDto.getPageSize());
        IPage<TaxVo> taxIPage = regulatorTaxDao.selServiceProviders(page, regulatorTaxDto);
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
        return ReturnJson.success(tax);
    }

    /**
     * 批量导出服务商信息
     *
     * @param taxIds 服务商id
     * @return
     */
    @Override
    public ReturnJson batchExportTax(String taxIds, HttpServletResponse response) {
        List list=Arrays.asList(taxIds.split(","));
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
        Page page = new Page(payInfoDto.getPage(), payInfoDto.getPageSize());
        IPage<RegulatorTaxPayInfoPo> infoPoIPage = taxDao.selectPayInfo(page, payInfoDto.getTaxId(), payInfoDto.getCompanySName(), payInfoDto.getStartDate(), payInfoDto.getEndDate());
        List<RegulatorTaxPayInfoPo> list= infoPoIPage.getRecords();
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
     * @param paymentOrderId
     * @return
     */
    @Override
    public ReturnJson getPaymentInventoryInfo(String paymentOrderId,Integer page,Integer pageSize) {
        Page<PaymentInventory> paymentInventoryPage = new Page<>(page, pageSize);
        paymentInventoryPage = paymentInventoryDao.selectPage(paymentInventoryPage, new QueryWrapper<PaymentInventory>().eq("payment_order_id", paymentOrderId));
        return ReturnJson.success(paymentInventoryPage);
    }



}
