package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.mybatis.dto.WorkerTaskDto;
import com.example.mybatis.entity.WorkerTask;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface WorkerTaskService extends IService<WorkerTask> {

    ReturnJson seavWorkerTask(Map map);

    ReturnJson eliminateWorker(Integer state,String workerId);

    ReturnJson updateCheckMoney(String taskId,Double money,String id);

    ReturnJson acceptanceResults(String workerTaskId,String achievementDesc,String achievementFiles);

    ReturnJson getWorkerTask(String workerTaskId);

}
