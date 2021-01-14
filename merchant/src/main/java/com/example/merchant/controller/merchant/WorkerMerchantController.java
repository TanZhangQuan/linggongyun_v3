package com.example.merchant.controller.merchant;


import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.WorkerDTO;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.WorkerService;
import com.example.merchant.service.WorkerTaskService;
import com.example.mybatis.entity.Worker;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
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
@Api(value = "商户端创客相关操作接口", tags = {"商户端创客相关操作接口"})
@RestController
@RequestMapping("/merchant/worker")
@Validated
public class WorkerMerchantController {

    @Resource
    private WorkerService workerService;

    @Resource
    private WorkerTaskService workerTaskService;

    @PostMapping("/getWorkerAll")
    @LoginRequired
    @ApiOperation(value = "获取商户的所有创客", notes = "获取商户的所有创客")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNo", value = "页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页的条数", required = true)
    })
    public ReturnJson getWorkerAll(@ApiParam(hidden = true) @RequestAttribute("userId") String merchantId,
                                   @RequestParam(defaultValue = "1") Integer pageNo,
                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        return workerService.getWorkerAll(merchantId, pageNo, pageSize);
    }



    @PostMapping("/getWorkerMany")
    @LoginRequired
    @ApiOperation(value = "按条件查询创客", notes = "按条件查询创客")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "workerDto", value = "查询条件", dataType = "WorkerDTO", required = true)})
    public ReturnJson getWorkerMany(@Valid @RequestBody WorkerDTO workerDto, @ApiParam(hidden = true) @RequestAttribute("userId") String merchantId) {
        return workerService.getByIdAndAccountNameAndMobile(merchantId, workerDto);
    }

    @PostMapping("/getWorkerInfo")
    @ApiOperation(value = "查询创客详情", notes = "查询创客详情")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "创客ID", required = true)})
    public ReturnJson getWorkerInfo(@NotBlank(message = "创客ID不能为空") @RequestParam(required = false) String id) {
        return workerService.getWorkerInfo(id);
    }

    @PostMapping("/saveWorker")
    @LoginRequired
    @ApiOperation(value = "导入创客", notes = "导入创客")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "workers", value = "需要导入的创客集合", required = true, allowMultiple = true, dataType = "Worker"),
            @ApiImplicitParam(name = "merchantId", value = "商户ID", required = true)})
    public ReturnJson saveWorker(@NotEmpty(message = "集合不能为空") @RequestBody List<Worker> workers, @ApiParam(hidden = true) @RequestAttribute("userId") String merchantId) throws Exception {
        return workerService.saveWorker(workers, merchantId);
    }

    @ApiOperation("已接单创客明细")
    @PostMapping(value = "/getYjWorkerDetails")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taskId", value = "任务id", required = true),
            @ApiImplicitParam(name = "pageNo", value = "第几页"), @ApiImplicitParam(name = "pageSize", value = "页大小")})
    public ReturnJson getYjWorkerDetails(@ApiParam(value = "任务id") @RequestParam String taskId,
                                         @ApiParam(value = "第几页") @RequestParam(defaultValue = "1") Integer pageNo,
                                         @ApiParam(value = "页大小") @RequestParam(defaultValue = "10") Integer pageSize) {
        return workerService.getWorkerByTaskId(taskId, pageNo, pageSize);
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
    public ReturnJson eliminateWorker(
            @NotBlank(message = "创客Id不能为空") @ApiParam(value = "创客id") @RequestParam String workerId,
                                      @NotBlank(message = "任务Id不能为空") @ApiParam(value = "任务id") @RequestParam String taskId) {
        return workerTaskService.eliminateWorker(workerId,taskId);
    }

    @ApiOperation("验收")
    @PostMapping(value = "/updateCheck")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "workerId", value = "创客Id", required = true)})
    public ReturnJson updateCheckMoney(@NotBlank(message = "请选择任务") @ApiParam(value = "任务id") @RequestParam String taskId,
                                       @ApiParam(value = "创客Id") @RequestParam String workerId) {
        return workerTaskService.updateCheck(taskId, workerId);
    }

    @ApiOperation("创客详情统计")
    @PostMapping(value = "/queryWorkerInfo")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "workerId", value = "创客Id", required = true)})
    public ReturnJson queryWorkerInfo(@ApiParam(value = "创客Id") @RequestParam String workerId) {
        return workerService.queryWorkerInfo(workerId);
    }

    @ApiOperation("创客任务详细信息")
    @PostMapping(value = "/queryWorkerTaskInfo")
    public ReturnJson queryWorkerTaskInfo(@NotNull(message = "当前页不能为空") @ApiParam(value = "当前页") @RequestParam Integer pageNo,
                                          @NotNull(message = "页大小不能为空")  @ApiParam(value = "页大小") @RequestParam Integer pageSize,
                                          @NotBlank(message = "创客Id不能为空") @ApiParam(value = "创客Id") @RequestParam String workerId) {
        return workerTaskService.queryWorkerTaskInfo(workerId,pageNo,pageSize);
    }

    @PostMapping("/queryWorkerCompanyByID")
    @LoginRequired
    @ApiOperation(value = "添加指定派单创客", notes = "添加指定派单创客")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNo", value = "页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页的条数", required = true)
    })
    public ReturnJson queryWorkerCompanyByID(@ApiParam(hidden = true) @RequestAttribute("userId") String merchantId,
                                   @RequestParam(defaultValue = "1") Integer pageNo,
                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        return workerService.queryWorkerCompanyByID(merchantId, pageNo, pageSize);
    }


    @ApiOperation("创客支付列表信息")
    @LoginRequired
    @PostMapping(value = "/queryWorkerPayInfo")
    public ReturnJson queryWorkerPayInfo(@NotNull(message = "当前页不能为空") @ApiParam(value = "当前页") @RequestParam Integer pageNo,
                                         @NotNull(message = "页大小不能为空") @ApiParam(value = "页大小") @RequestParam Integer pageSize,
                                         @NotBlank(message = "创客Id不能为空") @ApiParam(value = "创客Id") @RequestParam String workerId,
                                         @ApiParam(hidden = true) @RequestAttribute("userId") String merchantId) {
        return workerTaskService.queryMerchantWorkerPayInfo(workerId,merchantId, pageNo, pageSize);
    }
}

