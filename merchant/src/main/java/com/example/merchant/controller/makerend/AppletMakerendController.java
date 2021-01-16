package com.example.merchant.controller.makerend;

import com.example.common.enums.Identification;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.AppletBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 小程序设置 前端控制器
 * </p>
 *
 * @author xjw
 * @since 2021-01-12
 */
@Api(value = "小程序相关设置", tags = {"小程序相关设置"})
@RestController
@RequestMapping("/makerend/applet")
@Validated
public class AppletMakerendController {

    @Resource
    private AppletBannerService appletBannerService;

    @GetMapping("/getIdCardInfo")
    @ApiOperation(value = "获取Banner")
    public ReturnJson getIdCardInfo() {
        return appletBannerService.queryAppletBanner();
    }

    @GetMapping("/queryAppletFaqList")
    @ApiOperation(value = "查询所有的常见问题 ")
    public ReturnJson queryAppletFaqList() {
        return appletBannerService.queryAppletFaqList();
    }

    @GetMapping("/queryAppletFaqById")
    @ApiOperation(value = "查询常见问题")
    public ReturnJson queryAppletFaqById(@NotBlank(message = "ID不能为空") String id) {
        return appletBannerService.queryAppletFaqById(id);
    }

    @GetMapping("/queryAppletOtherInfo")
    @ApiOperation(value = "标识查询")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "identification", value = "标识符", dataType = "Identification", required = true)})
    public ReturnJson queryAppletOtherInfo(@NotNull(message = "标识不能为空") Identification identification) {
        return appletBannerService.queryAppletOtherInfo(identification);
    }

}
