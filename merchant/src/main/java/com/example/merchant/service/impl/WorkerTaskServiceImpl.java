package com.example.merchant.service.impl;

import com.example.common.util.ReturnJson;
import com.example.mybatis.dto.WorkerTaskDto;
import com.example.mybatis.entity.WorkerTask;
import com.example.mybatis.mapper.WorkerTaskDao;
import com.example.merchant.service.WorkerTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    @Override
    @Transactional
    public ReturnJson seavWorkerTask(Map map) {
        ReturnJson returnJson = new ReturnJson("添加失败", 300);
        String wid = map.get("workerId").toString();
        String[] workerIds = wid.split(",");
        for (int i = 0; i < workerIds.length; i++) {
            WorkerTask workerTask = new WorkerTask();
            workerTask.setTaskId(map.get("taskId").toString());
            workerTask.setWorkerId(workerIds[i]);
            workerTask.setGetType(2);
            workerTask.setArrangePerson(map.get("arrangePerson").toString());
            int j = workerTaskDao.addWorkerTask(workerTask);
            if (j > 0) {
                returnJson = new ReturnJson("添加成功", 200);
            } else {
                returnJson = new ReturnJson("添加失败", 300);
            }
        }
        return returnJson;
    }

    @Override
    public ReturnJson eliminateWorker(Integer state, String workerId) {
        ReturnJson returnJson = new ReturnJson("剔除失败", 300);
        if (state == 0 && state == 1) {
            returnJson = new ReturnJson("必须在发布中或已接单才能剔除", 300);
        } else {
            int num = workerTaskDao.eliminateWorker(workerId);
            if (num > 0) {
                returnJson = new ReturnJson("剔除成功", 200);
            }
        }
        return returnJson;
    }

    @Override
    public ReturnJson updateCheckMoney(Double money,String id) {
        ReturnJson returnJson = new ReturnJson("修改失败", 300);
        if (money != null){
            returnJson = new ReturnJson("验收金额不能为空", 300);
        }else {
            int num=workerTaskDao.updateCheckMoney(money,id);
            if (num > 0) {
                returnJson = new ReturnJson("修改成功", 200);
            }
        }
            return returnJson;
    }
}
