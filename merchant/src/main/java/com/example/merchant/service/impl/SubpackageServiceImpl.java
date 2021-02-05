package com.example.merchant.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.SubpackageService;
import com.example.merchant.vo.merchant.InvoiceCatalogVO;
import com.example.merchant.vo.merchant.InvoiceInfoVO;
import com.example.merchant.vo.merchant.QuerySubInfoVO;
import com.example.mybatis.dto.QuerySubpackageDTO;
import com.example.mybatis.entity.InvoiceCatalog;
import com.example.mybatis.entity.MakerTotalInvoice;
import com.example.mybatis.entity.Merchant;
import com.example.mybatis.mapper.*;
import com.example.mybatis.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SubpackageServiceImpl implements SubpackageService {

    @Resource
    private SubpackageDao subpackageDao;
    @Resource
    private MerchantDao merchantDao;
    @Resource
    private InvoiceCatalogDao invoiceCatalogDao;
    @Resource
    private MakerTotalInvoiceDao makerTotalInvoiceDao;

    @Override
    public ReturnJson getSummaryInfo(String id, String merchantId) {
        QuerySubInfoVO querySubInfoVo = new QuerySubInfoVO();
        List<PaymentOrderVO> paymentOrderVOList = subpackageDao.queryPaymentOrderInfo(id);
        querySubInfoVo.setPaymentOrderVos(paymentOrderVOList);
        BuyerVO buyerVo = merchantDao.getBuyerById(merchantDao.selectById(merchantId).getCompanyId());
        querySubInfoVo.setBuyerVo(buyerVo);
        MakerTotalInvoice makerTotalInvoice = makerTotalInvoiceDao.selectById(id);
        InvoiceInfoVO invoiceInfoVo = new InvoiceInfoVO();
        invoiceInfoVo.setInvoiceUrl(makerTotalInvoice.getMakerInvoiceUrl());
        invoiceInfoVo.setTaxReceiptUrl(makerTotalInvoice.getMakerTaxUrl());
        querySubInfoVo.setInvoiceInfoVo(invoiceInfoVo);
        InvoiceCatalog invoiceCatalog = invoiceCatalogDao.selectById(makerTotalInvoice.getInvoiceCategory());
        InvoiceCatalogVO invoiceCatalogVo = new InvoiceCatalogVO();
        BeanUtils.copyProperties(invoiceCatalog, invoiceCatalogVo);
        querySubInfoVo.setInvoiceCatalogVo(invoiceCatalogVo);
        querySubInfoVo.setRemarks(makerTotalInvoice.getMakerInvoiceDesc());
        return ReturnJson.success(querySubInfoVo);
    }

    @Override
    public ReturnJson getSummaryList(QuerySubpackageDTO querySubpackageDto, String merchantId) {
        Page page = new Page(querySubpackageDto.getPageNo(), querySubpackageDto.getPageSize());
        Merchant merchant = merchantDao.selectById(merchantId);
        IPage<SubpackageVO> list = subpackageDao.getSummaryList(page, querySubpackageDto, merchant.getCompanyId());
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getSummary(String invoiceId) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        SubpackageVO subpackage = subpackageDao.getSummary(invoiceId);
        if (subpackage != null) {
            returnJson = new ReturnJson("查询成功", subpackage, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getSubpackageInfoById(String invoiceId) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        SubpackageInfoVO subpackageInfo = subpackageDao.getSubpackageInfoById(invoiceId);
        if (subpackageInfo != null) {
            returnJson = new ReturnJson("查询成功", subpackageInfo, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getListByInvoiceId(String invoiceId, Integer pageNo, Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        IPage<InvoiceDetailsVO> list = subpackageDao.getListById(page, invoiceId);
        return ReturnJson.success(list);

    }
}
