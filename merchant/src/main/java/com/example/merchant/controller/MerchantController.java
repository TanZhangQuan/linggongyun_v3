package com.example.merchant.controller;


import com.example.common.util.ReturnJson;
import com.example.merchant.service.MerchantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 商户信息
 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "商户相关操作接口", tags = {"商户相关操作接口"})
@RestController
@RequestMapping("/merchant/merchant")
public class MerchantController {

    private static Logger logger= LoggerFactory.getLogger(MerchantController.class);

    @Resource
    private MerchantService merchantService;

    @ApiOperation("商户列表")
    @GetMapping(value = "/getIdAndName")
    public ReturnJson getIdAndName(){
        ReturnJson returnJson=new ReturnJson("查询失败",300);
        try {
            returnJson = merchantService.getIdAndName();
        }catch (Exception e){
            logger.error("出现异常错误",e);
        }
        return returnJson;
    }

}
