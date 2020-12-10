package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.DateUtil;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.MakerTotalInvoiceDto;
import com.example.merchant.service.MakerTotalInvoiceService;
import com.example.merchant.vo.merchant.InvoiceCatalogVo;
import com.example.merchant.vo.platform.MakerTotalInvoiceInfoVo;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.vo.BuyerVo;
import com.example.mybatis.vo.PaymentOrderVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
public class MakerTotalInvoiceServiceImpl extends ServiceImpl<MakerTotalInvoiceDao, MakerTotalInvoice> implements MakerTotalInvoiceService {

    @Resource
    private MakerTotalInvoiceDao makerTotalInvoiceDao;
    @Resource
    private InvoiceListDao invoiceListDao;
    @Resource
    private InvoiceDao invoiceDao;
    @Resource
    private PaymentOrderDao paymentOrderDao;
    @Resource
    private TaxDao taxDao;
    @Resource
    private InvoiceCatalogDao invoiceCatalogDao;
    @Resource
    private TaxPackageDao taxPackageDao;
    @Resource
    private ManagersDao managersDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson saveOrUpdateMakerTotalInvoice(MakerTotalInvoiceDto makerTotalInvoiceDto, String managerId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 200);
        DateTimeFormatter dfd = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");//时间转换
        MakerTotalInvoice makerTotalInvoice = new MakerTotalInvoice();
        makerTotalInvoice.setId(makerTotalInvoiceDto.getId());
        makerTotalInvoice.setInvoiceTypeNo(makerTotalInvoiceDto.getInvoiceTypeNo());
        makerTotalInvoice.setInvoiceSerialNo(makerTotalInvoiceDto.getInvoiceSerialNo());
        makerTotalInvoice.setInvoiceDate(LocalDateTime.parse(DateUtil.getTime(), dfd));
        makerTotalInvoice.setInvoiceCategory(makerTotalInvoiceDto.getInvoiceCategory());
        makerTotalInvoice.setTotalAmount(makerTotalInvoiceDto.getTotalAmount());
        String[] taxAmounts = makerTotalInvoiceDto.getTaxAmount().split(",");
        BigDecimal taxAmount = new BigDecimal("0.00");
        for (int i = 0; i < taxAmounts.length; i++) {
            taxAmount.add(new BigDecimal(taxAmounts[i]));
        }
        makerTotalInvoice.setTaxAmount(taxAmount);
        makerTotalInvoice.setInvoicePerson(managersDao.selectById(managerId).getRealName());
        makerTotalInvoice.setSaleCompany(makerTotalInvoiceDto.getSaleCompany());
        makerTotalInvoice.setMakerInvoiceDesc(makerTotalInvoiceDto.getMakerInvoiceDesc());
        makerTotalInvoice.setMakerInvoiceUrl(makerTotalInvoiceDto.getMakerInvoiceUrl());
        makerTotalInvoice.setMakerTaxUrl(makerTotalInvoiceDto.getMakerTaxUrl());
        makerTotalInvoice.setMakerVoiceUploadDateTime(LocalDateTime.now());
        if (makerTotalInvoice.getId() == null) {
            makerTotalInvoice.setCreateDate(LocalDateTime.parse(DateUtil.getTime(), dfd));
            int num = makerTotalInvoiceDao.insert(makerTotalInvoice);
            if (num > 0) {
                String[] paymentOrderId = makerTotalInvoiceDto.getPaymentOrderId().split(",");
                for (int i = 0; i < paymentOrderId.length; i++) {
                    PaymentOrder paymentOrder = new PaymentOrder();
                    paymentOrder.setId(makerTotalInvoiceDto.getInvoiceId());
                    paymentOrder.setIsSubpackage(1);
                    paymentOrder.setUpdateDate(LocalDateTime.parse(DateUtil.getTime(), dfd));
                    paymentOrderDao.updateById(paymentOrder);
                }
                String[] invoiceId = makerTotalInvoiceDto.getInvoiceId().split(",");
                for (int i = 0; i < invoiceId.length; i++) {
                    InvoiceList invoiceList = new InvoiceList();
                    invoiceList.setInvoiceId(invoiceId[i]);
                    invoiceList.setMakerTotalInvoiceId(makerTotalInvoice.getId());
                    int num2 = invoiceListDao.insert(invoiceList);
                    if (num2 > 0) {
                        return ReturnJson.success("操作成功");
                    }
                }
            }
        }
        if (makerTotalInvoice.getId() != null) {
            makerTotalInvoice.setCreateDate(LocalDateTime.parse(DateUtil.getTime(), dfd));
            int num = makerTotalInvoiceDao.updateById(makerTotalInvoice);
            if (num > 0) {
                return ReturnJson.success("操作成功");
            }
        }

        return returnJson;
    }

    @Override
    public ReturnJson queryMakerTotalInvoiceInfo(String invoiceIds) {
        MakerTotalInvoiceInfoVo makerTotalInvoiceInfoVo = new MakerTotalInvoiceInfoVo();
        List<String> invoiceId = Arrays.asList(invoiceIds.split(","));
        for (int i = 0; i < invoiceId.size(); i++) {
            Invoice invoice = invoiceDao.selectById(invoiceId.get(i));
            if (invoice == null) {
                return ReturnJson.error("不存在此发票");
            }
            InvoiceCatalog invoiceCatalog = invoiceCatalogDao.selectById(invoice.getInvoiceCatalog());
            if (invoiceCatalog != null) {
                InvoiceCatalogVo invoiceCatalogVo = new InvoiceCatalogVo();
                BeanUtils.copyProperties(invoiceCatalog, invoiceCatalogVo);
                makerTotalInvoiceInfoVo.setInvoiceCatalogVo(invoiceCatalogVo);
            }
        }
        List<PaymentOrderVo> paymentOrderVoList = paymentOrderDao.queryPaymentOrderInfoByIds(invoiceId);
        if (paymentOrderVoList.size() == 0) {
            return ReturnJson.error("支付信息有误！");
        }
        makerTotalInvoiceInfoVo.setPaymentOrderVoList(paymentOrderVoList);
        PaymentOrder paymentOrder = paymentOrderDao.selectById(paymentOrderVoList.get(0).getId());
        BuyerVo buyerVo = new BuyerVo();
        Tax tax = taxDao.selectById(paymentOrder.getTaxId());
        buyerVo.setCompanyAddress(tax.getTaxAddress());
        buyerVo.setCompanyName(tax.getTaxSName());
        buyerVo.setCreditCode(tax.getCreditCode());
        buyerVo.setTelephones(tax.getLinkMobile());
        TaxPackage taxPackage = taxPackageDao.selectOne(new QueryWrapper<TaxPackage>().eq("tax_id", tax.getId()).eq("package_status", 0));
        buyerVo.setBankCode(taxPackage.getBankCode());
        buyerVo.setBankName(taxPackage.getBankName());
        makerTotalInvoiceInfoVo.setBuyerVo(buyerVo);
        return ReturnJson.success(makerTotalInvoiceInfoVo);
    }
}
