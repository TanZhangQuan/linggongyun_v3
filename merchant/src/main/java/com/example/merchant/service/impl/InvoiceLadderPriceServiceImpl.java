package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.merchant.service.InvoiceLadderPriceService;
import com.example.mybatis.entity.InvoiceLadderPrice;
import com.example.mybatis.mapper.InvoiceLadderPriceDao;
import com.example.mybatis.vo.InvoiceLadderPriceDetailVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceLadderPriceServiceImpl extends ServiceImpl<InvoiceLadderPriceDao, InvoiceLadderPrice> implements InvoiceLadderPriceService {

    @Override
    public List<InvoiceLadderPriceDetailVO> queryInvoiceLadderPriceDetailVOList(String taxPackageId) {
        return baseMapper.queryInvoiceLadderPriceDetailVOList(taxPackageId);
    }
}