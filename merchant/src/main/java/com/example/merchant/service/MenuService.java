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

    /**
     * 商户端查询所有的权限列表
     *
     * @return
     */
    ReturnJson getMenuList();

    /**
     * 平台端端查询所有的权限列表
     *
     * @return
     */
    ReturnJson getPlatformMenuList();

    /**
     * 添加子账户
     *
     * @param merchantDto
     * @param merchantId
     * @return
     */
    ReturnJson saveRole(MerchantDto merchantDto, String merchantId);

    /**
     * 查看所有的账户权限
     *
     * @param merchantId
     * @return
     */
    ReturnJson getAllRole(String merchantId);

    /**
     * 删除子账户
     *
     * @param merchantId
     * @return
     */
    ReturnJson daleteRole(String merchantId);

    /**
     * 启用或停用子账户
     *
     * @param merchantId
     * @param status
     * @return
     */
    ReturnJson updataRoleStatus(String merchantId, Integer status);

    /**
     * 平台端添加子账户
     *
     * @param saveManagersRoleDto
     * @param managersId
     * @return
     */
    ReturnJson savePlatRole(SaveManagersRoleDto saveManagersRoleDto, String managersId);

    /**
     * 平台端查询用户对应的子账户
     *
     * @param managersId
     * @return
     */
    ReturnJson getPassAllRole(String managersId);

    /**
     * 删除子账户
     *
     * @param managersId
     * @return
     */
    ReturnJson daletePassRole(String managersId);

    /**
     * 修改子账户状态
     *
     * @param managersId
     * @param status
     * @return
     */
    ReturnJson updataPassRoleStatus(String managersId, Integer status);

    /**
     * 平台管理员权限信息
     *
     * @param managersId
     * @return
     */
    ReturnJson getManagersInfo(String managersId);

    /**
     * 商户权限信息
     *
     * @param userId
     * @return
     */
    ReturnJson queryMerchantMeun(String userId);
}
