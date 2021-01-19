package com.example.merchant.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.UnionpayBankType;
import com.example.common.util.ReturnJson;
import com.example.common.util.UnionpayUtil;
import com.example.merchant.service.CompanyUnionpayService;
import com.example.merchant.service.TaxUnionpayService;
import com.example.mybatis.entity.CompanyUnionpay;
import com.example.mybatis.entity.TaxUnionpay;
import com.example.mybatis.mapper.CompanyUnionpayDao;
import com.example.mybatis.vo.MerchantUnionpayBalanceVO;
import com.example.mybatis.vo.UnionpayTaxListVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商户银联信息表 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2021-01-08
 */
@Service
public class CompanyUnionpayServiceImpl extends ServiceImpl<CompanyUnionpayDao, CompanyUnionpay> implements CompanyUnionpayService {

    @Resource
    private CompanyUnionpayDao companyUnionpayDao;

    @Resource
    private TaxUnionpayService taxUnionpayService;

    @Override
    public CompanyUnionpay queryMerchantUnionpay(String companyId, String taxUnionpayId) {

        QueryWrapper<CompanyUnionpay> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CompanyUnionpay::getCompanyId, companyId)
                .eq(CompanyUnionpay::getTaxUnionpayId, taxUnionpayId);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public CompanyUnionpay queryMerchantUnionpayUnionpayBankType(String companyId, String taxUnionpayId, UnionpayBankType unionpayBankType) {
        return baseMapper.queryMerchantUnionpayUnionpayBankType(companyId, taxUnionpayId, unionpayBankType);
    }

    @Override
    public List<UnionpayBankType> queryCompanyUnionpayMethod(String companyId, String taxId) {
        return companyUnionpayDao.queryCompanyUnionpayMethod(companyId, taxId);
    }

    @Override
    public ReturnJson queryOfflineTaxList(String companyId, long pageNo, long pageSize) {
        IPage<UnionpayTaxListVO> uninopayTaxList = companyUnionpayDao.queryOfflineTaxList(new Page<>(pageNo, pageSize), companyId);
        return ReturnJson.success(uninopayTaxList);
    }

    @Override
    public ReturnJson queryUninopayTaxList(String companyId, long pageNo, long pageSize) {
        IPage<UnionpayTaxListVO> uninopayTaxList = companyUnionpayDao.queryUninopayTaxList(new Page<>(pageNo, pageSize), companyId);
        return ReturnJson.success(uninopayTaxList);
    }

    @Override
    public ReturnJson queryCompanyUnionpayDetail(String companyId, String taxId) throws Exception {

        //查询服务商所有主账号
        List<MerchantUnionpayBalanceVO> merchantUnionpayBalanceVOList = new ArrayList<>();
        List<TaxUnionpay> taxUnionpayList = taxUnionpayService.queryTaxUnionpay(taxId);
        for (TaxUnionpay taxUnionpay : taxUnionpayList) {

            //查询商户-服务商银联记录
            CompanyUnionpay companyUnionpay = queryMerchantUnionpay(companyId, taxUnionpay.getId());
            if (companyUnionpay == null) {
                log.error("商户-服务商银联记录不存在");
                continue;
            }

            MerchantUnionpayBalanceVO merchantUnionpayBalanceVO = new MerchantUnionpayBalanceVO();
            merchantUnionpayBalanceVO.setUnionpayBankType(taxUnionpay.getUnionpayBankType());
            merchantUnionpayBalanceVO.setSubAccountName(companyUnionpay.getSubAccountName());
            merchantUnionpayBalanceVO.setSubAccountCode(companyUnionpay.getSubAccountCode());

            JSONObject jsonObject = UnionpayUtil.AC081(taxUnionpay.getMerchno(), taxUnionpay.getAcctno(), taxUnionpay.getPfmpubkey(), taxUnionpay.getPrikey(), companyUnionpay.getUid());
            if (jsonObject == null) {
                log.error("查询子帐号" + taxUnionpay.getUnionpayBankType().getDesc() + "银联余额失败");
                merchantUnionpayBalanceVOList.add(merchantUnionpayBalanceVO);
                continue;
            }

            Boolean boolSuccess = jsonObject.getBoolean("success");
            if (boolSuccess == null || !boolSuccess) {
                String errMsg = jsonObject.getString("err_msg");
                log.error("查询子帐号" + taxUnionpay.getUnionpayBankType().getDesc() + "银联余额失败: " + errMsg);
                merchantUnionpayBalanceVOList.add(merchantUnionpayBalanceVO);
                continue;
            }

            JSONObject returnValue = jsonObject.getJSONObject("return_value");
            String rtnCode = returnValue.getString("rtn_code");
            if (!("S00000".equals(rtnCode))) {
                String errMsg = returnValue.getString("err_msg");
                log.error("查询子帐号" + taxUnionpay.getUnionpayBankType().getDesc() + "银联余额失败: " + errMsg);
                merchantUnionpayBalanceVOList.add(merchantUnionpayBalanceVO);
                continue;
            }

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

            merchantUnionpayBalanceVO.setUseBal(useBal);
            merchantUnionpayBalanceVO.setPfrzBal(pfrzBal);
            merchantUnionpayBalanceVO.setBfrzBal(bfrzBal);
            merchantUnionpayBalanceVO.setIwayBal(iwayBal);
            merchantUnionpayBalanceVO.setOwayBal(owayBal);
            merchantUnionpayBalanceVO.setActBal(actBal);

            merchantUnionpayBalanceVOList.add(merchantUnionpayBalanceVO);
        }

        return ReturnJson.success(merchantUnionpayBalanceVOList);
    }

}
