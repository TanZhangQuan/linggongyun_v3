package com.example.merchant.controller.merchant;


import com.example.common.util.*;
import com.example.merchant.service.TaskService;
import com.example.mybatis.dto.TaskDto;
import com.example.mybatis.dto.TaskListDto;
import com.example.mybatis.entity.Task;
import io.swagger.annotations.*;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Api(value = "任务相关操作接口", tags = {"任务相关操作接口"})
@RestController
@RequestMapping("/merchant/task")
public class TaskControllerMerchant {

    private static Logger logger = LoggerFactory.getLogger(TaskControllerMerchant.class);

    @Resource
    private TaskService taskService;

    @ApiOperation("任务列表")
    @PostMapping(value = "/getTasks")
    public ReturnJson<Task> TaskList(TaskListDto taskListDto) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        try {
            RowBounds rowBounds=new RowBounds(taskListDto.getPageNo(),3);
            returnJson = taskService.selectList(taskListDto,rowBounds);
        } catch (Exception err) {
            logger.error("返回错误类型",err);
        }
        return returnJson;
    }


    // 删除任务信息
    @ApiOperation("删除任务信息")
    @DeleteMapping(value = "/deleteTask")
    public ReturnJson DeleteTask(@ApiParam(value = "任务状态") @RequestParam Integer state,@ApiParam(value = "任务id") @RequestParam String id) {
        try {
            return taskService.delete(state,id);
        } catch (Exception err) {
            logger.error("添加备注标签异常", err);
            return new ReturnJson(err.toString(), 300);
        }
    }


    //添加任务信息
    @ApiOperation("任务新增")
    @PostMapping(value = "/addTask")
    public ReturnJson addTask(@RequestBody TaskDto taskDto) {
        try {
            return taskService.saveTask(taskDto);
        } catch (Exception err) {
            logger.error("添加备注标签异常", err);
            return new ReturnJson(err.toString(), 300);
        }
    }


    //查看任务详情
    @ApiOperation("查看任务详情")
    @PostMapping(value = "/getTaskById")
    public ReturnJson getTaskById(@ApiParam(value = "任务id") @RequestParam String taskCode) {
        try {
            return taskService.setTaskById(taskCode);
        } catch (Exception err) {
            logger.error("添加备注标签异常", err);
            return new ReturnJson(err.toString(), 300);
        }
    }


    // 关单
    @ApiOperation("关单")
    @PostMapping(value = "/colseTask")
    public ReturnJson colseTask(@ApiParam(value = "任务状态") @RequestParam Integer state,@ApiParam(value = "任务id") @RequestParam String id) {
        ReturnJson returnJson=new ReturnJson("关单失败",300);
        try {
            returnJson= taskService.close(state,id);
        } catch (Exception err) {
            logger.error("添加备注标签异常", err);
        }
        return returnJson;
    }


    // 重新开启任务
    @ApiOperation("重新开启任务")
    @PostMapping(value = "/openTask")
    public ReturnJson openTask(@ApiParam(value = "任务状态") @RequestParam Integer state,@ApiParam(value = "任务id") @RequestParam String id) {
        ReturnJson returnJson=new ReturnJson("重新开启任务失败",300);
        try {
            returnJson= taskService.openTask(state,id);
        } catch (Exception err) {
            logger.error("添加备注标签异常", err);
        }
        return returnJson;
    }


    @ApiOperation("平台端任务列表")
    @PostMapping(value = "/getplatformTasks")
    public ReturnJson<Task> platformTaskList(TaskListDto taskListDto) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        try {
            returnJson = taskService.getPlatformTaskList(taskListDto);
        } catch (Exception err) {
            logger.error("返回错误类型",err);
        }
        return returnJson;
    }

    //平台端添加任务信息
    @ApiOperation("平台端任务新增")
    @PostMapping(value = "/addPlatformTask")
    public ReturnJson addPlatformTask(@RequestBody TaskDto taskDto) {
        try {
            return taskService.savePlatformTask(taskDto);
        } catch (Exception err) {
            logger.error("添加备注标签异常", err);
            return new ReturnJson(err.toString(), 300);
        }
    }

}
