package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.Task;
import com.example.mybatis.entity.WorkerTask;
import com.example.mybatis.mapper.TaskDao;
import com.example.mybatis.mapper.WorkerDao;
import com.example.mybatis.mapper.WorkerTaskDao;
import com.example.merchant.service.WorkerTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatis.vo.WorkerPayInfoVO;
import com.example.mybatis.vo.WorkerTaskInfoVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
    @Resource
    private TaskDao taskDao;
    @Resource
    private WorkerDao workerDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson seavWorkerTask(Map map) {
        Task task = taskDao.selectById(map.get("taskId").toString());
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
        int count = workerTaskDao.selectCount(new QueryWrapper<WorkerTask>().eq("task_id", map.get("taskId").toString()).eq("task_status", 0));
        if (count == task.getUpperLimit()) {
            task.setState(1);
            taskDao.updateById(task);
        }
        return ReturnJson.success("添加成功");
    }

    @Override
    public ReturnJson eliminateWorker(String workerId, String taskId) {
        Task task = taskDao.setTaskById(taskId);
        if (task == null) {
            return ReturnJson.error("不存在此任务信息！");
        }
        WorkerTask workerTask = workerTaskDao.selectOne(new QueryWrapper<WorkerTask>()
                .eq("worker_id", workerId).eq("task_id", taskId));
        if (workerTask.getStatus() != 0) {
            return ReturnJson.error("该创客已经完成了该任务不能进行剔除！");
        }
        if (task.getState() == 0 && task.getState() == 1) {
            return ReturnJson.error("必须在发布中或已接单才能剔除", 300);
        } else {
            workerTaskDao.eliminateWorker(workerId, taskId);
            return ReturnJson.success("剔除成功");
        }
    }

    @Override
    public ReturnJson updateCheckMoney(String taskId, Double money, String id) {
        ReturnJson returnJson = new ReturnJson("修改失败", 300);
        if (money == null) {
            returnJson = new ReturnJson("验收金额不能为空", 300);
        } else {
            int num = workerTaskDao.updateCheckMoney(money, id, taskId);
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
        int num = workerTaskDao.updateCheckMoney(null, id, taskId);
        if (num > 0) {
            returnJson = new ReturnJson("修改成功", 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson acceptanceResults(String workerTaskId, String achievementDesc, String achievementFiles) {
        WorkerTask workerTask = workerTaskDao.selectById(workerTaskId);
        workerTask.setAchievementDesc(achievementDesc);
        workerTask.setAchievementFiles(achievementFiles);
        //获取系统当前时间
        workerTask.setAchievementDate(LocalDateTime.now());
        workerTask.setUpdateDate(LocalDateTime.now());
        workerTask.setStatus(3);
        workerTaskDao.updateById(workerTask);
        Task task = taskDao.selectById(workerTask.getTaskId());
        if (task.getState() != 1) {
            return ReturnJson.error("任务还在开启状态不能提交工作成果！");
        }
        if (task.getState() != 2) {
            task.setState(2);
            taskDao.updateById(task);
        }
        return ReturnJson.success("提交成功");
    }

    @Override
    public ReturnJson getWorkerTask(String workerTaskId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        WorkerTask workerTask = workerTaskDao.selectOne(new QueryWrapper<WorkerTask>().eq("id", workerTaskId));
        if (workerTask != null) {
            returnJson = new ReturnJson("操作成果", workerTask, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson queryWorkerTaskInfo(String workerId, Integer pageNo, Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        IPage<WorkerTaskInfoVO> iPage = workerTaskDao.queryWorkerTaskInfo(page, workerId);
        return ReturnJson.success(iPage);
    }

    @Override
    public ReturnJson queryWorkerPayInfo(String workerId, Integer pageNo, Integer pageSize) {
        Page page = new Page(pageNo, pageSize);
        IPage<WorkerPayInfoVO> iPage = workerDao.queryWorkerPayInfo(page, workerId);
        return ReturnJson.success(iPage);
    }

}
