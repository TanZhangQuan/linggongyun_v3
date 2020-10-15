package com.example.merchant.service.impl;

import com.example.common.contract.SignAContractUtils;
import com.example.common.contract.exception.DefineException;
import com.example.common.contract.helper.SignHelper;
import com.example.common.util.HttpClientUtils;
import com.example.common.util.IdCardUtils;
import com.example.common.util.JsonUtils;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.makerend.IdCardInfoDto;
import com.example.merchant.dto.makerend.WorkerBankDto;
import com.example.merchant.service.AuthenticationService;
import com.example.merchant.service.FileOperationService;
import com.example.merchant.util.RealnameVerifyUtil;
import com.example.mybatis.entity.Worker;
import com.example.mybatis.entity.WorkerBank;
import com.example.mybatis.mapper.WorkerBankDao;
import com.example.mybatis.mapper.WorkerDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Resource
    private WorkerDao workerDao;

    @Resource
    private WorkerBankDao workerBankDao;

    @Value("${PathImage_KEY}")
    private String PathImage_KEY;

    @Value("${PathContractFile_KEY}")
    private String PathContractFile_KEY;

    @Value("${appSecret}")
    private String appSecret;

    @Resource
    private FileOperationService fileOperationService;

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
        Worker worker = workerDao.selectById(idCardInfoDto.getWokerId());
        if (worker == null) {
            return ReturnJson.error("该创客不存在！");
        }
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
     *
     * @param workerBankDto
     * @return
     */
    @Override
    public ReturnJson saveBankInfo(WorkerBankDto workerBankDto) {
        WorkerBank workerBank = new WorkerBank();
        BeanUtils.copyProperties(workerBankDto, workerBank);
        int insert = workerBankDao.insert(workerBank);
        if (insert == 1) {
            return ReturnJson.success("银行卡绑定成功！");
        }
        return ReturnJson.success("银行卡绑定失败！");
    }

    /**
     * 保存创客的活体视频信息
     *
     * @param workerId
     * @param fileVideoPath
     * @return
     */
    @Override
    public ReturnJson saveWorkerVideo(String workerId, String fileVideoPath) {
        Worker worker = workerDao.selectById(workerId);
        if (worker == null) {
            return ReturnJson.error("该创客不存在！");
        }
        worker.setId(workerId);
        worker.setAttestationVideo(fileVideoPath);
        int i = workerDao.updateById(worker);
        if (i == 1) {
            return ReturnJson.success("活体视频上传成功！");
        }
        return ReturnJson.error("活体视频上传失败！");
    }

    /**
     * 发送签署加盟合同
     *
     * @param workerId
     * @return
     */
    @Override
    public ReturnJson senSignAContract(String workerId) throws DefineException {
        Worker worker = workerDao.selectById(workerId);
        if (worker == null) {
            return ReturnJson.error("该用户不存在！");
        }
        if (worker.getAgreementSign() == 0 || worker.getAgreementSign() == 3) {
            ReturnJson returnJson = SignAContractUtils.signAContract(PathContractFile_KEY, worker.getId(), worker.getAccountName(), worker.getIdcardCode(),
                    worker.getMobileCode());
            worker.setAgreementSign(1);
            workerDao.updateById(worker);
            return returnJson;
        } else if (worker.getAgreementSign() == 1) {
            return ReturnJson.error("加盟合同正在签署中，请查看手机短信并通过链接进行网签《加盟合同》！");
        }
        return ReturnJson.error("您已经签署了加盟合同，请勿重复签署！");
    }

    /**
     * 合同签署成功后的回调
     *
     * @param request
     * @return
     */
    @Override
    public synchronized ReturnJson callBackSignAContract(HttpServletRequest request){
        //查询body的数据进行验签
        Map map = null;
        boolean res = false;
        try {
            String rbody = RealnameVerifyUtil.getRequestBody(request, "UTF-8");
            map = JsonUtils.jsonToPojo(rbody, Map.class);
            res = RealnameVerifyUtil.checkPass(request, rbody, appSecret);
        } catch (Exception e) {
            log.error(e.toString()+":"+e.getMessage());
            return null;
        }
        if (!res) {
            return ReturnJson.error("签约失败！");
        }
        String flowId = map.get("flowId") == null ? "" : String.valueOf(map.get("flowId"));
        Integer signResult = map.get("signResult") == null ? null : (Integer) map.get("signResult");
        String thirdPartyUserId = map.get("thirdPartyUserId") == null ? "" : String.valueOf(map.get("thirdPartyUserId"));
        if (StringUtils.isBlank(thirdPartyUserId) || StringUtils.isBlank(flowId)) {
            return null;
        }
        if (!StringUtils.isBlank(thirdPartyUserId)) {
            Worker worker = workerDao.selectById(thirdPartyUserId);
            if (worker != null) {
                if (worker.getAgreementSign() == 2) {
                    log.info("该创客加盟合同已签署完成！");
                    return ReturnJson.error("该创客加盟合同已签署完成!");
                }
            }
        }
        if (signResult != null && signResult == 2 && !StringUtils.isBlank(flowId)) {
            log.info("---------------------签署完成后，通知回调，平台方进行签署流程归档 start-----------------------------");
            try {
                SignHelper.archiveSignFlow(flowId);
            } catch (DefineException e) {
                log.error(e.toString()+":"+e.getMessage());
                Worker worker = workerDao.selectById(thirdPartyUserId);
                worker.setAgreementSign(3);
                workerDao.updateById(worker);
                return null;
            }
            log.info("---------------------归档后，获取文件下载地址 start-----------------------------");
            Map<String, List<Map<String, Object>>> jsonHelper = null;
            try {
                jsonHelper = (Map<String, List<Map<String, Object>>>) SignHelper.downloadFlowDoc(flowId);
            } catch (DefineException e) {
                log.error(e.toString()+":"+e.getMessage());
                Worker worker = workerDao.selectById(thirdPartyUserId);
                worker.setAgreementSign(3);
                workerDao.updateById(worker);
                return null;
            }
            List<Map<String, Object>> list = jsonHelper.get("docs");
            Map<String, Object> flowInfo = list.get(0);
            Worker worker = workerDao.selectById(thirdPartyUserId);
            if (worker != null) {
                String fileUrl = String.valueOf(flowInfo.get("fileUrl"));
                CloseableHttpResponse closeableHttpResponse = HttpClientUtils.urlGet(fileUrl);
                String url = fileOperationService.uploadJpgOrPdf(closeableHttpResponse, request);
                if (StringUtils.isBlank(url)) {
                    worker.setAgreementSign(3);
                    workerDao.updateById(worker);
                    return ReturnJson.success("签署加盟合同失败！");
                }
                worker.setAgreementSign(2);
                worker.setAgreementUrl(url);
                workerDao.updateById(worker);
                return ReturnJson.success("签署加盟合同成功！");
            }
        }

        if (signResult != null && signResult == 3 && !StringUtils.isBlank(thirdPartyUserId)) {
            Worker worker = workerDao.selectById(thirdPartyUserId);
            if (worker != null) {
                worker.setAgreementSign(3);
                workerDao.updateById(worker);
                String resultDescription = (String) map.get("resultDescription");
                return ReturnJson.success(resultDescription);
            }
        }
        return ReturnJson.success("签署流程开启！");
    }

    /**
     * 查看创客是否签署了加盟合同
     *
     * @param workerId
     * @return
     */
    @Override
    public ReturnJson findSignAContract(String workerId) {
        Worker worker = workerDao.selectById(workerId);
        if (worker == null) {
            return ReturnJson.error("该用户不存在！");
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("status", worker.getAgreementSign());
        return ReturnJson.success(map);
    }
}
