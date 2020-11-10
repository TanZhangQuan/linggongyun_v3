package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.*;
import com.example.merchant.dto.platform.RegulatorDto;
import com.example.merchant.dto.platform.RegulatorQueryDto;
import com.example.merchant.dto.platform.RegulatorTaxDto;
import com.example.merchant.dto.regulator.RegulatorMerchantDto;
import com.example.merchant.dto.regulator.RegulatorMerchantPaymentOrderDto;
import com.example.merchant.dto.regulator.RegulatorWorkerDto;
import com.example.merchant.dto.regulator.RegulatorWorkerPaymentDto;
import com.example.merchant.service.MerchantService;
import com.example.merchant.service.RegulatorService;
import com.example.merchant.service.RegulatorTaxService;
import com.example.merchant.service.TaxService;
import com.example.merchant.util.JwtUtils;
import com.example.merchant.vo.ExpressInfoVO;
import com.example.merchant.vo.PaymentOrderInfoVO;
import com.example.merchant.vo.TaxBriefVO;
import com.example.merchant.vo.platform.HomePageVO;
import com.example.merchant.vo.platform.RegulatorTaxVO;
import com.example.merchant.vo.regulator.*;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.mybatis.po.*;
import com.example.redis.dao.RedisDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 监管部门 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-25
 */
@Service
@Slf4j
public class RegulatorServiceImpl extends ServiceImpl<RegulatorDao, Regulator> implements RegulatorService {

    @Resource
    private TaxDao taxDao;

    @Resource
    private TaxService taxService;

    @Resource
    private RegulatorTaxService regulatorTaxService;

    @Resource
    private PaymentOrderDao paymentOrderDao;

    @Resource
    private PaymentOrderManyDao paymentOrderManyDao;

    @Resource
    private RegulatorDao regulatorDao;

    @Resource
    private MerchantService merchantService;

    @Resource
    private InvoiceDao invoiceDao;

    @Resource
    private CrowdSourcingInvoiceDao crowdSourcingInvoiceDao;

    @Resource
    private PaymentInventoryDao paymentInventoryDao;

    @Resource
    private RedisDao redisDao;

    @Resource
    private JwtUtils jwtUtils;

    @Value("${PWD_KEY}")
    private String PWD_KEY;

    @Value("${TOKEN}")
    private String TOKEN;

    /**
     * 添加监管部门
     *
     * @param regulatorDto
     * @return
     */
    @Override
    public ReturnJson addRegulator(RegulatorDto regulatorDto) {
        if (StringUtils.isBlank(regulatorDto.getConfirmPassWord()) || StringUtils.isBlank(regulatorDto.getPassWord())) {
            return ReturnJson.error("密码不能为空！");
        }
        if (!regulatorDto.getPassWord().equals(regulatorDto.getConfirmPassWord())) {
            return ReturnJson.error("输入的2次密码不一样，请重新输入！");
        }
        Regulator regulator = new Regulator();
        BeanUtils.copyProperties(regulatorDto, regulator);
        regulator.setPassWord(PWD_KEY + MD5.md5(regulatorDto.getPassWord()));
        this.save(regulator);
        return ReturnJson.success("添加监管部门成功！");
    }

    /**
     * 编辑监管部门
     *
     * @param regulatorDto
     * @return
     */
    @Override
    public ReturnJson updateRegulator(RegulatorDto regulatorDto) {
        Regulator regulator = new Regulator();
        BeanUtils.copyProperties(regulatorDto, regulator);
        if (!StringUtils.isBlank(regulatorDto.getPassWord())) {
            if (!regulatorDto.getPassWord().equals(regulatorDto.getConfirmPassWord())) {
                return ReturnJson.error("输入的2次密码不一样，请重新输入！");
            }
            regulator.setPassWord(PWD_KEY + MD5.md5(regulatorDto.getPassWord()));
        }
        if (!StringUtils.isBlank(regulatorDto.getConfirmPassWord())) {
            if (!regulatorDto.getConfirmPassWord().equals(regulatorDto.getPassWord())) {
                return ReturnJson.error("输入的2次密码不一样，请重新输入！");
            }
            regulator.setPassWord(PWD_KEY + MD5.md5(regulatorDto.getConfirmPassWord()));
        }
        boolean flag = this.updateById(regulator);
        if (flag) {
            return ReturnJson.success("编辑监管部门成功！");
        }
        return ReturnJson.error("编辑监管部门失败！");

    }

    /**
     * 按ID查询监管部门
     *
     * @param regulatorId
     * @return
     */
    @Override
    public ReturnJson getByRegulatorId(Long regulatorId) {
        Regulator regulator = this.getById(regulatorId);
        regulator.setPassWord("");
        return ReturnJson.success(regulator);
    }

    /**
     * 按条件查询监管部门
     *
     * @param regulatorQueryDto
     * @return
     */
    @Override
    public ReturnJson getRegulatorQuery(RegulatorQueryDto regulatorQueryDto) {
        Page<Regulator> regulatorPage = new Page<>(regulatorQueryDto.getPage(), regulatorQueryDto.getPageSize());
        IPage<Regulator> regulatorIPage = regulatorDao.selectRegulator(regulatorPage, regulatorQueryDto.getRegulatorName(), regulatorQueryDto.getStartDate(), regulatorQueryDto.getEndDate());
        return ReturnJson.success(regulatorIPage);
    }

    /**
     * 查询服务商
     *
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public ReturnJson getTaxAll(Integer page, Integer pageSize) {
        Page taxPage = new Page<>(page, pageSize);
        taxPage = taxDao.selectPage(taxPage, new QueryWrapper<Tax>().eq("tax_status", 0));
        List<Tax> records = taxPage.getRecords();
        List<TaxBriefVO> taxBriefVOS = new ArrayList<>();
        records.forEach(tax -> {
            TaxBriefVO taxBriefVO = new TaxBriefVO();
            BeanUtils.copyProperties(tax,taxBriefVO);
            taxBriefVOS.add(taxBriefVO);
        });
        taxPage.setRecords(taxBriefVOS);
        return ReturnJson.success(taxPage);
    }

    /**
     * 添加监管服务商
     *
     * @param regulatorTaxDtos
     * @return
     */
    @Override
    public ReturnJson addRegulatorTax(List<RegulatorTaxDto> regulatorTaxDtos) {
        List<RegulatorTax> regulatorTaxes = new ArrayList<>();
        for (RegulatorTaxDto regulatorTaxDto : regulatorTaxDtos) {
            RegulatorTax regulatorTax = new RegulatorTax();
            BeanUtils.copyProperties(regulatorTaxDto, regulatorTax);
            regulatorTaxes.add(regulatorTax);
        }
        boolean flag = regulatorTaxService.saveBatch(regulatorTaxes);
        if (flag) {
            return ReturnJson.success("添加监管服务商成功！");
        }
        return ReturnJson.error("添加监管服务商失败！");
    }

    /**
     * 查询监管部门监管的服务商交易统计
     *
     * @param regulatorId
     * @return
     */
    @Override
    public ReturnJson getRegultorPaymentCount(String regulatorId) {
        List<String> taxIds = new ArrayList<>();
        List<RegulatorTax> regulatorTaxes = regulatorTaxService.list(new QueryWrapper<RegulatorTax>().eq("regulator_id", regulatorId));
        for (RegulatorTax regulatorTax : regulatorTaxes) {
            taxIds.add(regulatorTax.getTaxId());
        }
        HomePageVO homePageVO = new HomePageVO();
        try {
            BigDecimal total30DayMoney = paymentOrderDao.selectBy30DayPaasRegulator(taxIds);
            homePageVO.setPayment30TotalMoney(total30DayMoney);
            BigDecimal totalMoney = paymentOrderDao.selectTotalPaasRegulator(taxIds);
            homePageVO.setPaymentTotalMoney(totalMoney);

            BigDecimal many30DayMoney = paymentOrderManyDao.selectBy30DayPaasRegulator(taxIds);
            homePageVO.setPayment30ManyMoney(many30DayMoney);
            BigDecimal manyMoney = paymentOrderManyDao.selectTotalPaasRegulator(taxIds);
            homePageVO.setPaymentManyMoney(manyMoney);

            InvoicePO totalInvoice = invoiceDao.selectInvoiceMoneyPaasRegultor(taxIds);
            homePageVO.setInvoiceTotalCount(totalInvoice.getCount());
            homePageVO.setInvoiceTotalMoney(totalInvoice.getTotalMoney());

            InvoicePO manyInvoice = crowdSourcingInvoiceDao.selectCrowdInvoiceMoneyPaasRegultor(taxIds);
            homePageVO.setInvoiceManyCount(manyInvoice.getCount());
            homePageVO.setInvoiceManyMoney(manyInvoice.getTotalMoney());
            homePageVO.setTaxTotal(regulatorTaxes.size());
        } catch (Exception e) {
            log.error(e.toString());
        }
        //获取10条具体的交易流水
        ReturnJson returnJson = this.getRegulatorTaxAll(regulatorId, 1, 10);
        returnJson.setObj(homePageVO);
        return returnJson;
    }

    /**
     * 查看监管服务商
     *
     * @param regulatorId
     * @return
     */
    @Override
    public ReturnJson getRegulatorTaxAll(String regulatorId, Integer page, Integer pageSize) {
        List<RegulatorTaxVO> regulatorTaxVOS = new ArrayList<>();
        Page<RegulatorTax> taxPage = new Page<>(page, pageSize);
        Page<RegulatorTax> regulatorTaxPage = regulatorTaxService.page(taxPage, new QueryWrapper<RegulatorTax>().eq("regulator_id", regulatorId));
        List<RegulatorTax> regulatorTaxes = regulatorTaxPage.getRecords();
        ReturnJson returnJson = ReturnJson.success(regulatorTaxPage);
        for (RegulatorTax regulatorTax : regulatorTaxes) {
            Tax tax = taxDao.selectById(regulatorTax.getTaxId());
            RegulatorTaxVO regulatorTaxVO = new RegulatorTaxVO();
            List<PaymentOrder> paymentOrders = paymentOrderDao.selectList(new QueryWrapper<PaymentOrder>().eq("tax_id", tax.getId()).ge("payment_order_status", 2));
            BigDecimal totalTab = new BigDecimal("0");
            for (PaymentOrder paymentOrder : paymentOrders) {
                totalTab = totalTab.add(paymentOrder.getRealMoney());
                log.info(totalTab.toString());
            }
            BigDecimal manyTab = new BigDecimal("0");
            List<PaymentOrderMany> paymentOrderManies = paymentOrderManyDao.selectList(new QueryWrapper<PaymentOrderMany>().eq("tax_id", tax.getId()).ge("payment_order_status", 2));
            for (PaymentOrderMany paymentOrderMany : paymentOrderManies) {
                manyTab = manyTab.add(paymentOrderMany.getRealMoney());
                log.info(manyTab.toString());
            }
            regulatorTaxVO.setTaxId(tax.getId());
            regulatorTaxVO.setTaxName(tax.getTaxName());
            regulatorTaxVO.setStatus(regulatorTax.getStatus());
            regulatorTaxVO.setStartRegulatorDate(regulatorTax.getCreateDate());
            regulatorTaxVO.setTotalTab(totalTab);
            regulatorTaxVO.setManyTba(manyTab);
            regulatorTaxVOS.add(regulatorTaxVO);
        }
        returnJson.setData(regulatorTaxVOS);
        return returnJson;
    }

    /**
     * 查看监管服务商的成交明细
     *
     * @param taxId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public ReturnJson getRegulatorTaxPaymentList(String taxId, Integer page, Integer pageSize) {
        return taxService.transactionRecord(taxId, page, pageSize);
    }

    /**
     * 查看成交订单
     *
     * @param paymentOrderId
     * @param packageStatus
     * @return
     */
    @Override
    public ReturnJson getPaymentInfo(String paymentOrderId, Integer packageStatus) {
        return merchantService.getMerchantPaymentInfo(paymentOrderId, packageStatus);
    }

    /**
     * 查看支付清单
     *
     * @param paymentOrderId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public ReturnJson getPaymentInventoryInfo(String paymentOrderId, Integer page, Integer pageSize) {
        return merchantService.getMerchantPaymentInventory(paymentOrderId, page, pageSize);
    }

    /**
     * 按条件查询所监管的创客
     *
     * @param regulatorWorkerDto
     * @return
     */
    @Override
    public ReturnJson getRegulatorWorker(RegulatorWorkerDto regulatorWorkerDto, String regulatorId) {
        ReturnJson result = this.getPaymentOrderIds(regulatorId);
        if (result.getCode() == 300) {
            return result;
        }
        List<String> paymentOrderIds = (List<String>) result.getData();
        if (VerificationCheck.listIsNull(paymentOrderIds)) {
            return ReturnJson.error("您所监管的服务商还没产生过流水！");
        }
        Page<RegulatorWorkerPO> regulatorWorkerPOPage = new Page<>(regulatorWorkerDto.getPage(), regulatorWorkerDto.getPageSize());
        IPage<RegulatorWorkerPO> regulatorWorkerPOIPage = regulatorDao.selectRegulatorWorker(regulatorWorkerPOPage,
                regulatorWorkerDto.getWorkerId(), regulatorWorkerDto.getWorkerName(), regulatorWorkerDto.getIdCardCode(),
                paymentOrderIds, regulatorWorkerDto.getStartDate(), regulatorWorkerDto.getEndDate());
        ReturnJson returnJson = ReturnJson.success(regulatorWorkerPOIPage);
        List<RegulatorWorkerPO> regulatorWorkerPOS = regulatorWorkerPOIPage.getRecords();
        List<RegulatorWorkerVO> regulatorWorkerVOS = new ArrayList<>();
        for (RegulatorWorkerPO regulatorWorkerPO : regulatorWorkerPOS) {
            RegulatorWorkerVO regulatorWorkerVO = new RegulatorWorkerVO();
            BeanUtils.copyProperties(regulatorWorkerPO, regulatorWorkerVO);
            regulatorWorkerVO.setAttestation(regulatorWorkerPO.getAttestation() == 0 ? "未认证" : "已认证");
            regulatorWorkerVO.setOrderCount(regulatorWorkerPO.getTotalOrderCount() + "/" + regulatorWorkerPO.getManyOrderCount());
            regulatorWorkerVOS.add(regulatorWorkerVO);
        }
        returnJson.setData(regulatorWorkerVOS);
        return returnJson;
    }

    /**
     * 导出创客
     *
     * @param workerIds
     * @return
     */
    @Override
    public ReturnJson exportRegulatorWorker(String workerIds, String regulatorId, HttpServletResponse response) {
        ReturnJson result = this.getPaymentOrderIds(regulatorId);
        if (result.getCode() == 300) {
            return result;
        }
        List<String> paymentOrderIds = (List<String>) result.getData();
        if (VerificationCheck.listIsNull(paymentOrderIds)) {
            return ReturnJson.error("您所监管的服务商还没产生过流水！");
        }
        List<RegulatorWorkerPO> regulatorWorkerPOS = regulatorDao.selectExportRegulatorWorker(Arrays.asList(workerIds.split(",")), paymentOrderIds);
        List<RegulatorWorkerVO> regulatorWorkerVOS = new ArrayList<>();
        for (RegulatorWorkerPO regulatorWorkerPO : regulatorWorkerPOS) {
            RegulatorWorkerVO regulatorWorkerVO = new RegulatorWorkerVO();
            BeanUtils.copyProperties(regulatorWorkerPO, regulatorWorkerVO);
            regulatorWorkerVO.setAttestation(regulatorWorkerPO.getAttestation() == 0 ? "未认证" : "已认证");
            regulatorWorkerVO.setOrderCount(regulatorWorkerPO.getTotalOrderCount() + "/" + regulatorWorkerPO.getManyOrderCount());
            regulatorWorkerVOS.add(regulatorWorkerVO);
        }
        if (!VerificationCheck.listIsNull(regulatorWorkerVOS)) {
            try {
                ExcelUtils.exportExcel(regulatorWorkerVOS, "创客交易流水信息", "流水信息", RegulatorWorkerVO.class, "RegulatorWorker", true, response);
                return ReturnJson.success("创客导出成功！");
            } catch (IOException e) {
                log.error(e.toString() + ":" + e.getMessage());
            }
        }
        return ReturnJson.error("创客导出失败！");
    }

    /**
     * 获取所监管的创客的流水统计
     *
     * @param regulatorId
     * @return
     */
    @Override
    public ReturnJson countRegulatorWorker(String regulatorId) {
        ReturnJson result = this.getPaymentOrderIds(regulatorId);
        if (result.getCode() == 300) {
            return result;
        }
        List<String> paymentOrderIds = (List<String>) result.getData();
        if (VerificationCheck.listIsNull(paymentOrderIds)) {
            return ReturnJson.error("您所监管的服务商还没产生过流水！");
        }
        List<PaymentInventory> paymentInventories = paymentInventoryDao.selectList(new QueryWrapper<PaymentInventory>().in("payment_order_id", paymentOrderIds));
        Integer totalOrderCount = 0;
        BigDecimal totalMoney = new BigDecimal(0);
        BigDecimal totalTaxMoney = new BigDecimal(0);
        Integer manyOrderCount = 0;
        BigDecimal manyMoney = new BigDecimal(0);
        BigDecimal manyTaxMoney = new BigDecimal(0);
        Set<String> set = new HashSet<>();
        for (PaymentInventory paymentInventory : paymentInventories) {
            if (paymentInventory.getPackageStatus() == 0) {
                totalMoney = totalMoney.add(paymentInventory.getRealMoney());
                totalTaxMoney = totalTaxMoney.add(paymentInventory.getTaxAmount());
                totalOrderCount++;
            } else {
                manyMoney = manyMoney.add(paymentInventory.getRealMoney());
                manyTaxMoney = manyTaxMoney.add(paymentInventory.getTaxAmount());
                manyOrderCount++;
            }
            set.add(paymentInventory.getWorkerId());
        }
        Integer countWorker = set.size();
        CountRegulatorWorkerVO countRegulatorWorkerVO = new CountRegulatorWorkerVO(countWorker, totalOrderCount, totalMoney, totalTaxMoney, manyOrderCount, manyMoney, manyTaxMoney);
        return ReturnJson.success(countRegulatorWorkerVO);
    }

    @Resource
    private WorkerDao workerDao;

    /**
     * 获取所监管的创客的详情
     *
     * @param regulatorId
     * @param workerId
     * @return
     */
    @Override
    public ReturnJson countRegulatorWorkerInfo(String regulatorId, String workerId) {
        Worker worker = workerDao.selectById(workerId);
        if (worker == null) {
            return ReturnJson.error("您输入的创客ID不存在！");
        }
        worker.setUserPwd("");
        ReturnJson result = this.getPaymentOrderIds(regulatorId);
        if (result.getCode() == 300) {
            return result;
        }
        List<String> paymentOrderIds = (List<String>) result.getData();
        if (VerificationCheck.listIsNull(paymentOrderIds)) {
            return ReturnJson.error("您所监管的服务商还没产生过流水！");
        }
        List<String> workerIds = new ArrayList<>();
        workerIds.add(workerId);
        List<RegulatorWorkerPO> regulatorWorkerPOS = regulatorDao.selectExportRegulatorWorker(workerIds, paymentOrderIds);
        RegulatorWorkerPO regulatorWorkerPO = regulatorWorkerPOS.get(0);
        CountSingleRegulatorWorkerVO countSingleRegulatorWorkerVO = new CountSingleRegulatorWorkerVO();
        BeanUtils.copyProperties(regulatorWorkerPO, countSingleRegulatorWorkerVO);
        Page<WorekerPaymentListPo> worekerPaymentListPoPage = new Page<>(1, 10);
        IPage<WorekerPaymentListPo> worekerPaymentListPoIPage = workerDao.regulatorWorkerPaymentList(worekerPaymentListPoPage, workerId, paymentOrderIds);
        CountRegulatorWorkerInfoVO countRegulatorWorkerInfoVO = new CountRegulatorWorkerInfoVO();
        countRegulatorWorkerInfoVO.setCountSingleRegulatorWorkerVO(countSingleRegulatorWorkerVO);
        countRegulatorWorkerInfoVO.setWorekerPaymentListPos(worekerPaymentListPoIPage.getRecords());
        countRegulatorWorkerInfoVO.setWorker(worker);
        return ReturnJson.success(countRegulatorWorkerInfoVO);
    }

    /**
     * 查询所监管创客的支付明细
     *
     * @param regulatorWorkerPaymentDto
     * @return
     */
    @Override
    public ReturnJson getRegulatorWorkerPaymentInfo(RegulatorWorkerPaymentDto regulatorWorkerPaymentDto, String regulatorId) {
        ReturnJson result = this.getPaymentOrderIds(regulatorId);
        if (result.getCode() == 300) {
            return result;
        }
        List<String> paymentOrderIds = (List<String>) result.getData();
        if (VerificationCheck.listIsNull(paymentOrderIds)) {
            return ReturnJson.error("您所监管的服务商还没产生过流水！");
        }
        Page<WorekerPaymentListPo> paymentListPoPage = new Page<>(regulatorWorkerPaymentDto.getPage(), regulatorWorkerPaymentDto.getPageSize());
        IPage<WorekerPaymentListPo> worekerPaymentListPoIPage = workerDao.selectRegulatorWorkerPaymentInfo(paymentListPoPage,
                paymentOrderIds, regulatorWorkerPaymentDto.getWorkerId(), regulatorWorkerPaymentDto.getCompanyName(),
                regulatorWorkerPaymentDto.getTaxName(), regulatorWorkerPaymentDto.getStartDate(), regulatorWorkerPaymentDto.getEndDate());
        List<RegulatorWorkerPaymentInfoVO> regulatorWorkerPaymentInfoVOS = new ArrayList<>();
        List<WorekerPaymentListPo> records = worekerPaymentListPoIPage.getRecords();
        for (WorekerPaymentListPo worekerPaymentListPo : records) {
            RegulatorWorkerPaymentInfoVO regulatorWorkerPaymentInfoVO = new RegulatorWorkerPaymentInfoVO();
            BeanUtils.copyProperties(worekerPaymentListPo, regulatorWorkerPaymentInfoVO);
            regulatorWorkerPaymentInfoVO.setPackageStatus(worekerPaymentListPo.getPackageStatus() == 0 ? "总包+分包" : "众包");
            regulatorWorkerPaymentInfoVO.setInvoiceStatus(worekerPaymentListPo.getInvoiceStatus() == 0 ? "未开票" : "已完成");
            regulatorWorkerPaymentInfoVOS.add(regulatorWorkerPaymentInfoVO);
        }
        ReturnJson returnJson = ReturnJson.success(worekerPaymentListPoIPage);
        returnJson.setData(regulatorWorkerPaymentInfoVOS);
        return returnJson;
    }

    /**
     * 导出所监管的创客支付明细
     *
     * @param workerId
     * @param paymentIds
     * @param response
     * @return
     */
    @Override
    public ReturnJson exportRegulatorWorkerPaymentInfo(String workerId, String paymentIds, HttpServletResponse response) {
        List<WorekerPaymentListPo> worekerPaymentListPos = workerDao.exportRegulatorWorkerPaymentInfo(Arrays.asList(paymentIds.split(",")), workerId);
        if (VerificationCheck.listIsNull(worekerPaymentListPos)) {
            return ReturnJson.error("订单有误，请重试！");
        }
        List<RegulatorWorkerPaymentInfoVO> regulatorWorkerPaymentInfoVOS = new ArrayList<>();
        for (WorekerPaymentListPo worekerPaymentListPo : worekerPaymentListPos) {
            RegulatorWorkerPaymentInfoVO regulatorWorkerPaymentInfoVO = new RegulatorWorkerPaymentInfoVO();
            BeanUtils.copyProperties(worekerPaymentListPo, regulatorWorkerPaymentInfoVO);
            regulatorWorkerPaymentInfoVO.setPackageStatus(worekerPaymentListPo.getPackageStatus() == 0 ? "总包+分包" : "众包");
            regulatorWorkerPaymentInfoVO.setInvoiceStatus(worekerPaymentListPo.getInvoiceStatus() == 0 ? "未开票" : "已完成");
            regulatorWorkerPaymentInfoVOS.add(regulatorWorkerPaymentInfoVO);
        }
        try {
            ExcelUtils.exportExcel(regulatorWorkerPaymentInfoVOS, "创客支付明细", "创客支付明细", RegulatorWorkerPaymentInfoVO.class, "RegulatorWorkerPaymentInfo", true, response);
            return ReturnJson.success("导出成功！");
        } catch (IOException e) {
            log.error(e.toString() + ":" + e.getMessage());
        }
        return ReturnJson.error("导出失败！");
    }

    /**
     * 查询支付订单详情
     *
     * @param workerId
     * @param paymentId
     * @param packageStatus
     * @return
     */
    @Override
    public ReturnJson getPaymentOrderInfo(String workerId, String paymentId, Integer packageStatus) {
        PaymentOrderInfoPO paymentOrderInfoPO = null;
        PaymentOrderInfoVO paymentOrderInfoVO = new PaymentOrderInfoVO();
        ExpressInfoVO expressInfoVO = new ExpressInfoVO();
        if (packageStatus == 0) {
            //为总包订单
            paymentOrderInfoPO = paymentOrderDao.selectPaymentOrderInfo(paymentId);
            if (paymentOrderInfoPO == null) {
                return ReturnJson.error("订单编号有误，请重新输入！");
            }
            InvoiceInfoPO invoiceInfoPO = invoiceDao.selectInvoiceInfoPO(paymentId);
            if (invoiceInfoPO != null) {
                //总包发票信息
                paymentOrderInfoVO.setInvoice(invoiceInfoPO.getInvoiceUrl());
                paymentOrderInfoVO.setSubpackageInvoice(invoiceInfoPO.getMakerInvoiceUrl());
                expressInfoVO.setExpressCompanyName(invoiceInfoPO.getExpressCompanyName());
                expressInfoVO.setExpressCode(invoiceInfoPO.getExpressSheetNo());
                List<ExpressLogisticsInfo> expressLogisticsInfos = KdniaoTrackQueryAPI.getExpressInfo(invoiceInfoPO.getExpressCompanyName(), invoiceInfoPO.getExpressSheetNo());
                expressInfoVO.setExpressLogisticsInfos(expressLogisticsInfos);
            }
        } else {
            //为众包订单
            paymentOrderInfoPO = paymentOrderManyDao.selectPaymentOrderInfo(paymentId);
            if (paymentOrderInfoPO == null) {
                return ReturnJson.error("订单编号有误，请重新输入！");
            }
            InvoiceInfoPO invoiceInfoPO = crowdSourcingInvoiceDao.selectInvoiceInfoPO(paymentId);
            if (invoiceInfoPO != null) {
                //众包发票信息
                paymentOrderInfoVO.setInvoice(invoiceInfoPO.getInvoiceUrl());
                expressInfoVO.setExpressCompanyName(invoiceInfoPO.getExpressCompanyName());
                expressInfoVO.setExpressCode(invoiceInfoPO.getExpressSheetNo());
                List<ExpressLogisticsInfo> expressLogisticsInfos = KdniaoTrackQueryAPI.getExpressInfo(invoiceInfoPO.getExpressCompanyName(), invoiceInfoPO.getExpressSheetNo());
                expressInfoVO.setExpressLogisticsInfos(expressLogisticsInfos);
            }
        }
        List<PaymentInventory> paymentInventories = paymentInventoryDao.selectPaymentInventoryList(paymentId, workerId);
        paymentOrderInfoVO.setPaymentInventories(paymentInventories);
        paymentOrderInfoVO.setPaymentOrderInfoPO(paymentOrderInfoPO);
        paymentOrderInfoVO.setExpressInfoVO(expressInfoVO);
        return ReturnJson.success(paymentOrderInfoVO);
    }

    /**
     * 分页查询支付明细
     *
     * @param paymentOrderId
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public ReturnJson getPaymentInventory(String paymentOrderId, Integer page, Integer pageSize) {
        Page<PaymentInventory> paymentInventoryPage = new Page<>(page, pageSize);
        paymentInventoryPage = paymentInventoryDao.selectPage(paymentInventoryPage, new QueryWrapper<PaymentInventory>().eq("payment_order_id", paymentOrderId));
        return ReturnJson.success(paymentInventoryPage);
    }

    /**
     * 按条件查询所监管的商户
     *
     * @param regulatorMerchantDto
     * @return
     */
    @Override
    public ReturnJson getRegulatorMerchant(RegulatorMerchantDto regulatorMerchantDto, String regulatorId) {
        ReturnJson returnJson = this.getTaxIds(regulatorId);
        if (returnJson.getCode() == 300) {
            return returnJson;
        }
        List<String> taxIds = (List<String>) returnJson.getData();
        Page<RegulatorMerchantInfoPO> regulatorMerchantInfoPOPage = new Page<>(regulatorMerchantDto.getPage(), regulatorMerchantDto.getPageSize());
        IPage<RegulatorMerchantInfoPO> regulatorMerchantInfoPOIPage = regulatorDao.selectRegulatorMerchant(regulatorMerchantInfoPOPage, taxIds, regulatorMerchantDto.getCompanyId(),
                regulatorMerchantDto.getCompanyName(), regulatorMerchantDto.getStartDate(), regulatorMerchantDto.getEndDate());
        List<RegulatorMerchantInfoPO> records = regulatorMerchantInfoPOIPage.getRecords();
        List<RegulatorMerchantVO> regulatorMerchantVOS = new ArrayList<>();
        for (RegulatorMerchantInfoPO regulatorMerchantInfoPO : records) {
            RegulatorMerchantVO regulatorMerchantVO = new RegulatorMerchantVO();
            BeanUtils.copyProperties(regulatorMerchantInfoPO, regulatorMerchantVO);
            regulatorMerchantVO.setOrderCount(regulatorMerchantInfoPO.getCountTotalOrder() + "/" + regulatorMerchantInfoPO.getCountManyOrder());
            regulatorMerchantVO.setAuditStatus(regulatorMerchantInfoPO.getAuditStatus() == 0 ? "正常" : "停用");
            regulatorMerchantVOS.add(regulatorMerchantVO);
        }
        ReturnJson success = ReturnJson.success(regulatorMerchantInfoPOIPage);
        success.setData(regulatorMerchantVOS);
        return success;
    }

    /**
     * 导出所监管的商户
     *
     * @param companyIds
     * @param regulatorId
     * @param response
     * @return
     */
    @Override
    public ReturnJson exportRegulatorMerchant(String companyIds, String regulatorId, HttpServletResponse response) {
        ReturnJson returnJson = this.getTaxIds(regulatorId);
        if (returnJson.getCode() == 300) {
            return returnJson;
        }
        List<String> taxIds = (List<String>) returnJson.getData();
        List<RegulatorMerchantInfoPO> regulatorMerchantInfoPOS = regulatorDao.selectExportRegulatorMerchant(Arrays.asList(companyIds.split(",")), taxIds);
        List<RegulatorMerchantVO> regulatorMerchantVOS = new ArrayList<>();
        for (RegulatorMerchantInfoPO regulatorMerchantInfoPO : regulatorMerchantInfoPOS) {
            RegulatorMerchantVO regulatorMerchantVO = new RegulatorMerchantVO();
            BeanUtils.copyProperties(regulatorMerchantInfoPO, regulatorMerchantVO);
            regulatorMerchantVO.setOrderCount(regulatorMerchantInfoPO.getCountTotalOrder() + "/" + regulatorMerchantInfoPO.getCountManyOrder());
            regulatorMerchantVO.setAuditStatus(regulatorMerchantInfoPO.getAuditStatus() == 0 ? "正常" : "停用");
            regulatorMerchantVOS.add(regulatorMerchantVO);
        }
        if (VerificationCheck.listIsNull(regulatorMerchantVOS)) {
            log.error("导出的数据为空！");
            return ReturnJson.error("导出失败，请重试！");
        }
        try {
            ExcelUtils.exportExcel(regulatorMerchantVOS, "监管的商户", "商户信息", RegulatorMerchantVO.class, "RegulatorMerchant", true, response);
        } catch (IOException e) {
            log.error(e.toString() + ":" + e.getMessage());
            return ReturnJson.error("导出失败，请重试！");
        }
        return ReturnJson.success("");
    }

    @Resource
    private CompanyTaxDao companyTaxDao;

    /**
     * 统计所监管的商户的流水
     *
     * @param regulatorId
     * @return
     */
    @Override
    public ReturnJson getCountRegulatorMerchant(String regulatorId) {
        ReturnJson returnJson = this.getTaxIds(regulatorId);
        if (returnJson.getCode() == 300) {
            return returnJson;
        }
        List<String> taxIds = (List<String>) returnJson.getData();
        List<String> paymentOrderIds = new ArrayList<>();
        List<PaymentOrder> paymentOrders = paymentOrderDao.selectList(new QueryWrapper<PaymentOrder>().in("tax_id", taxIds).ge("payment_order_status", 2));
        Integer totalOrderCount = paymentOrders.size();
        BigDecimal totalMoney = new BigDecimal(0);
        for (PaymentOrder paymentOrder : paymentOrders) {
            totalMoney = totalMoney.add(paymentOrder.getRealMoney());
            paymentOrderIds.add(paymentOrder.getId());
        }

        List<PaymentOrderMany> paymentOrderManies = paymentOrderManyDao.selectList(new QueryWrapper<PaymentOrderMany>().in("tax_id", taxIds).ge("payment_order_status", 2));
        Integer manyOrderCount = paymentOrderManies.size();
        BigDecimal manyMoney = new BigDecimal(0);
        for (PaymentOrderMany paymentOrderMany : paymentOrderManies) {
            manyMoney = manyMoney.add(paymentOrderMany.getRealMoney());
            paymentOrderIds.add(paymentOrderMany.getId());
        }

        List<PaymentInventory> paymentInventories = paymentInventoryDao.selectList(new QueryWrapper<PaymentInventory>().in("payment_order_id", paymentOrderIds));
        BigDecimal manyTaxMoney = new BigDecimal(0);
        BigDecimal totalTaxMoney = new BigDecimal(0);
        if (!VerificationCheck.listIsNull(paymentInventories)) {
            for (PaymentInventory paymentInventory : paymentInventories) {
                if (paymentInventory.getPackageStatus() == 0) {
                    totalTaxMoney = totalTaxMoney.add(paymentInventory.getTaxAmount());
                } else {
                    manyTaxMoney = manyTaxMoney.add(paymentInventory.getTaxAmount());
                }
            }
        }
        Set<String> companyIds = new HashSet<>();
        List<CompanyTax> companyTaxes = companyTaxDao.selectList(new QueryWrapper<CompanyTax>().in("tax_id", taxIds));
        for (CompanyTax companyTax : companyTaxes) {
            companyIds.add(companyTax.getCompanyId());
        }
        Integer countMerchant = companyIds.size();

        CountRegulatorMerchantVO countRegulatorMerchantVO = new CountRegulatorMerchantVO(countMerchant, totalOrderCount, totalMoney, totalTaxMoney, manyOrderCount, manyMoney, manyTaxMoney);
        return ReturnJson.success(countRegulatorMerchantVO);
    }


    @Resource
    private CompanyInfoDao companyInfoDao;

    /**
     * 查询所监管的公司详情
     *
     * @param companyId
     * @param regulatorId
     * @return
     */
    @Override
    public ReturnJson getRegulatorMerchantParticulars(String companyId, String regulatorId) {

        CompanyInfo companyInfo = companyInfoDao.selectById(companyId);
        if (companyInfo == null) {
            return ReturnJson.error("输入的公司不存在！");
        }
        RegulatorMerchantInfoVO regulatorMerchantInfoVO = new RegulatorMerchantInfoVO();
        BeanUtils.copyProperties(companyInfo, regulatorMerchantInfoVO);
        regulatorMerchantInfoVO.setCompanyStatus(companyInfo.getCompanyStatus() == 0 ? "正常" : "停用");
        ReturnJson returnJson = this.getTaxIds(regulatorId);
        if (returnJson.getCode() == 300) {
            return returnJson;
        }
        List<String> taxIds = (List<String>) returnJson.getData();
        List<String> paymentOrderIds = new ArrayList<>();
        List<PaymentOrder> paymentOrders = paymentOrderDao.selectList(new QueryWrapper<PaymentOrder>().in("tax_id", taxIds).ge("payment_order_status", 2).eq("company_id", companyId));
        Integer totalOrderCount = paymentOrders.size();
        BigDecimal totalMoney = new BigDecimal(0);
        for (PaymentOrder paymentOrder : paymentOrders) {
            totalMoney = totalMoney.add(paymentOrder.getRealMoney());
            paymentOrderIds.add(paymentOrder.getId());
        }

        List<PaymentOrderMany> paymentOrderManies = paymentOrderManyDao.selectList(new QueryWrapper<PaymentOrderMany>().in("tax_id", taxIds).ge("payment_order_status", 2).eq("company_id", companyId));
        Integer manyOrderCount = paymentOrderManies.size();
        BigDecimal manyMoney = new BigDecimal(0);
        for (PaymentOrderMany paymentOrderMany : paymentOrderManies) {
            manyMoney = manyMoney.add(paymentOrderMany.getRealMoney());
            paymentOrderIds.add(paymentOrderMany.getId());
        }

        List<PaymentInventory> paymentInventories = paymentInventoryDao.selectList(new QueryWrapper<PaymentInventory>().in("payment_order_id", paymentOrderIds));
        BigDecimal manyTaxMoney = new BigDecimal(0);
        BigDecimal totalTaxMoney = new BigDecimal(0);
        if (!VerificationCheck.listIsNull(paymentInventories)) {
            for (PaymentInventory paymentInventory : paymentInventories) {
                if (paymentInventory.getPackageStatus() == 0) {
                    totalTaxMoney = totalTaxMoney.add(paymentInventory.getTaxAmount());
                } else {
                    manyTaxMoney = manyTaxMoney.add(paymentInventory.getTaxAmount());
                }
            }
        }
        CountSingleRegulatorMerchantVO countSingleRegulatorMerchantVO = new CountSingleRegulatorMerchantVO(companyInfo.getCompanyName(), totalOrderCount, totalMoney, totalTaxMoney, manyOrderCount, manyMoney, manyTaxMoney);

        IPage<CompanyPaymentOrderPO> companyPaymentOrderPOIPage = companyInfoDao.selectCompanyPaymentOrder(new Page(1, 10), taxIds, companyId, null, null, null);
        List<CompanyPaymentOrderPO> companyPaymentOrderPOS = companyPaymentOrderPOIPage.getRecords();
        List<RegulatorMerchantPaymentOrderVO> regulatorMerchantPaymentOrderVOS = new ArrayList<>();

        for (CompanyPaymentOrderPO companyPaymentOrderPO : companyPaymentOrderPOS) {
            RegulatorMerchantPaymentOrderVO regulatorMerchantPaymentOrderVO = new RegulatorMerchantPaymentOrderVO();
            BeanUtils.copyProperties(companyPaymentOrderPO, regulatorMerchantPaymentOrderVO);
            regulatorMerchantPaymentOrderVO.setPackageStatus(companyPaymentOrderPO.getPackageStatus() == 0 ? "总包+分包" : "众包");
            regulatorMerchantPaymentOrderVO.setIsInvoice(companyPaymentOrderPO.getIsInvoice() == 0 ? "未开票" : "已完成");
            regulatorMerchantPaymentOrderVOS.add(regulatorMerchantPaymentOrderVO);
        }

        RegulatorMerchantParticularsVO regulatorMerchantParticularsVO = new RegulatorMerchantParticularsVO();
        regulatorMerchantParticularsVO.setCountSingleRegulatorMerchantVO(countSingleRegulatorMerchantVO);
        regulatorMerchantParticularsVO.setRegulatorMerchantPaymentOrderVOS(regulatorMerchantPaymentOrderVOS);
        regulatorMerchantParticularsVO.setRegulatorMerchantInfoVO(regulatorMerchantInfoVO);
        return ReturnJson.success(regulatorMerchantParticularsVO);
    }

    /**
     * 查询所监管商户的支付订单
     *
     * @param regulatorMerchantPaymentOrderDto
     * @return
     */
    @Override
    public ReturnJson getRegulatorMerchantPaymentOrder(RegulatorMerchantPaymentOrderDto regulatorMerchantPaymentOrderDto, String regulatorId) {
        ReturnJson returnJson = this.getTaxIds(regulatorId);
        if (returnJson.getCode() == 300) {
            return returnJson;
        }
        List<String> taxIds = (List<String>) returnJson.getData();
        Page<CompanyPaymentOrderPO> page = new Page(regulatorMerchantPaymentOrderDto.getPage(), regulatorMerchantPaymentOrderDto.getPageSize());
        IPage<CompanyPaymentOrderPO> companyPaymentOrderPOIPage = companyInfoDao.selectCompanyPaymentOrder(page, taxIds, regulatorMerchantPaymentOrderDto.getCompanyId(),
                regulatorMerchantPaymentOrderDto.getTaxName(), regulatorMerchantPaymentOrderDto.getStartDate(), regulatorMerchantPaymentOrderDto.getEndDate());

        List<CompanyPaymentOrderPO> companyPaymentOrderPOS = companyPaymentOrderPOIPage.getRecords();
        List<RegulatorMerchantPaymentOrderVO> regulatorMerchantPaymentOrderVOS = new ArrayList<>();

        for (CompanyPaymentOrderPO companyPaymentOrderPO : companyPaymentOrderPOS) {
            RegulatorMerchantPaymentOrderVO regulatorMerchantPaymentOrderVO = new RegulatorMerchantPaymentOrderVO();
            BeanUtils.copyProperties(companyPaymentOrderPO, regulatorMerchantPaymentOrderVO);
            regulatorMerchantPaymentOrderVO.setPackageStatus(companyPaymentOrderPO.getPackageStatus() == 0 ? "总包+分包" : "众包");
            regulatorMerchantPaymentOrderVO.setIsInvoice(companyPaymentOrderPO.getIsInvoice() == 0 ? "未开票" : "已完成");
            regulatorMerchantPaymentOrderVOS.add(regulatorMerchantPaymentOrderVO);
        }
        ReturnJson success = ReturnJson.success(companyPaymentOrderPOIPage);
        success.setData(regulatorMerchantPaymentOrderVOS);
        return success;
    }

    /**
     * 导出所监管商户的支付订单
     *
     * @param paymentOrderIds
     * @return
     */
    @Override
    public ReturnJson exportRegulatorMerchantPaymentOrder(String paymentOrderIds, HttpServletResponse response) {
        List<CompanyPaymentOrderPO> companyPaymentOrderPOS = companyInfoDao.exportCompanyPaymentOrder(Arrays.asList(paymentOrderIds.split(",")));

        if (VerificationCheck.listIsNull(companyPaymentOrderPOS)) {
            log.error("数据库没有导出的数据！");
            ReturnJson.error("导出失败，请重试！");
        }

        List<RegulatorMerchantPaymentOrderVO> regulatorMerchantPaymentOrderVOS = new ArrayList<>();
        for (CompanyPaymentOrderPO companyPaymentOrderPO : companyPaymentOrderPOS) {
            RegulatorMerchantPaymentOrderVO regulatorMerchantPaymentOrderVO = new RegulatorMerchantPaymentOrderVO();
            BeanUtils.copyProperties(companyPaymentOrderPO, regulatorMerchantPaymentOrderVO);
            regulatorMerchantPaymentOrderVO.setPackageStatus(companyPaymentOrderPO.getPackageStatus() == 0 ? "总包+分包" : "众包");
            regulatorMerchantPaymentOrderVO.setIsInvoice(companyPaymentOrderPO.getIsInvoice() == 0 ? "未开票" : "已完成");
            regulatorMerchantPaymentOrderVOS.add(regulatorMerchantPaymentOrderVO);
        }
        try {
            ExcelUtils.exportExcel(regulatorMerchantPaymentOrderVOS, "商户支付订单", "订单信息", RegulatorMerchantPaymentOrderVO.class, "MerchantPaymentOrder", true, response);
        } catch (IOException e) {
            log.error(e + ":" + e.getMessage());
            ReturnJson.error("导出失败，请重试！");
        }
        return null;
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @param response
     * @return
     */
    @Override
    public ReturnJson regulatorLogin(String username, String password, HttpServletResponse response) {
        String encryptPWD = PWD_KEY + MD5.md5(password);
//        Subject currentUser = SecurityUtils.getSubject();
        QueryWrapper<Regulator> merchantQueryWrapper = new QueryWrapper<>();
        merchantQueryWrapper.eq("user_name", username).eq("pass_word", encryptPWD);
        Regulator re = regulatorDao.selectOne(merchantQueryWrapper);
        if (re == null) {
            throw new AuthenticationException("账号或密码错误");
        }
        if (re.getStatus() == 1) {
            throw new LockedAccountException("账号已被禁用");
        }
//        CustomizedToken customizedToken = new CustomizedToken(username, encryptPWD, REGULATOR);
//        currentUser.login(customizedToken);//shiro验证身份
        String token = jwtUtils.generateToken(re.getId());
        response.setHeader(TOKEN, token);
        redisDao.set(re.getId(), token);
        redisDao.setExpire(re.getId(), 60 * 60 * 24 * 7);
        return ReturnJson.success("登录成功！",token);
    }

    /**
     * 登出
     *
     * @param regulatorId 监督用户Id
     * @return
     */
    @Override
    public ReturnJson regulatorLogout(String regulatorId) {
        redisDao.remove(regulatorId);
        return ReturnJson.success("登出成功");
    }


    /**
     * 获取监管部门所监管的服务商下产生的支付订单
     *
     * @param regulatorId
     * @return
     */
    private ReturnJson getPaymentOrderIds(String regulatorId) {
        List<String> paymentOrderIds = new ArrayList<>();
        List<String> taxIds = new ArrayList<>();

        //获取所以监管的服务商
        List<RegulatorTax> regulatorTaxes = regulatorTaxService.list(new QueryWrapper<RegulatorTax>().eq("regulator_id", regulatorId));
        for (RegulatorTax regulatorTax : regulatorTaxes) {
            taxIds.add(regulatorTax.getTaxId());
        }
        if (VerificationCheck.listIsNull(taxIds)) {
            return ReturnJson.error("您还没有监管的服务商！");
        }

        //获取所有使用了监管服务商的支付订单
        List<PaymentOrder> paymentOrders = paymentOrderDao.selectList(new QueryWrapper<PaymentOrder>().in("tax_id", taxIds));
        for (PaymentOrder paymentOrder : paymentOrders) {
            paymentOrderIds.add(paymentOrder.getId());
        }

        List<PaymentOrderMany> paymentOrderManies = paymentOrderManyDao.selectList(new QueryWrapper<PaymentOrderMany>().in("tax_id", taxIds));
        for (PaymentOrderMany paymentOrderMany : paymentOrderManies) {
            paymentOrderIds.add(paymentOrderMany.getId());
        }
        return ReturnJson.success(paymentOrderIds);
    }

    /**
     * 获取所有监管的服务商ID
     *
     * @param regulatorId
     * @return
     */
    private ReturnJson getTaxIds(String regulatorId) {
        List<String> paymentOrderIds = new ArrayList<>();
        List<String> taxIds = new ArrayList<>();

        //获取所以监管的服务商
        List<RegulatorTax> regulatorTaxes = regulatorTaxService.list(new QueryWrapper<RegulatorTax>().eq("regulator_id", regulatorId));
        for (RegulatorTax regulatorTax : regulatorTaxes) {
            taxIds.add(regulatorTax.getTaxId());
        }
        if (VerificationCheck.listIsNull(taxIds)) {
            return ReturnJson.error("您还没有监管的服务商！");
        }
        return ReturnJson.success(taxIds);
    }
}
