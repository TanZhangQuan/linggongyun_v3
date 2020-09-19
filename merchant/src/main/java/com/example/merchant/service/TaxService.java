package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.Tax;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 合作园区信息 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface TaxService extends IService<Tax> {
    ReturnJson getTaxAll(String merchantId, Integer packageStatus);
}
