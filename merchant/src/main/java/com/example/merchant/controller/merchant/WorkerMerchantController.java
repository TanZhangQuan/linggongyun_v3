package com.example.merchant.controller.merchant;


import com.example.common.util.ReturnJson;
import com.example.merchant.service.WorkerService;
import com.example.merchant.service.WorkerTaskService;
import com.example.mybatis.entity.Worker;
import io.swagger.annotations.*;
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
@Api(value = "商户端创客相关操作接口", tags = {"商户端创客相关操作接口"})
@RestController
@RequestMapping("/merchant/worker")
@Validated
public class WorkerMerchantController {

    @Resource
    private WorkerService workerService;

    @Resource
    private WorkerTaskService workerTaskService;

    @PostMapping("/getWorkerAll")
    @ApiOperation(value = "获取商户的所以创客", notes = "获取商户的所以创客", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "merchantId", value = "用户ID", required = true),
            @ApiImplicitParam(name = "page", value = "页数", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页的条数", required = true)
    })
    public ReturnJson getWorkerAll(@NotBlank(message = "用户ID不能为空") @RequestParam(required = false) String merchantId,
                                   @RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        return workerService.getWorkerAll(merchantId, page, pageSize);
    }

    @PostMapping("/getWorkerMany")
    @ApiOperation(value = "按条件查询创客", notes = "按条件查询创客", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "merchantId", value = "用户ID", required = true),
            @ApiImplicitParam(name = "id", value = "创客ID", required = false),
            @ApiImplicitParam(name = "accountName", value = "创客的真实姓名", required = false),
            @ApiImplicitParam(name = "mobileCode", value = "创客的手机号", required = false),
            @ApiImplicitParam(name = "page", value = "当前页数"),
            @ApiImplicitParam(name = "pageSize", value = "每页的条数")
    })
    public ReturnJson getWorkerMany(@NotBlank(message = "商户的公司ID不能为空") @RequestParam(required = false) String companyId, @RequestParam(required = false) String id,
                                    @RequestParam(required = false) String accountName, @RequestParam(required = false) String mobileCode,
                                    @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {
        return workerService.getByIdAndAccountNameAndMobile(companyId, id, accountName, mobileCode, page, pageSize);
    }

    @PostMapping("/getWorkerInfo")
    @ApiOperation(value = "查询创客详情", notes = "查询创客详情", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "id", value = "创客ID", required = true)})
    public ReturnJson getWorkerInfo(@NotBlank(message = "创客ID不能为空") @RequestParam(required = false) String id) {
        return workerService.getWorkerInfo(id);
    }

    @PostMapping("/saveWorker")
    @ApiOperation(value = "导入创客", notes = "导入创客", httpMethod = "POST")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "workers", value = "需要导入的创客集合", required = true, allowMultiple = true, dataType = "Worker"),
            @ApiImplicitParam(name = "merchantId", value = "商户ID", required = true)})
    public ReturnJson saveWorker(@NotEmpty(message = "集合不能为空") @RequestBody List<Worker> workers, @NotBlank(message = "用户ID不能为空") @RequestParam(required = false) String merchantId) {
        return workerService.saveWorker(workers, merchantId);
    }

    @ApiOperation("已接单创客明细")
    @PostMapping(value = "/getYjWorkerDetails")
    public ReturnJson getYjWorkerDetails(String taskId, Integer offset) {
        return workerService.getWorkerByTaskId(taskId, offset);
    }

    @ApiOperation("验收已接单创客明细")
    @PostMapping(value = "/getCheckByTaskId")
    public ReturnJson getCheckByTaskId(String taskId, Integer offset) {
        return workerService.getCheckByTaskId(taskId, offset);
    }


    @ApiOperation("剔除创客信息")
    @PostMapping(value = "/eliminateWorker")
    public ReturnJson eliminateWorker(@ApiParam(value = "任务状态") Integer state, @ApiParam(value = "创客id") String workerId) {
        return workerTaskService.eliminateWorker(state, workerId);
    }

    @ApiOperation("修改验收金额")
    @PostMapping(value = "/updateCheckMoney")
    public ReturnJson updateCheckMoney(Double money, String id) {
        return workerTaskService.updateCheckMoney(money, id);
    }

}

