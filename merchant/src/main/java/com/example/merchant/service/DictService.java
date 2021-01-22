package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author xjw
 * @since 2021-01-21
 */
public interface DictService extends IService<Dict> {

    /**
     * 更具code获取字典values
     *
     * @param code
     * @return
     */
    ReturnJson queryDictValues(String code);

    /**
     * 获取字典code
     *
     * @return
     */
    ReturnJson queryDictValueAndKey();

}
