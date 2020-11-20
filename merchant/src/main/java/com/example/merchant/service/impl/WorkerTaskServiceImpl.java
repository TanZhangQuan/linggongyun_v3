package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.util.ReturnJson;
import com.example.mybatis.dto.WorkerTaskDto;
import com.example.mybatis.entity.Worker;
import com.example.mybatis.entity.WorkerTask;
import com.example.mybatis.mapper.WorkerTaskDao;
import com.example.merchant.service.WorkerTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class WorkerTaskServiceImpl extends ServiceImpl<WorkerTaskDao, WorkerTask> implements WorkerTaskService {

    @Resource
    private WorkerTaskDao workerTaskDao;

    /**
     * 派单给指定创客
     *
     * @param map
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson seavWorkerTask(Map map) {
        String wid = map.get("workerId").toString();
        String[] workerIds = wid.split(",");
        for (int i = 0; i < workerIds.length; i++) {
            WorkerTask workerTask = new WorkerTask();
            workerTask.setTaskId(map.get("taskId").toString());
            workerTask.setWorkerId(workerIds[i]);
            workerTask.setGetType(2);
            workerTask.setArrangePerson(map.get("arrangePerson").toString());
            workerTaskDao.addWorkerTask(workerTask);
        }
        return ReturnJson.success("添加成功");
    }

    /**
     * 剔除用户,判断任务是否为发布中或已接单,逻辑删除
     *
     * @param workerId
     * @return
     */
    @Override
    public ReturnJson eliminateWorker(Integer state, String workerId,String taskId) {
        ReturnJson returnJson = new ReturnJson("剔除失败", 300);
        if (state == 0 && state == 1) {
            returnJson = new ReturnJson("必须在发布中或已接单才能剔除", 300);
        } else {
            int num = workerTaskDao.eliminateWorker(workerId,taskId);
            if (num > 0) {
                returnJson = new ReturnJson("剔除成功", 200);
            }
        }
        return returnJson;
    }

    /**
     * 修改验收金额
     *
     * @param money
     * @return
     */
    @Override
    public ReturnJson updateCheckMoney(String taskId, Double money, String id) {
        ReturnJson returnJson = new ReturnJson("修改失败", 300);
        if (money == null) {
            returnJson = new ReturnJson("验收金额不能为空", 300);
        } else {
            int num = workerTaskDao.updateCheckMoney(money,id,taskId);
            if (num > 0) {
                returnJson = new ReturnJson("修改成功", 200);
            }
        }
        return returnJson;
    }

    @Override
    public ReturnJson updateCheck(String taskId, String id) {
        ReturnJson returnJson = new ReturnJson("修改失败", 300);
        WorkerTask workerTask = new WorkerTask();
        workerTask.setWorkerId(id);
        workerTask.setTaskId(taskId);
        int num = workerTaskDao.updateCheckMoney(null,id,taskId);
        if (num > 0) {
            returnJson = new ReturnJson("修改成功", 200);
        }
        return returnJson;
    }

    /**
     * 提交工作成成果
     *
     * @param workerTaskId
     * @param achievementDesc
     * @param achievementFiles
     * @return
     */
    @Override
    public ReturnJson acceptanceResults(String workerTaskId, String achievementDesc, String achievementFiles) {
        WorkerTask workerTask = new WorkerTask();
        workerTask.setId(workerTaskId);
        workerTask.setAchievementDesc(achievementDesc);
        workerTask.setAchievementFiles(achievementFiles);
        //获取系统当前时间
        workerTask.setAchievementDate(LocalDateTime.now());
        workerTask.setUpdateDate(LocalDateTime.now());
        workerTask.setStatus(3);
        int num = workerTaskDao.updateById(workerTask);
        if (num > 0) {
            return ReturnJson.success("提交成功");
        }
        return ReturnJson.error("提交失败");
    }

    /**
     * 查询任务完成成果
     *
     * @param workerTaskId
     * @return
     */
    @Override
    public ReturnJson getWorkerTask(String workerTaskId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        WorkerTask workerTask = workerTaskDao.selectOne(new QueryWrapper<WorkerTask>().eq("id", workerTaskId));
        if (workerTask != null) {
            returnJson = new ReturnJson("操作成果", workerTask, 200);
        }
        return returnJson;
    }
}
