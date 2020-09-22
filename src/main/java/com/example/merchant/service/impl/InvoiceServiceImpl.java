package com.example.merchant.service.impl;

import com.example.merchant.entity.Invoice;
import com.example.merchant.mapper.InvoiceDao;
import com.example.merchant.service.InvoiceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 总包发票 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-21
 */
@Service
public class InvoiceServiceImpl extends ServiceImpl<InvoiceDao, Invoice> implements InvoiceService {

}
