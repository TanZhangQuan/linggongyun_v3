package com.example.merchant.controller.platform;


import com.example.common.util.ReturnJson;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.TaskService;
import com.example.mybatis.dto.PlatformTaskDTO;
import com.example.mybatis.dto.TaskDTO;
import com.example.mybatis.entity.Task;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
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
    public ReturnJson getTaskById(@NotBlank(message = "请选择任务") @ApiParam(value = "任务id") @RequestParam String taskId) {
        return taskService.setTaskById(taskId);
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
    @LoginRequired
    public ReturnJson<Task> platformTaskList(@Valid @RequestBody PlatformTaskDTO platformTaskDto,
                                             @RequestAttribute("userId")@ApiParam(hidden = true)String userId) {
        return taskService.getPlatformTaskList(platformTaskDto,userId);
    }

    @ApiOperation("平台端任务新增")
    @PostMapping(value = "/addPlatformTask")
    public ReturnJson addPlatformTask(@Valid @RequestBody TaskDTO taskDto) {
        return taskService.savePlatformTask(taskDto);
    }

    @ApiOperation("平台端任务编辑")
    @PostMapping(value = "/updatePlatfromTask")
    public ReturnJson updatePlatfromTask(@Valid @RequestBody TaskDTO taskDto) {
        return taskService.updatePlatfromTask(taskDto);
    }
}
