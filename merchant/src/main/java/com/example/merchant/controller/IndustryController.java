package com.example.merchant.controller;


import com.example.common.util.PageData;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.IndustryService;
import com.example.merchant.service.MerchantService;
import com.example.mybatis.entity.Industry;
import com.example.mybatis.entity.Merchant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 行业表 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "行业相关操作接口", tags = {"行业相关操作接口"})
@RestController
@RequestMapping("/merchant/industry")
public class IndustryController {

    @Resource
    private IndustryService industryService;

    @Resource
    private MerchantService merchantService;

    @ApiOperation("二级菜单")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "登录令牌", dataType = "string", paramType = "header"),
            @ApiImplicitParam(dataType = "int", name = "oneLevel", value = "任务编号", paramType = "form")})
    @RequestMapping(value = "/GetIndustrs", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"})
    public ReturnJson GetIndustrs(Integer oneLevel) {
        String currentUserId = "8982af2d0b664d6a9d62fba790746de2";
        Merchant merchant = merchantService.findByID(currentUserId);
        if (merchant != null){
            try {
                PageData pd=new PageData();
                pd.put("oneLevel",oneLevel);
                List<Industry> industryList=industryService.getlist(pd);
                return new ReturnJson("操作成功",industryList,200);
            }catch (Exception e){
                return new ReturnJson(e.toString(),300);
            }
        }else {
            return new ReturnJson("请先登录",300);
        }
    }
}
