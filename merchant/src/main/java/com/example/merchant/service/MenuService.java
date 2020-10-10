package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.MerchantDto;
import com.example.mybatis.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mybatis.entity.Merchant;
import com.example.mybatis.entity.MerchantRole;
import org.springframework.transaction.annotation.Transactional;

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

    ReturnJson saveRole(MerchantDto merchantDto);

    ReturnJson updateRole(MerchantDto merchantDto);

    ReturnJson getAllRole(String merchantId);

    ReturnJson daleteRole(String merchantId);

    ReturnJson updataRoleStatus(String merchantId, Integer status);
}
