package com.example.merchant.controller.merchant;


import com.example.common.util.ReturnJson;
import com.example.merchant.service.IndustryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
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
@Api(value = "商户端行业相关操作接口", tags = {"商户端行业相关操作接口"})
@RestController
@RequestMapping("/merchant/industry")
public class IndustryMerchantController {

    @Resource
    private IndustryService industryService;

    @ApiOperation("二级菜单")
    @PostMapping(value = "getIndustrs")
    public ReturnJson GetIndustrs() {
        return industryService.getlist();
    }
}
