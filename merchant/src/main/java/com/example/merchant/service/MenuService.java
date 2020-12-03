package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.MerchantDto;
import com.example.merchant.dto.platform.SaveManagersRoleDto;
import com.example.mybatis.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

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

    ReturnJson getPlatformMenuList();

    ReturnJson saveRole(MerchantDto merchantDto,String merchantId);

    ReturnJson getAllRole(String merchantId);

    ReturnJson daleteRole(String merchantId);

    ReturnJson updataRoleStatus(String merchantId, Integer status);

    ReturnJson savePlatRole(SaveManagersRoleDto saveManagersRoleDto,String managersId);

    ReturnJson getPassAllRole(String managersId);

    ReturnJson daletePassRole(String managersId);

    ReturnJson updataPassRoleStatus(String managersId, Integer status);

    ReturnJson getManagersInfo(String managersId);
}
