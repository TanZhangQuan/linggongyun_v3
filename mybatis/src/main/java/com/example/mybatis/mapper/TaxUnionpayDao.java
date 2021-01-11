package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.enums.UnionpayBankType;
import com.example.mybatis.entity.TaxUnionpay;
import com.example.mybatis.vo.TaxUnionpayListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 服务商银联信息表 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2021-01-08
 */
public interface TaxUnionpayDao extends BaseMapper<TaxUnionpay> {

    /**
     * 查询服务商银联列表
     *
     * @param taxId
     * @param page
     * @return
     */
    IPage<TaxUnionpayListVO> queryTaxUnionpayList(Page<TaxUnionpayListVO> page, @Param("taxId") String taxId);

    /**
     * 查询服务商拥有的银联支付方式
     *
     * @param taxId
     * @return
     */
    List<UnionpayBankType> queryTaxUnionpayMethod(String taxId);
}
