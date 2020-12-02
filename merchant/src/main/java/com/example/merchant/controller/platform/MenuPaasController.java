package com.example.merchant.controller.platform;


import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.SaveManagersRoleDto;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.MenuService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "平台端权限相关操作接口", tags = {"平台端权限相关操作接口"})
@RestController
@RequestMapping("/platform/menu")
@Validated
public class MenuPaasController {

    @Resource
    private MenuService menuService;

    @ApiOperation("查询权限菜单")
    @GetMapping(value = "/getPlatformMenuList")
    public ReturnJson getPlatformMenuList() {
        return menuService.getPlatformMenuList();
    }

    @ApiOperation("添加子用户or编辑")
    @PostMapping(value = "/addManagers")
    @LoginRequired
    public ReturnJson addMerchant(@Valid @RequestBody SaveManagersRoleDto saveManagersRoleDto, @RequestAttribute(value = "userId") @ApiParam(hidden = true) String managersId) {
        return menuService.savePlatRole(saveManagersRoleDto, managersId);
    }

    @LoginRequired
    @ApiOperation("权限管理，查看所有用户")
    @PostMapping(value = "/getAllRole")
    public ReturnJson getAllRole(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String managersId) {
        return menuService.getPassAllRole(managersId);
    }


    @ApiOperation("权限管理，删除子账户")
    @PostMapping(value = "/daleteRole")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "managersId", value = "子账户Id", required = true)})
    public ReturnJson daleteRole(@NotNull(message = "子账户Id不能为空") @RequestParam String managersId) {
        return menuService.daletePassRole(managersId);
    }


    @ApiOperation("权限管理，修改子用户状态")
    @PostMapping(value = "/updataRoleStatus")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "managersId", value = "子账户Id", required = true),
            @ApiImplicitParam(name = "status", value = "子账户Id", required = true)})
    public ReturnJson updataRoleStatus(@NotNull(message = "子账户Id不能为空") @RequestParam String managersId, @NotNull(message = "status不能为空") @RequestParam Integer status) {
        return menuService.updataPassRoleStatus(managersId, status);
    }
}
