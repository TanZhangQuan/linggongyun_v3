package com.example.merchant.controller.merchant;


import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.MerchantDto;
import com.example.merchant.service.MenuService;
import com.example.mybatis.entity.MerchantRole;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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
    private static Logger logger = LoggerFactory.getLogger(TaskMerchantController.class);

    @Resource
    private MenuService menuService;

    @ApiOperation("查询权限菜单")
    @GetMapping(value = "/getMenuList")
    public ReturnJson getMenuList() {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        try {
            returnJson = menuService.getMenuList();
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }

    @ApiOperation("添加子用户")
    @PostMapping(value = "/addMerchant")
    public ReturnJson addMerchant(MerchantDto merchantDto) {
        ReturnJson returnJson = new ReturnJson("添加失败", 300);
        try {
            returnJson = menuService.saveRole(merchantDto);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }

    @ApiOperation("权限管理，查看所有用户")
    @PostMapping(value = "/getAllRole")
    public ReturnJson getAllRole(@NotNull(message = "商户id不能为空") String merchantId) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        try {
            returnJson = menuService.getAllRole(merchantId);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }

    @ApiOperation("权限管理，编辑子账户")
    @PostMapping(value = "/updateRole")
    public ReturnJson updateRole(MerchantDto merchantDto) {
        ReturnJson returnJson = new ReturnJson("修改失败", 300);
        try {
            returnJson = menuService.updateRole(merchantDto);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }

    @ApiOperation("权限管理，删除子账户")
    @PostMapping(value = "/daleteRole")
    public ReturnJson daleteRole(@NotNull(message = "账户Id不能为空")String merchantId) {
        ReturnJson returnJson = new ReturnJson("删除失败", 300);
        try {
            returnJson = menuService.daleteRole(merchantId);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }


    @ApiOperation("权限管理，修改子用户状态")
    @PostMapping(value = "/updataRoleStatus")
    public ReturnJson updataRoleStatus(@NotNull(message = "merchantId不能为空") String merchantId,@NotNull(message = "status不能为空") Integer status) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        try {
            returnJson = menuService.updataRoleStatus(merchantId, status);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }
}
