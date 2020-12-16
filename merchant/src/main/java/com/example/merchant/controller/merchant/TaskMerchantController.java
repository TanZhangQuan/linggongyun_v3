package com.example.merchant.controller.merchant;


import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.AddTaskDto;
import com.example.merchant.interceptor.LoginRequired;
import com.example.merchant.service.TaskService;
import com.example.mybatis.dto.TaskDto;
import com.example.mybatis.dto.TaskListDto;
import com.example.mybatis.entity.Task;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.session.RowBounds;
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
@Api(value = "商户端任务相关操作接口", tags = {"商户端任务相关操作接口"})
@RestController
@RequestMapping("/merchant/task")
public class TaskMerchantController {

    @Resource
    private TaskService taskService;

    @LoginRequired
    @ApiOperation("任务列表")
    @PostMapping(value = "/getTasks")
    public ReturnJson<Task> TaskList(TaskListDto taskListDto,@RequestAttribute("userId") @ApiParam(hidden = true) String userId) {
        return taskService.selectList(taskListDto,userId);
    }

    @ApiOperation("删除任务信息")
    @DeleteMapping(value = "/deleteTask")
    public ReturnJson DeleteTask(@ApiParam(value = "任务id") @RequestParam String taskId) {
        return taskService.delete(taskId);
    }

    @LoginRequired
    @ApiOperation("任务新增")
    @PostMapping(value = "/addTask")
    public ReturnJson addTask(@RequestBody AddTaskDto addTaskDto, @RequestAttribute(value = "userId") @ApiParam(hidden = true) String userId) {
        return taskService.saveTask(addTaskDto,userId);
    }

    @ApiOperation("查看任务详情")
    @PostMapping(value = "/getTaskById")
    public ReturnJson getTaskById(@ApiParam(value = "任务id") @RequestParam String taskId) {
        return taskService.setTaskById(taskId);
    }

    @ApiOperation("关单")
    @PostMapping(value = "/colseTask")
    public ReturnJson colseTask(@ApiParam(value = "任务id") @RequestParam String taskId) {
        return taskService.close(taskId);
    }

    @ApiOperation("重新开启任务")
    @PostMapping(value = "/openTask")
    public ReturnJson openTask(@ApiParam(value = "任务id") @RequestParam String taskId) {
        return taskService.openTask(taskId);
    }

}
