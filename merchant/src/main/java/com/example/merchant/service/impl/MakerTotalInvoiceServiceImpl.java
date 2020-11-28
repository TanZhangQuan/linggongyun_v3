package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.DateUtil;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.MakerTotalInvoiceDto;
import com.example.merchant.service.MakerTotalInvoiceService;
import com.example.mybatis.entity.InvoiceList;
import com.example.mybatis.entity.MakerTotalInvoice;
import com.example.mybatis.entity.PaymentOrder;
import com.example.mybatis.mapper.InvoiceListDao;
import com.example.mybatis.mapper.MakerTotalInvoiceDao;
import com.example.mybatis.mapper.PaymentOrderDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MakerTotalInvoiceServiceImpl extends ServiceImpl<MakerTotalInvoiceDao, MakerTotalInvoice> implements MakerTotalInvoiceService {

    @Resource
    private MakerTotalInvoiceDao makerTotalInvoiceDao;

    @Resource
    private InvoiceListDao invoiceListDao;

    @Resource
    private PaymentOrderDao paymentOrderDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson saveOrUpdateMakerTotalInvoice(MakerTotalInvoiceDto makerTotalInvoiceDto) {
        ReturnJson returnJson = new ReturnJson("操作失败", 200);
        DateTimeFormatter dfd = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");//时间转换
        DecimalFormat dfb = new DecimalFormat("0.00");//金额转换
        MakerTotalInvoice makerTotalInvoice = new MakerTotalInvoice();
        makerTotalInvoice.setId(makerTotalInvoiceDto.getId());
        makerTotalInvoice.setInvoiceTypeNo(makerTotalInvoiceDto.getInvoiceTypeNo());
        makerTotalInvoice.setInvoiceSerialNo(makerTotalInvoiceDto.getInvoiceSerialNo());
        if (makerTotalInvoiceDto.getInvoiceDate() == null) {
            makerTotalInvoice.setInvoiceDate(LocalDateTime.parse(DateUtil.getTime(), dfd));
        }
        makerTotalInvoice.setInvoiceCategory(makerTotalInvoiceDto.getInvoiceCategory());
        makerTotalInvoice.setTotalAmount(makerTotalInvoiceDto.getTotalAmount());
        String[] taxAmounts = makerTotalInvoiceDto.getTaxAmount().split(",");
        BigDecimal taxAmount = new BigDecimal("0.00");
        for (int i = 0; i < taxAmounts.length; i++) {
            taxAmount.add(new BigDecimal(taxAmounts[i]));
        }
        makerTotalInvoice.setTaxAmount(taxAmount);
        makerTotalInvoice.setInvoicePerson(makerTotalInvoiceDto.getInvoicePerson());
        makerTotalInvoice.setSaleCompany(makerTotalInvoiceDto.getSaleCompany());
        makerTotalInvoice.setMakerInvoiceDesc(makerTotalInvoiceDto.getMakerInvoiceDesc());
        makerTotalInvoice.setMakerInvoiceUrl(makerTotalInvoiceDto.getMakerInvoiceUrl());
        makerTotalInvoice.setMakerTaxUrl(makerTotalInvoiceDto.getMakerTaxUrl());
        makerTotalInvoice.setMakerVoiceUploadDateTime(makerTotalInvoiceDto.getMakerVoiceUploadDateTime());
        if (makerTotalInvoice.getId() == null) {
            if (makerTotalInvoiceDto.getCreateTime() == null) {
                makerTotalInvoice.setCreateDate(LocalDateTime.parse(DateUtil.getTime(), dfd));
            }
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
                        returnJson = new ReturnJson("操作成功", 200);
                    }
                }
            }
        }
        if (makerTotalInvoice.getId() != null) {
            if (makerTotalInvoiceDto.getUpdateTime() == null) {
                makerTotalInvoice.setCreateDate(LocalDateTime.parse(DateUtil.getTime(), dfd));
            }
            int num = makerTotalInvoiceDao.updateById(makerTotalInvoice);
            if (num > 0) {
                returnJson = new ReturnJson("操作成功", 200);
            }
        }

        return returnJson;
    }
}
