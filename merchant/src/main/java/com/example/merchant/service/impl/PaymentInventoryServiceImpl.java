package com.example.merchant.service.impl;

import com.example.mybatis.entity.PaymentInventory;
import com.example.mybatis.mapper.PaymentInventoryDao;
import com.example.merchant.service.PaymentInventoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付清单明细
 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class PaymentInventoryServiceImpl extends ServiceImpl<PaymentInventoryDao, PaymentInventory> implements PaymentInventoryService {

}
