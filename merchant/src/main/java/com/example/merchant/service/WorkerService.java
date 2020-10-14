package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.WorkerDto;
import com.example.merchant.exception.CommonException;
import com.example.mybatis.entity.Worker;

import javax.servlet.http.HttpServletResponse;
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

    ReturnJson getByIdAndAccountNameAndMobile(WorkerDto workerDto);

    ReturnJson getWorkerInfo(String id);

    ReturnJson saveWorker(List<Worker> workers, String merchantId);

    ReturnJson getWorkerByTaskId(String taskId, Integer offset);

    ReturnJson getCheckByTaskId(String taskId, Integer offset);

    ReturnJson getWorkerAllPaas(String managersId, Integer page, Integer pageSize) throws CommonException;

    ReturnJson getWorkerAllNotPaas(String managersId, Integer page, Integer pageSize) throws CommonException;

    ReturnJson getByIdAndAccountNameAndMobilePaas(String managersId, String id, String accountName, String mobileCode) throws CommonException;

    ReturnJson getByIdAndAccountNameAndMobileNotPaas(String managersId, String id, String accountName, String mobileCode) throws CommonException;

    ReturnJson getWorkerInfoPaas(String id);

    ReturnJson getWorkerPaymentListPaas(String id, Integer page, Integer pageSize);

    ReturnJson updateWorkerPaas(Worker worker);

    ReturnJson loginWorker(String username, String password, HttpServletResponse response);

    ReturnJson senSMS(String mobileCode);

    ReturnJson loginMobile(String loginMobile, String checkCode, HttpServletResponse resource);

    ReturnJson updataPassWord(String loginMobile, String checkCode, String newPassWord);

    ReturnJson wxLogin(String code, String iv, String encryptedData);

    ReturnJson setWorkerMakeMoney();

    ReturnJson logout(String workerId);
}
