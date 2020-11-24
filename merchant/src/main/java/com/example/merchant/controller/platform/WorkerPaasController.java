package com.example.merchant.controller.platform;


import com.example.common.util.ReturnJson;
import com.example.merchant.dto.platform.WorkerQueryDto;
import com.example.merchant.exception.CommonException;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.WorkerService;
import com.example.merchant.service.WorkerTaskService;
import com.example.mybatis.entity.Worker;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 创客表 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "平台端创客相关操作接口", tags = {"平台端创客相关操作接口"})
@RestController
@RequestMapping("/platform/worker")
@Validated
public class WorkerPaasController {

    @Resource
    private WorkerService workerService;

    @Resource
    private WorkerTaskService workerTaskService;

    @PostMapping("/getWorkerQuery")
    @LoginRequired
    @ApiOperation(value = "按条件获取已认证的创客", notes = "按条件获取已认证的创客", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "创客ID"),
            @ApiImplicitParam(name = "accountName", value = "创客的真实姓名"),
            @ApiImplicitParam(name = "mobileCode", value = "创客的手机号")
    })
    public ReturnJson getWorkerQuery(@ApiParam(hidden = true) @RequestAttribute("userId") String managersId, @RequestBody WorkerQueryDto workerQueryDto) throws CommonException {
        return workerService.getWorkerQuery(managersId, workerQueryDto);
    }

    @PostMapping("/getWorkerQueryNot")
    @LoginRequired
    @ApiOperation(value = "按条件获取所以未认证的创客", notes = "按条件获取所以未认证的创客", httpMethod = "POST")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "创客ID"),
            @ApiImplicitParam(name = "accountName", value = "创客的真实姓名"),
            @ApiImplicitParam(name = "mobileCode", value = "创客的手机号")
    })
    public ReturnJson getWorkerQueryNot(@ApiParam(hidden = true) @RequestAttribute("userId") String managersId, @RequestBody WorkerQueryDto workerQueryDto) throws CommonException {
        return workerService.getWorkerQueryNot(managersId, workerQueryDto);
    }

    @PostMapping("/getWorkerInfo")
    @ApiOperation(value = "查询创客详情", notes = "查询创客详情", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "创客ID", required = true)})
    public ReturnJson getWorkerInfo(@NotBlank(message = "创客ID不能为空") @RequestParam(required = false) String id) {
        return workerService.getWorkerInfo(id);
    }

    @PostMapping("/getWorkerPaymentList")
    @ApiOperation(value = "查询创客的收入列表", notes = "查询创客的收入列表", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "创客ID", required = true)})
    public ReturnJson getWorkerPaymentList(@NotBlank(message = "创客ID不能为空") @RequestParam(required = false) String id,
                                           @RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        return workerService.getWorkerPaymentListPaas(id, page, pageSize);
    }

    @PostMapping("/saveWorker")
    @ApiOperation(value = "导入创客", notes = "导入创客", httpMethod = "POST")
    @LoginRequired
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "workers", value = "需要导入的创客集合", required = true, allowMultiple = true, dataType = "Worker")})
    public ReturnJson saveWorker(@NotEmpty(message = "集合不能为空") @RequestBody List<Worker> workers, @ApiParam(hidden = true) @RequestAttribute("userId") String managersId) throws Exception {
        return workerService.saveWorker(workers, managersId);
    }

    @PostMapping("/updetaWorker")
    @ApiOperation(value = "编辑创客", notes = "编辑创客", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "worker", value = "编辑后的创客", required = true, dataType = "Worker")})
    public ReturnJson updataWorker(@RequestBody Worker worker) {
        return workerService.updateWorkerPaas(worker);
    }

    @ApiOperation("已接单创客明细")
    @PostMapping(value = "/getYjWorkerDetails")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taskId", value = "任务id", required = true),
            @ApiImplicitParam(name = "pageNo", value = "第几页"), @ApiImplicitParam(name = "pageSize", value = "页大小")})
    public ReturnJson getYjWorkerDetails(@ApiParam(value = "任务id") @RequestParam String taskId,
                                         @ApiParam(value = "第几页") @RequestParam(defaultValue = "1") Integer pageNo,
                                         @ApiParam(value = "页大小") @RequestParam(defaultValue = "10") Integer pageSize) {
        return workerService.getPaasWorkerByTaskId(taskId, pageNo, pageSize);
    }

    @ApiOperation("验收已接单创客明细")
    @PostMapping(value = "/getCheckByTaskId")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taskId", value = "任务id", required = true),
            @ApiImplicitParam(name = "pageNo", value = "第几页"), @ApiImplicitParam(name = "pageSize", value = "页大小")})
    public ReturnJson getCheckByTaskId(@NotBlank(message = "请选择任务") @ApiParam(value = "任务id") @RequestParam String taskId,
                                       @ApiParam(value = "第几页") @RequestParam(defaultValue = "1") Integer pageNo,
                                       @ApiParam(value = "页大小") @RequestParam(defaultValue = "10") Integer pageSize) {
        return workerService.getCheckByTaskId(taskId, pageNo, pageSize);
    }

    @ApiOperation("剔除创客信息")
    @PostMapping(value = "/eliminateWorker")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "workerId", value = "创客id", required = true),
            @ApiImplicitParam(name = "taskId", value = "任务Id", required = true)})
    public ReturnJson eliminateWorker(@NotBlank(message = "创客id不能为空") @ApiParam(value = "创客id") @RequestParam String workerId,
                                      @NotBlank(message = "任务Id不能为空") @ApiParam(value = "任务id") @RequestParam String taskId) {
        return workerTaskService.eliminateWorker(null, workerId,taskId);
    }

    @ApiOperation("修改验收金额")
    @PostMapping(value = "/updateCheckMoney")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "taskId", value = "任务Id", required = true),
            @ApiImplicitParam(name = "money", value = "验收金额", required = true),
            @ApiImplicitParam(name = "workerId", value = "创客Id", required = true)})
    public ReturnJson updateCheckMoney(@NotBlank(message = "请选择任务") @ApiParam(value = "任务id") @RequestParam String taskId,
                                       @NotNull(message = "验收金额不能为空") @ApiParam(value = "验收金额") @RequestParam Double money,
                                       @ApiParam(value = "创客Id") @RequestParam String workerId) {
        return workerTaskService.updateCheckMoney(taskId, money, workerId);
    }

}

