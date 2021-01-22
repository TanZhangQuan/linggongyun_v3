package com.example.merchant.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.*;
import com.example.common.util.*;
import com.example.merchant.dto.merchant.AddPaymentOrderManyDTO;
import com.example.merchant.dto.merchant.PaymentOrderManyPayDTO;
import com.example.merchant.dto.merchant.PaymentOrderMerchantDTO;
import com.example.merchant.dto.platform.PaymentOrderDTO;
import com.example.merchant.dto.platform.PaymentOrderManyDTO;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.*;
import com.example.merchant.util.AcquireID;
import com.example.merchant.util.SnowflakeIdWorker;
import com.example.merchant.vo.ExpressInfoVO;
import com.example.merchant.vo.PaymentOrderInfoVO;
import com.example.mybatis.dto.QueryCrowdSourcingDTO;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.po.InvoiceInfoPO;
import com.example.mybatis.po.PaymentOrderInfoPO;
import com.example.mybatis.vo.*;
import com.example.redis.dao.RedisDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 众包支付单操作
 * 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-08
 */
@Slf4j
@Service
public class PaymentOrderManyServiceImpl extends ServiceImpl<PaymentOrderManyDao, PaymentOrderMany> implements PaymentOrderManyService {

    @Value("${TOKEN}")
    private String TOKEN;

    @Resource
    private RedisDao redisDao;

    @Resource
    private PaymentOrderManyDao paymentOrderManyDao;

    @Resource
    private PaymentInventoryDao paymentInventoryDao;

    @Resource
    private CompanyTaxDao companyTaxDao;

    @Resource
    private PaymentInventoryService paymentInventoryService;

    @Resource
    private CompanyLadderServiceDao companyLadderServiceDao;

    @Resource
    private AcquireID acquireID;

    @Resource
    private TaxDao taxDao;

    @Resource
    private MerchantDao merchantDao;

    @Resource
    private CrowdSourcingInvoiceDao crowdSourcingInvoiceDao;

    @Resource
    private CompanyInfoService companyInfoService;

    @Resource
    private TaxUnionpayService taxUnionpayService;

    @Resource
    private CompanyUnionpayService companyUnionpayService;

    @Resource
    private PaymentHistoryService paymentHistoryService;

    /**
     * 众包今天的支付金额
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getDay(String merchantId) {
        Merchant merchant = merchantDao.selectById(merchantId);
        if (merchant == null) {
            return ReturnJson.error("您输入的信息有误！");
        }
        return ReturnJson.success((paymentOrderManyDao.getTodayById(merchant.getCompanyId())));
    }

    /**
     * 众包本周的支付金额
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getWeek(String merchantId) {
        Merchant merchant = merchantDao.selectById(merchantId);
        if (merchant == null) {
            return ReturnJson.error("您输入的信息有误！");
        }
        return ReturnJson.success((paymentOrderManyDao.getWeekTradeById(merchant.getCompanyId())));
    }

    /**
     * 众包本月的支付金额
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getMonth(String merchantId) {
        Merchant merchant = merchantDao.selectById(merchantId);
        if (merchant == null) {
            return ReturnJson.error("您输入的信息有误！");
        }
        return ReturnJson.success((paymentOrderManyDao.getMonthTradeById(merchant.getCompanyId())));
    }

    /**
     * 众包今年的支付金额
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getYear(String merchantId) {
        Merchant merchant = merchantDao.selectById(merchantId);
        if (merchant == null) {
            return ReturnJson.error("您输入的信息有误！");
        }
        return ReturnJson.success((paymentOrderManyDao.getYearTradeById(merchant.getCompanyId())));
    }

    /**
     * 根据商户id查众包待开票数据
     *
     * @param queryCrowdSourcingDto
     * @param userId
     * @return
     */
    @Override
    public ReturnJson getListCSIByID(QueryCrowdSourcingDTO queryCrowdSourcingDto, String userId) {
        Page page = new Page(queryCrowdSourcingDto.getPageNo(), queryCrowdSourcingDto.getPageSize());
        Merchant merchant = merchantDao.selectById(userId);
        IPage<CrowdSourcingInvoiceInfoVO> list = paymentOrderManyDao.getListCSIByID(page, queryCrowdSourcingDto, merchant.getCompanyId());
        return ReturnJson.success(list);
    }

    /**
     * 根据支付id查询众包支付信息
     *
     * @param id
     * @return
     */
    @Override
    public ReturnJson getPayOrderManyById(String id) {
        PaymentOrderManyVO paymentOrderManyVo = paymentOrderManyDao.getPayOrderManyById(id);
        return ReturnJson.success(paymentOrderManyVo);
    }

    @Override
    public ReturnJson getInvoiceDetailsByPayId(String id, Integer pageNo, Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        IPage<InvoiceDetailsVO> list = paymentOrderManyDao.getInvoiceDetailsByPayId(page, id);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getPaymentOrderMany(String merchantId, PaymentOrderMerchantDTO paymentOrderMerchantDto) {
        Merchant merchant = merchantDao.selectById(merchantId);
        String companyId = merchant.getCompanyId();
        String paymentOrderId = paymentOrderMerchantDto.getPaymentOrderId();
        String taxId = paymentOrderMerchantDto.getTaxId();
        Integer pageSize = paymentOrderMerchantDto.getPageSize();
        Integer page = paymentOrderMerchantDto.getPageNo();
        String beginDate = paymentOrderMerchantDto.getBeginDate();
        String endDate = paymentOrderMerchantDto.getEndDate();
        Page<PaymentOrderMany> paymentOrderManyPage = new Page<>(page, pageSize);
        IPage<PaymentOrderMany> paymentOrderManyIPage = paymentOrderManyDao.selectMany(paymentOrderManyPage, companyId, paymentOrderId, taxId, beginDate, endDate);
        return ReturnJson.success(paymentOrderManyIPage);
    }

    @Override
    public ReturnJson getPaymentOrderManyInfo(String id) {
        PaymentOrderInfoVO paymentOrderInfoVO = new PaymentOrderInfoVO();
        ExpressInfoVO expressInfoVO = new ExpressInfoVO();

        //为众包订单
        PaymentOrderInfoPO paymentOrderInfoPO = paymentOrderManyDao.selectPaymentOrderInfo(id);
        if (paymentOrderInfoPO == null) {
            return ReturnJson.error("订单编号有误，请重新输入！");
        }
        //查询商户银联支付银行
        List<UnionpayBankType> companyUnionpayBankTypeList = companyUnionpayService.queryCompanyUnionpayMethod(paymentOrderInfoPO.getMerchantId(), paymentOrderInfoPO.getTaxId());
        paymentOrderInfoPO.setCompanyUnionpayBankTypeList(companyUnionpayBankTypeList);
        //获取商户主账号手机号
        String loginMobile = merchantDao.queryMainMerchantloginMobile(paymentOrderInfoPO.getMerchantId());
        paymentOrderInfoPO.setLoginMobile(loginMobile);

        InvoiceInfoPO invoiceInfoPO = crowdSourcingInvoiceDao.selectInvoiceInfoPO(id);
        if (invoiceInfoPO != null) {
            //众包发票信息
            paymentOrderInfoVO.setInvoice(invoiceInfoPO.getInvoiceUrl());
            expressInfoVO.setExpressCompanyName(invoiceInfoPO.getExpressCompanyName());
            expressInfoVO.setExpressCode(invoiceInfoPO.getExpressSheetNo());
            List<ExpressLogisticsInfo> expressLogisticsInfos = KdniaoTrackQueryAPI.getExpressInfo(invoiceInfoPO.getExpressCompanyName(), invoiceInfoPO.getExpressSheetNo());
            expressInfoVO.setExpressLogisticsInfos(expressLogisticsInfos);
        }
        List<PaymentInventoryVO> paymentInventories = paymentInventoryDao.selectPaymentOrderManyInfo(id, null);
        paymentOrderInfoVO.setPaymentInventories(paymentInventories);
        paymentOrderInfoVO.setPaymentOrderInfoPO(paymentOrderInfoPO);
        paymentOrderInfoVO.setExpressInfoVO(expressInfoVO);
        return ReturnJson.success(paymentOrderInfoVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson saveOrUpdataPaymentOrderMany(AddPaymentOrderManyDTO addPaymentOrderManyDto, String merchantId) throws CommonException {
        PaymentOrderMany paymentOrderMany = new PaymentOrderMany();
        PaymentOrderManyDTO paymentOrderManyDto = addPaymentOrderManyDto.getPaymentOrderManyDto();
        BeanUtils.copyProperties(paymentOrderManyDto, paymentOrderMany);
        paymentOrderMany.setTradeNo(SnowflakeIdWorker.getSerialNumber());
        Tax tax = taxDao.selectById(paymentOrderMany.getTaxId());
        paymentOrderMany.setPlatformServiceProvider(tax.getTaxName());
        Merchant merchant = merchantDao.selectById(merchantId);
        if (merchant != null) {
            paymentOrderMany.setCompanyId(merchant.getCompanyId());
            paymentOrderMany.setCompanySName(merchant.getCompanyName());
        } else {
            CompanyInfo companyInfo = companyInfoService.getById(merchantId);
            paymentOrderMany.setCompanyId(companyInfo.getId());
            paymentOrderMany.setCompanySName(companyInfo.getCompanyName());
        }
        List<PaymentInventory> paymentInventories = addPaymentOrderManyDto.getPaymentInventories();
        String id = paymentOrderMany.getId();
        if (id != "" && paymentOrderMany.getPaymentOrderStatus() == 0) {
            List<PaymentInventory> paymentInventoryList = paymentInventoryDao.selectList(new QueryWrapper<PaymentInventory>().eq("payment_order_id", id));
            List<String> ids = new ArrayList<>();
            for (PaymentInventory paymentInventory : paymentInventoryList) {
                ids.add(paymentInventory.getId());
            }
            paymentInventoryDao.delete(new QueryWrapper<PaymentInventory>().eq("payment_order_id", id));
            this.removeById(id);
        }

        BigDecimal merchantTax = new BigDecimal("100.00");
        BigDecimal receviceTax = new BigDecimal("0.00");
        BigDecimal compositeTax = new BigDecimal("0.00");
        BigDecimal countMoney = new BigDecimal("0");
        BigDecimal countServiceMoney = new BigDecimal("0");

        Integer taxStatus = paymentOrderMany.getTaxStatus();
        paymentOrderMany.setMerchantTax(merchantTax);
        paymentOrderMany.setReceviceTax(receviceTax);
        CompanyTax companyTax = companyTaxDao.selectOne(new QueryWrapper<CompanyTax>().
                eq("tax_id", paymentOrderMany.getTaxId()).
                eq("company_id", paymentOrderMany.getCompanyId()).
                eq("package_status", 1));

        if (companyTax.getChargeStatus() == 0) {
            compositeTax = companyTax.getServiceCharge().divide(BigDecimal.valueOf(100));
            for (PaymentInventory paymentInventory : paymentInventories) {
                BigDecimal realMoney = paymentInventory.getRealMoney();
                paymentInventory.setTaskMoney(realMoney);
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
                countServiceMoney = countServiceMoney.add(paymentInventory.getServiceMoney());
            }
        } else {
            List<CompanyLadderService> companyLadderServices = companyLadderServiceDao.selectList(new QueryWrapper<CompanyLadderService>().eq("company_tax_id", companyTax.getId()).orderByAsc("start_money"));
            for (PaymentInventory paymentInventory : paymentInventories) {
                BigDecimal realMoney = paymentInventory.getRealMoney();
                paymentInventory.setTaskMoney(realMoney);
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
                countServiceMoney = countServiceMoney.add(paymentInventory.getServiceMoney());
            }
        }
        paymentOrderMany.setServiceMoney(countServiceMoney);
        paymentOrderMany.setRealMoney(countMoney);
        boolean b = this.saveOrUpdate(paymentOrderMany);
        if (!b) {
            throw new CommonException(300, "订单创建失败！");
        }
        for (PaymentInventory paymentInventory : paymentInventories) {
            paymentInventory.setCompositeTax(compositeTax.multiply(new BigDecimal("100.00")));
            paymentInventory.setTradeNo(SnowflakeIdWorker.getSerialNumber());
            paymentInventory.setPaymentOrderId(paymentOrderMany.getId());
            paymentInventory.setPackageStatus(1);
        }
        paymentInventoryService.saveOrUpdateBatch(paymentInventories);
        return ReturnJson.success("支付订单创建成功！");
    }

    @Override
    public ReturnJson paymentOrderManyPay(PaymentOrderManyPayDTO paymentOrderManyPayDTO) throws Exception {

        PaymentOrderMany paymentOrderMany = getById(paymentOrderManyPayDTO.getPaymentOrderManyId());
        if (paymentOrderMany == null) {
            return ReturnJson.error("众包支付单信息不存在");
        }

        //检查众包清单总手续费
        BigDecimal serviceCharge = paymentOrderMany.getServiceMoney();
        if (serviceCharge == null || serviceCharge.compareTo(BigDecimal.ZERO) <= 0) {
            return ReturnJson.error("众包总手续费有误");
        }

        //判断支付密码是否正确
        boolean flag = companyInfoService.verifyPayPwd(paymentOrderMany.getCompanyId(), paymentOrderManyPayDTO.getPayPwd());
        if (!flag) {
            return ReturnJson.error("支付密码不正确");
        }

        //判断短信验证码是否正确
        //获取商户主账号手机号
        String loginMobile = merchantDao.queryMainMerchantloginMobile(paymentOrderMany.getCompanyId());
        String redisCheckCode = redisDao.get(loginMobile);
        if (!(paymentOrderManyPayDTO.getCheckCode().equals(redisCheckCode))) {
            return ReturnJson.error("短信验证码不正确");
        }

        TaxUnionpay taxUnionpay;
        CompanyUnionpay companyUnionpay;
        JSONObject jsonObject;
        Boolean boolSuccess;
        JSONObject returnValue;
        String rtnCode;
        BigDecimal useBal;
        switch (paymentOrderManyPayDTO.getPaymentMode()) {

            case 0:

                if (StringUtils.isBlank(paymentOrderManyPayDTO.getTurnkeyProjectPayment())) {
                    return ReturnJson.error("请上传众包支付回单");
                }

                paymentOrderMany.setManyPayment(paymentOrderManyPayDTO.getTurnkeyProjectPayment());

                break;

            case 1:

                return ReturnJson.error("连连支付暂未开通");

            case 2:

                return ReturnJson.error("网商银行支付暂未开通");

            case 3:

                taxUnionpay = taxUnionpayService.queryTaxUnionpay(paymentOrderMany.getTaxId(), UnionpayBankType.SJBK);
                if (taxUnionpay == null) {
                    return ReturnJson.error("服务商盛京主账号不存在");
                }

                //查询商户-服务商银联记录
                companyUnionpay = companyUnionpayService.queryMerchantUnionpay(paymentOrderMany.getCompanyId(), taxUnionpay.getId());
                if (companyUnionpay == null) {
                    return ReturnJson.error("商户-服务商盛京子账号不存在");
                }

                jsonObject = UnionpayUtil.AC081(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), companyUnionpay.getUid());
                if (jsonObject == null) {
                    return ReturnJson.error("查询子帐号" + taxUnionpay.getUnionpayBankType().getDesc() + "银联支付余额失败");
                }

                boolSuccess = jsonObject.getBoolean("success");
                if (boolSuccess == null || !boolSuccess) {
                    String errMsg = jsonObject.getString("err_msg");
                    return ReturnJson.error("查询子帐号" + taxUnionpay.getUnionpayBankType().getDesc() + "银联支付余额失败: " + errMsg);
                }

                returnValue = jsonObject.getJSONObject("return_value");
                rtnCode = returnValue.getString("rtn_code");
                if (!("S00000".equals(rtnCode))) {
                    String errMsg = returnValue.getString("err_msg");
                    return ReturnJson.error("查询子帐号" + taxUnionpay.getUnionpayBankType().getDesc() + "银联支付余额失败: " + errMsg);
                }

                //可用余额，单位元
                useBal = returnValue.getBigDecimal("use_bal");
                if (paymentOrderMany.getServiceMoney().compareTo(useBal) > 0) {
                    return ReturnJson.error("商户盛京子帐号可用余额不足");
                }

                break;

            case 4:

                taxUnionpay = taxUnionpayService.queryTaxUnionpay(paymentOrderMany.getTaxId(), UnionpayBankType.PABK);
                if (taxUnionpay == null) {
                    return ReturnJson.error("服务商银联记录不存在");
                }

                //查询商户-服务商银联记录
                companyUnionpay = companyUnionpayService.queryMerchantUnionpay(paymentOrderMany.getCompanyId(), taxUnionpay.getId());
                if (companyUnionpay == null) {
                    return ReturnJson.error("商户-服务商盛京子账号不存在");
                }

                jsonObject = UnionpayUtil.AC081(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), companyUnionpay.getUid());
                if (jsonObject == null) {
                    return ReturnJson.error("查询子帐号" + taxUnionpay.getUnionpayBankType().getDesc() + "银联支付余额失败");
                }

                boolSuccess = jsonObject.getBoolean("success");
                if (boolSuccess == null || !boolSuccess) {
                    String errMsg = jsonObject.getString("err_msg");
                    return ReturnJson.error("查询子帐号" + taxUnionpay.getUnionpayBankType().getDesc() + "银联支付余额失败: " + errMsg);
                }

                returnValue = jsonObject.getJSONObject("return_value");
                rtnCode = returnValue.getString("rtn_code");
                if (!("S00000".equals(rtnCode))) {
                    String errMsg = returnValue.getString("err_msg");
                    return ReturnJson.error("查询子帐号" + taxUnionpay.getUnionpayBankType().getDesc() + "银联支付余额失败: " + errMsg);
                }

                //可用余额，单位元
                useBal = returnValue.getBigDecimal("use_bal");
                if (paymentOrderMany.getServiceMoney().compareTo(useBal) > 0) {
                    return ReturnJson.error("商户盛京子帐号可用余额不足");
                }

                break;

            case 5:

                taxUnionpay = taxUnionpayService.queryTaxUnionpay(paymentOrderMany.getTaxId(), UnionpayBankType.WSBK);
                if (taxUnionpay == null) {
                    return ReturnJson.error("服务商银联记录不存在");
                }

                //查询商户-服务商银联记录
                companyUnionpay = companyUnionpayService.queryMerchantUnionpay(paymentOrderMany.getCompanyId(), taxUnionpay.getId());
                if (companyUnionpay == null) {
                    return ReturnJson.error("商户-服务商盛京子账号不存在");
                }

                jsonObject = UnionpayUtil.AC081(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), companyUnionpay.getUid());
                if (jsonObject == null) {
                    return ReturnJson.error("查询子帐号" + taxUnionpay.getUnionpayBankType().getDesc() + "银联支付余额失败");
                }

                boolSuccess = jsonObject.getBoolean("success");
                if (boolSuccess == null || !boolSuccess) {
                    String errMsg = jsonObject.getString("err_msg");
                    return ReturnJson.error("查询子帐号" + taxUnionpay.getUnionpayBankType().getDesc() + "银联支付余额失败: " + errMsg);
                }

                returnValue = jsonObject.getJSONObject("return_value");
                rtnCode = returnValue.getString("rtn_code");
                if (!("S00000".equals(rtnCode))) {
                    String errMsg = returnValue.getString("err_msg");
                    return ReturnJson.error("查询子帐号" + taxUnionpay.getUnionpayBankType().getDesc() + "银联支付余额失败: " + errMsg);
                }

                //可用余额，单位元
                useBal = returnValue.getBigDecimal("use_bal");
                if (paymentOrderMany.getServiceMoney().compareTo(useBal) > 0) {
                    return ReturnJson.error("商户盛京子帐号可用余额不足");
                }

                break;

            case 6:

                taxUnionpay = taxUnionpayService.queryTaxUnionpay(paymentOrderMany.getTaxId(), UnionpayBankType.ZSBK);
                if (taxUnionpay == null) {
                    return ReturnJson.error("服务商银联记录不存在");
                }

                //查询商户-服务商银联记录
                companyUnionpay = companyUnionpayService.queryMerchantUnionpay(paymentOrderMany.getCompanyId(), taxUnionpay.getId());
                if (companyUnionpay == null) {
                    return ReturnJson.error("商户-服务商盛京子账号不存在");
                }

                jsonObject = UnionpayUtil.AC081(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), companyUnionpay.getUid());
                if (jsonObject == null) {
                    return ReturnJson.error("查询子帐号" + taxUnionpay.getUnionpayBankType().getDesc() + "银联支付余额失败");
                }

                boolSuccess = jsonObject.getBoolean("success");
                if (boolSuccess == null || !boolSuccess) {
                    String errMsg = jsonObject.getString("err_msg");
                    return ReturnJson.error("查询子帐号" + taxUnionpay.getUnionpayBankType().getDesc() + "银联支付余额失败: " + errMsg);
                }

                returnValue = jsonObject.getJSONObject("return_value");
                rtnCode = returnValue.getString("rtn_code");
                if (!("S00000".equals(rtnCode))) {
                    String errMsg = returnValue.getString("err_msg");
                    return ReturnJson.error("查询子帐号" + taxUnionpay.getUnionpayBankType().getDesc() + "银联支付余额失败: " + errMsg);
                }

                //可用余额，单位元
                useBal = returnValue.getBigDecimal("use_bal");
                if (paymentOrderMany.getServiceMoney().compareTo(useBal) > 0) {
                    return ReturnJson.error("商户盛京子帐号可用余额不足");
                }

                break;

            default:
                return ReturnJson.error("支付方式不存在");
        }

        paymentOrderMany.setPaymentDate(LocalDateTime.now());
        paymentOrderMany.setPaymentMode(paymentOrderManyPayDTO.getPaymentMode());
        paymentOrderMany.setPaymentOrderStatus(2);
        updateById(paymentOrderMany);

        return ReturnJson.success("操作成功");
    }


    @Override
    public ReturnJson getDayPaas(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        List<PaymentOrderMany> list = paymentOrderManyDao.selectDaypaas(merchantIds);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getWeekPaas(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        List<PaymentOrderMany> list = paymentOrderManyDao.selectWeekpaas(merchantIds);
        return ReturnJson.success(list);
    }


    @Override
    public ReturnJson getMonthPaas(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        List<PaymentOrderMany> list = paymentOrderManyDao.selectMonthpaas(merchantIds);
        return ReturnJson.success(list);
    }

    @Override
    public ReturnJson getYearPaas(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        List<PaymentOrderMany> list = paymentOrderManyDao.selectYearpaas(merchantIds);
        return ReturnJson.success(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson paymentOrderManyAudit(String paymentOrderId, Boolean boolPass, String reasonsForRejection) throws Exception {

        PaymentOrderMany paymentOrderMany = getById(paymentOrderId);
        if (paymentOrderMany == null) {
            return ReturnJson.error("众包支付单信息不存在");
        }

        //redis上锁
        long time = System.currentTimeMillis() + 5 * 1000 + 1000;
        if (!redisDao.lock(paymentOrderMany.getTradeNo().intern(), time)) {
            return ReturnJson.error("订单号为" + paymentOrderMany.getTradeNo() + "的众包正在审核，请勿重复请求");
        }
        log.info("获得锁的时间戳：{}", time);

        try {

            if (paymentOrderMany.getPaymentOrderStatus() != -1 && paymentOrderMany.getPaymentOrderStatus() != 2) {
                return ReturnJson.error("非交易失败或支付中状态众包支付单不可审核");
            }

            if (boolPass == null || !boolPass) {

                if (StringUtils.isBlank(reasonsForRejection)) {
                    return ReturnJson.error("请输入驳回原因");
                }

                paymentOrderMany.setPaymentOrderStatus(4);
                paymentOrderMany.setReasonsForRejection(reasonsForRejection);

            } else {

                //查询众包清单总手续费
                BigDecimal serviceCharge = paymentOrderMany.getServiceMoney();
                if (serviceCharge == null || serviceCharge.compareTo(BigDecimal.ZERO) <= 0) {
                    return ReturnJson.error("众包总手续费有误");
                }

                PaymentHistory paymentHistory = paymentHistoryService.queryPaymentHistory(OrderType.MANYORDER, paymentOrderMany.getId(), TradeStatus.SUCCESS);
                if (paymentHistory != null) {
                    return ReturnJson.error("众包总手续费已支付成功");
                }

                paymentHistory = paymentHistoryService.queryPaymentHistory(OrderType.MANYORDER, paymentOrderMany.getId(), TradeStatus.TRADING);
                if (paymentHistory != null) {
                    return ReturnJson.error("众包总手续费正在支付中...");
                }

                //新建交易记录
                paymentHistory = new PaymentHistory();
                paymentHistory.setTradeNo(SnowflakeIdWorker.getSerialNumber());
                paymentHistory.setOrderType(OrderType.MANYORDER);
                paymentHistory.setOrderId(paymentOrderMany.getId());
                paymentHistory.setTradeObject(TradeObject.COMPANY);
                paymentHistory.setTradeObjectId(paymentOrderMany.getCompanyId());
                paymentHistory.setAmount(serviceCharge);

                TaxUnionpay taxUnionpay;
                CompanyUnionpay companyUnionpay;
                JSONObject jsonObject;
                Boolean boolSuccess;
                JSONObject returnValue;
                String rtnCode;
                switch (paymentOrderMany.getPaymentMode()) {

                    case 0:

                        //设置众包支付成功
                        paymentOrderMany.setPaymentOrderStatus(3);

                        //设置交易记录交易方式
                        paymentHistory.setPaymentMethod(PaymentMethod.OFFLINE);
                        paymentHistory.setTradeStatus(TradeStatus.SUCCESS);

                        break;

                    case 1:

                        return ReturnJson.error("连连支付暂未开通");

                    case 2:

                        return ReturnJson.error("网商银行支付暂未开通");

                    case 3:

                        //查询服务商盛京银联记录
                        taxUnionpay = taxUnionpayService.queryTaxUnionpay(paymentOrderMany.getTaxId(), UnionpayBankType.SJBK);
                        if (taxUnionpay == null) {
                            return ReturnJson.error("服务商未开通银联盛京银行支付");
                        }

                        //查询商户是否已开通银联盛京银行子账号
                        companyUnionpay = companyUnionpayService.queryMerchantUnionpayUnionpayBankType(paymentOrderMany.getCompanyId(), taxUnionpay.getId());
                        if (companyUnionpay == null) {
                            return ReturnJson.error("商户未开通服务商的银联盛京银行子账号");
                        }


                        //设置交易记录交易方式
                        paymentHistory.setPaymentMethod(PaymentMethod.UNIONSJBK);

                        //支付众包手续费
                        jsonObject = UnionpayUtil.AC054(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), paymentHistory.getTradeNo(), companyUnionpay.getUid(), taxUnionpay.getServiceChargeNo(), serviceCharge);
                        if (jsonObject == null) {
                            //设置总包订单为失败
                            paymentOrderMany.setPaymentOrderStatus(-1);
                            paymentOrderMany.setTradeFailReason("同步回调参数为空");
                            updateById(paymentOrderMany);
                            //设置交易记录为失败
                            paymentHistory.setTradeStatus(TradeStatus.FAIL);
                            paymentHistory.setTradeFailReason("同步回调参数为空");
                            paymentHistoryService.save(paymentHistory);
                            return ReturnJson.error("支付众包手续费失败");
                        }

                        boolSuccess = jsonObject.getBoolean("success");
                        if (boolSuccess == null || !boolSuccess) {
                            String errMsg = jsonObject.getString("err_msg");
                            //设置总包订单为失败
                            paymentOrderMany.setPaymentOrderStatus(-1);
                            paymentOrderMany.setTradeFailReason(errMsg);
                            updateById(paymentOrderMany);
                            //设置交易记录为失败
                            paymentHistory.setTradeStatus(TradeStatus.FAIL);
                            paymentHistory.setTradeFailReason(errMsg);
                            paymentHistoryService.save(paymentHistory);
                            return ReturnJson.error("支付众包手续费失败：" + errMsg);
                        }

                        returnValue = jsonObject.getJSONObject("return_value");
                        rtnCode = returnValue.getString("rtn_code");
                        if (!("S00000".equals(rtnCode))) {
                            String errMsg = returnValue.getString("err_msg");
                            //设置总包订单为失败
                            paymentOrderMany.setPaymentOrderStatus(-1);
                            paymentOrderMany.setTradeFailReason(errMsg);
                            updateById(paymentOrderMany);
                            //设置交易记录为失败
                            paymentHistory.setTradeStatus(TradeStatus.FAIL);
                            paymentHistory.setTradeFailReason(errMsg);
                            paymentHistoryService.save(paymentHistory);
                            return ReturnJson.error("支付众包手续费失败：" + errMsg);
                        }

                        //设置交易记录状态
                        paymentHistory.setTradeStatus(TradeStatus.TRADING);

                        break;

                    case 4:

                        //查询服务商平安银联记录
                        taxUnionpay = taxUnionpayService.queryTaxUnionpay(paymentOrderMany.getTaxId(), UnionpayBankType.PABK);
                        if (taxUnionpay == null) {
                            return ReturnJson.error("服务商未开通银联平安银行支付");
                        }

                        //查询商户是否已开通银联平安银行子账号
                        companyUnionpay = companyUnionpayService.queryMerchantUnionpayUnionpayBankType(paymentOrderMany.getCompanyId(), taxUnionpay.getId());
                        if (companyUnionpay == null) {
                            return ReturnJson.error("商户未已开通服务商的银联平安银行子账号");
                        }

                        //设置交易记录交易方式
                        paymentHistory.setPaymentMethod(PaymentMethod.UNIONPABK);

                        //支付众包手续费
                        jsonObject = UnionpayUtil.AC054(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), paymentHistory.getTradeNo(), companyUnionpay.getUid(), taxUnionpay.getServiceChargeNo(), serviceCharge);
                        if (jsonObject == null) {
                            //设置总包订单为失败
                            paymentOrderMany.setPaymentOrderStatus(-1);
                            paymentOrderMany.setTradeFailReason("同步回调参数为空");
                            updateById(paymentOrderMany);
                            //设置交易记录为失败
                            paymentHistory.setTradeStatus(TradeStatus.FAIL);
                            paymentHistory.setTradeFailReason("同步回调参数为空");
                            paymentHistoryService.save(paymentHistory);
                            return ReturnJson.error("支付众包手续费失败");
                        }

                        boolSuccess = jsonObject.getBoolean("success");
                        if (boolSuccess == null || !boolSuccess) {
                            String errMsg = jsonObject.getString("err_msg");
                            //设置总包订单为失败
                            paymentOrderMany.setPaymentOrderStatus(-1);
                            paymentOrderMany.setTradeFailReason(errMsg);
                            updateById(paymentOrderMany);
                            //设置交易记录为失败
                            paymentHistory.setTradeStatus(TradeStatus.FAIL);
                            paymentHistory.setTradeFailReason(errMsg);
                            paymentHistoryService.save(paymentHistory);
                            return ReturnJson.error("支付众包手续费失败：" + errMsg);
                        }

                        returnValue = jsonObject.getJSONObject("return_value");
                        rtnCode = returnValue.getString("rtn_code");
                        if (!("S00000".equals(rtnCode))) {
                            String errMsg = returnValue.getString("err_msg");
                            //设置总包订单为失败
                            paymentOrderMany.setPaymentOrderStatus(-1);
                            paymentOrderMany.setTradeFailReason(errMsg);
                            updateById(paymentOrderMany);
                            //设置交易记录为失败
                            paymentHistory.setTradeStatus(TradeStatus.FAIL);
                            paymentHistory.setTradeFailReason(errMsg);
                            paymentHistoryService.save(paymentHistory);
                            return ReturnJson.error("支付众包手续费失败：" + errMsg);
                        }

                        //设置交易记录状态
                        paymentHistory.setTradeStatus(TradeStatus.TRADING);

                        break;

                    case 5:

                        //查询服务商网商银联记录
                        taxUnionpay = taxUnionpayService.queryTaxUnionpay(paymentOrderMany.getTaxId(), UnionpayBankType.WSBK);
                        if (taxUnionpay == null) {
                            return ReturnJson.error("服务商未开通银联网商银行支付");
                        }

                        //查询商户是否已开通银联网商银行子账号
                        companyUnionpay = companyUnionpayService.queryMerchantUnionpayUnionpayBankType(paymentOrderMany.getCompanyId(), taxUnionpay.getId());
                        if (companyUnionpay == null) {
                            return ReturnJson.error("商户未已开通服务商的银联网商银行子账号");
                        }

                        //设置交易记录交易方式
                        paymentHistory.setPaymentMethod(PaymentMethod.UNIONWSBK);

                        //支付众包手续费
                        jsonObject = UnionpayUtil.AC054(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), paymentHistory.getTradeNo(), companyUnionpay.getUid(), taxUnionpay.getServiceChargeNo(), serviceCharge);
                        if (jsonObject == null) {
                            //设置总包订单为失败
                            paymentOrderMany.setPaymentOrderStatus(-1);
                            paymentOrderMany.setTradeFailReason("同步回调参数为空");
                            updateById(paymentOrderMany);
                            //设置交易记录为失败
                            paymentHistory.setTradeStatus(TradeStatus.FAIL);
                            paymentHistory.setTradeFailReason("同步回调参数为空");
                            paymentHistoryService.save(paymentHistory);
                            return ReturnJson.error("支付众包手续费失败");
                        }

                        boolSuccess = jsonObject.getBoolean("success");
                        if (boolSuccess == null || !boolSuccess) {
                            String errMsg = jsonObject.getString("err_msg");
                            //设置总包订单为失败
                            paymentOrderMany.setPaymentOrderStatus(-1);
                            paymentOrderMany.setTradeFailReason(errMsg);
                            updateById(paymentOrderMany);
                            //设置交易记录为失败
                            paymentHistory.setTradeStatus(TradeStatus.FAIL);
                            paymentHistory.setTradeFailReason(errMsg);
                            paymentHistoryService.save(paymentHistory);
                            return ReturnJson.error("支付众包手续费失败：" + errMsg);
                        }

                        returnValue = jsonObject.getJSONObject("return_value");
                        rtnCode = returnValue.getString("rtn_code");
                        if (!("S00000".equals(rtnCode))) {
                            String errMsg = returnValue.getString("err_msg");
                            //设置总包订单为失败
                            paymentOrderMany.setPaymentOrderStatus(-1);
                            paymentOrderMany.setTradeFailReason(errMsg);
                            updateById(paymentOrderMany);
                            //设置交易记录为失败
                            paymentHistory.setTradeStatus(TradeStatus.FAIL);
                            paymentHistory.setTradeFailReason(errMsg);
                            paymentHistoryService.save(paymentHistory);
                            return ReturnJson.error("支付众包手续费失败：" + errMsg);
                        }

                        //设置交易记录状态
                        paymentHistory.setTradeStatus(TradeStatus.TRADING);

                        break;

                    case 6:

                        //查询服务商招商银联记录
                        taxUnionpay = taxUnionpayService.queryTaxUnionpay(paymentOrderMany.getTaxId(), UnionpayBankType.ZSBK);
                        if (taxUnionpay == null) {
                            return ReturnJson.error("服务商未开通银联招商银行支付");
                        }

                        //查询商户是否已开通银联招商银行子账号
                        companyUnionpay = companyUnionpayService.queryMerchantUnionpayUnionpayBankType(paymentOrderMany.getCompanyId(), taxUnionpay.getId());
                        if (companyUnionpay == null) {
                            return ReturnJson.error("商户未已开通服务商的银联招商银行子账号");
                        }

                        //设置交易记录交易方式
                        paymentHistory.setPaymentMethod(PaymentMethod.UNIONZSBK);

                        //支付众包手续费
                        jsonObject = UnionpayUtil.AC054(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), paymentHistory.getTradeNo(), companyUnionpay.getUid(), taxUnionpay.getServiceChargeNo(), serviceCharge);
                        if (jsonObject == null) {
                            //设置总包订单为失败
                            paymentOrderMany.setPaymentOrderStatus(-1);
                            paymentOrderMany.setTradeFailReason("同步回调参数为空");
                            updateById(paymentOrderMany);
                            //设置交易记录为失败
                            paymentHistory.setTradeStatus(TradeStatus.FAIL);
                            paymentHistory.setTradeFailReason("同步回调参数为空");
                            paymentHistoryService.save(paymentHistory);
                            return ReturnJson.error("支付众包手续费失败");
                        }

                        boolSuccess = jsonObject.getBoolean("success");
                        if (boolSuccess == null || !boolSuccess) {
                            String errMsg = jsonObject.getString("err_msg");
                            //设置总包订单为失败
                            paymentOrderMany.setPaymentOrderStatus(-1);
                            paymentOrderMany.setTradeFailReason(errMsg);
                            updateById(paymentOrderMany);
                            //设置交易记录为失败
                            paymentHistory.setTradeStatus(TradeStatus.FAIL);
                            paymentHistory.setTradeFailReason(errMsg);
                            paymentHistoryService.save(paymentHistory);
                            return ReturnJson.error("支付众包手续费失败：" + errMsg);
                        }

                        returnValue = jsonObject.getJSONObject("return_value");
                        rtnCode = returnValue.getString("rtn_code");
                        if (!("S00000".equals(rtnCode))) {
                            String errMsg = returnValue.getString("err_msg");
                            //设置总包订单为失败
                            paymentOrderMany.setPaymentOrderStatus(-1);
                            paymentOrderMany.setTradeFailReason(errMsg);
                            updateById(paymentOrderMany);
                            //设置交易记录为失败
                            paymentHistory.setTradeStatus(TradeStatus.FAIL);
                            paymentHistory.setTradeFailReason(errMsg);
                            paymentHistoryService.save(paymentHistory);
                            return ReturnJson.error("支付众包手续费失败：" + errMsg);
                        }

                        //设置交易记录状态
                        paymentHistory.setTradeStatus(TradeStatus.TRADING);

                        break;

                    default:
                        return ReturnJson.error("支付方式不存在");
                }

                paymentHistoryService.save(paymentHistory);
            }
            //编辑分包
            updateById(paymentOrderMany);

            return ReturnJson.success("操作成功");

        } finally {
            try {
                //释放锁
                redisDao.release(paymentOrderMany.getTradeNo().intern(), time);
                log.info("释放锁的时间戳：{}", time);
            } catch (Exception e) {
                log.info("释放锁的时间戳异常", e);
            }
        }

    }

    @Override
    public ReturnJson getPaymentOrderManyPaas(PaymentOrderDTO paymentOrderDto, String managersId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(managersId);
        if (VerificationCheck.listIsNull(merchantIds)) {
            return ReturnJson.success("");
        }
        String merchantName = paymentOrderDto.getMerchantName();
        String paymentOrderId = paymentOrderDto.getPaymentOrderId();
        String taxId = paymentOrderDto.getTaxId();
        Integer pageSize = paymentOrderDto.getPageSize();
        Integer page = paymentOrderDto.getPageNo();
        String beginDate = paymentOrderDto.getBeginDate();
        String endDate = paymentOrderDto.getEndDate();
        Page<PaymentOrderMany> paymentOrderManyPage = new Page<>(page, pageSize);
        IPage<PaymentOrderMany> paymentOrderManyIPage = paymentOrderManyDao.selectManyPaas(paymentOrderManyPage,
                merchantIds, merchantName, paymentOrderId, taxId, beginDate, endDate);
        return ReturnJson.success(paymentOrderManyIPage);
    }

    @Override
    public ReturnJson getDayPaa(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        TodayVO todayVO = paymentOrderManyDao.selectDaypaa(merchantIds);
        return ReturnJson.success(todayVO);
    }

    @Override
    public ReturnJson getWeekPaa(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        WeekTradeVO weekTradeVO = paymentOrderManyDao.selectWeekpaa(merchantIds);
        return ReturnJson.success(weekTradeVO);
    }

    @Override
    public ReturnJson getMonthPaa(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        MonthTradeVO monthTradeVO = paymentOrderManyDao.selectMonthpaa(merchantIds);
        return ReturnJson.success(monthTradeVO);
    }

    @Override
    public ReturnJson getYearPaa(String merchantId) throws CommonException {
        List<String> merchantIds = acquireID.getCompanyIds(merchantId);
        YearTradeVO yearTradeVO = paymentOrderManyDao.selectYearpaa(merchantIds);
        return ReturnJson.success(yearTradeVO);
    }

    @Override
    public PaymentOrderMany queryPaymentOrderManyByTradeNo(String tradeNo) {

        QueryWrapper<PaymentOrderMany> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PaymentOrderMany::getTradeNo, tradeNo);

        return baseMapper.selectOne(queryWrapper);
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

    @Override
    public void queryTaxPlatformReconciliationFile(Date beginDate, Date endDate, String taxUnionpayId, HttpServletResponse response) throws Exception {

        String start = "2021-01-15";
        String end = "2021-01-20";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        beginDate = simpleDateFormat.parse(start);
        endDate = simpleDateFormat.parse(end);
        log.info("beginDate: {}", simpleDateFormat.format(beginDate));
        log.info("endDate: {}", simpleDateFormat.format(endDate));

        TaxUnionpay taxUnionpay = taxUnionpayService.getById(taxUnionpayId);
        if (taxUnionpay == null) {
            throw new CommonException(300, "服务商银联不存在");
        }

        if (beginDate.after(endDate)) {
            throw new CommonException(300, "开始日期不能大于结束日期");
        }

        //首先要获取到Calendar类，该类有对应的添加日期的方法！！
        Calendar calendar = Calendar.getInstance();

        //获取日期的毫秒值除以每天的毫秒值
        int betweenDays = (int) ((endDate.getTime() / (24 * 60 * 60 * 1000)) - (beginDate.getTime() / (24 * 60 * 60 * 1000)));
        if (betweenDays + 1 > 30) {
            throw new CommonException(300, "时间段只能选择30天以内");
        }

        //然后把相差的天数set到calendar类中，这样就改变日期了
        List<String> fileUrlList = new ArrayList<>();
        calendar.setTime(beginDate);
        for (int i = 0; i < betweenDays; i++) {
            // 两个参数，第一个是要添加的日期(年月日)，第二个是要添加多少天
            calendar.add(Calendar.DATE, i); //加一天
            Date date = calendar.getTime();
            log.info("endDate: {}", simpleDateFormat.format(date));

            //获取平台对账文件
            JSONObject jsonObject = UnionpayUtil.AC091(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), date);
            if (jsonObject == null) {
                log.error("服务商" + taxUnionpay.getUnionpayBankType().getDesc() + "银联查询平台对账文件查询失败");
                continue;
            }

            Boolean boolSuccess = jsonObject.getBoolean("success");
            if (boolSuccess == null || !boolSuccess) {
                String errMsg = jsonObject.getString("err_msg");
                log.error("服务商" + taxUnionpay.getUnionpayBankType().getDesc() + "银联查询平台对账文件查询失败: " + errMsg);
                continue;
            }

            JSONObject returnValue = jsonObject.getJSONObject("return_value");
            String rtnCode = returnValue.getString("rtn_code");
            if (!("S00000".equals(rtnCode))) {
                String errMsg = returnValue.getString("err_msg");
                log.error("服务商" + taxUnionpay.getUnionpayBankType().getDesc() + "银联查询平台对账文件查询失败: " + errMsg);
                continue;
            }

            String fileUrl = returnValue.getString("file_url");
            log.info("fileUrl: {}", fileUrl);

            fileUrlList.add(fileUrl);
        }

        if (fileUrlList.size() <= 0) {
            throw new CommonException(300, "暂无相应的平台对账文件");
        }

        //sftp下载文件
        SftpUtils sftp = null;
        try {
            sftp = new SftpUtils("47.99.58.100", "tax_read", "DWFwPe4DgXWxaBPX");
            sftp.connect();
            // 下载
            sftp.downLoadFile(fileUrlList, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sftp.disconnect();
        }
    }
}
