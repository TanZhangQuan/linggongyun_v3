package com.example.merchant.controller.makerend;

import com.example.common.util.ReturnJson;
import com.example.merchant.service.TaskService;
import com.example.merchant.service.WorkerTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "小程序任务", tags = {"小程序任务"})
@RestController
@RequestMapping(value = "makerend/task")
public class TaskMakerendController {

    private static Logger logger= LoggerFactory.getLogger(TaskMakerendController.class);

    @Autowired
    private TaskService taskService;
    @Autowired
    private WorkerTaskService workerTaskService;

    @ApiOperation(value = "任务大厅")
    @GetMapping(value = "/missionHall")
    public ReturnJson setTask(@ApiParam(value = "商户id",required = true) @RequestParam("merchantId")String merchantId,
                              @ApiParam(value = "任务行业类型") @RequestParam("industryType")String industryType){
        ReturnJson returnJson=new ReturnJson("操作失败",300);
        try {
            returnJson=taskService.setTask(merchantId,industryType);
        }catch (Exception err){
            logger.error("出现异常错误",err);
        }
        return returnJson;
    }

    @ApiOperation(value = "任务详情")
    @GetMapping(value = "/taskDetails")
    public ReturnJson taskDetails(@ApiParam(value = "任务id",required = true) @RequestParam("taskId")String taskId){
        ReturnJson returnJson=new ReturnJson("操作失败",300);
        try {
            returnJson=taskService.taskDetails(taskId);
        }catch (Exception err){
            logger.error("出现异常错误",err);
        }
        return returnJson;
    }

    @ApiOperation(value = "我的任务")
    @GetMapping(value = "/myTask")
    public ReturnJson myTask(@ApiParam(value = "创客id",required = true) @RequestParam("workerId")String workerId,
                             @ApiParam(value = "创客完成状态0进行中1已完成2已失效3已提交4已验收") @RequestParam(value = "status",required = false)String status){
        ReturnJson returnJson=new ReturnJson("操作失败",300);
        try {
            returnJson=taskService.myTask(workerId, status);
        }catch (Exception err){
            logger.error("出现异常错误",err);
        }
        return returnJson;
    }

    @ApiOperation(value = "提交工作结果")
    @GetMapping(value = "/acceptanceResults")
    public ReturnJson acceptanceResults(@ApiParam(value = "任务创客关联id",required = true) @RequestParam("workerTaskId")String workerTaskId,
                                        @ApiParam(value = "工作成果说明",required = true) @RequestParam("achievementDesc") String achievementDesc,
                                        @ApiParam(value = "工作成果附件,可以多个文件,隔开",required = true) @RequestParam("achievementFiles")String achievementFiles){
        ReturnJson returnJson=new ReturnJson("操作失败",300);
        try {
            returnJson=workerTaskService.acceptanceResults(workerTaskId,achievementDesc,achievementFiles);
        }catch (Exception err){
            logger.error("出现异常错误",err);
        }
        return returnJson;
    }

    @ApiOperation(value = "查看某条工作成果")
    @GetMapping(value = "/getWorkerTask")
    public ReturnJson getWorkerTask(@ApiParam(value = "任务创客关联id",required = true) @RequestParam("workerTaskId")String workerTaskId){
        ReturnJson returnJson=new ReturnJson("操作失败",300);
        try {
            returnJson=workerTaskService.getWorkerTask(workerTaskId);
        }catch (Exception err){
            logger.error("出现异常错误",err);
        }
        return returnJson;
    }

    @ApiOperation(value = "抢单")
    @GetMapping(value = "/orderGrabbing")
    public ReturnJson orderGrabbing(@ApiParam(value = "任务id",required = true)@RequestParam("taskId") String taskId,
                                    @ApiParam(value = "创客id",required = true)@RequestParam("workerId")String workerId){
        ReturnJson returnJson=new ReturnJson("操作失败",300);
        try {
            returnJson=taskService.orderGrabbing(taskId, workerId);
        }catch (Exception err){
            logger.error("出现异常错误",err);
        }
        return returnJson;
    }
}
