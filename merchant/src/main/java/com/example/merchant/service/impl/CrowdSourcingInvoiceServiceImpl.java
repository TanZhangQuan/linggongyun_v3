package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.DateUtil;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.CrowdSourcingInvoiceService;
import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.CrowdSourcingApplicationDao;
import com.example.mybatis.mapper.CrowdSourcingInvoiceDao;
import com.example.mybatis.mapper.InvoiceLadderPriceDao;
import com.example.mybatis.vo.*;
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

/**
 * @author Jwei
 */
@Service
public class CrowdSourcingInvoiceServiceImpl extends ServiceImpl<CrowdSourcingInvoiceDao, CrowdSourcingInvoice> implements CrowdSourcingInvoiceService {

    @Resource
    private CrowdSourcingInvoiceDao crowdSourcingInvoiceDao;
    @Resource
    private InvoiceLadderPriceDao invoiceLadderPriceDao;
    @Resource
    private CrowdSourcingApplicationDao crowdSourcingApplicationDao;

    /**
     * 众包开票申请
     *
     * @param applicationCrowdSourcing
     * @return
     */
    @Override
    public ReturnJson addCrowdSourcingInvoice(ApplicationCrowdSourcing applicationCrowdSourcing) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        IdentifierGenerator identifierGenerator = new DefaultIdentifierGenerator();
        applicationCrowdSourcing.setId(identifierGenerator.nextId(new Object()).toString());
        int num = crowdSourcingInvoiceDao.addCrowdSourcingInvoice(applicationCrowdSourcing);
        if (num > 0) {
            returnJson = new ReturnJson("操作成功", 200);
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
        Page page = new Page(tobeinvoicedDto.getPageNo(), tobeinvoicedDto.getPageSize());
        IPage<CrowdSourcingInfoVo> vos = crowdSourcingInvoiceDao.getCrowdSourcingInfo(page, tobeinvoicedDto);
        return ReturnJson.success(vos);
    }

    /**
     * 查询单条众包信息
     *
     * @param csiId
     * @return
     */
    @Override
    public ReturnJson getInvoiceById(String csiId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        InvoiceInformationVo vo = crowdSourcingInvoiceDao.getInvoiceById(csiId);
        if (vo != null) {
            returnJson = new ReturnJson("操作成功", vo, 200);
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
        Page page = new Page(tobeinvoicedDto.getPageNo(), tobeinvoicedDto.getPageSize());
        IPage<CrowdSourcingInvoiceVo> list = crowdSourcingInvoiceDao.getCrowdSourcingInvoicePass(page, tobeinvoicedDto);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getPaymentOrderMany(String payId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        PaymentOrderManyVo vo = crowdSourcingInvoiceDao.getPaymentOrderManyPass(payId);
        if (vo != null) {
            returnJson = new ReturnJson("操作成功", vo, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getPaymentInventoryPass(String payId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
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
                        //纳税率
                        vo.setTaxRate(price.getRate());
                        BigDecimal bigDecimal = vo.getTaxRate().multiply(vo.getTaskMoney());
                        //个人服务费
                        vo.setPersonalServiceFee(bigDecimal.setScale(2, RoundingMode.HALF_UP));
                        //纳税金额
                        vo.setTaxAmount((vo.getTaskMoney().subtract(vo.getPersonalServiceFee())).divide((vo.getTaxRate().add(new BigDecimal("1.00"))).multiply(vo.getTaxRate()), 2, BigDecimal.ROUND_HALF_UP));
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
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        BuyerVo buyerVo = crowdSourcingInvoiceDao.getBuyer(id);
        if (buyerVo != null) {
            returnJson = new ReturnJson("操作成功", buyerVo, 200);
        }
        return returnJson;
    }

    /**
     * 众包申请开票信息查询
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

    /**
     * 众包开票
     *
     * @param crowdSourcingInvoice
     * @return
     */
    @Override
    public ReturnJson saveCrowdSourcingInvoice(CrowdSourcingInvoice crowdSourcingInvoice) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        //时间转换
        DateTimeFormatter dfd = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (crowdSourcingInvoice.getApplicationId() == null) {
            returnJson = new ReturnJson("商户未申请，支付id不能为空", 300);
        }
        if (crowdSourcingInvoice.getInvoicePrintDate() == null) {
            //开票时间给系统默认时间
            crowdSourcingInvoice.setInvoicePrintDate(LocalDateTime.parse(DateUtil.getTime(), dfd));
        }
        String invoiceCode = crowdSourcingInvoiceDao.getCrowdInvoiceCode();
        int code = Integer.valueOf(invoiceCode.substring(2)) + 1;
        String codes = String.valueOf(code);
        if (codes.length() < 4) {
            if (codes.length() == 1) {
                codes = "000" + code;
            } else if (codes.length() == 2) {
                codes = "00" + code;
            } else {
                codes = "0" + code;
            }
        }
        crowdSourcingInvoice.setInvoiceCode("FP" + codes);
        //创建时间
        crowdSourcingInvoice.setCreateDate(LocalDateTime.parse(DateUtil.getTime(), dfd));
        int num = crowdSourcingInvoiceDao.insert(crowdSourcingInvoice);
        if (crowdSourcingInvoice.getApplicationId() != null) {
            CrowdSourcingApplication crowdSourcingApplication = new CrowdSourcingApplication();
            crowdSourcingApplication.setId(crowdSourcingInvoice.getApplicationId());
            crowdSourcingApplication.setApplicationState(3);
            crowdSourcingApplicationDao.updateById(crowdSourcingApplication);
        }
        if (num > 0) {
            returnJson = new ReturnJson("操作成功", 200);
        }
        return returnJson;
    }

    /**
     * 平台端查询众包已开票
     *
     * @param tobeinvoicedDto
     * @return
     */
    @Override
    public ReturnJson getCrowdSourcingInfoPass(TobeinvoicedDto tobeinvoicedDto) {
        Page page = new Page(tobeinvoicedDto.getPageNo(), tobeinvoicedDto.getPageSize());
        IPage<CrowdSourcingInfoVo> vos = crowdSourcingInvoiceDao.getCrowdSourcingInfoPass(page, tobeinvoicedDto);
        return ReturnJson.success(vos);
    }

    @Override
    public ReturnJson getPaymentInventoryInfoPass(String invoiceId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        List<InvoiceDetailsVo> list = crowdSourcingInvoiceDao.getPaymentInventoryInfoPass(invoiceId);
        if (list != null) {
            returnJson = new ReturnJson("操作成功", list, 300);
            returnJson.setPageSize(9);
        }
        return returnJson;
    }
}
