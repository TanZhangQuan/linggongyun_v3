package com.example.merchant.service;

import com.example.common.contract.exception.DefineException;
import com.example.common.enums.IdCardSide;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.makerend.IdCardInfoDTO;
import com.example.merchant.dto.makerend.WorkerBankDTO;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    /**
     * 识别身份证获取信息
     *
     * @param filePath
     * @param idCardSide
     * @return
     * @throws Exception
     */
    ReturnJson getIdCardInfo(String filePath, IdCardSide idCardSide) throws Exception;

    /**
     * 保存创客的身份证信息
     *
     * @param idCardInfoDto
     * @param workerId
     * @return
     * @throws Exception
     */
    ReturnJson saveIdCardinfo(IdCardInfoDTO idCardInfoDto, String workerId) throws Exception;

    /**
     * 绑定银行卡号
     *
     * @param workerBankDto
     * @param workerId
     * @return
     * @throws Exception
     */
    ReturnJson saveBankInfo(WorkerBankDTO workerBankDto, String workerId) throws Exception;

    /**
     * 保存创客的活体视频信息
     *
     * @param workerId
     * @param fileVideoPath
     * @return
     */
    ReturnJson saveWorkerVideo(String workerId, String fileVideoPath);

    /**
     * 发送签署加盟合同
     *
     * @param workerId
     * @return
     */
    ReturnJson senSignAContract(String workerId,HttpServletRequest request) throws DefineException;

    /**
     * 合同签署成功后的回调
     *
     * @param request
     * @return
     */
    ReturnJson callBackSignAContract(HttpServletRequest request);

    /**
     * 查看创客是否签署了加盟合同
     *
     * @param workerId
     * @return
     */
    ReturnJson findSignAContract(String workerId);
}
