package com.example.merchant.controller.merchant;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.AddApplicationCrowdSourcingDTO;
import com.example.merchant.exception.CommonException;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.CrowdSourcingInvoiceService;
import com.example.merchant.service.MerchantService;
import com.example.merchant.service.PaymentOrderManyService;
import com.example.mybatis.dto.QueryCrowdSourcingDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Api(value = "商户端众包发票关操作接口", tags = {"商户端众包发票关操作接口"})
@RestController
@RequestMapping("/merchant/crowdSourcing")
@Validated
public class CrowdSourcingMerchantController {

    @Resource
    private PaymentOrderManyService paymentOrderManyService;

    @Resource
    private CrowdSourcingInvoiceService crowdSourcingInvoiceService;

    @Resource
    private MerchantService merchantService;

    @ApiOperation("众包发票列表")
    @PostMapping(value = "/getListCSIByID")
    @LoginRequired
    public ReturnJson getListCSIByID(@Valid @RequestBody QueryCrowdSourcingDTO queryCrowdSourcingDto, @RequestAttribute("userId") @ApiParam(hidden = true) String userId) {
        return paymentOrderManyService.getListCSIByID(queryCrowdSourcingDto, userId);
    }

    @ApiOperation("众包支付信息")
    @GetMapping(value = "/getPayOrderManyById")
    public ReturnJson getPayOrderManyById(String id) {
        return paymentOrderManyService.getPayOrderManyById(id);
    }

    @ApiOperation("众包支付信息,创客支付明细")
    @PostMapping(value = "/getInvoiceDetailsByPayId")
    public ReturnJson getInvoiceDetailsByPayId(String id, Integer pageNo, Integer pageSize) {
        return paymentOrderManyService.getInvoiceDetailsByPayId(id, pageNo, pageSize);
    }

    @ApiOperation("众包支付信息,申请开票")
    @PostMapping(value = "/addCrowdSourcingInvoice")
    public ReturnJson addCrowdSourcingInvoice(@Valid @RequestBody AddApplicationCrowdSourcingDTO addApplicationCrowdSourcingDto) throws CommonException {
        return crowdSourcingInvoiceService.addCrowdSourcingInvoice(addApplicationCrowdSourcingDto);
    }

    @ApiOperation("众包支付信息,已开票页面")
    @PostMapping(value = "/getCrowdSourcingInfo")
    @LoginRequired
    public ReturnJson getCrowdSourcingInfo(@Valid @RequestBody QueryCrowdSourcingDTO queryCrowdSourcingDto, @RequestAttribute("userId") @ApiParam(hidden = true) String userId) {
        return crowdSourcingInvoiceService.getCrowdSourcingInfo(queryCrowdSourcingDto, userId);
    }

    @ApiOperation("众包发票列表,发票信息")
    @PostMapping(value = "/getInvoiceById")
    public ReturnJson getInvoiceById(String csiId) {
        return crowdSourcingInvoiceService.getInvoiceById(csiId);
    }

    @ApiOperation("开票信息，购买方,商户id")
    @GetMapping(value = "/getBuyerById")
    @LoginRequired
    public ReturnJson getBuyerById(@RequestAttribute("userId") @ApiParam(hidden = true) String id) {
        return merchantService.getBuyerById(id);
    }

    @ApiOperation("编辑开票信息")
    @GetMapping(value = "/queryApplicationInfo")
    @LoginRequired
    public ReturnJson queryApplicationInfo(@NotBlank(message = "申请开票id不能为空") @RequestParam String applicationId, @RequestAttribute("userId") @ApiParam(hidden = true) String id) throws CommonException {
        return crowdSourcingInvoiceService.queryApplicationInfo(applicationId, id);
    }

    @ApiOperation("已开票，详情信息")
    @GetMapping(value = "/queryInvoiceInfo")
    @LoginRequired
    public ReturnJson queryInvoiceInfo(@NotBlank(message = "申请开票id不能为空") @RequestParam String invoiceId, @RequestAttribute("userId") @ApiParam(hidden = true) String id) {
        return crowdSourcingInvoiceService.queryInvoiceInfo(invoiceId, id);
    }
}
