package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.entity.Merchant;
import com.example.mybatis.po.MerchantInfoPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商户信息
 * Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface MerchantDao extends BaseMapper<Merchant> {

    Merchant findByID(String id);

    List<Merchant> getIdAndName();

    String getNameById(String id);

    IPage<MerchantInfoPo> selectMerchantInfoPo(Page page, @Param("merchantIds") List<String> merchantIds, @Param("merchantId") String merchantId,
                                               @Param("merchantName") String merchantName, @Param("linkMobile") String linkMobile,@Param("auditStatus")Integer auditStatus);
}
