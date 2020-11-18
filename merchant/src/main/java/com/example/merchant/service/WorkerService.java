package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.makerend.AddWorkerDto;
import com.example.merchant.dto.merchant.WorkerDto;
import com.example.merchant.dto.platform.WorkerQueryDto;
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

    ReturnJson getByIdAndAccountNameAndMobile(String merchantId, WorkerDto workerDto);

    ReturnJson getWorkerInfo(String id);

    ReturnJson saveWorker(List<Worker> workers, String merchantId) throws Exception;

    ReturnJson getWorkerByTaskId(String taskId, Integer pageNo, Integer pageSize);

    ReturnJson getCheckByTaskId(String taskId, Integer pageNo, Integer pageSize);

    ReturnJson getWorkerPaymentListPaas(String id, Integer page, Integer pageSize);

    ReturnJson updateWorkerPaas(Worker worker);

    ReturnJson loginWorker(String username, String password, HttpServletResponse response);

    ReturnJson senSMS(String mobileCode,String isNot);

    ReturnJson loginMobile(String loginMobile, String checkCode, HttpServletResponse resource);

    ReturnJson updataPassWord(String loginMobile, String checkCode, String newPassWord);

    ReturnJson wxLogin(String code, String iv, String encryptedData);

    ReturnJson setWorkerMakeMoney();

    ReturnJson logout(String workerId);

    ReturnJson getWorkerInfoBytoken(String userId);

    ReturnJson getPaasWorkerByTaskId(String taskId, Integer pageNo, Integer pageSize);

    ReturnJson registerWorker(AddWorkerDto addWorkerDto);

    /**
     * 功能描述: 按添加查询已认证的创客
     *
     * @param managersId
	 * @param workerQueryDto
     * @Return com.example.common.util.ReturnJson
     * @Author 忆惜
     * @Date 2020/11/10 10:34
     */
    ReturnJson getWorkerQuery(String managersId, WorkerQueryDto workerQueryDto) throws CommonException;

    /**
     * 功能描述: 按添加查询未认证的创客
     *
     * @param managersId
	 * @param workerQueryDto
     * @Return com.example.common.util.ReturnJson
     * @Author 忆惜
     * @Date 2020/11/10 10:56
     */
    ReturnJson getWorkerQueryNot(String managersId, WorkerQueryDto workerQueryDto) throws CommonException;
}
