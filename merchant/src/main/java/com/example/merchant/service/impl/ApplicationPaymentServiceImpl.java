package com.example.merchant.service.impl;

import com.example.merchant.service.ApplicationPaymentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatis.entity.ApplicationPayment;
import com.example.mybatis.mapper.ApplicationPaymentDao;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjw
 * @since 2021-02-03
 */
@Service
public class ApplicationPaymentServiceImpl extends ServiceImpl<ApplicationPaymentDao, ApplicationPayment> implements ApplicationPaymentService {

}
