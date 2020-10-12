package com.example.merchant.controller.platform;


import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.SaveManagersRoleDto;
import com.example.merchant.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "平台端权限相关操作接口", tags = {"平台端权限相关操作接口"})
@RestController
@RequestMapping("/platform/menu")
public class MenuPaasController {
    private static Logger logger = LoggerFactory.getLogger(TaskPaasController.class);

    @Resource
    private MenuService menuService;

    @ApiOperation("查询权限菜单")
    @GetMapping(value = "/getPlatformMenuList")
    public ReturnJson getPlatformMenuList(){
        ReturnJson returnJson=new ReturnJson("查询失败",300);
        try {
            returnJson=menuService.getPlatformMenuList();
        }catch (Exception err){
            logger.error("出现异常错误",err);
        }
        return returnJson;
    }

    @ApiOperation("添加子用户")
    @PostMapping(value = "/addManagers")
    public ReturnJson addMerchant(SaveManagersRoleDto saveManagersRoleDto) {
        ReturnJson returnJson = new ReturnJson("添加失败", 300);
        try {
            returnJson = menuService.savePlatRole(saveManagersRoleDto);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }

    @ApiOperation("权限管理，查看所有用户")
    @PostMapping(value = "/getAllRole")
    public ReturnJson getAllRole(String managersId) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        try {
            returnJson = menuService.getPassAllRole(managersId);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }

    @ApiOperation("权限管理，编辑子账户")
    @PostMapping(value = "/updateRole")
    public ReturnJson updateRole(SaveManagersRoleDto saveManagersRoleDto) {
        ReturnJson returnJson = new ReturnJson("修改失败", 300);
        try {
            returnJson = menuService.updatePassRole(saveManagersRoleDto);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }

    @ApiOperation("权限管理，删除子账户")
    @PostMapping(value = "/daleteRole")
    public ReturnJson daleteRole(@NotNull(message = "账户Id不能为空")String managersId) {
        ReturnJson returnJson = new ReturnJson("删除失败", 300);
        try {
            returnJson = menuService.daletePassRole(managersId);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }


    @ApiOperation("权限管理，修改子用户状态")
    @PostMapping(value = "/updataRoleStatus")
    public ReturnJson updataRoleStatus(@NotNull(message = "managersId不能为空") String managersId,@NotNull(message = "status不能为空") Integer status) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        try {
            returnJson = menuService.updataPassRoleStatus(managersId, status);
        } catch (Exception err) {
            logger.error("出现异常错误", err);
        }
        return returnJson;
    }
}
