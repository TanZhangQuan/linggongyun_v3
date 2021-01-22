package com.example.merchant.controller;


import com.example.common.util.ReturnJson;
import com.example.merchant.service.DictService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author xjw
 * @since 2021-01-21
 */
@Validated
@RestController
@RequestMapping("/merchant/dict")
public class DictController {

    @Resource
    private DictService dictService;

    @GetMapping("/queryDictValues")
    @ApiOperation(value = "更具code获取字典values", notes = "更具code获取字典values")
    public ReturnJson queryDictValues(@RequestParam @NotBlank(message = "code不能为空") String code) {
        return dictService.queryDictValues(code);
    }

    @GetMapping("/queryDictValueAndKey")
    @ApiOperation(value = "查询字典所有code", notes = "查询字典所有code")
    public ReturnJson queryDictValueAndKey() {
        return dictService.queryDictValueAndKey();
    }

}
