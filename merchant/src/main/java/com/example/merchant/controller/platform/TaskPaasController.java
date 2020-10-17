package com.example.merchant.controller.platform;


import com.example.common.util.ReturnJson;
import com.example.merchant.service.TaskService;
import com.example.mybatis.dto.PlatformTaskDto;
import com.example.mybatis.dto.TaskDto;
import com.example.mybatis.dto.TaskListDto;
import com.example.mybatis.entity.Task;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

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
@Validated
public class TaskPaasController {

    @Resource
    private TaskService taskService;

    @ApiOperation("删除任务信息")
    @DeleteMapping(value = "/deleteTask")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taskId", value = "任务id", required = true)})
    public ReturnJson DeleteTask(@NotBlank(message = "请选择任务") @ApiParam(value = "任务id") @RequestParam String taskId) {
        return taskService.delete(taskId);
    }

    @ApiOperation("查看任务详情")
    @PostMapping(value = "/getTaskById")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taskId", value = "任务id", required = true)})
    public ReturnJson getTaskById(@NotBlank(message = "请选择任务") @ApiParam(value = "任务id") @RequestParam String taskCode) {
        return taskService.setTaskById(taskCode);
    }

    @ApiOperation("关单")
    @PostMapping(value = "/colseTask")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taskId", value = "任务id", required = true)})
    public ReturnJson colseTask(@NotBlank(message = "请选择任务") @ApiParam(value = "任务id") @RequestParam String taskId) {
        return taskService.close(taskId);
    }

    @ApiOperation("重新开启任务")
    @PostMapping(value = "/openTask")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "taskId", value = "任务id", required = true)})
    public ReturnJson openTask(@NotBlank(message = "请选择任务") @ApiParam(value = "任务id") @RequestParam String taskId) {
        return taskService.openTask(taskId);
    }

    @ApiOperation("平台端任务列表")
    @PostMapping(value = "/getplatformTasks")
    public ReturnJson<Task> platformTaskList(PlatformTaskDto platformTaskDto) {
        return taskService.getPlatformTaskList(platformTaskDto);
    }

    @ApiOperation("平台端任务新增")
    @PostMapping(value = "/addPlatformTask")
    public ReturnJson addPlatformTask(@RequestBody TaskDto taskDto) {
        return taskService.savePlatformTask(taskDto);
    }

}
