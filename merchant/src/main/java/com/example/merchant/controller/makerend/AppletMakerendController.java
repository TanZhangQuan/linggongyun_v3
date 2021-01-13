package com.example.merchant.controller.makerend;

import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 平台端小程序设置 前端控制器
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


}
