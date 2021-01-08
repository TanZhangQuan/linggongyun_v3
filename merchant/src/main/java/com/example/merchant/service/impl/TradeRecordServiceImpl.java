package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.merchant.service.TradeRecordService;
import com.example.mybatis.entity.TradeRecord;
import com.example.mybatis.mapper.TradeRecordDao;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商户银联信息表 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2021-01-08
 */
@Service
public class TradeRecordServiceImpl extends ServiceImpl<TradeRecordDao, TradeRecord> implements TradeRecordService {

}
