package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.mybatis.dto.WorkerTaskDto;
import com.example.mybatis.entity.WorkerTask;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface WorkerTaskService extends IService<WorkerTask> {

    /**
     * 派单给指定创客
     *
     * @param map
     * @return
     */
    ReturnJson seavWorkerTask(Map map);

    /**
     * 剔除用户,判断任务是否为发布中或已接单,逻辑删除
     *
     * @param state
     * @param workerId
     * @param taskId
     * @return
     */
    ReturnJson eliminateWorker(Integer state, String workerId, String taskId);

    /**
     * 修改验收金额
     *
     * @param taskId
     * @param money
     * @param id
     * @return
     */
    ReturnJson updateCheckMoney(String taskId, Double money, String id);

    /**
     * 修改验收
     *
     * @param taskId
     * @param id
     * @return
     */
    ReturnJson updateCheck(String taskId, String id);

    /**
     * 提交工作成成果
     *
     * @param workerTaskId
     * @param achievementDesc
     * @param achievementFiles
     * @return
     */
    ReturnJson acceptanceResults(String workerTaskId, String achievementDesc, String achievementFiles);

    /**
     * 查询任务完成成果
     *
     * @param workerTaskId
     * @return
     */
    ReturnJson getWorkerTask(String workerTaskId);

}
