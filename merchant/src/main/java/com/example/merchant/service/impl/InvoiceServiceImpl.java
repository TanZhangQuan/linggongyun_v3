package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.merchant.service.InvoiceService;
import com.example.mybatis.entity.Invoice;
import com.example.mybatis.mapper.InvoiceDao;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 发票相关 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class InvoiceServiceImpl extends ServiceImpl<InvoiceDao, Invoice> implements InvoiceService {

}
