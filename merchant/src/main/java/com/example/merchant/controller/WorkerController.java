package com.example.merchant.controller;


import com.example.common.util.ReturnJson;
import com.example.merchant.service.WorkerService;
import com.example.merchant.service.WorkerTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 创客表 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "创客相关操作接口", tags = {"创客相关操作接口"})
@RestController
@RequestMapping("/merchant/worker")
public class WorkerController {

    private static Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Resource
    private WorkerService workerService;

    @Resource
    private WorkerTaskService workerTaskService;


    @ApiOperation("已接单创客明细")
    @PostMapping(value = "/getYjWorkerDetails")
    public ReturnJson getYjWorkerDetails(String taskId, Integer offset) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        try {
            returnJson = workerService.getWorkerByTaskId(taskId, offset);
        } catch (Exception e) {
            returnJson = new ReturnJson(e.toString(), 300);
        }
        return returnJson;
    }

    @ApiOperation("验收已接单创客明细")
    @PostMapping(value = "/getCheckByTaskId")
    public ReturnJson getCheckByTaskId(String taskId, Integer offset) {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        try {
            returnJson = workerService.getCheckByTaskId(taskId, offset);
        } catch (Exception e) {
            returnJson = new ReturnJson(e.toString(), 300);
        }
        return returnJson;
    }


    @ApiOperation("剔除创客信息")
    @PostMapping(value = "/eliminateWorker")
    public ReturnJson eliminateWorker(@ApiParam(value = "任务状态") Integer state, @ApiParam(value = "创客id") String workerId) {
        ReturnJson returnJson = new ReturnJson("剔除失败", 300);
        try {
            returnJson = workerTaskService.eliminateWorker(state, workerId);
        } catch (Exception e) {
            logger.error("出现异常错误", e);
        }
        return returnJson;
    }

    @ApiOperation("修改验收金额")
    @PostMapping(value = "/updateCheckMoney")
    public ReturnJson updateCheckMoney(Double money, String id) {
        ReturnJson returnJson = new ReturnJson("修改失败", 300);
        try {
            returnJson = workerTaskService.updateCheckMoney(money, id);
        } catch (Exception e) {
            logger.error("出现异常错误", e);
        }
        return returnJson;
    }

}
