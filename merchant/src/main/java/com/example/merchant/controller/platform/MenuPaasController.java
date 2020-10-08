package com.example.merchant.controller.platform;


import com.example.common.util.ReturnJson;
import com.example.merchant.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    @GetMapping(value = "/getMenuList")
    public ReturnJson getMenuList(){
        ReturnJson returnJson=new ReturnJson("查询失败",300);
        try {
            returnJson=menuService.getMenuList();
        }catch (Exception err){
            logger.error("出现异常错误",err);
        }
        return returnJson;
    }
}
