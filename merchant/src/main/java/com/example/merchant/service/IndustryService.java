package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.Industry;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 行业表 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface IndustryService extends IService<Industry> {

    /**
     * 获取所有的行业列表
     *
     * @return
     */
    ReturnJson getlist();
}
