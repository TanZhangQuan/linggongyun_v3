package com.example.merchant.controller.merchant;


import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.MerchantDTO;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
@Validated
public class MenuMerchantController {

    @Resource
    private MenuService menuService;

    @ApiOperation("查询权限菜单")
    @GetMapping(value = "/getMenuList")
    @LoginRequired
    public ReturnJson getMenuList() {
        return menuService.getMenuList();
    }

    @ApiOperation("添加子用户or编辑")
    @PostMapping(value = "/addMerchant")
    @LoginRequired
    public ReturnJson addMerchant(@Valid @RequestBody MerchantDTO merchantDto, @RequestAttribute(value = "userId") @ApiParam(hidden = true) String merchantId) {
        return menuService.saveRole(merchantDto,merchantId);
    }

    @ApiOperation("权限管理，查看所有用户")
    @PostMapping(value = "/getAllRole")
    @LoginRequired
    public ReturnJson getAllRole(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String merchantId) {
        return menuService.getAllRole(merchantId);
    }

    @ApiOperation("权限管理，删除子账户")
    @PostMapping(value = "/daleteRole")
    public ReturnJson daleteRole(@NotNull(message = "子账户Id不能为空")@RequestParam String merchantId) {
        return menuService.daleteRole(merchantId);
    }

    @ApiOperation("权限管理，修改子用户状态")
    @PostMapping(value = "/updataRoleStatus")
    public ReturnJson updataRoleStatus(@NotBlank(message = "子用户Id不能为空") @RequestParam String merchantId, @NotNull(message = "status不能为空") @RequestParam Integer status) {
        return menuService.updataRoleStatus(merchantId, status);
    }

    @ApiOperation("权限管理，用户详情")
    @PostMapping(value = "/getRole")
    public ReturnJson getRole(@RequestParam String merchantId) {
        return menuService.queryMerchantMeun(merchantId);
    }
}
