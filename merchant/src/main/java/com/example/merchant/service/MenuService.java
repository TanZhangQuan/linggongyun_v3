package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface MenuService extends IService<Menu> {

    ReturnJson getMenuList();
}
