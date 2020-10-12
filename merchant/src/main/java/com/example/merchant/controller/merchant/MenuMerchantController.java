package com.example.merchant.controller.merchant;


import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.MerchantDto;
import com.example.merchant.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "商户端权限相关操作接口", tags = {"商户端权限相关操作接口"})
@RestController
@RequestMapping("/merchant/menu")
public class MenuMerchantController {

    @Resource
    private MenuService menuService;

    @ApiOperation("查询权限菜单")
    @GetMapping(value = "/getMenuList")
    public ReturnJson getMenuList() {
        return menuService.getMenuList();
    }

    @ApiOperation("添加子用户")
    @PostMapping(value = "/addMerchant")
    public ReturnJson addMerchant(MerchantDto merchantDto) {
        return menuService.saveRole(merchantDto);
    }

    @ApiOperation("权限管理，查看所有用户")
    @PostMapping(value = "/getAllRole")
    public ReturnJson getAllRole(@NotNull(message = "商户id不能为空") String merchantId) {
        return menuService.getAllRole(merchantId);
    }

    @ApiOperation("权限管理，编辑子账户")
    @PostMapping(value = "/updateRole")
    public ReturnJson updateRole(MerchantDto merchantDto) {
        return menuService.updateRole(merchantDto);
    }

    @ApiOperation("权限管理，删除子账户")
    @PostMapping(value = "/daleteRole")
    public ReturnJson daleteRole(@NotNull(message = "账户Id不能为空") String merchantId) {
        return menuService.daleteRole(merchantId);
    }

    @ApiOperation("权限管理，修改子用户状态")
    @PostMapping(value = "/updataRoleStatus")
    public ReturnJson updataRoleStatus(@NotNull(message = "merchantId不能为空") String merchantId, @NotNull(message = "status不能为空") Integer status) {
        return menuService.updataRoleStatus(merchantId, status);
    }
}
