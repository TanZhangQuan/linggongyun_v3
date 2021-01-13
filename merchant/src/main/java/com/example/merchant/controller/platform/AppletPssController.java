package com.example.merchant.controller.platform;


import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.SaveOrUpdateAppletBannerDTO;
import com.example.merchant.dto.platform.SaveOrUpdateAppletFaqDTO;
import com.example.merchant.dto.platform.SaveOrUpdateAppletOtherInfoDTO;
import com.example.merchant.service.AppletFaqService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 平台端小程序设置 前端控制器
 * </p>
 *
 * @author xjw
 * @since 2021-01-12
 */
@Api(value = "平台端小程序设置", tags = {"平台端小程序设置"})
@RestController
@RequestMapping("/platform/applet")
@Validated
public class AppletPssController {

    @Resource
    private AppletFaqService appletFaqService;

    @PostMapping("/addOrUpdateAppletFaq")
    @ApiOperation(value = "添加修改小程序常见问题")
    public ReturnJson addOrUpdateAppletFaq(@Valid @RequestBody SaveOrUpdateAppletFaqDTO saveOrUpdateAppletFaqDto) {
        return appletFaqService.saveOrUpdateAppletFaq(saveOrUpdateAppletFaqDto);
    }

    @PostMapping("/selectAppletFaq")
    @ApiOperation(value = "查看所有小程序常见问题")
    public ReturnJson selectAppletFaq(@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        return appletFaqService.selectAppletFaq(pageNo, pageSize);
    }

    @PostMapping("/queryAppletFaqInfo")
    @ApiOperation(value = "查看某一个常见问题")
    public ReturnJson queryAppletFaqInfo(@RequestParam(value = "id") @NotBlank(message = "ID不能为空") String id) {
        return appletFaqService.queryAppletFaqInfo(id);
    }

    @DeleteMapping("/deleteAppletFaq")
    @ApiOperation(value = "删除某一个常见问题")
    public ReturnJson deleteAppletFaq(@RequestParam(value = "id") @NotBlank(message = "ID不能为空") String id) {
        return appletFaqService.deleteAppletFaq(id);
    }

    @PostMapping("/saveOrUpdateAppletBanner")
    @ApiOperation(value = "添加修改小程序轮播图")
    public ReturnJson saveOrUpdateAppletBanner(@Valid @RequestBody SaveOrUpdateAppletBannerDTO saveOrUpdateAppletBannerDto) {
        return appletFaqService.saveOrUpdateAppletBanner(saveOrUpdateAppletBannerDto);
    }

    @PostMapping("/selectAppletBanner")
    @ApiOperation(value = "查看所有小程序轮播图")
    public ReturnJson selectAppletBanner(@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        return appletFaqService.selectAppletBanner(pageNo, pageSize);
    }

    @PostMapping("/queryAppletBannerInfo")
    @ApiOperation(value = "查看某一个轮播图")
    public ReturnJson queryAppletBannerInfo(@RequestParam(value = "id") @NotBlank(message = "ID不能为空") String id) {
        return appletFaqService.queryAppletBannerInfo(id);
    }

    @DeleteMapping("/deleteAppletBanner")
    @ApiOperation(value = "删除某一个轮播图")
    public ReturnJson deleteAppletBanner(@RequestParam(value = "id") @NotBlank(message = "ID不能为空") String id) {
        return appletFaqService.deleteAppletBanner(id);
    }

    @PostMapping("/saveOrUpdateAppletOtherInfo")
    @ApiOperation(value = "添加修改小程序其他问题")
    public ReturnJson saveOrUpdateAppletOtherInfo(@Valid @RequestBody SaveOrUpdateAppletOtherInfoDTO saveOrUpdateAppletOtherInfoDto) {
        return appletFaqService.saveOrUpdateAppletOtherInfo(saveOrUpdateAppletOtherInfoDto);
    }

    @PostMapping("/selectAppletOtherInfo")
    @ApiOperation(value = "查看所有小程序其他问题")
    public ReturnJson selectAppletOtherInfo(@RequestParam(defaultValue = "1") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        return appletFaqService.selectAppletOtherInfo(pageNo, pageSize);
    }

    @PostMapping("/queryAppletOtherInfo")
    @ApiOperation(value = "查看某一个其他问题")
    public ReturnJson queryAppletOtherInfo(@RequestParam(value = "id") @NotBlank(message = "ID不能为空") String id) {
        return appletFaqService.queryAppletOtherInfo(id);
    }

    @DeleteMapping("/deleteAppletOtherInfo")
    @ApiOperation(value = "删除某一个其他问题")
    public ReturnJson deleteAppletOtherInfo(@RequestParam(value = "id") @NotBlank(message = "ID不能为空") String id) {
        return appletFaqService.deleteAppletOtherInfo(id);
    }
}
