package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.DateUtil;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.MakerTotalInvoiceDTO;
import com.example.merchant.vo.platform.MakerTotalInvoiceDetailsVO;
import com.example.merchant.vo.platform.QueryMakerTotalInvoiceDetailVO;
import com.example.merchant.service.MakerTotalInvoiceService;
import com.example.merchant.vo.merchant.InvoiceCatalogVO;
import com.example.merchant.vo.platform.MakerTotalInvoiceInfoVO;
import com.example.mybatis.dto.QueryMakerTotalInvoiceDTO;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.vo.BuyerVO;
import com.example.mybatis.vo.InvoiceListVO;
import com.example.mybatis.vo.MakerTotalInvoiceVO;
import com.example.mybatis.vo.PaymentOrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ReturnJson saveOrUpdateMakerTotalInvoice(MakerTotalInvoiceDTO makerTotalInvoiceDto, String managerId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 200);
        DateTimeFormatter dfd = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");//时间转换
        MakerTotalInvoice makerTotalInvoice = new MakerTotalInvoice();
        if (makerTotalInvoiceDto.getId() == null) {
            makerTotalInvoice.setId(makerTotalInvoiceDto.getId());
            makerTotalInvoice.setInvoiceDate(LocalDateTime.parse(DateUtil.getTime(), dfd));
            makerTotalInvoice.setInvoiceCategory(makerTotalInvoiceDto.getInvoiceCategory());
            makerTotalInvoice.setTotalAmount(makerTotalInvoiceDto.getTotalAmount());
            String[] taxAmounts = makerTotalInvoiceDto.getTaxAmount().split(",");
            BigDecimal taxAmount = new BigDecimal("0.00");
            for (int i = 0; i < taxAmounts.length; i++) {
                if (!"".equals(taxAmounts[i])) {
                    taxAmount.add(new BigDecimal(taxAmounts[i]));
                }
                taxAmount.add(new BigDecimal("0.00"));
            }
            makerTotalInvoice.setTaxAmount(taxAmount);
            makerTotalInvoice.setInvoicePerson(managersDao.selectById(managerId).getRealName());
            makerTotalInvoice.setSaleCompany(makerTotalInvoiceDto.getSaleCompany());
            makerTotalInvoice.setMakerInvoiceDesc(makerTotalInvoiceDto.getMakerInvoiceDesc());
            makerTotalInvoice.setMakerInvoiceUrl(makerTotalInvoiceDto.getMakerInvoiceUrl());
            makerTotalInvoice.setMakerTaxUrl(makerTotalInvoiceDto.getMakerTaxUrl());
            makerTotalInvoice.setMakerVoiceUploadDateTime(LocalDateTime.now());
            makerTotalInvoice.setCreateDate(LocalDateTime.parse(DateUtil.getTime(), dfd));
            int num = makerTotalInvoiceDao.insert(makerTotalInvoice);
            if (num > 0) {
                String[] paymentOrderId = makerTotalInvoiceDto.getPaymentOrderId().split(",");
                for (int i = 0; i < paymentOrderId.length; i++) {
                    PaymentOrder paymentOrder = paymentOrderDao.selectById(paymentOrderId[i]);
                    paymentOrder.setIsSubpackage(1);
                    paymentOrder.setUpdateDate(LocalDateTime.parse(DateUtil.getTime(), dfd));
                    paymentOrderDao.updateById(paymentOrder);
                }
                String[] invoiceId = makerTotalInvoiceDto.getInvoiceId().split(",");
                for (int i = 0; i < invoiceId.length; i++) {
                    InvoiceList invoiceList = new InvoiceList();
                    invoiceList.setInvoiceId(invoiceId[i]);
                    invoiceList.setMakerTotalInvoiceId(makerTotalInvoice.getId());
                    invoiceListDao.insert(invoiceList);
                    Invoice invoice = invoiceDao.selectById(invoiceId[i]);
                    invoice.setIsNotTotal(0);
                    invoiceDao.updateById(invoice);
                }
                return ReturnJson.success("操作成功");
            }
        }
        if (makerTotalInvoiceDto.getId() != null) {
            BeanUtils.copyProperties(makerTotalInvoiceDto, makerTotalInvoice);
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
        MakerTotalInvoiceInfoVO makerTotalInvoiceInfoVo = new MakerTotalInvoiceInfoVO();
        List<String> invoiceId = Arrays.asList(invoiceIds.split(","));
        for (int i = 0; i < invoiceId.size(); i++) {
            Invoice invoice = invoiceDao.selectById(invoiceId.get(i));
            if (invoice == null) {
                return ReturnJson.error("不存在此发票");
            }
            InvoiceCatalog invoiceCatalog = invoiceCatalogDao.selectById(invoice.getInvoiceCatalog());
            if (invoiceCatalog != null) {
                InvoiceCatalogVO invoiceCatalogVo = new InvoiceCatalogVO();
                BeanUtils.copyProperties(invoiceCatalog, invoiceCatalogVo);
                makerTotalInvoiceInfoVo.setInvoiceCatalogVo(invoiceCatalogVo);
            }
        }
        List<PaymentOrderVO> paymentOrderVOList = paymentOrderDao.queryPaymentOrderInfoByIds(invoiceId);
        if (paymentOrderVOList.size() == 0) {
            return ReturnJson.error("支付信息有误！");
        }
        makerTotalInvoiceInfoVo.setPaymentOrderVoList(paymentOrderVOList);
        PaymentOrder paymentOrder = paymentOrderDao.selectById(paymentOrderVOList.get(0).getId());
        BuyerVO buyerVo = new BuyerVO();
        Tax tax = taxDao.selectById(paymentOrder.getTaxId());
        buyerVo.setAddressAndTelephone(tax.getTaxAddress() + "," + tax.getLinkMobile());
        buyerVo.setCompanyName(tax.getTaxSName());
        buyerVo.setCreditCode(tax.getCreditCode());
        TaxPackage taxPackage = taxPackageDao.selectOne(new QueryWrapper<TaxPackage>().eq("tax_id", tax.getId()).eq("package_status", 0));
        buyerVo.setBankAndAccount(taxPackage.getBankName() + "," + taxPackage.getBankCode());
        makerTotalInvoiceInfoVo.setBuyerVo(buyerVo);
        return ReturnJson.success(makerTotalInvoiceInfoVo);
    }

    @Override
    public ReturnJson queryMakerTotalInvoice(QueryMakerTotalInvoiceDTO queryMakerTotalInvoiceDto) {
        Page page = new Page(queryMakerTotalInvoiceDto.getPageNo(), queryMakerTotalInvoiceDto.getPageSize());
        IPage<MakerTotalInvoiceVO> makerTotalInvoiceVoIPage = makerTotalInvoiceDao.queryMakerTotalInvoice(page, queryMakerTotalInvoiceDto);
        return ReturnJson.success(makerTotalInvoiceVoIPage);
    }

    @Override
    public ReturnJson queryMakerTotalInvoiceDetails(String invoiceId) {
        List<PaymentOrderVO> paymentOrderVOList = paymentOrderDao.queryPaymentOrderInfoById(invoiceId);
        BuyerVO queryBuyer = paymentOrderDao.queryBuyer(invoiceId);
        MakerTotalInvoice makerTotalInvoice = makerTotalInvoiceDao.selectById(invoiceId);
        QueryMakerTotalInvoiceDetailVO queryMakerTotalInvoiceDetail = new QueryMakerTotalInvoiceDetailVO();
        queryMakerTotalInvoiceDetail.setPaymentOrderVoList(paymentOrderVOList);
        queryMakerTotalInvoiceDetail.setQueryBuyer(queryBuyer);
        MakerTotalInvoiceDetailsVO makerTotalInvoiceDetails = new MakerTotalInvoiceDetailsVO();
        BeanUtils.copyProperties(makerTotalInvoice, makerTotalInvoiceDetails);
        queryMakerTotalInvoiceDetail.setMakerTotalInvoiceDetails(makerTotalInvoiceDetails);
        InvoiceCatalog invoiceCatalog = invoiceCatalogDao.selectById(makerTotalInvoice.getInvoiceCategory());
        InvoiceCatalogVO invoiceCatalogVo = new InvoiceCatalogVO();
        BeanUtils.copyProperties(invoiceCatalog, invoiceCatalogVo);
        queryMakerTotalInvoiceDetail.setInvoiceCatalogVo(invoiceCatalogVo);
        return ReturnJson.success(queryMakerTotalInvoiceDetail);
    }

    @Override
    public ReturnJson getMakerTotalInvoicePayList(String invoiceId, Integer pageNo, Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        IPage<InvoiceListVO> invoiceListVoIPage = paymentOrderDao.queryMakerPaymentInventory(page, invoiceId);
        Map<String, Object> map = new HashMap<>(0);
        BigDecimal totalTaxPrice = new BigDecimal("0.00");
        for (InvoiceListVO invoiceListVo : invoiceListVoIPage.getRecords()) {
            totalTaxPrice = totalTaxPrice.add(invoiceListVo.getRealMoney());
        }
        map.put("invoiceListVoIPage", invoiceListVoIPage.getRecords());
        map.put("totalTaxPrice", totalTaxPrice);
        return ReturnJson.success(map);
    }

}