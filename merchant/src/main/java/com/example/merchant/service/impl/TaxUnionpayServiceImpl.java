package com.example.merchant.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.*;
import com.example.common.util.ReturnJson;
import com.example.common.util.UnionpayUtil;
import com.example.merchant.dto.platform.AddOrUpdateTaxUnionpayDTO;
import com.example.merchant.service.CompanyUnionpayService;
import com.example.merchant.service.ManagersService;
import com.example.merchant.service.PaymentHistoryService;
import com.example.merchant.service.TaxUnionpayService;
import com.example.merchant.util.SnowflakeIdWorker;
import com.example.mybatis.entity.CompanyUnionpay;
import com.example.mybatis.entity.Managers;
import com.example.mybatis.entity.PaymentHistory;
import com.example.mybatis.entity.TaxUnionpay;
import com.example.mybatis.mapper.TaxUnionpayDao;
import com.example.mybatis.vo.CompanyIdAndNameList;
import com.example.mybatis.vo.TaxUnionpayBalanceVO;
import com.example.mybatis.vo.TaxUnionpayListVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务商银联信息表 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2021-01-08
 */
@Service
public class TaxUnionpayServiceImpl extends ServiceImpl<TaxUnionpayDao, TaxUnionpay> implements TaxUnionpayService {

    @Resource
    private TaxUnionpayDao taxUnionpayDao;

    @Resource
    private ManagersService managersService;

    @Resource
    private PaymentHistoryService paymentHistoryService;

    @Resource
    private CompanyUnionpayService companyUnionpayService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson addOrUpdateTaxUnionpay(AddOrUpdateTaxUnionpayDTO addOrUpdateTaxUnionpayDTO) {

        TaxUnionpay taxUnionpay;
        int count;
        if (StringUtils.isNotBlank(addOrUpdateTaxUnionpayDTO.getTaxUnionpayId())) {

            taxUnionpay = getById(addOrUpdateTaxUnionpayDTO.getTaxUnionpayId());
            if (taxUnionpay == null) {
                return ReturnJson.error("服务商银联记录不存在");
            }

            //查询是否有子账号，存在子账号则不可修改，只能启用或停用
            QueryWrapper<CompanyUnionpay> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(CompanyUnionpay::getTaxUnionpayId, addOrUpdateTaxUnionpayDTO.getTaxUnionpayId());
            int merchantUnionpayCount = companyUnionpayService.count(queryWrapper);
            if (merchantUnionpayCount > 0) {
                return ReturnJson.error("服务商银联存在子账号，不可编辑");
            }

            //判断是否已存在相同服务商-银联银行记录
            count = queryTaxUnionpayCount(taxUnionpay.getId(), addOrUpdateTaxUnionpayDTO.getTaxId(), addOrUpdateTaxUnionpayDTO.getUnionpayBankType());
            if (count > 0) {
                return ReturnJson.error("已存在相同银联银行的服务商银联记录");
            }

            //判断是否已存在相同服务商-银联银行记录
            count = queryTaxUnionpayMerchNoCount(taxUnionpay.getId(), addOrUpdateTaxUnionpayDTO.getMerchno());
            if (count > 0) {
                return ReturnJson.error("已存在相同商户号");
            }

            //填充参数
            addOrUpdateTaxUnionpayDTO.setTaxId(taxUnionpay.getTaxId());
            if (StringUtils.isBlank(addOrUpdateTaxUnionpayDTO.getPfmpubkey())) {
                addOrUpdateTaxUnionpayDTO.setPfmpubkey(taxUnionpay.getPfmpubkey());
            }
            if (StringUtils.isBlank(addOrUpdateTaxUnionpayDTO.getPrikey())) {
                addOrUpdateTaxUnionpayDTO.setPrikey(taxUnionpay.getPrikey());
            }

        } else {

            if (StringUtils.isBlank(addOrUpdateTaxUnionpayDTO.getTaxId())) {
                return ReturnJson.error("请选择服务商");
            }

            if (StringUtils.isBlank(addOrUpdateTaxUnionpayDTO.getPfmpubkey())) {
                return ReturnJson.error("请输入平台公钥");
            }

            if (StringUtils.isBlank(addOrUpdateTaxUnionpayDTO.getPrikey())) {
                return ReturnJson.error("请输入合作方私钥");
            }

            //判断是否已存在相同服务商-银联银行记录
            count = queryTaxUnionpayCount(null, addOrUpdateTaxUnionpayDTO.getTaxId(), addOrUpdateTaxUnionpayDTO.getUnionpayBankType());
            if (count > 0) {
                return ReturnJson.error("已存在相同银联银行的服务商银联记录");
            }

            //判断是否已存在相同商户号
            count = queryTaxUnionpayMerchNoCount(null, addOrUpdateTaxUnionpayDTO.getMerchno());
            if (count > 0) {
                return ReturnJson.error("已存在相同商户号");
            }

            taxUnionpay = new TaxUnionpay();
        }

        BeanUtils.copyProperties(addOrUpdateTaxUnionpayDTO, taxUnionpay);
        saveOrUpdate(taxUnionpay);

        return ReturnJson.success("操作成功");
    }

    @Override
    public ReturnJson boolEnableTaxUnionpay(String taxUnionpayId, Boolean boolEnable) {

        TaxUnionpay taxUnionpay = getById(taxUnionpayId);
        if (taxUnionpay == null) {
            return ReturnJson.error("服务商银联记录不存在");
        }

        taxUnionpay.setBoolEnable(boolEnable);
        updateById(taxUnionpay);

        return ReturnJson.success("操作成功");
    }

    @Override
    public int queryTaxUnionpayCount(String taxUnionpayId, String taxId, UnionpayBankType unionpayBankType) {

        QueryWrapper<TaxUnionpay> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().ne(StringUtils.isNotBlank(taxUnionpayId), TaxUnionpay::getId, taxUnionpayId)
                .eq(TaxUnionpay::getTaxId, taxId)
                .eq(TaxUnionpay::getUnionpayBankType, unionpayBankType);

        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public int queryTaxUnionpayMerchNoCount(String taxUnionpayId, String merchNo) {

        QueryWrapper<TaxUnionpay> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().ne(StringUtils.isNotBlank(taxUnionpayId), TaxUnionpay::getId, taxUnionpayId)
                .eq(TaxUnionpay::getMerchno, merchNo);

        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public TaxUnionpay queryTaxUnionpay(String taxId, UnionpayBankType unionpayBankType) {

        QueryWrapper<TaxUnionpay> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TaxUnionpay::getTaxId, taxId)
                .eq(TaxUnionpay::getUnionpayBankType, unionpayBankType);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public TaxUnionpay queryTaxUnionpayByMerchNo(String merchNo) {

        QueryWrapper<TaxUnionpay> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TaxUnionpay::getMerchno, merchNo);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public List<TaxUnionpay> queryTaxUnionpay(String taxId) {

        QueryWrapper<TaxUnionpay> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TaxUnionpay::getTaxId, taxId);

        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public ReturnJson queryTaxUnionpayList(String taxId) {
        List<TaxUnionpayListVO> taxUnionpayList = taxUnionpayDao.queryTaxUnionpayList(taxId);
        return ReturnJson.success(taxUnionpayList);
    }

    @Override
    public List<UnionpayBankType> queryTaxUnionpayMethod(String taxId) {
        return taxUnionpayDao.queryTaxUnionpayMethod(taxId);
    }

    @Override
    public ReturnJson queryTaxUnionpayBalance(String taxUnionpayId) throws Exception {

        TaxUnionpay taxUnionpay = getById(taxUnionpayId);
        if (taxUnionpay == null) {
            return ReturnJson.error("服务商银联记录不存在");
        }

        //查询服务商银联账户余额
        List<TaxUnionpayBalanceVO> taxUnionpayBalanceVOList = new ArrayList<>();
        //清分子账号余额
        TaxUnionpayBalanceVO clearTaxUnionpayBalanceVO = new TaxUnionpayBalanceVO();
        clearTaxUnionpayBalanceVO.setAccount(taxUnionpay.getClearNo());
        //手续费子账号余额
        TaxUnionpayBalanceVO serviceTaxUnionpayBalanceVO = new TaxUnionpayBalanceVO();
        serviceTaxUnionpayBalanceVO.setAccount(taxUnionpay.getServiceChargeNo());

        //查询清分子帐号余额
        JSONObject jsonObject = UnionpayUtil.AC081(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), taxUnionpay.getClearNo());
        if (jsonObject == null) {
            log.error("查询服务商清分子帐号" + taxUnionpay.getUnionpayBankType().getDesc() + "银联支付余额失败");
            taxUnionpayBalanceVOList.add(clearTaxUnionpayBalanceVO);
        } else {
            Boolean boolSuccess = jsonObject.getBoolean("success");
            if (boolSuccess == null || !boolSuccess) {
                String errMsg = jsonObject.getString("err_msg");
                log.error("查询服务商清分子帐号" + taxUnionpay.getUnionpayBankType().getDesc() + "银联支付余额失败: " + errMsg);
                taxUnionpayBalanceVOList.add(clearTaxUnionpayBalanceVO);
            } else {
                JSONObject returnValue = jsonObject.getJSONObject("return_value");
                String rtnCode = returnValue.getString("rtn_code");
                if (!("S00000".equals(rtnCode))) {
                    String errMsg = returnValue.getString("err_msg");
                    log.error("查询服务商清分子帐号" + taxUnionpay.getUnionpayBankType().getDesc() + "银联支付余额失败: " + errMsg);
                    taxUnionpayBalanceVOList.add(clearTaxUnionpayBalanceVO);
                } else {
                    //可用余额，单位元
                    BigDecimal useBal = returnValue.getBigDecimal("use_bal");
                    //冻结余额（平台），单位元
                    BigDecimal pfrzBal = returnValue.getBigDecimal("pfrz_bal");
                    //冻结余额（银行），单位元
                    BigDecimal bfrzBal = returnValue.getBigDecimal("bfrz_bal");
                    //在途余额（入），单位元
                    BigDecimal iwayBal = returnValue.getBigDecimal("iway_bal");
                    //在途余额（出），单位元
                    BigDecimal owayBal = returnValue.getBigDecimal("oway_bal");
                    //账面余额，单位元
                    BigDecimal actBal = returnValue.getBigDecimal("act_bal");

                    clearTaxUnionpayBalanceVO.setUseBal(useBal);
                    clearTaxUnionpayBalanceVO.setPfrzBal(pfrzBal);
                    clearTaxUnionpayBalanceVO.setBfrzBal(bfrzBal);
                    clearTaxUnionpayBalanceVO.setIwayBal(iwayBal);
                    clearTaxUnionpayBalanceVO.setOwayBal(owayBal);
                    clearTaxUnionpayBalanceVO.setActBal(actBal);

                    taxUnionpayBalanceVOList.add(clearTaxUnionpayBalanceVO);
                }
            }
        }

        //查询手续费子账户余额
        jsonObject = UnionpayUtil.AC081(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), taxUnionpay.getServiceChargeNo());
        if (jsonObject == null) {
            log.error("查询服务商手续费子账户" + taxUnionpay.getUnionpayBankType().getDesc() + "银联支付余额失败");
            taxUnionpayBalanceVOList.add(serviceTaxUnionpayBalanceVO);
        } else {
            Boolean boolSuccess = jsonObject.getBoolean("success");
            if (boolSuccess == null || !boolSuccess) {
                String errMsg = jsonObject.getString("err_msg");
                log.error("查询服务商手续费子账户" + taxUnionpay.getUnionpayBankType().getDesc() + "银联支付余额失败: " + errMsg);
                taxUnionpayBalanceVOList.add(serviceTaxUnionpayBalanceVO);
            } else {
                JSONObject returnValue = jsonObject.getJSONObject("return_value");
                String rtnCode = returnValue.getString("rtn_code");
                if (!("S00000".equals(rtnCode))) {
                    String errMsg = returnValue.getString("err_msg");
                    log.error("查询服务商手续费子账户" + taxUnionpay.getUnionpayBankType().getDesc() + "银联支付余额失败: " + errMsg);
                    taxUnionpayBalanceVOList.add(serviceTaxUnionpayBalanceVO);
                } else {
                    //可用余额，单位元
                    BigDecimal useBal = returnValue.getBigDecimal("use_bal");
                    //冻结余额（平台），单位元
                    BigDecimal pfrzBal = returnValue.getBigDecimal("pfrz_bal");
                    //冻结余额（银行），单位元
                    BigDecimal bfrzBal = returnValue.getBigDecimal("bfrz_bal");
                    //在途余额（入），单位元
                    BigDecimal iwayBal = returnValue.getBigDecimal("iway_bal");
                    //在途余额（出），单位元
                    BigDecimal owayBal = returnValue.getBigDecimal("oway_bal");
                    //账面余额，单位元
                    BigDecimal actBal = returnValue.getBigDecimal("act_bal");

                    serviceTaxUnionpayBalanceVO.setUseBal(useBal);
                    serviceTaxUnionpayBalanceVO.setPfrzBal(pfrzBal);
                    serviceTaxUnionpayBalanceVO.setBfrzBal(bfrzBal);
                    serviceTaxUnionpayBalanceVO.setIwayBal(iwayBal);
                    serviceTaxUnionpayBalanceVO.setOwayBal(owayBal);
                    serviceTaxUnionpayBalanceVO.setActBal(actBal);

                    taxUnionpayBalanceVOList.add(serviceTaxUnionpayBalanceVO);
                }
            }
        }

        return ReturnJson.success(taxUnionpayBalanceVOList);
    }

    @Override
    public ReturnJson queryTaxUnionpayCompanyUnionpayList(String taxUnionpayId) {
        List<CompanyIdAndNameList> companyIdAndNameList = baseMapper.queryTaxUnionpayCompanyUnionpayList(taxUnionpayId);
        return ReturnJson.success(companyIdAndNameList);
    }

    @Override
    public ReturnJson clarify(String userId, String taxUnionpayId, String companyId, BigDecimal amount) throws Exception {

        //判断金额
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return ReturnJson.error("请输入大于0的清分金额");
        }

        //判断当前管理员是否是超级管理员
        Managers managers = managersService.getById(userId);
        if (managers == null) {
            return ReturnJson.error("管理员不存在");
        }

        if (managers.getUserSign() != 3 || !("0".equals(managers.getParentId()))) {
            return ReturnJson.error("非超级管理员不可进行清分操作");
        }

        //查询服务商银联
        TaxUnionpay taxUnionpay = getById(taxUnionpayId);
        if (taxUnionpay == null) {
            return ReturnJson.error("服务商银联记录不存在");
        }

        //查询子账号是否存在
        CompanyUnionpay companyUnionpay = companyUnionpayService.queryMerchantUnionpay(companyId, taxUnionpayId);
        if (companyUnionpay == null) {
            return ReturnJson.error("商户对应服务商" + taxUnionpay.getUnionpayBankType().getDesc() + "的银联子账号不存在");
        }

        PaymentMethod paymentMethod;
        switch (taxUnionpay.getUnionpayBankType()) {

            case SJBK:

                paymentMethod = PaymentMethod.UNIONSJBK;
                break;

            case PABK:

                paymentMethod = PaymentMethod.UNIONPABK;
                break;

            case WSBK:

                paymentMethod = PaymentMethod.UNIONWSBK;
                break;

            case ZSBK:

                paymentMethod = PaymentMethod.UNIONZSBK;
                break;

            default:
                return ReturnJson.error("服务商银联银行类型不存在");
        }

        //新建交易记录
        PaymentHistory paymentHistory = new PaymentHistory();
        paymentHistory.setTradeNo(SnowflakeIdWorker.getSerialNumber());

        //清分操作
        JSONObject jsonObject = UnionpayUtil.AC051(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), paymentHistory.getTradeNo(), companyUnionpay.getUid(), amount);
        if (jsonObject == null) {
            return ReturnJson.error("服务商" + taxUnionpay.getUnionpayBankType().getDesc() + "银联清分子账户清分失败");
        }

        Boolean boolSuccess = jsonObject.getBoolean("success");
        if (boolSuccess == null || !boolSuccess) {
            String errMsg = jsonObject.getString("err_msg");
            return ReturnJson.error("服务商" + taxUnionpay.getUnionpayBankType().getDesc() + "银联清分子账户清分失败: " + errMsg);
        }

        JSONObject returnValue = jsonObject.getJSONObject("return_value");
        String rtnCode = returnValue.getString("rtn_code");
        if (!("S00000".equals(rtnCode))) {
            String errMsg = returnValue.getString("err_msg");
            return ReturnJson.error("服务商" + taxUnionpay.getUnionpayBankType().getDesc() + "银联清分子账户清分失败: " + errMsg);
        }

        //设置第三方订单号
        String batchItfId = returnValue.getString("batch_itf_id");
        paymentHistory.setOuterTradeNo(batchItfId);

        //设置交易记录状态
        String status = returnValue.getString("status");
        if ("91".equals(status)) {
            //设置交易状态为成功
            paymentHistory.setTradeStatus(TradeStatus.SUCCESS);

            //新建交易记录
            PaymentHistory rechargePaymentHistory = new PaymentHistory();
            rechargePaymentHistory.setTradeNo(SnowflakeIdWorker.getSerialNumber());
            rechargePaymentHistory.setOrderType(OrderType.RECHARGE);
            rechargePaymentHistory.setPaymentMethod(paymentMethod);
            rechargePaymentHistory.setTradeObject(TradeObject.COMPANY);
            rechargePaymentHistory.setTradeObjectId(companyUnionpay.getCompanyId());
            rechargePaymentHistory.setAmount(amount);
            rechargePaymentHistory.setTradeStatus(TradeStatus.SUCCESS);
            paymentHistoryService.save(rechargePaymentHistory);

        } else {
            paymentHistory.setTradeStatus(TradeStatus.TRADING);
        }

        paymentHistory.setOrderType(OrderType.CLEAR);
        paymentHistory.setPaymentMethod(paymentMethod);
        paymentHistory.setTradeObject(TradeObject.TAX);
        paymentHistory.setTradeObjectId(taxUnionpay.getTaxId());
        paymentHistory.setAmount(amount);
        paymentHistoryService.save(paymentHistory);

        return ReturnJson.success("操作成功");
    }

}
