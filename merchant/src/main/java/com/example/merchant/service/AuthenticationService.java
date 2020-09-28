package com.example.merchant.service;

import com.example.common.util.ReturnJson;
import com.example.merchant.dto.IdCardInfoDto;
import com.example.merchant.dto.WorkerBankDto;

public interface AuthenticationService {
    ReturnJson getIdCardInfo(String filePath);

    ReturnJson saveIdCardinfo(IdCardInfoDto idCardInfoDto);

    ReturnJson saveBankInfo(WorkerBankDto workerBankDto);

    ReturnJson saveWorkerVideo(String workerId, String fileVideoPath);
}
