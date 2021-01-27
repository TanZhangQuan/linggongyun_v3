package com.example.merchant.controller.makerend;

import com.example.common.contract.exception.DefineException;
import com.example.common.enums.IdCardSide;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.makerend.IdCardInfoDTO;
import com.example.merchant.dto.makerend.WorkerBankDTO;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.AuthenticationService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Api(value = "小程序认证操作", tags = "小程序认证操作")
@RestController
@RequestMapping("/makerend/authentication")
@Validated
public class AuthenticationController {

    @Resource
    private AuthenticationService authenticationService;

    @PostMapping("/getIdCardInfo")
    @ApiOperation(value = "识别身份证获取信息", notes = "识别身份证获取信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "filePath", value = "身份证的访问地址", paramType = "query", required = true),
            @ApiImplicitParam(name = "idCardSide", value = "身份证正反面，FRONT正面，BACK反面", paramType = "query", dataType = "IdCardSide")})
    public ReturnJson getIdCardInfo(@NotBlank(message = "访问地址不能为空！") @RequestParam(required = false) String filePath, @RequestParam(required = false) @NotNull(message = "正反面不能为空！") IdCardSide idCardSide) throws Exception {
        return authenticationService.getIdCardInfo(filePath, idCardSide);
    }

    @PostMapping("/saveIdCardInfo")
    @ApiOperation(value = "保存创客的身份证信息", notes = "保存创客的身份证信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "idCardInfoDto", value = "身份证信息", dataType = "IdCardInfoDTO", required = true)})
    @LoginRequired
    public ReturnJson saveIdCardInfo(@Valid @RequestBody IdCardInfoDTO idCardInfoDto, @RequestAttribute(value = "userId") @ApiParam(hidden = true) String workerId) throws Exception {
        return authenticationService.saveIdCardinfo(idCardInfoDto, workerId);
    }

    @PostMapping("/saveBankInfo")
    @ApiOperation(value = "保存创客的银行卡信息", notes = "保存创客的银行卡信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "workerBankDto", value = "银行卡信息", dataType = "WorkerBankDTO", required = true)})
    @LoginRequired
    public ReturnJson saveBankInfo(@Valid @RequestBody WorkerBankDTO workerBankDto, @RequestAttribute(value = "userId") @ApiParam(hidden = true) String workerId) throws Exception {
        return authenticationService.saveBankInfo(workerBankDto, workerId);
    }

    @PostMapping("/saveWorkerVideo")
    @ApiOperation(value = "保存创客的活体视频", notes = "保存创客的活体视频")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "fileVideoPath", value = "活体视频的访问地址", paramType = "query", required = true)})
    @LoginRequired
    public ReturnJson saveWorkerVideo(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String workerId, @NotBlank(message = "访问地址不能为空！") @RequestParam(required = false) String fileVideoPath) {
        return authenticationService.saveWorkerVideo(workerId, fileVideoPath);
    }

    @PostMapping("/findSignAContract")
    @ApiOperation(value = "查看创客是否签署了加盟合同", notes = "查看创客是否签署了加盟合同")
    @LoginRequired
    public ReturnJson findSignAContract(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String workerId) {
        return authenticationService.findSignAContract(workerId);
    }

    @PostMapping("/senSignAContract")
    @ApiOperation(value = "发送签署加盟合同", notes = "发送签署加盟合同")
    @LoginRequired
    public ReturnJson senSignAContract(@RequestAttribute(value = "userId") @ApiParam(hidden = true) String workerId) throws DefineException {
        return authenticationService.senSignAContract(workerId);
    }

    @PostMapping("/callBackSignAContract")
    @ApiOperation(value = "签署加盟合同回调接口", notes = "签署加盟合同回调接口", hidden = true)
    public ReturnJson callBackSignAContract(HttpServletRequest request) {
        return authenticationService.callBackSignAContract(request);
    }

    @GetMapping("/callBackYYQSignAContract")
    @ApiOperation(value = "签署加盟合同回调接口", notes = "签署加盟合同回调接口", hidden = true)
    public ReturnJson callBackYYQSignAContract(String workerId,String contractNum,HttpServletRequest request) {
        return authenticationService.callBackYYQSignAContract(workerId,contractNum,request);
    }
}
