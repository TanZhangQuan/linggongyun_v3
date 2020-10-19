package com.example.merchant.controller.platform;


import com.example.common.util.ReturnJson;
import com.example.merchant.service.IndustryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 行业表 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "平台端行业相关操作接口", tags = {"平台端行业相关操作接口"})
@RestController
@RequestMapping("/platform/industry")
public class IndustryPaasController {

    @Resource
    private IndustryService industryService;

    @ApiOperation("二级菜单")
    @GetMapping(value = "getIndustrs")
    public ReturnJson GetIndustrs() {
        return industryService.getlist();
    }
}
