package com.example.merchant.service.impl;

import com.example.common.util.IdCardUtils;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.IdCardInfoDto;
import com.example.merchant.dto.WorkerBankDto;
import com.example.merchant.service.AuthenticationService;
import com.example.mybatis.entity.Worker;
import com.example.mybatis.entity.WorkerBank;
import com.example.mybatis.mapper.WorkerBankDao;
import com.example.mybatis.mapper.WorkerDao;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private WorkerDao workerDao;

    @Autowired
    private WorkerBankDao workerBankDao;

    @Value("${PathImage_KEY}")
    private String PathImage_KEY;


    /**
     * 识别身份证获取信息
     *
     * @param filePath
     * @return
     */
    @Override
    public ReturnJson getIdCardInfo(String filePath) {
        String realFilePath = PathImage_KEY + filePath.substring(filePath.lastIndexOf("/") + 1);
        log.info(realFilePath);
        Map<String, String> idCardInfo = IdCardUtils.getIdCardInfo(realFilePath);
        return ReturnJson.success(idCardInfo);
    }

    /**
     * 保存创客的身份证信息
     *
     * @param idCardInfoDto
     * @return
     */
    @Override
    public ReturnJson saveIdCardinfo(IdCardInfoDto idCardInfoDto) {
        Worker worker = new Worker();
        worker.setId(idCardInfoDto.getWokerId());
        worker.setAccountName(idCardInfoDto.getRealName());
        worker.setIdcardCode(idCardInfoDto.getIdCard());
        worker.setIdcardFront(idCardInfoDto.getIdCardFront());
        worker.setIdcardBack(idCardInfoDto.getIdCardBack());
        int i = workerDao.updateById(worker);
        if (i == 1) {
            return ReturnJson.success("身份证上传成功！");
        }
        return ReturnJson.error("身份证上传失败！");
    }

    /**
     * 绑定银行卡号
     * @param workerBankDto
     * @return
     */
    @Override
    public ReturnJson saveBankInfo(WorkerBankDto workerBankDto) {
        WorkerBank workerBank = new WorkerBank();
        BeanUtils.copyProperties(workerBankDto,workerBank);
        int insert = workerBankDao.insert(workerBank);
        if (insert == 1) {
            return ReturnJson.success("银行卡绑定成功！");
        }
        return ReturnJson.success("银行卡绑定失败！");
    }

    /**
     * 保存创客的活体视频信息
     * @param workerId
     * @param fileVideoPath
     * @return
     */
    @Override
    public ReturnJson saveWorkerVideo(String workerId, String fileVideoPath) {
        Worker worker = new Worker();
        worker.setId(workerId);
        worker.setAttestationVideo(fileVideoPath);
        int i = workerDao.updateById(worker);
        if (i == 1) {
            return ReturnJson.success("活体视频上传成功！");
        }
        return ReturnJson.error("活体视频上传失败！");
    }
}
