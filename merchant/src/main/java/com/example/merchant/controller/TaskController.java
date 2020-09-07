package com.example.merchant.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.common.util.PageData;
import com.example.common.util.ReturnJson;
import com.example.common.util.Tools;
import com.example.common.util.VerificationCheck;
import com.example.merchant.service.MerchantService;
import com.example.merchant.service.TaskService;
import com.example.mybatis.entity.Merchant;
import com.example.mybatis.entity.Task;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
public class TaskController {

    @Resource
    private TaskService taskService;
    @Resource
    private MerchantService merchantService;

    @ApiOperation("任务列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "登录令牌", dataType = "string", paramType = "header"),
            @ApiImplicitParam(dataType = "string", name = "taskCode", value = "任务编号", paramType = "form"),
            @ApiImplicitParam(dataType = "string", name = "taskName", value = "任务名称", paramType = "form"),
            @ApiImplicitParam(dataType = "string", name = "releaseDate", value = "开始日期", paramType = "form"),
            @ApiImplicitParam(dataType = "string", name = "deadlineDate", value = "结束日期", paramType = "form"),
            @ApiImplicitParam(dataType = "int", name = "cooperateMode", value = "合作类型 0总包+分包,1众包", paramType = "form"),
            @ApiImplicitParam(dataType = "int", name = "pageNo", value = "页码数,默认第一页参数为1", paramType = "form")})
    @RequestMapping(value = "/GetTasks", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"})
    public ReturnJson<Task> TaskList(String taskCode, String taskName, String releaseDate, String deadlineDate, Integer cooperateMode, Integer pageNo) {
        if (!VerificationCheck.isNull(pageNo)) {
            return new ReturnJson("页码数不能为空", 300);
        }
        try {
            //获取当前登录的用户
            String currentUserId = "8982af2d0b664d6a9d62fba790746de2";//商户id
            Merchant merchant = merchantService.findByID(currentUserId);
            if (merchant != null) {
                try {
                    PageData pd = new PageData();

                    pd.put("merchantId", currentUserId);

                    if (taskCode == null) {
                        pd.put("taskCode", "");
                    } else if (!StringUtils.isBlank(taskCode.replaceAll("\"", "").replaceAll("'", ""))) {
                        pd.put("taskCode", taskCode.replaceAll("\"", "").replaceAll("'", ""));
                    } else {
                        pd.put("taskCode", "");
                    }

                    if (taskName == null) {
                        pd.put("taskName", "");
                    } else if (!StringUtils.isBlank(taskName.replaceAll("\"", "").replaceAll("'", ""))) {
                        pd.put("taskName", taskName.replaceAll("\"", "").replaceAll("'", ""));
                    } else {
                        pd.put("taskName", "");
                    }

                    if (cooperateMode != -1) {
                        pd.put("cooperateMode", cooperateMode + "");
                    }

                    if (releaseDate == null) {
                        pd.put("releaseDate", "");
                    } else if (!StringUtils.isBlank(releaseDate.replaceAll("\"", "").replaceAll("'", ""))) {
                        if (Tools.str2Date(releaseDate) == null) {
                            return new ReturnJson("开始日期格式不正确", 300);
                        } else {
                            pd.put("releaseDate", releaseDate);
                        }
                    } else {
                        pd.put("releaseDate", "");
                    }

                    if (deadlineDate == null) {
                        pd.put("deadlineDate", "");
                    } else if (!StringUtils.isBlank(deadlineDate.replaceAll("\"", "").replaceAll("'", ""))) {
                        if (Tools.str2Date(deadlineDate) == null) {
                            return new ReturnJson("开始日期格式不正确", 300);
                        } else {
                            pd.put("deadlineDate", deadlineDate);
                        }
                    } else {
                        pd.put("deadlineDate", "");
                    }

                    pd.put("pageSize", pd.getPageSize());
                    pd.put("start", (pageNo - 1) * pd.getPageSize());

                    int count = taskService.count(pd);
                    List<Task> taskList = taskService.selectList(pd);
                    pd.setItemsCount(count);
                    if (pd.getPageCount() < pageNo) {
                        return new ReturnJson("页码数据越界", 300);
                    }
                    if (pd.getPageCount() == pageNo) {
                        return new ReturnJson("操作成功", taskList, count, 200, true);
                    } else {
                        return new ReturnJson("操作成功", taskList, count, 200);
                    }

                } catch (Exception err) {
                    return new ReturnJson(err.toString(), 300);
                }
            } else {
                return new ReturnJson("请先登录", 401);
            }
        } catch (Exception ex) {
            return new ReturnJson(ex.toString(), 300);
        }
    }


    // 删除任务信息
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "登录令牌", dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "id", value = "订单id", dataType = "int", paramType = "form")})
    @ApiOperation("删除任务信息")
    @RequestMapping(value = "/DeleteTask", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"})
    public ReturnJson DeleteTask(Long id) {
        String currentUserId = "8982af2d0b664d6a9d62fba790746de2";
        if (currentUserId != null) {
            if (!VerificationCheck.isNull(id)) {
                return new ReturnJson("删除数据的ID不能为空", 300);
            }

            PageData pd = new PageData();
            pd.put("TaskGUID", id);
            try {

                taskService.delete(pd);
                return new ReturnJson("操作成功", "操作成功", 200);
            } catch (Exception errs) {
                return new ReturnJson(errs.toString(), 300);
            }

        } else {
            return new ReturnJson("请先登录", 401);
        }
    }


    //添加任务信息
    @ApiOperation("任务新增")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "登录令牌", dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "taskName", value = "任务名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "taskDesc", value = "任务说明文字", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "industryType", value = "行业类型，两个数据拼接以/隔开", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "taskCostMin", value = "最低费用", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "taskCostMax", value = "最高费用", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "taskSkill", value = "创客所需技能", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "releaseDate", value = "发布时间", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "deadlineDate", value = "截至时间", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "upperLimit", value = "任务上限人数", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "cooperateMode", value = "合作类型", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "taskMode", value = "任务模式", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "makerIds", value = "多个创客id拼接，以逗号隔开", dataType = "string", paramType = "form")})
    @RequestMapping(value = "/addTask", method = RequestMethod.POST, produces = {
            "application/json;charset=UTF-8"})
    public ReturnJson addTask(String taskName, String taskDesc, String industryType, String taskCostMin, String taskCostMax, String taskSkill,
                              String releaseDate, String deadlineDate, Integer upperLimit, Integer cooperateMode, Integer taskMode, String makerIds) {
        try {
            if (!VerificationCheck.isNull(taskName)) {
                return new ReturnJson("任务名称不能为空", 300);
            }
            if (!VerificationCheck.isNull(taskDesc)) {
                return new ReturnJson("任务说明文字不能为空", 300);
            }
            if (!VerificationCheck.isNull(industryType)) {
                return new ReturnJson("行业类型不能为空", 300);
            }
            if (!VerificationCheck.isNull(cooperateMode)) {
                return new ReturnJson("合作类型不能为空", 300);
            } else if (cooperateMode != 0 && cooperateMode != 1) {
                return new ReturnJson("合作类型只能为0或1", 300);
            }
            if (!VerificationCheck.isNull(taskMode)) {
                return new ReturnJson("任务模式不能为空", 300);
            } else if (taskMode != 0 && taskMode != 1) {
                return new ReturnJson("任务模式只能为0或1,2", 300);
            }
        } catch (Exception e) {
            return new ReturnJson(e.toString(), 300);
        }
        if(Tools.str2Date(deadlineDate) == null){
            return new ReturnJson("发布时间不能为空", 300);
        }

        return null;
    }
}
