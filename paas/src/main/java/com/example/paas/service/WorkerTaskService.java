package com.example.paas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.WorkerTask;

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

    /**
     * 派单给指定创客
     * @param map
     * @return
     */
    ReturnJson seavWorkerTask(Map map);

    /**
     * 剔除用户,判断任务是否为发布中或已接单
     * @param workerId
     * @return
     */
    ReturnJson eliminateWorker(Integer state,String workerId);

    /**
     * 修改验收金额
     * @param money
     * @return
     */
    ReturnJson updateCheckMoney(Double money,String id);


}
