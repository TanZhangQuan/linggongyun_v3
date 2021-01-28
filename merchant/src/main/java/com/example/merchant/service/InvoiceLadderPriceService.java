package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatis.entity.InvoiceLadderPrice;
import com.example.mybatis.vo.InvoiceLadderPriceDetailVO;

import java.util.List;

public interface InvoiceLadderPriceService extends IService<InvoiceLadderPrice> {

    /**
     * 获取梯度价
     *
     * @param taxPackageId
     * @return
     */
    List<InvoiceLadderPriceDetailVO> queryInvoiceLadderPriceDetailVOList(String taxPackageId);
}
