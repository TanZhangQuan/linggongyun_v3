package com.example.merchant.controller.platform;


import com.example.common.util.ReturnJson;
import com.example.merchant.service.TaskService;
import com.example.mybatis.dto.TaskDto;
import com.example.mybatis.dto.TaskListDto;
import com.example.mybatis.entity.Task;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 任务表 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "平台端任务相关操作接口", tags = {"平台端任务相关操作接口"})
@RestController
@RequestMapping("/platform/task")
public class TaskPaasController {

    @Resource
    private TaskService taskService;

    @ApiOperation("删除任务信息")
    @DeleteMapping(value = "/deleteTask")
    public ReturnJson DeleteTask(@ApiParam(value = "任务id") @RequestParam String taskId) {
        return taskService.delete(taskId);
    }

    @ApiOperation("查看任务详情")
    @PostMapping(value = "/getTaskById")
    public ReturnJson getTaskById(@ApiParam(value = "任务id") @RequestParam String taskCode) {
        return taskService.setTaskById(taskCode);
    }

    @ApiOperation("关单")
    @PostMapping(value = "/colseTask")
    public ReturnJson colseTask(@ApiParam(value = "任务id") @RequestParam String taskId) {
        return taskService.close(taskId);
    }

    @ApiOperation("重新开启任务")
    @PostMapping(value = "/openTask")
    public ReturnJson openTask(@ApiParam(value = "任务状态") @RequestParam Integer state, @ApiParam(value = "任务id") @RequestParam String taskId) {
        return taskService.openTask(taskId);
    }

    @ApiOperation("平台端任务列表")
    @PostMapping(value = "/getplatformTasks")
    public ReturnJson<Task> platformTaskList(TaskListDto taskListDto) {
        return taskService.getPlatformTaskList(taskListDto);
    }

    @ApiOperation("平台端任务新增")
    @PostMapping(value = "/addPlatformTask")
    public ReturnJson addPlatformTask(@RequestBody TaskDto taskDto) {
        return taskService.savePlatformTask(taskDto);
    }

}
