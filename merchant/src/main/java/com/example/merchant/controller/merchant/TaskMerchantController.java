package com.example.merchant.controller.merchant;


import com.example.common.util.ReturnJson;
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

    @ApiOperation("任务列表")
    @PostMapping(value = "/getTasks")
    public ReturnJson<Task> TaskList(TaskListDto taskListDto) {
        RowBounds rowBounds = new RowBounds(taskListDto.getPageNo(), taskListDto.getPageSize());
        return taskService.selectList(taskListDto, rowBounds);
    }

    @ApiOperation("删除任务信息")
    @DeleteMapping(value = "/deleteTask")
    public ReturnJson DeleteTask(@ApiParam(value = "任务状态") @RequestParam Integer state, @ApiParam(value = "任务id") @RequestParam String id) {
        return taskService.delete(state, id);
    }

    @ApiOperation("任务新增")
    @PostMapping(value = "/addTask")
    public ReturnJson addTask(@RequestBody TaskDto taskDto) {
        return taskService.saveTask(taskDto);
    }

    @ApiOperation("查看任务详情")
    @PostMapping(value = "/getTaskById")
    public ReturnJson getTaskById(@ApiParam(value = "任务id") @RequestParam String taskCode) {
        return taskService.setTaskById(taskCode);
    }

    @ApiOperation("关单")
    @PostMapping(value = "/colseTask")
    public ReturnJson colseTask(@ApiParam(value = "任务状态") @RequestParam Integer state, @ApiParam(value = "任务id") @RequestParam String id) {
        return taskService.close(state, id);
    }

    @ApiOperation("重新开启任务")
    @PostMapping(value = "/openTask")
    public ReturnJson openTask(@ApiParam(value = "任务状态") @RequestParam Integer state, @ApiParam(value = "任务id") @RequestParam String id) {
        return taskService.openTask(state, id);
    }

}
