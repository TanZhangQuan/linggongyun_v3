package com.example.merchant.controller.makerend;

import com.example.common.util.ReturnJson;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.TaskService;
import com.example.merchant.service.WorkerTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(value = "小程序任务", tags = {"小程序任务"})
@RestController
@RequestMapping(value = "makerend/task")
public class TaskMakerendController {

    @Resource
    private TaskService taskService;

    @Resource
    private WorkerTaskService workerTaskService;

    @ApiOperation(value = "任务大厅")
    @GetMapping(value = "/missionHall")
    public ReturnJson setTask(
            @ApiParam(value = "任务行业类型") @RequestParam("industryType") String industryType) {

        return taskService.setTask(industryType);
    }

    @ApiOperation(value = "任务详情")
    @GetMapping(value = "/taskDetails")
    public ReturnJson taskDetails(@ApiParam(value = "任务id", required = true) @RequestParam("taskId") String taskId) {
        return taskService.taskDetails(taskId);
    }

    @ApiOperation(value = "我的任务")
    @GetMapping(value = "/myTask")
    @LoginRequired
    public ReturnJson myTask(@ApiParam(value = "创客id", hidden = true) @RequestAttribute(value = "userId") String workerId,
                             @ApiParam(value = "创客完成状态0进行中1已完成2已失效3已提交4已验收") @RequestParam(value = "status", required = false) String status) {
        return taskService.myTask(workerId, status);
    }

    @ApiOperation(value = "提交工作结果")
    @GetMapping(value = "/acceptanceResults")
    public ReturnJson acceptanceResults(@ApiParam(value = "任务创客关联id", required = true) @RequestParam("workerTaskId") String workerTaskId,
                                        @ApiParam(value = "工作成果说明", required = true) @RequestParam("achievementDesc") String achievementDesc,
                                        @ApiParam(value = "工作成果附件,可以多个文件,隔开", required = true) @RequestParam("achievementFiles") String achievementFiles) {

        return workerTaskService.acceptanceResults(workerTaskId, achievementDesc, achievementFiles);
    }

    @ApiOperation(value = "查看某条工作成果")
    @GetMapping(value = "/getWorkerTask")
    public ReturnJson getWorkerTask(@ApiParam(value = "任务创客关联id", required = true) @RequestParam("workerTaskId") String workerTaskId) {
        return workerTaskService.getWorkerTask(workerTaskId);
    }

    @ApiOperation(value = "抢单")
    @GetMapping(value = "/orderGrabbing")
    @LoginRequired
    public ReturnJson orderGrabbing(@ApiParam(value = "任务id", required = true) @RequestParam("taskId") String taskId,
                                    @ApiParam(value = "创客id", hidden = true) @RequestAttribute(value = "userId") String workerId) {
        return taskService.orderGrabbing(taskId, workerId);
    }

    @ApiOperation(value = "行业列表")
    @GetMapping(value = "/queryIndustryType")
    public ReturnJson queryIndustryType() {
        return taskService.getindustryType();
    }
}
