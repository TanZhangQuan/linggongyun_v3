package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ExpressLogisticsInfo;
import com.example.common.util.KdniaoTrackQueryAPI;
import com.example.common.util.ReturnJson;
import com.example.common.util.VerificationCheck;
import com.example.merchant.dto.merchant.AddPaymentOrderManyDto;
import com.example.merchant.dto.merchant.PaymentOrderMerchantDto;
import com.example.merchant.dto.platform.PaymentOrderDto;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.PaymentInventoryService;
import com.example.merchant.service.PaymentOrderManyService;
import com.example.merchant.util.AcquireID;
import com.example.merchant.util.JwtUtils;
import com.example.merchant.vo.ExpressInfoVO;
import com.example.merchant.vo.PaymentOrderInfoVO;
import com.example.mybatis.dto.TobeinvoicedDto;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.po.InvoiceInfoPO;
import com.example.mybatis.po.PaymentOrderInfoPO;
import com.example.mybatis.vo.CrowdSourcingInvoiceVo;
import com.example.mybatis.vo.InvoiceDetailsVo;
import com.example.mybatis.vo.PaymentOrderManyVo;
import io.jsonwebtoken.Claims;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 众包支付单操作
 * 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-08
 */
@Service
public class PaymentOrderManyServiceImpl extends ServiceImpl<PaymentOrderManyDao, PaymentOrderMany> implements PaymentOrderManyService {

    @Value("${TOKEN}")
    private String TOKEN;

    @Resource
    private PaymentOrderManyDao paymentOrderManyDao;

    @Resource
    private PaymentInventoryDao paymentInventoryDao;

    @Resource
    private CompanyTaxDao companyTaxDao;

    @Resource
    private TaxDao taxDao;

    @Resource
    private PaymentInventoryService paymentInventoryService;

    @Resource
    private CompanyLadderServiceDao companyLadderServiceDao;

    @Resource
    private AcquireID acquireID;

    @Resource
    private JwtUtils jwtUtils;

    /**
     * 众包今天的支付金额
     *
     * @param request
     * @return
     */
    @Override
    public ReturnJson getDay(HttpServletRequest request) {
        String token = request.getHeader(TOKEN);
        Claims claim = jwtUtils.getClaimByToken(token);
        String merchantId = null;
        if (claim != null) {
            merchantId = claim.getSubject();
        }
        List<PaymentOrderMany> list = paymentOrderManyDao.selectDay(merchantId);
        return ReturnJson.success(list);
    }

    /**
     * 众包本周的支付金额
     *
     * @param request
     * @return
     */
    @Override
    public ReturnJson getWeek(HttpServletRequest request) {
        String token = request.getHeader(TOKEN);
        Claims claim = jwtUtils.getClaimByToken(token);
        String merchantId = null;
        if (claim != null) {
            merchantId = claim.getSubject();
        }
        List<PaymentOrderMany> list = paymentOrderManyDao.selectWeek(merchantId);
        return ReturnJson.success(list);
    }


    /**
     * 众包本月的支付金额
     *
     * @param request
     * @return
     */
    @Override
    public ReturnJson getMonth(HttpServletRequest request) {
        String token = request.getHeader(TOKEN);
        Claims claim = jwtUtils.getClaimByToken(token);
        String merchantId = null;
        if (claim != null) {
            merchantId = claim.getSubject();
        }
        List<PaymentOrderMany> list = paymentOrderManyDao.selectMonth(merchantId);
        return ReturnJson.success(list);
    }

    /**
     * 众包今年的支付金额
     *
     * @param request
     * @return
     */
    @Override
    public ReturnJson getYear(HttpServletRequest request) {
        String token = request.getHeader(TOKEN);
        Claims claim = jwtUtils.getClaimByToken(token);
        String merchantId = null;
        if (claim != null) {
            merchantId = claim.getSubject();
        }
        List<PaymentOrderMany> list = paymentOrderManyDao.selectYear(merchantId);
        return ReturnJson.success(list);
    }

    /**
     * 根据商户id查众包待开票数据
     *
     * @param tobeinvoicedDto
     * @return
     */
    @Override
    public ReturnJson getListCSIByID(TobeinvoicedDto tobeinvoicedDto) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        RowBounds rowBounds = new RowBounds((tobeinvoicedDto.getPageNo() - 1) * tobeinvoicedDto.getPageSize(), tobeinvoicedDto.getPageSize());
        List<CrowdSourcingInvoiceVo> list = paymentOrderManyDao.getListCSIByID(tobeinvoicedDto, rowBounds);
        if (list != null) {
            returnJson = new ReturnJson("查询成功", list, 200);
        }
        return returnJson;
    }

    /**
     * 根据支付id查询众包支付信息
     *
     * @param id
     * @return
     */
    @Override
    public ReturnJson getPayOrderManyById(String id) {
        PaymentOrderManyVo paymentOrderManyVo = paymentOrderManyDao.getPayOrderManyById(id);
        return ReturnJson.success(paymentOrderManyVo);
    }

    /**
     * 根据支付id查找发票信息详情
     *
     * @param id
     * @param pageNo
     * @return
     */
    @Override
    public ReturnJson getInvoiceDetailsByPayId(String id, Integer pageNo) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        RowBounds rowBounds = new RowBounds((pageNo - 1) * 10, 10);
        List<InvoiceDetailsVo> list = paymentOrderManyDao.getInvoiceDetailsByPayId(id, rowBounds);
        if (list != null) {
            returnJson = new ReturnJson("查询成功", list, 200);
        }
        return returnJson;
    }

    /**
     * 查询众包的支付订单
     *
     * @param paymentOrderMerchantDto
     * @return
     */
    @Override
    public ReturnJson getPaymentOrderMany(PaymentOrderMerchantDto paymentOrderMerchantDto) {
        String companyId = paymentOrderMerchantDto.getCompanyId();
        String paymentOrderId = paymentOrderMerchantDto.getPaymentOrderId();
        String taxId = paymentOrderMerchantDto.getTaxId();
        Integer pageSize = paymentOrderMerchantDto.getPageSize();
        Integer page = paymentOrderMerchantDto.getPage();
        String beginDate = paymentOrderMerchantDto.getBeginDate();
        String endDate = paymentOrderMerchantDto.getEndDate();
        Page<PaymentOrderMany> paymentOrderManyPage = new Page<>(page, pageSize);
        IPage<PaymentOrderMany> paymentOrderManyIPage = paymentOrderManyDao.selectMany(paymentOrderManyPage, companyId, paymentOrderId, taxId, beginDate, endDate);
        return ReturnJson.success(paymentOrderManyIPage);
    }

    @Resource
    private CrowdSourcingInvoiceDao crowdSourcingInvoiceDao;
    /**
     * 众包支付订单详情
     *
     * @param id
     * @return
     */
    @Override
    public ReturnJson getPaymentOrderManyInfo(String id) {
        PaymentOrderInfoPO paymentOrderInfoPO = null;
        PaymentOrderInfoVO paymentOrderInfoVO = new PaymentOrderInfoVO();
        ExpressInfoVO expressInfoVO = new ExpressInfoVO();

        //为众包订单
        paymentOrderInfoPO = paymentOrderManyDao.selectPaymentOrderInfo(id);
        if (paymentOrderInfoPO == null) {
            return ReturnJson.error("订单编号有误，请重新输入！");
        }
        InvoiceInfoPO invoiceInfoPO = crowdSourcingInvoiceDao.selectInvoiceInfoPO(id);
        if (invoiceInfoPO != null) {
            //众包发票信息
            paymentOrderInfoVO.setInvoice(invoiceInfoPO.getInvoiceUrl());
            expressInfoVO.setExpressCompanyName(invoiceInfoPO.getExpressCompanyName());
            expressInfoVO.setExpressCode(invoiceInfoPO.getExpressSheetNo());
            List<ExpressLogisticsInfo> expressLogisticsInfos = KdniaoTrackQueryAPI.getExpressInfo(invoiceInfoPO.getExpressCompanyName(), invoiceInfoPO.getExpressSheetNo());
            expressInfoVO.setExpressLogisticsInfos(expressLogisticsInfos);
        }
        List<PaymentInventory> paymentInventories = paymentInventoryDao.selectPaymentInventoryList(id, null);
        paymentOrderInfoVO.setPaymentInventories(paymentInventories);
        paymentOrderInfoVO.setPaymentOrderInfoPO(paymentOrderInfoPO);
        paymentOrderInfoVO.setExpressInfoVO(expressInfoVO);
        return ReturnJson.success(paymentOrderInfoVO);
    }

    /**
     * 创建或修改众包支付订单
     *
     * @param addPaymentOrderManyDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson saveOrUpdataPaymentOrderMany(AddPaymentOrderManyDto addPaymentOrderManyDto) {
        PaymentOrderMany paymentOrderMany = addPaymentOrderManyDto.getPaymentOrderMany();
        List<PaymentInventory> paymentInventories = addPaymentOrderManyDto.getPaymentInventories();
        String id = paymentOrderMany.getId();
        if (id != null && paymentOrderMany.getPaymentOrderStatus() == 0) {
            List<PaymentInventory> paymentInventoryList = paymentInventoryDao.selectList(new QueryWrapper<PaymentInventory>().eq("payment_order_id", id));
            List<String> ids = new ArrayList<>();
            for (PaymentInventory paymentInventory : paymentInventoryList) {
                ids.add(paymentInventory.getId());
            }
            paymentInventoryDao.delete(new QueryWrapper<PaymentInventory>().eq("payment_order_id", id));
            this.removeById(id);
        }

        BigDecimal receviceTax = paymentOrderMany.getReceviceTax().divide(BigDecimal.valueOf(100));
        BigDecimal merchantTax = paymentOrderMany.getMerchantTax().divide(BigDecimal.valueOf(100));
        BigDecimal compositeTax = new BigDecimal("0");
        BigDecimal countMoney = new BigDecimal("0");
        Integer taxStatus = paymentOrderMany.getTaxStatus();

        CompanyTax companyTax = companyTaxDao.selectOne(new QueryWrapper<CompanyTax>().eq("tax_id", paymentOrderMany.getTaxId()).eq("company_id", paymentOrderMany.getCompanyId()));

        if (companyTax.getChargeStatus() == 0) {
            compositeTax = companyTax.getServiceCharge().divide(BigDecimal.valueOf(100));
            for (PaymentInventory paymentInventory : paymentInventories) {
                BigDecimal realMoney = paymentInventory.getRealMoney();
                paymentInventory.setCompositeTax(compositeTax);
                if (taxStatus == 0) {
                    paymentInventory.setMerchantPaymentMoney(realMoney.multiply(compositeTax));
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                } else if (taxStatus == 1) {
                    paymentInventory.setMerchantPaymentMoney(realMoney);
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                    paymentInventory.setRealMoney(realMoney.subtract(realMoney.multiply(compositeTax)));
                } else {
                    paymentInventory.setMerchantPaymentMoney(realMoney.multiply(compositeTax.subtract(merchantTax).add(new BigDecimal("1"))));
                    paymentInventory.setRealMoney(realMoney.subtract(realMoney.multiply(compositeTax.subtract(receviceTax))));
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                }
                countMoney = countMoney.add(paymentInventory.getMerchantPaymentMoney());
            }
        } else {
            List<CompanyLadderService> companyLadderServices = companyLadderServiceDao.selectList(new QueryWrapper<CompanyLadderService>().eq("company_tax_id", companyTax.getId()).orderByAsc("start_money"));
            for (PaymentInventory paymentInventory : paymentInventories) {
                BigDecimal realMoney = paymentInventory.getRealMoney();
                compositeTax = this.getCompositeTax(companyLadderServices, realMoney);
                paymentInventory.setCompositeTax(compositeTax);
                if (taxStatus == 0) {
                    paymentInventory.setMerchantPaymentMoney(realMoney.multiply(compositeTax));
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                } else if (taxStatus == 1) {
                    paymentInventory.setMerchantPaymentMoney(realMoney);
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                    paymentInventory.setRealMoney(realMoney.subtract(realMoney.multiply(compositeTax)));
                } else {
                    paymentInventory.setMerchantPaymentMoney(realMoney.multiply(compositeTax.subtract(merchantTax).add(new BigDecimal("1"))));
                    paymentInventory.setRealMoney(realMoney.subtract(realMoney.multiply(compositeTax.subtract(receviceTax))));
                    paymentInventory.setServiceMoney(realMoney.multiply(compositeTax));
                }
                countMoney = countMoney.add(paymentInventory.getMerchantPaymentMoney());
            }
        }
        paymentOrderMany.setRealMoney(countMoney);
        boolean b = this.saveOrUpdate(paymentOrderMany);
        if (!b) {
            return ReturnJson.error("订单创建失败！");
        }
        for (PaymentInventory paymentInventory : paymentInventories) {
            paymentInventory.setPaymentOrderId(paymentOrderMany.getId());
            paymentInventory.setPackageStatus(1);
        }
        paymentInventoryService.saveOrUpdateBatch(paymentInventories);
        return ReturnJson.success("支付订单创建成功！");
    }

    /**
     * 众包线下支付
     *
     * @param id
     * @param manyPayment
     * @return
     */
    @Override
    public ReturnJson offlinePayment(String id, String manyPayment) {
        PaymentOrderMany paymentOrder = new PaymentOrderMany();
        paymentOrder.setId(id);
        paymentOrder.setManyPayment(manyPayment);
        paymentOrder.setPaymentDate(LocalDateTime.now());
        paymentOrder.setPaymentOrderStatus(2);
        int i = paymentOrderManyDao.updateById(paymentOrder);
        if (i == 1) {
            return ReturnJson.success("支付成功！");
        }
        return ReturnJson.error("支付失败，请重试！");
    }


    /**
     * 众包今天的支付金额
     *
     * @param request
     * @return
     */
    @Override
    public ReturnJson getDayPaas(HttpServletRequest request) throws CommonException {
        String token = request.getHeader(TOKEN);
        Claims claim = jwtUtils.getClaimByToken(token);
        String merchantId = null;
        if (claim != null) {
            merchantId = claim.getSubject();
        }
        List<String> merchantIds = acquireID.getMerchantIds(merchantId);
        List<PaymentOrderMany> list = paymentOrderManyDao.selectDaypaas(merchantIds);
        return ReturnJson.success(list);
    }

    /**
     * 众包本周的支付金额
     *
     * @param request
     * @return
     */
    @Override
    public ReturnJson getWeekPaas(HttpServletRequest request) throws CommonException {
        String token = request.getHeader(TOKEN);
        Claims claim = jwtUtils.getClaimByToken(token);
        String merchantId = null;
        if (claim != null) {
            merchantId = claim.getSubject();
        }
        List<String> merchantIds = acquireID.getMerchantIds(merchantId);
        List<PaymentOrderMany> list = paymentOrderManyDao.selectWeekpaas(merchantIds);
        return ReturnJson.success(list);
    }


    /**
     * 众包本月的支付金额
     *
     * @param request
     * @return
     */
    @Override
    public ReturnJson getMonthPaas(HttpServletRequest request) throws CommonException {
        String token = request.getHeader(TOKEN);
        Claims claim = jwtUtils.getClaimByToken(token);
        String merchantId = null;
        if (claim != null) {
            merchantId = claim.getSubject();
        }
        List<String> merchantIds = acquireID.getMerchantIds(merchantId);
        List<PaymentOrderMany> list = paymentOrderManyDao.selectMonthpaas(merchantIds);
        return ReturnJson.success(list);
    }

    /**
     * 众包今年的支付金额
     *
     * @param request
     * @return
     */
    @Override
    public ReturnJson getYearPaas(HttpServletRequest request) throws CommonException {
        String token = request.getHeader(TOKEN);
        Claims claim = jwtUtils.getClaimByToken(token);
        String merchantId = null;
        if (claim != null) {
            merchantId = claim.getSubject();
        }
        List<String> merchantIds = acquireID.getMerchantIds(merchantId);
        List<PaymentOrderMany> list = paymentOrderManyDao.selectYearpaas(merchantIds);
        return ReturnJson.success(list);
    }

    /**
     * 众包确认收款
     *
     * @param id
     * @return
     */
    @Override
    public ReturnJson confirmPaymentManyPaas(String id) {
        PaymentOrderMany paymentOrderMany = new PaymentOrderMany();
        paymentOrderMany.setId(id);
        paymentOrderMany.setPaymentOrderStatus(3);
        paymentOrderManyDao.updateById(paymentOrderMany);
        return ReturnJson.success("已成功确认收款");
    }

    /**
     * 查询众包的支付订单
     *
     * @param paymentOrderDto
     * @return
     */
    @Override
    public ReturnJson getPaymentOrderManyPaas(PaymentOrderDto paymentOrderDto) throws CommonException {
        List<String> merchantIds = acquireID.getMerchantIds(paymentOrderDto.getManagersId());
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success("");
        }
        String merchantName = paymentOrderDto.getMerchantName();
        String paymentOrderId = paymentOrderDto.getPaymentOrderId();
        String taxId = paymentOrderDto.getTaxId();
        Integer pageSize = paymentOrderDto.getPageSize();
        Integer page = paymentOrderDto.getPage();
        String beginDate = paymentOrderDto.getBeginDate();
        String endDate = paymentOrderDto.getEndDate();
        Page<PaymentOrderMany> paymentOrderManyPage = new Page<>(page, pageSize);
        IPage<PaymentOrderMany> paymentOrderManyIPage = paymentOrderManyDao.selectManyPaas(paymentOrderManyPage, merchantIds, merchantName, paymentOrderId, taxId, beginDate, endDate);
        return ReturnJson.success(paymentOrderManyIPage);
    }

    /**
     * 众包支付订单详情
     *
     * @param id
     * @return
     */
    @Override
    public ReturnJson getPaymentOrderManyInfoPaas(String id) {
        PaymentOrderMany paymentOrderMany = paymentOrderManyDao.selectById(id);
        List<PaymentInventory> paymentInventories = paymentInventoryDao.selectList(new QueryWrapper<PaymentInventory>().eq("payment_order_id", id));
        Tax tax = taxDao.selectById(paymentOrderMany.getTaxId());
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("paymentInventories", paymentInventories);
        map.put("tax", tax);
        list.add(map);
        return ReturnJson.success(paymentOrderMany, list);
    }

    /**
     * 获取综合费率
     *
     * @param companyLadderServices
     * @param realMoney
     * @return
     */
    private BigDecimal getCompositeTax(List<CompanyLadderService> companyLadderServices, BigDecimal realMoney) {
        BigDecimal compositeTax = new BigDecimal("0");
        for (CompanyLadderService companyLadderService : companyLadderServices) {
            BigDecimal startMoney = companyLadderService.getStartMoney();
            if (realMoney.compareTo(startMoney) >= 0) {
                compositeTax = companyLadderService.getServiceCharge().divide(BigDecimal.valueOf(100));
            }
        }
        return compositeTax;
    }

}
