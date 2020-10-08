package com.example.merchant.controller.platform;


import com.example.common.util.ReturnJson;
import com.example.merchant.exception.CommonException;
import com.example.merchant.service.WorkerService;
import com.example.merchant.service.WorkerTaskService;
import com.example.mybatis.entity.Worker;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 * 创客表 前端控制器
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Api(value = "平台端创客相关操作接口", tags = {"平台端创客相关操作接口"})
@RestController
@RequestMapping("/platform/worker")
@Validated
public class WorkerPaasController {

    @Autowired
    private WorkerService workerService;

    @PostMapping("/getWorkerAll")
    @ApiOperation(value = "获取所以已认证的创客", notes = "获取所以已认证的创客", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "managersId", value = "管理人员ID", required = true),
            @ApiImplicitParam(name = "page", value = "页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页的条数", required = true)
    })
    public ReturnJson getWorkerAll(@NotBlank(message = "用户ID不能为空") @RequestParam(required = false) String managersId,
                                   @RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer pageSize) throws CommonException {
        return workerService.getWorkerAllPaas(managersId, page, pageSize);
    }

    @PostMapping("/getWorkerAllNot")
    @ApiOperation(value = "获取所以未认证的创客", notes = "获取所以未认证的创客", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "managersId", value = "管理人员ID", required = true),
            @ApiImplicitParam(name = "page", value = "页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页的条数", required = true)
    })
    public ReturnJson getWorkerAllNot(@NotBlank(message = "用户ID不能为空") @RequestParam(required = false) String managersId,
                                      @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer pageSize) throws CommonException {
        return workerService.getWorkerAllNotPaas(managersId, page, pageSize);
    }


    @PostMapping("/getWorkerMany")
    @ApiOperation(value = "按条件查询已认证的创客", notes = "按条件查询已认证的创客", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "managersId", value = "管理人员ID", required = true),
            @ApiImplicitParam(name = "id", value = "创客ID", required = false),
            @ApiImplicitParam(name = "accountName", value = "创客的真实姓名", required = false),
            @ApiImplicitParam(name = "mobileCode", value = "创客的手机号", required = false)
    })
    public ReturnJson getWorkerMany(@NotBlank(message = "管理人员ID不能为空") @RequestParam(required = false) String managersId, @RequestParam(required = false) String id,
                                    @RequestParam(required = false) String accountName, @RequestParam(required = false) String mobileCode) throws CommonException {
        return workerService.getByIdAndAccountNameAndMobilePaas(managersId, id, accountName, mobileCode);
    }

    @PostMapping("/getWorkerManyNot")
    @ApiOperation(value = "按条件查询未认证的创客", notes = "按条件查询已认证的创客", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "managersId", value = "管理人员ID", required = true),
            @ApiImplicitParam(name = "id", value = "创客ID", required = false),
            @ApiImplicitParam(name = "accountName", value = "创客的真实姓名", required = false),
            @ApiImplicitParam(name = "mobileCode", value = "创客的手机号", required = false)
    })
    public ReturnJson getWorkerManyNot(@NotBlank(message = "管理人员ID不能为空") @RequestParam(required = false) String managersId, @RequestParam(required = false) String id,
                                       @RequestParam(required = false) String accountName, @RequestParam(required = false) String mobileCode) throws CommonException {
        return workerService.getByIdAndAccountNameAndMobileNotPaas(managersId, id, accountName, mobileCode);
    }

    @PostMapping("/getWorkerInfo")
    @ApiOperation(value = "查询创客详情", notes = "查询创客详情", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "创客ID", required = true)})
    public ReturnJson getWorkerInfo(@NotBlank(message = "创客ID不能为空") @RequestParam(required = false) String id) {
        return workerService.getWorkerInfo(id);
    }

    @PostMapping("/getWorkerPaymentList")
    @ApiOperation(value = "查询创客的收入列表", notes = "查询创客的收入列表", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "创客ID", required = true)})
    public ReturnJson getWorkerPaymentList(@NotBlank(message = "创客ID不能为空") @RequestParam(required = false) String id,
                                           @RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        return workerService.getWorkerPaymentListPaas(id, page, pageSize);
    }

    @PostMapping("/saveWorker")
    @ApiOperation(value = "导入创客", notes = "导入创客", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "workers", value = "需要导入的创客集合", required = true, allowMultiple = true, dataType = "Worker"),
            @ApiImplicitParam(name = "managersId", value = "管理人员ID", required = true)})
    public ReturnJson saveWorker(@NotEmpty(message = "集合不能为空") @RequestBody List<Worker> workers, @NotBlank(message = "用户ID不能为空") @RequestParam(required = false) String managersId) {
        return workerService.saveWorker(workers, managersId);
    }

    @PostMapping("/updetaWorker")
    @ApiOperation(value = "编辑创客", notes = "编辑创客", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "worker", value = "编辑后的创客", required = true, dataType = "Worker")})
    public ReturnJson updataWorker(@RequestBody Worker worker) {
        return workerService.updateWorkerPaas(worker);
    }

    private static Logger logger = LoggerFactory.getLogger(TaskPaasController.class);


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

