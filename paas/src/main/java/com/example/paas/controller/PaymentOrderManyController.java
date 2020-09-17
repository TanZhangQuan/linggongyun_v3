package com.example.paas.controller;

import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.PaymentInventory;
import com.example.mybatis.entity.PaymentOrderMany;
import com.example.paas.dto.PaymentOrderDto;
import com.example.paas.interceptor.LoginRequired;
import com.example.paas.service.PaymentOrderManyService;
import com.example.paas.service.PaymentOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 * 众包支付单信息
 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@RestController
@RequestMapping("/paas/paymentOrderMany")
@Api(value = "众包支付管理", tags = "众包支付管理")
public class PaymentOrderManyController {

    @Autowired
    private PaymentOrderManyService paymentOrderManyService;

    @Autowired
    private PaymentOrderService paymentOrderService;


    @PostMapping("/findMerchant")
    @LoginRequired
    @ApiOperation(value = "查询商户", notes = "查询商户", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="managersId",value = "管理人员ID",required = true)})
    public ReturnJson findMerchant(@NotBlank(message = "支付订单不能为空！") @RequestParam String managersId){
        return paymentOrderService.findMerchant(managersId);
    }


    @PostMapping("/getPaymentOrderManyAll")
    @ApiOperation(value = "查询众包订单", notes = "查询众包订单", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="paymentOrderDto",value = "查询条件",required = true, dataType = "PaymentOrderDto")})
    public ReturnJson getPaymentOrderManyAll(@Valid @RequestBody PaymentOrderDto paymentOrderDto){
        return paymentOrderManyService.getPaymentOrderMany(paymentOrderDto);
    }

    @GetMapping("/getPaymentOrderManyInfo")
    @ApiOperation(value = "查询众包支付订单详情", notes = "查询众包支付订单详情", httpMethod = "GET")
    @ApiImplicitParams(value={@ApiImplicitParam(name="id",value = "支付订单ID",required = true)})
    public ReturnJson getPaymentOrderManyInfo(@NotBlank(message = "支付订单ID不能为空") @RequestParam(required = false) String id) {
        return paymentOrderManyService.getPaymentOrderManyInfo(id);
    }

    @PostMapping("/saveOrUpdata")
    @ApiOperation(value = "创建或修改众包支付订单", notes = "创建或修改众包支付订单", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="paymentOrderMany",value = "支付订单",required = true, dataType = "PaymentOrderMany"),
            @ApiImplicitParam(name="paymentInventories",value = "支付订单清单",required = true, allowMultiple = true, dataType = "PaymentInventory")})
    public ReturnJson saveOrUpdataPaymentOrderMany(@NotEmpty(message = "订单内容不能为空") @RequestBody PaymentOrderMany paymentOrderMany, @NotEmpty(message = "支付清单不能为空") @RequestBody List<PaymentInventory> paymentInventories){
        return paymentOrderManyService.saveOrUpdataPaymentOrderMany(paymentOrderMany, paymentInventories);
    }

    @PostMapping("/confirmPayment")
    @ApiOperation(value = "众包确认支付", notes = "众包确认支付", httpMethod = "POST")
    @ApiImplicitParams(value={@ApiImplicitParam(name="id",value = "众包支付订单ID",required = true)})
    public ReturnJson confirmPaymentMany(String id) {
        return paymentOrderManyService.confirmPaymentMany(id);
    }
}
