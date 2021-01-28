package com.example.mybatis.mapper;

import com.example.mybatis.entity.InvoiceLadderPrice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.vo.InvoiceLadderPriceDetailVO;

import java.util.List;

/**
 * <p>
 * 服务商发票税率梯度价 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-21
 */
public interface InvoiceLadderPriceDao extends BaseMapper<InvoiceLadderPrice> {

    /**
     * 获取梯度价
     *
     * @param taxPackageId
     * @return
     */
    List<InvoiceLadderPriceDetailVO> queryInvoiceLadderPriceDetailVOList(String taxPackageId);
}
