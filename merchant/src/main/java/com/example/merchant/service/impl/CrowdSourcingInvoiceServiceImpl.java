package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.example.common.util.DateUtil;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.CrowdSourcingInvoiceService;
import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.CrowdSourcingApplicationDao;
import com.example.mybatis.mapper.CrowdSourcingInvoiceDao;
import com.example.mybatis.mapper.InvoiceLadderPriceDao;
import com.example.mybatis.vo.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrowdSourcingInvoiceServiceImpl implements CrowdSourcingInvoiceService {

    @Resource
    private CrowdSourcingInvoiceDao crowdSourcingInvoiceDao;
    @Autowired
    private InvoiceLadderPriceDao invoiceLadderPriceDao;
    @Autowired
    private CrowdSourcingApplicationDao crowdSourcingApplicationDao;

    /**
     * 众包开票申请
     *
     * @param applicationCrowdSourcing
     * @return
     */
    @Override
    public ReturnJson addCrowdSourcingInvoice(ApplicationCrowdSourcing applicationCrowdSourcing) {
        ReturnJson returnJson = new ReturnJson("添加错误", 300);
        IdentifierGenerator identifierGenerator = new DefaultIdentifierGenerator();
        applicationCrowdSourcing.setId(identifierGenerator.nextId(new Object()).toString());
        int num = crowdSourcingInvoiceDao.addCrowdSourcingInvoice(applicationCrowdSourcing);
        if (num > 0) {
            returnJson = new ReturnJson("添加成功", 200);
        }
        return returnJson;
    }

    /**
     * 众包发票详情信息
     *
     * @param tobeinvoicedDto
     * @return
     */
    @Override
    public ReturnJson getCrowdSourcingInfo(TobeinvoicedDto tobeinvoicedDto) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        RowBounds rowBounds = new RowBounds((tobeinvoicedDto.getPageNo() - 1) * 9, 9);
        List<CrowdSourcingInfoVo> vos = crowdSourcingInvoiceDao.getCrowdSourcingInfo(tobeinvoicedDto, rowBounds);
        if (vos != null) {
            returnJson = new ReturnJson("查询成功", vos, 200);
        }
        return returnJson;
    }

    /**
     * 查询单条众包信息
     *
     * @param csiId
     * @return
     */
    @Override
    public ReturnJson getInvoiceById(String csiId) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        InvoiceInformationVo vo = crowdSourcingInvoiceDao.getInvoiceById(csiId);
        if (vo != null) {
            returnJson = new ReturnJson("查询成功", vo, 200);
        }
        return returnJson;
    }

    /**
     * 平台端查询众包待开票
     *
     * @param tobeinvoicedDto
     * @return
     */
    @Override
    public ReturnJson getTobeCrowdSourcingInvoice(TobeinvoicedDto tobeinvoicedDto) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        RowBounds rowBounds = new RowBounds((tobeinvoicedDto.getPageNo() - 1) * 9, 9);
        List<CrowdSourcingInvoiceVo> list = crowdSourcingInvoiceDao.getCrowdSourcingInvoicePass(tobeinvoicedDto, rowBounds);

        if (list != null) {
            returnJson = new ReturnJson("查询成功", list, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getPaymentOrderMany(String payId) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        PaymentOrderManyVo vo = crowdSourcingInvoiceDao.getPaymentOrderManyPass(payId);
        if (vo != null) {
            returnJson = new ReturnJson("查询成功", vo, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getPaymentInventoryPass(String payId) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        BigDecimal totalTaxPrice = new BigDecimal("0.00");
        DecimalFormat df = new DecimalFormat("0.00");
        Map<String, Object> map = new HashMap();
        List<InvoiceDetailsVo> list = crowdSourcingInvoiceDao.getPaymentInventoryPass(payId);
        for (InvoiceDetailsVo vo : list) {
            totalTaxPrice = totalTaxPrice.add(vo.getTaskMoney());
            PaymentInventory paymentInventory = new PaymentInventory();
            paymentInventory.setId(vo.getId());
            List<InvoiceLadderPrice> invoiceLadderPrice = invoiceLadderPriceDao.selectList(new QueryWrapper<InvoiceLadderPrice>().eq("tax_id", vo.getTaxId()));
            if (invoiceLadderPrice != null) {
                for (InvoiceLadderPrice price : invoiceLadderPrice) {
                    if ((vo.getTaskMoney().compareTo(price.getStartMoney()) > -1) && (vo.getTaskMoney().compareTo(price.getEndMoney()) == -1)) {
                        vo.setTaxRate(price.getRate());//纳税率
                        BigDecimal bigDecimal = vo.getTaxRate().multiply(vo.getTaskMoney());
                        vo.setPersonalServiceFee(bigDecimal.setScale(2, RoundingMode.HALF_UP));//个人服务费
                        vo.setTaxAmount((vo.getTaskMoney().subtract(vo.getPersonalServiceFee())).divide((vo.getTaxRate().add(new BigDecimal("1.00"))).multiply(vo.getTaxRate()), 2, BigDecimal.ROUND_HALF_UP));//纳税金额
                    }
                }
            }
            map.put("list", list);
            map.put("税价合计", totalTaxPrice);
            returnJson = new ReturnJson("操作成功", map, 200);
        }
        return returnJson;
    }

    /**
     * 根据商户id查询购买方信息
     *
     * @param id
     * @return
     */
    @Override
    public ReturnJson getBuyer(String id) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        BuyerVo buyerVo = crowdSourcingInvoiceDao.getBuyer(id);
        if (buyerVo != null) {
            returnJson = new ReturnJson("操作成功", buyerVo, 200);
        }
        return returnJson;
    }

    /**
     * 众包申请开票信息
     *
     * @param applicationId
     * @return
     */
    @Override
    public ReturnJson getApplicationInfo(String applicationId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        CrowdSourcingApplication crowdSourcingApplication = crowdSourcingApplicationDao.selectById(applicationId);
        if (crowdSourcingApplication != null) {
            returnJson = new ReturnJson("操作成功", crowdSourcingApplication, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson saveCrowdSourcingInvoice(CrowdSourcingInvoice crowdSourcingInvoice) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        DateTimeFormatter dfd = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");//时间转换
        if (crowdSourcingInvoice.getInvoicePrintDate()==null){
            //开票时间给系统默认时间
            crowdSourcingInvoice.setInvoicePrintDate(LocalDateTime.parse(DateUtil.getTime(), dfd));
        }
        String invoiceCode = crowdSourcingInvoiceDao.getCrowdInvoiceCode();
        int code = Integer.valueOf(invoiceCode.substring(2)) + 1;
        String codes=String.valueOf(code);
        if (codes.length()<4){
            if (codes.length()==1){
                codes="000"+code;
            }else if (codes.length()==2){
                codes="00"+code;
            }else{
                codes="0"+code;
            }
        }
        crowdSourcingInvoice.setInvoiceCode("FP" + codes);
        crowdSourcingInvoice.setCreateDate(LocalDateTime.parse(DateUtil.getTime(), dfd));//创建时间
        int num=crowdSourcingInvoiceDao.insert(crowdSourcingInvoice);
        if (num >0){
            returnJson = new ReturnJson("操作成功", 200);
        }
        return returnJson;
    }
}
