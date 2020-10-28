package com.example.merchant.controller.platform;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.AddLianLianPay;
import com.example.merchant.exception.CommonException;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.LianLianPayTaxService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

@Api(value = "平台端服务商连连支付关操作接口", tags = {"平台端服务商连连支付关操作接口"})
@RestController
@RequestMapping("/platform/lianlianpay")
public class LianLianPayTaxController {

    @Resource
    private LianLianPayTaxService LianLianPayTaxService;

    @LoginRequired
    @ApiOperation("添加连连支付商户号和私钥")
    @PostMapping(value = "/addLianlianPay")
    public ReturnJson addLianlianPay(@ApiParam("服务商ID") @NotBlank(message = "服务商ID不能为空！") @RequestParam(required = false) String taxId, @RequestBody AddLianLianPay addLianLianPay) {
        return LianLianPayTaxService.addLianlianPay(taxId, addLianLianPay);
    }

    @RequestMapping("/workerNotifyUrl")
    @ApiOperation(value = "支付创客回调接口", notes = "商户支付回调接口", hidden = true, httpMethod = "POST")
    public Map<String, String> workerNotifyUrl(HttpServletRequest request) {
        LianLianPayTaxService.workerNotifyUrl(request);
        Map<String, String> map = new HashMap<>();
        map.put("ret_code", "0000");
        map.put("ret_msg", "交易成功");
        return map;
    }

    @LoginRequired
    @PostMapping("/taxPay")
    @ApiOperation(value = "服务商支付接口", notes = "服务商支付接口", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "paymentInventoryIds", value = "支付清单ID字符集，每个ID之间英文逗号隔开", required = true)
    })
    public ReturnJson taxPay(@NotBlank(message = "支付内容不能为空！") @RequestParam(required = false) String paymentInventoryIds) {
        return LianLianPayTaxService.taxPay(paymentInventoryIds);
    }

}
