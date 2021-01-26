package com.example.merchant.controller.platform;

import com.example.common.enums.UnionpayBankType;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.AddPaymentOrderManyDTO;
import com.example.merchant.dto.platform.PaymentOrderDTO;
import com.example.merchant.exception.CommonException;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.CompanyUnionpayService;
import com.example.merchant.service.PaymentOrderManyService;
import com.example.merchant.service.PaymentOrderService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 众包支付单信息
 * 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "平台端众包支付管理", tags = "平台端众包支付管理")
@RestController
@RequestMapping("/platform/paymentOrderMany")
@Validated
public class PaymentOrderManyPaasController {

    @Resource
    private PaymentOrderManyService paymentOrderManyService;

    @Resource
    private PaymentOrderService paymentOrderService;

    @Resource
    private CompanyUnionpayService companyUnionpayService;

    @PostMapping("/findMerchant")
    @ApiOperation(value = "查询商户", notes = "查询商户")
    @LoginRequired
    public ReturnJson findMerchant(@ApiParam(hidden = true) @RequestAttribute("userId") String managersId) {
        return paymentOrderService.findMerchantPaas(managersId);
    }

    @LoginRequired
    @PostMapping("/getPaymentOrderManyAll")
    @ApiOperation(value = "查询众包订单", notes = "查询众包订单")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "paymentOrderDto", value = "查询条件", required = true, dataType = "PaymentOrderDTO")})
    public ReturnJson getPaymentOrderManyAll(@Valid @RequestBody PaymentOrderDTO paymentOrderDto, @RequestAttribute("userId") @ApiParam(hidden = true) String managersId) throws CommonException {
        return paymentOrderManyService.getPaymentOrderManyPaas(paymentOrderDto, managersId);
    }

    @GetMapping("/getPaymentOrderManyInfo")
    @ApiOperation(value = "查询众包支付订单详情", notes = "查询众包支付订单详情")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "支付订单ID", required = true)})
    public ReturnJson getPaymentOrderManyInfo(@NotBlank(message = "支付订单ID不能为空") @RequestParam(required = false) String id) {
        return paymentOrderManyService.getPaymentOrderManyInfo(id);
    }

    @PostMapping("/saveOrUpdata")
    @ApiOperation(value = "创建或修改众包支付订单", notes = "创建或修改众包支付订单")
    public ReturnJson saveOrUpdataPaymentOrderMany(@Valid @RequestBody AddPaymentOrderManyDTO addPaymentOrderManyDto, @RequestParam String merchantId) throws CommonException {
        return paymentOrderManyService.saveOrUpdataPaymentOrderMany(addPaymentOrderManyDto, merchantId);
    }

    @PostMapping("/paymentOrderManyAudit")
    @ApiOperation(value = "众包审核", notes = "众包审核")
    public ReturnJson paymentOrderManyAudit(@ApiParam(value = "众包支付订单ID") @NotBlank(message = "请选择众包支付订单") @RequestParam(required = false) String paymentOrderId,
                                            @ApiParam(value = "是否审核通过") @NotNull(message = "请选择是否审核通过") @RequestParam(required = false) Boolean boolPass,
                                            @ApiParam(value = "拒绝原因") @RequestParam(required = false) String reasonsForRejection) throws Exception {
        return paymentOrderManyService.paymentOrderManyAudit(paymentOrderId, boolPass, reasonsForRejection);
    }

    @GetMapping("/queryCompanyUnionpayBalance")
    @ApiOperation(value = "查询商户相应银联的余额详情", notes = "查询商户相应银联的余额详情")
    @LoginRequired
    public ReturnJson queryCompanyUnionpayDetail(@ApiParam(value = "商户ID") @NotBlank(message = "请选择商户") @RequestParam(required = false) String merchantId,
                                                 @ApiParam(value = "服务商ID") @NotBlank(message = "请选择服务商") @RequestParam(required = false) String taxId,
                                                 @ApiParam(value = "银行类型") @NotNull(message = "请选择银行类型") @RequestParam(required = false) UnionpayBankType unionpayBankType) throws Exception {
        return companyUnionpayService.queryCompanyUnionpayDetail(merchantId, taxId, unionpayBankType);
    }

}
