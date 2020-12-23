package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.makerend.AddWorkerDTO;
import com.example.merchant.dto.merchant.WorkerDTO;
import com.example.merchant.dto.platform.WorkerQueryDTO;
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
    /**
     * 分页查询商户下的所以创客
     *
     * @param merchantId
     * @param page
     * @param pageSize
     * @return
     */
    ReturnJson getWorkerAll(String merchantId, Integer page, Integer pageSize);

    /**
     * 按编号、姓名、手机号，查询该商户下的创客
     *
     * @param workerDto
     * @return
     */
    ReturnJson getByIdAndAccountNameAndMobile(String merchantId, WorkerDTO workerDto);

    /**
     * 查询创客的基本信息
     *
     * @param id
     * @return
     */
    ReturnJson getWorkerInfo(String id);

    /**
     * 导入创客，当创客没注册时先注册创客（创客登录账号为手机号码，密码为身份证后6为数）
     *
     * @param workers
     * @param merchantId
     * @return
     */
    ReturnJson saveWorker(List<Worker> workers, String merchantId) throws Exception;

    /**
     * 查询任务对应创客
     *
     * @param taskId   任务id
     * @param pageNo   第几页
     * @param pageSize 页大小
     * @return
     */
    ReturnJson getWorkerByTaskId(String taskId, Integer pageNo, Integer pageSize);

    /**
     * 验收创客是否完成
     *
     * @param taskId
     * @param pageNo
     * @param pageSize
     * @return
     */
    ReturnJson getCheckByTaskId(String taskId, Integer pageNo, Integer pageSize);

    /**
     * 查询创客的收入列表
     *
     * @param id
     * @param page
     * @param pageSize
     * @return
     */
    ReturnJson getWorkerPaymentListPaas(String id, Integer page, Integer pageSize);

    /**
     * 编辑创客
     *
     * @param worker
     * @return
     */
    ReturnJson updateWorkerPaas(Worker worker);

    /**
     * 创客登录
     *
     * @param username
     * @param password
     * @param response
     * @return
     */
    ReturnJson loginWorker(String username, String password, HttpServletResponse response);

    /**
     * 发送验证码
     *
     * @param mobileCode
     * @return
     */
    ReturnJson senSMS(String mobileCode, String isNot);

    /**
     * 手机号登录
     *
     * @param loginMobile
     * @param checkCode
     * @param resource
     * @return
     */
    ReturnJson loginMobile(String loginMobile, String checkCode, HttpServletResponse resource);

    /**
     * 修改密码忘记密码
     *
     * @param loginMobile
     * @param checkCode
     * @param newPassWord
     * @return
     */
    ReturnJson updataPassWord(String loginMobile, String checkCode, String newPassWord);

    /**
     * 微信登录
     *
     * @param code
     * @return
     */
    ReturnJson wxLogin(String code, String iv, String encryptedData);

    /**
     * 首页创客赚钱信息
     *
     * @return
     */
    ReturnJson setWorkerMakeMoney();

    /**
     * 退出登录
     *
     * @param workerId
     * @return
     */
    ReturnJson logout(String workerId);

    /**
     * 根据token获取用户信息
     *
     * @param userId
     * @return
     */
    ReturnJson getWorkerInfoBytoken(String userId);

    /**
     * 平台端查询创客任务
     *
     * @param taskId
     * @param pageNo
     * @param pageSize
     * @return
     */
    ReturnJson getPaasWorkerByTaskId(String taskId, Integer pageNo, Integer pageSize);

    /**
     * 注册创客
     *
     * @param addWorkerDto
     * @return
     */
    ReturnJson registerWorker(AddWorkerDTO addWorkerDto);

    /**
     * 功能描述: 按添加查询已认证的创客
     *
     * @param managersId
     * @param workerQueryDto
     * @Return com.example.common.util.ReturnJson
     * @Author 忆惜
     * @Date 2020/11/10 10:34
     */
    ReturnJson getWorkerQuery(String managersId, WorkerQueryDTO workerQueryDto) throws CommonException;

    /**
     * 功能描述: 按添加查询未认证的创客
     *
     * @param managersId
     * @param workerQueryDto
     * @Return com.example.common.util.ReturnJson
     * @Author 忆惜
     * @Date 2020/11/10 10:56
     */
    ReturnJson getWorkerQueryNot(String managersId, WorkerQueryDTO workerQueryDto) throws CommonException;

    /**
     * 查询创客统计信息
     *
     * @param workerId
     * @return
     */
    ReturnJson queryWorkerInfo(String workerId);

    /**
     * 查询商户可用的创客
     *
     * @param merchantId
     * @param pageNo
     * @param pageSize
     * @return
     */
    ReturnJson queryWorkerCompanyByID(String merchantId, Integer pageNo, Integer pageSize);
}
