package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatis.entity.MerchantRole;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface MenuService extends IService<Menu> {

    ReturnJson getMenuList();

    ReturnJson saveRole(MerchantRole merchantRole, String menuIds);

    ReturnJson updateRole(MerchantRole merchantRole, String menuIds);

    ReturnJson getAllRole(String merchantId);

    ReturnJson daleteRole(String merchantRoleId);

    ReturnJson updataRoleStatus(String merchantRoleId, Integer status);
}
