package com.example.merchant.controller.paas;


import com.example.common.util.ReturnJson;
import com.example.merchant.service.IndustryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Api(value = "平台端行业相关操作接口", tags = {"平台端行业相关操作接口"})
@RestController
@RequestMapping("/paas/industry")
public class IndustryControllerPaas {
    private static Logger logger = LoggerFactory.getLogger(TaskControllerPaas.class);

    @Resource
    private IndustryService industryService;


    @ApiOperation("二级菜单")
    @PostMapping(value = "getIndustrs")
    public ReturnJson GetIndustrs(String oneLevel) {
        ReturnJson returnJson=new ReturnJson("出现错误",300);
        try {
            returnJson= industryService.getlist(oneLevel);
        } catch (Exception e) {
            returnJson= new ReturnJson(e.toString(), 300);
        }
        return returnJson;
    }
}
