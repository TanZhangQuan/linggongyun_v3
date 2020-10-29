package com.example.merchant.service;

import com.example.common.contract.exception.DefineException;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.makerend.IdCardInfoDto;
import com.example.merchant.dto.makerend.WorkerBankDto;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    ReturnJson getIdCardInfo(String filePath);

    ReturnJson saveIdCardinfo(IdCardInfoDto idCardInfoDto, String workerId) throws Exception;

    ReturnJson saveBankInfo(WorkerBankDto workerBankDto, String workerId) throws Exception;

    ReturnJson saveWorkerVideo(String workerId, String fileVideoPath);

    ReturnJson senSignAContract(String workerId) throws DefineException;

    ReturnJson callBackSignAContract(HttpServletRequest request);

    ReturnJson findSignAContract(String workerId);
}
