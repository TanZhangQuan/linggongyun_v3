package com.example.paas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.Industry;

/**
 * <p>
 * 行业表 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface IndustryService extends IService<Industry> {

    ReturnJson getlist(String oneLevel);
}
