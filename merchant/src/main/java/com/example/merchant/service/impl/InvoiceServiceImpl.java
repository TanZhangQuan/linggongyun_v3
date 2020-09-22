package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.DateUtil;
import com.example.common.util.ReturnJson;
import com.example.common.util.Tools;
import com.example.merchant.service.InvoiceApplicationService;
import com.example.merchant.service.InvoiceService;
import com.example.mybatis.dto.AddInvoiceDto;
import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.entity.Invoice;
import com.example.mybatis.entity.InvoiceLadderPrice;
import com.example.mybatis.mapper.InvoiceDao;
import com.example.mybatis.mapper.InvoiceLadderPriceDao;
import com.example.mybatis.vo.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 发票相关 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class InvoiceServiceImpl extends ServiceImpl<InvoiceDao, Invoice> implements InvoiceService {

    @Resource
    private InvoiceDao invoiceDao;
    @Resource
    private InvoiceApplicationService invoiceApplicationService;
    @Autowired
    private InvoiceLadderPriceDao invoiceLadderPriceDao;


    @Override
    public ReturnJson selectTobeinvoiced(TobeinvoicedDto tobeinvoicedDto) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        RowBounds rowBounds=new RowBounds((tobeinvoicedDto.getPageNo()-1)*9,9);
        List<TobeinvoicedVo> list = invoiceDao.selectTobeinvoiced(tobeinvoicedDto,rowBounds);
        if (list!=null&& list.size()>0){
            returnJson=new ReturnJson("查询成功",list,200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getInvoiceList(TobeinvoicedDto tobeinvoicedDto) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        RowBounds rowBounds=new RowBounds((tobeinvoicedDto.getPageNo()-1)*9,9);
        List<InvoiceVo> list = invoiceDao.getInvoiceList(tobeinvoicedDto,rowBounds);
        if (list!=null&& list.size()>0){
            returnJson=new ReturnJson("查询成功",list,200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getInvInfoById(String invId) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        InvoiceInformationVo vo = invoiceDao.getInvInfoById(invId);
        if ( vo != null ) {
            returnJson = new ReturnJson("查询成功", vo,200);
        }
        return returnJson;
    }



    @Override
    public ReturnJson getPlaInvoiceList(TobeinvoicedDto tobeinvoicedDto) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        RowBounds rowBounds = new RowBounds((tobeinvoicedDto.getPageNo() - 1) * 9, 9);
        List<InvoiceVo> list = invoiceDao.getPlaInvoiceList(tobeinvoicedDto, rowBounds);
        if (list != null) {
            returnJson = new ReturnJson("查询成功", list, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getPlaInvoiceInfo(String applicationId) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        PlaInvoiceInfoVo plaInvoiceInfoVo = invoiceDao.getPlaInvoiceInfo(applicationId);
        if (plaInvoiceInfoVo != null) {
            returnJson = new ReturnJson("查询成功", plaInvoiceInfoVo, 200);
        }
        return returnJson;
    }

    @Override
    @Transactional
    public ReturnJson saveInvoice(AddInvoiceDto addInvoiceDto) {
        ReturnJson returnJson = new ReturnJson("添加失败", 300);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Invoice invoice = new Invoice();
        invoice.setApplicationId(addInvoiceDto.getApplicationId());
        String invoiceCode = this.getInvoiceCode();
        if (invoiceCode != null) {
            int code = Integer.valueOf(invoiceCode.substring(2)) + 1;
            invoice.setInvoiceCode("FP" + String.valueOf(code));
        } else {
            invoice.setInvoiceCode("FP" + String.valueOf(0000));
        }
        if (Tools.str2Date(addInvoiceDto.getInvoicePrintDate()) == null) {
            //输入时间不正确给系统当前默认时间
            invoice.setInvoicePrintDate(LocalDateTime.parse(DateUtil.getTime(), df));
        }
        invoice.setInvoiceNumber(addInvoiceDto.getInvoiceNumber());
        invoice.setInvoiceCodeNo(addInvoiceDto.getInvoiceCodeNo());
        invoice.setInvoicePrintPerson(addInvoiceDto.getInvoicePrintPerson());
        invoice.setApplicationInvoicePerson(addInvoiceDto.getApplicationInvoicePerson());
        invoice.setInvoiceNumbers(addInvoiceDto.getInvoiceNumbers());
        invoice.setInvoiceMoney(addInvoiceDto.getInvoiceMoney());
        invoice.setInvoiceCatalog(addInvoiceDto.getInvoiceCatalog());
        invoice.setInvoiceUrl(addInvoiceDto.getInvoiceUrl());
        invoice.setTaxReceiptUrl(addInvoiceDto.getTaxReceiptUrl());
        invoice.setExpressSheetNo(addInvoiceDto.getExpressSheetNo());
        invoice.setExpressCompanyName(addInvoiceDto.getExpressCompanyName());
        invoice.setInvoiceDesc(addInvoiceDto.getInvoiceDesc());
        if (addInvoiceDto.getCreateDate() == null) {
            invoice.setCreateDate((LocalDateTime.parse(DateUtil.getTime(), df)));
        }
        int num = invoiceDao.insert(invoice);
        System.out.println(invoice.getId());
        if (num > 0) {
            int num2 = invoiceApplicationService.updateById(addInvoiceDto.getApplicationId(), 3);
            if (num2 > 0) {
                returnJson = new ReturnJson("添加成功", 200);
            }
        }
        return returnJson;
    }

    @Override
    public String getInvoiceCode() {
        return invoiceDao.getInvoiceCode();
    }

    @Override
    public ReturnJson getListInvoicequery(TobeinvoicedDto tobeinvoicedDto) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        RowBounds rowBounds = new RowBounds((tobeinvoicedDto.getPageNo() - 1) * 9, 9);
        List<InvoiceVo> list = invoiceDao.getListInvoicequery(tobeinvoicedDto, rowBounds);
        if (list != null) {
            returnJson = new ReturnJson("查询成功", list, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson updateInvoiceById(AddInvoiceDto addInvoiceDto) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Invoice invoice = new Invoice();
        invoice.setId(addInvoiceDto.getId());
        invoice.setInvoiceNumber(addInvoiceDto.getInvoiceNumber());
        invoice.setInvoiceCodeNo(addInvoiceDto.getInvoiceCodeNo());
        invoice.setInvoiceNumbers(addInvoiceDto.getInvoiceNumbers());
        invoice.setInvoiceCatalog(addInvoiceDto.getInvoiceCatalog());
        invoice.setInvoiceUrl(addInvoiceDto.getInvoiceUrl());
        invoice.setTaxReceiptUrl(addInvoiceDto.getTaxReceiptUrl());
        invoice.setExpressSheetNo(addInvoiceDto.getExpressSheetNo());
        invoice.setExpressCompanyName(addInvoiceDto.getExpressCompanyName());
        invoice.setInvoiceDesc(addInvoiceDto.getInvoiceDesc());
        if (addInvoiceDto.getUpdateDate() == null) {
            invoice.setUpdateDate((LocalDateTime.parse(DateUtil.getTime(), df)));
        }
        int num = invoiceDao.updateById(invoice);
        if (num > 0) {
            returnJson = new ReturnJson("操作成功", 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getListSubQuery(TobeinvoicedDto tobeinvoicedDto) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        RowBounds rowBounds = new RowBounds((tobeinvoicedDto.getPageNo() - 1) * 9, 9);
        List<ToSubcontractInvoiceVo> list = invoiceDao.getListSubQuery(tobeinvoicedDto, rowBounds);
        if (list != null) {
            returnJson = new ReturnJson("查询成功", list, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getInvoiceListQuery(String invoiceId,String companySNames,String platformServiceProviders) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        String[] companySName = companySNames.split(",");
        for (int i = 0; i < companySName.length; i++) {
            if (!(companySName[0]).equals(companySName[i])) {
                return new ReturnJson("商户必须为同一个", 300);
            }
        }
        String[] platformServiceProvider = platformServiceProviders.split(",");
        for (int i = 0; i < platformServiceProvider.length; i++) {
            if (!(platformServiceProvider[0]).equals(platformServiceProvider[i])) {
                return new ReturnJson("服务商必须为同一个", 300);
            }
        }
        String[] id = invoiceId.split(",");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            list.add(id[i]);
        }
        List<InvoiceListVo> voList = invoiceDao.getInvoiceListQuery(list);
        for (InvoiceListVo vo: voList){
            List<InvoiceLadderPrice> invoiceLadderPrice = invoiceLadderPriceDao.selectList(new QueryWrapper<InvoiceLadderPrice>().eq("tax_id",vo.getTaxId()));
            
        }
        return returnJson;
    }
}
