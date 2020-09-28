package com.example.merchant.controller.makerend;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.IdCardInfoDto;
import com.example.merchant.dto.WorkerBankDto;
import com.example.merchant.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/makerend/authentication")
@Api(value = "小程序认证操作", tags = "小程序认证操作")
@Validated
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/getIdCardInfo")
    @ApiOperation(value = "识别身份证获取信息", notes = "识别身份证获取信息", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "filePath", value = "身份证正面的访问地址", paramType = "query",required = true)})
    public ReturnJson getIdCardInfo(@NotBlank(message = "访问地址不能为空！") @RequestParam String filePath) {
        return authenticationService.getIdCardInfo(filePath);
    }

    @PostMapping("/saveIdCardInfo")
    @ApiOperation(value = "保存创客的身份证信息", notes = "保存创客的身份证信息", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "idCardInfoDto", value = "身份证信息", dataType = "IdCardInfoDto",required = true)})
    public ReturnJson saveIdCardInfo(@Valid @RequestBody IdCardInfoDto idCardInfoDto){
        return authenticationService.saveIdCardinfo(idCardInfoDto);
    }

    @PostMapping("/saveBankInfo")
    @ApiOperation(value = "保存创客的银行卡信息", notes = "保存创客的银行卡信息", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "workerBankDto", value = "身份证正面的访问地址", dataType = "WorkerBankDto",required = true)})
    public ReturnJson saveBankInfo(@Valid @RequestBody WorkerBankDto workerBankDto){
        return authenticationService.saveBankInfo(workerBankDto);
    }

    @PostMapping("/saveWorkerVideo")
    @ApiOperation(value = "保存创客的活体视频", notes = "保存创客的活体视频", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "workerId", value = "创客ID", paramType = "query",required = true),@ApiImplicitParam(name = "fileVideoPath", value = "活体视频的访问地址", paramType = "query",required = true)})
    public ReturnJson saveWorkerVideo(@NotBlank(message = "创客ID不能为空！") @RequestParam String workerId,@NotBlank(message = "访问地址不能为空！") @RequestParam String fileVideoPath) {
        return authenticationService.saveWorkerVideo(workerId,fileVideoPath);
    }
}
