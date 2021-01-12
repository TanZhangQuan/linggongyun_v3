package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.enums.UnionpayBankType;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.AddOrUpdateTaxUnionpayDTO;
import com.example.merchant.service.TaxUnionpayService;
import com.example.mybatis.entity.MerchantUnionpay;
import com.example.mybatis.entity.TaxUnionpay;
import com.example.mybatis.mapper.MerchantUnionpayDao;
import com.example.mybatis.mapper.TaxUnionpayDao;
import com.example.mybatis.vo.TaxUnionpayListVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    private MerchantUnionpayDao merchantUnionpayDao;

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
            QueryWrapper<MerchantUnionpay> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(MerchantUnionpay::getTaxUnionpayId, addOrUpdateTaxUnionpayDTO.getTaxUnionpayId());
            int merchantUnionpayCount = merchantUnionpayDao.selectCount(queryWrapper);
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
        save(taxUnionpay);

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
    public ReturnJson queryTaxUnionpayMethod(String taxId) {
        List<UnionpayBankType> taxUnionpayList = taxUnionpayDao.queryTaxUnionpayMethod(taxId);
        return ReturnJson.success(taxUnionpayList);
    }
}
