package com.example.merchant.service;

import com.example.common.contract.exception.DefineException;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.IdCardInfoDto;
import com.example.merchant.dto.WorkerBankDto;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    ReturnJson getIdCardInfo(String filePath);

    ReturnJson saveIdCardinfo(IdCardInfoDto idCardInfoDto);

    ReturnJson saveBankInfo(WorkerBankDto workerBankDto);

    ReturnJson saveWorkerVideo(String workerId, String fileVideoPath);

    ReturnJson senSignAContract(String workerId) throws DefineException;

    ReturnJson callBackSignAContract(HttpServletRequest request) throws Exception;

    ReturnJson findSignAContract(String workerId);
}
