package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.entity.MerchantUnionpay;
import com.example.mybatis.vo.UnionpayTaxListVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商户银联信息表 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2021-01-08
 */
public interface MerchantUnionpayDao extends BaseMapper<MerchantUnionpay> {

    /**
     * 查询线下支付关联的服务商
     *
     * @param page
     * @param merchantId
     * @return
     */
    IPage<UnionpayTaxListVO> queryOfflineTaxList(Page<UnionpayTaxListVO> page, @Param("merchantId") String merchantId);

    /**
     * 查询银联支付关联的服务商
     *
     * @param page
     * @param merchantId
     * @return
     */
    IPage<UnionpayTaxListVO> queryUninopayTaxList(Page<UnionpayTaxListVO> page, @Param("merchantId") String merchantId);

}
