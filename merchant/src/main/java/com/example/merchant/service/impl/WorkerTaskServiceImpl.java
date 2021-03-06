package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.WorkerTaskService;
import com.example.mybatis.entity.Merchant;
import com.example.mybatis.entity.Task;
import com.example.mybatis.entity.WorkerTask;
import com.example.mybatis.mapper.MerchantDao;
import com.example.mybatis.mapper.TaskDao;
import com.example.mybatis.mapper.WorkerDao;
import com.example.mybatis.mapper.WorkerTaskDao;
import com.example.mybatis.vo.WorkerPayInfoVO;
import com.example.mybatis.vo.WorkerTaskInfoVO;
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
    @Resource
    private TaskDao taskDao;
    @Resource
    private WorkerDao workerDao;

    @Resource
    private MerchantDao merchantDao;

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
            workerTask.setGetType(1);
            workerTask.setArrangePerson(map.get("arrangePerson").toString());
            workerTaskDao.addWorkerTask(workerTask);
        }
        if (task.getTaskMode() == 2) {
            int count = workerTaskDao.selectCount(new QueryWrapper<WorkerTask>().lambda()
                    .eq(WorkerTask::getTaskId, map.get("taskId").toString())
                    .eq(WorkerTask::getTaskStatus, 0));
            if (count == task.getUpperLimit()) {
                task.setState(1);
                taskDao.updateById(task);
            }
        } else {
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
        WorkerTask workerTask = workerTaskDao.selectOne(new QueryWrapper<WorkerTask>().lambda()
                .eq(WorkerTask::getWorkerId, workerId)
                .eq(WorkerTask::getTaskId, taskId));
        if (workerTask.getStatus() != 0) {
            return ReturnJson.error("该创客已经完成了该任务不能进行剔除！");
        }
        if (workerTask.getGetType() == 1) {
            return ReturnJson.success("此创客为派单获取此任务不能剔除");
        }
        if (task.getState() == 0 && task.getState() == 1) {
            return ReturnJson.error("必须在发布中或已接单才能剔除", 300);
        } else {
            workerTaskDao.eliminateWorker(workerId, taskId);
            return ReturnJson.success("剔除成功");
        }
    }

    @Override
    public ReturnJson updateCheckMoney(String taskId, Double money, String id, String userId) {
        ReturnJson returnJson;
        if (money == null) {
            returnJson = new ReturnJson("验收金额不能为空", 300);
        } else {
            WorkerTask workerTask = workerTaskDao.selectOne(new QueryWrapper<WorkerTask>().lambda()
                    .eq(WorkerTask::getWorkerId, id).eq(WorkerTask::getTaskId, taskId));
            workerTask.setCheckMoney(money);
            workerTask.setStatus(4);
            workerTask.setCheckDate(LocalDateTime.now());
            workerTask.setCheckPerson(userId);
            workerTaskDao.updateById(workerTask);
            List<WorkerTask> list = workerTaskDao.selectList(new QueryWrapper<WorkerTask>().lambda()
                    .eq(WorkerTask::getTaskId, taskId)
                    .eq(WorkerTask::getTaskStatus, 0));
            int j = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getStatus() == 4) {
                    j++;
                }
            }
            if (j == list.size()) {
                Task task = taskDao.selectById(taskId);
                task.setState(3);
                taskDao.updateById(task);
                for (WorkerTask workerTask1 : list) {
                    workerTask1.setStatus(1);
                    workerTaskDao.updateById(workerTask1);
                }
            }
            returnJson = new ReturnJson("修改成功", 200);
        }

        return returnJson;
    }

    @Override
    public ReturnJson updateCheck(String taskId, String id) {
        workerTaskDao.updateCheckMoney(null, id, taskId);
        List<WorkerTask> list = workerTaskDao.selectList(new QueryWrapper<WorkerTask>().lambda()
                .eq(WorkerTask::getTaskId, taskId)
                .eq(WorkerTask::getTaskStatus, 0));
        int j = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getStatus() == 4) {
                j++;
            }
        }
        if (j == list.size()) {
            Task task = taskDao.selectById(taskId);
            task.setState(3);
            taskDao.updateById(task);
            for (WorkerTask workerTask : list) {
                workerTask.setStatus(1);
                workerTaskDao.updateById(workerTask);
            }
        }
        return ReturnJson.success("修改成功");
    }

    @Override
    public ReturnJson acceptanceResults(String workerTaskId, String achievementDesc, String achievementFiles) {
        WorkerTask workerTask = workerTaskDao.selectById(workerTaskId);
        Task task = taskDao.selectById(workerTask.getTaskId());
        if (task.getState() != 1) {
            return ReturnJson.error("任务还在开启状态不能提交工作成果！");
        }
        workerTask.setAchievementDesc(achievementDesc);
        workerTask.setAchievementFiles(achievementFiles);
        //获取系统当前时间
        workerTask.setAchievementDate(LocalDateTime.now());
        workerTask.setUpdateDate(LocalDateTime.now());
        workerTask.setStatus(3);
        workerTaskDao.updateById(workerTask);
        if (task.getState() != 2) {
            task.setState(2);
            taskDao.updateById(task);
        }
        return ReturnJson.success("提交成功");
    }

    @Override
    public ReturnJson getWorkerTask(String workerTaskId) {
        ReturnJson returnJson = new ReturnJson("操作失败", 300);
        WorkerTask workerTask = workerTaskDao.selectOne(new QueryWrapper<WorkerTask>().lambda()
                .eq(WorkerTask::getId, workerTaskId));
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
        IPage<WorkerPayInfoVO> iPage = workerDao.queryWorkerPayInfo(page, workerId, null);
        return ReturnJson.success(iPage);
    }

    @Override
    public ReturnJson queryMerchantWorkerPayInfo(String workerId, String merchantId, Integer pageNo, Integer pageSize) {
        Merchant merchant = merchantDao.selectById(merchantId);
        Page page = new Page(pageNo, pageSize);
        IPage<WorkerPayInfoVO> iPage = workerDao.queryWorkerPayInfo(page, workerId, merchant.getCompanyId());
        return ReturnJson.success(iPage);
    }

}
