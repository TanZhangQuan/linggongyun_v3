package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.DateUtil;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.MakerInvoiceDto;
import com.example.merchant.service.MakerInvoiceService;
import com.example.mybatis.entity.InvoiceLadderPrice;
import com.example.mybatis.entity.MakerInvoice;
import com.example.mybatis.entity.PaymentInventory;
import com.example.mybatis.mapper.InvoiceLadderPriceDao;
import com.example.mybatis.mapper.MakerInvoiceDao;
import com.example.mybatis.mapper.PaymentInventoryDao;
import com.example.mybatis.vo.InvoiceListVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MakerInvoiceServiceImpl extends ServiceImpl<MakerInvoiceDao, MakerInvoice> implements MakerInvoiceService {

    @Resource
    private MakerInvoiceDao makerInvoiceDao;

    @Resource
    private InvoiceLadderPriceDao invoiceLadderPriceDao;

    @Resource
    private PaymentInventoryDao paymentInventoryDao;

    /**
     * 查询所有对应发票id的支付清单并给发票明细表赋值
     *
     * @param invoiceId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson getPaymentInventory(String invoiceId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        Map<String, Object> map = new HashMap<>();
        List<InvoiceListVo> listVos = makerInvoiceDao.getInvoiceListQuery(invoiceId);
        for (InvoiceListVo vo : listVos) {
            PaymentInventory paymentInventory = new PaymentInventory();
            paymentInventory.setId(vo.getId());
            List<InvoiceLadderPrice> invoiceLadderPrice = invoiceLadderPriceDao.selectList(new QueryWrapper<InvoiceLadderPrice>().eq("tax_id", vo.getTaxId()));
            if (invoiceLadderPrice != null) {
                for (InvoiceLadderPrice price : invoiceLadderPrice) {
                    if ((vo.getTaskMoney().compareTo(price.getStartMoney()) > -1) && (vo.getTaskMoney().compareTo(price.getEndMoney()) == -1)) {
                        vo.setTaxRate(price.getRate());//纳税率
                        BigDecimal bigDecimal = vo.getTaxRate().multiply(vo.getTaskMoney());
                        vo.setPersonalServiceFee(bigDecimal.setScale(2, RoundingMode.HALF_UP));//个人服务费
                        vo.setTaxAmount((vo.getTaskMoney().subtract(vo.getPersonalServiceFee())).divide((vo.getTaxRate().add(new BigDecimal("1.00"))), 2, BigDecimal.ROUND_HALF_UP).multiply(vo.getTaxRate()));//纳税金额
                        paymentInventory.setTaxRate(vo.getTaxRate());
                        paymentInventory.setTaxAmount(vo.getTaxAmount());
                    }
                }
            }
            paymentInventoryDao.updateById(paymentInventory);//修改支付清单的信息
            map.put("voList", listVos);
            returnJson = new ReturnJson("操作成功", listVos, 200);
        }
        return returnJson;
    }

    /**
     * 创建或修改发票
     *
     * @param makerInvoiceDto
     * @return
     */
    @Override
    public ReturnJson saveMakerInvoice(MakerInvoiceDto makerInvoiceDto) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        MakerInvoice makerInvoice = new MakerInvoice();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        makerInvoice.setId(makerInvoiceDto.getId());
        makerInvoice.setPaymentInventoryId(makerInvoiceDto.getPaymentInventoryId());
        makerInvoice.setInvoiceTypeNo(makerInvoiceDto.getInvoiceTypeNo());
        makerInvoice.setInvoiceSerialNo(makerInvoiceDto.getInvoiceSerialNo());
        //开发票时间为空则给系统当前时间
        makerInvoice.setMakerVoiceGetDateTime(LocalDateTime.parse(DateUtil.getTime(), df));
        makerInvoice.setInvoiceCategory(makerInvoiceDto.getInvoiceCategory());
        makerInvoice.setTotalAmount(makerInvoiceDto.getTotalAmount());
        makerInvoice.setTaxAmount(makerInvoiceDto.getTaxAmount());
        makerInvoice.setIvoicePerson(makerInvoiceDto.getIvoicePerson());
        makerInvoice.setSaleCompany(makerInvoiceDto.getSaleCompany());
        if (makerInvoiceDto.getMakerVoiceUrl() != null) {
            makerInvoice.setMakerVoiceUrl(makerInvoiceDto.getMakerVoiceUrl());
            makerInvoice.setMakerVoiceUploadDateTime(LocalDateTime.parse(DateUtil.getTime(), df));
            makerInvoice.setCreateDate(LocalDateTime.parse(DateUtil.getTime(), df));
        }
        makerInvoice.setMakerTaxUrl(makerInvoiceDto.getMakerTaxUrl());
        int num = makerInvoiceDao.insert(makerInvoice);
        if (num > 0) {
            returnJson = new ReturnJson("操作成功", 200);
        }
        return returnJson;
    }
}
