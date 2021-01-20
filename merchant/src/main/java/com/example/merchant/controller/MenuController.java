package com.example.merchant.controller;

import com.example.common.util.ReturnJson;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(value = "登陆获取权限", tags = "登陆获取权限")
@RestController
@RequestMapping("/Menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping("/queryMenuList")
    @LoginRequired
    public ReturnJson queryMenuList(@RequestAttribute("userId") @ApiParam(hidden = true) String userId) {
        return menuService.queryMenuByUserId(userId);
    }

}
