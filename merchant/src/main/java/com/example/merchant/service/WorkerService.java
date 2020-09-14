package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.Worker;

import java.util.List;

/**
 * <p>
 * 创客表 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface WorkerService extends IService<Worker> {
    ReturnJson getWorkerAll(String merchantId, Integer page, Integer pageSize);
    ReturnJson getByIdAndAccountNameAndMobile(String merchantId, String id, String accountName, String mobileCode);
    ReturnJson getWorkerInfo(String id);
    ReturnJson saveWorker(List<Worker> workers,String merchantId);
    ReturnJson getWorkerByTaskId(String taskId, Integer offset);
    ReturnJson getCheckByTaskId(String taskId, Integer offset);
}
