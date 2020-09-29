package com.example.merchant.service.impl;

import com.example.common.contract.SignAContractUtils;
import com.example.common.contract.exception.DefineException;
import com.example.common.contract.helper.SignHelper;
import com.example.common.util.IdCardUtils;
import com.example.common.util.JsonUtils;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.IdCardInfoDto;
import com.example.merchant.dto.WorkerBankDto;
import com.example.merchant.service.AuthenticationService;
import com.example.merchant.util.RealnameVerifyUtil;
import com.example.mybatis.entity.Worker;
import com.example.mybatis.entity.WorkerBank;
import com.example.mybatis.mapper.WorkerBankDao;
import com.example.mybatis.mapper.WorkerDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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

    @Value("${PathContractFile_KEY}")
    private String PathContractFile_KEY;

    @Value("${appSecret}")
    private String appSecret;
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

    /**
     * 发送签署加盟合同
     * @param workerId
     * @return
     */
    @Override
    public ReturnJson senSignAContract(String workerId) throws DefineException {
        Worker worker = workerDao.selectById(workerId);
        if (worker == null) {
            return ReturnJson.error("该用户不存在！");
        }
        if (worker.getAgreementSign() == 0){
            ReturnJson returnJson = SignAContractUtils.signAContract(PathContractFile_KEY, worker.getId(), worker.getAccountName(), worker.getIdcardCode(),
                    worker.getMobileCode());
            return returnJson;
        }
        return ReturnJson.error("您已经签署了加盟合同，请勿重复签署！");
    }

    /**
     * 合同签署成功后的回调
     * @param request
     * @return
     */
    @Override
    public ReturnJson callBackSignAContract(HttpServletRequest request) throws Exception{
        //查询body的数据进行验签
            String rbody = RealnameVerifyUtil.getRequestBody(request, "UTF-8");
            Map map = JsonUtils.jsonToPojo(rbody, Map.class);
            String flowId = (String) map.get("flowId");
            Integer signResult = (Integer)map.get("signResult");
            String thirdPartyUserId = (String)map.get("thirdPartyUserId");
            boolean res = RealnameVerifyUtil.checkPass(request, rbody, appSecret);
            if (!res) {
                return ReturnJson.error("签约失败！");
            }
            if (signResult != null && signResult == 2) {
                log.info("---------------------签署完成后，通知回调，平台方进行签署流程归档 start-----------------------------");
                SignHelper.archiveSignFlow(flowId);
                log.info("---------------------归档后，获取文件下载地址 start-----------------------------");
                Map<String, List<Map<String,Object>>> jsonHelper= (Map<String, List<Map<String,Object>>>)SignHelper.downloadFlowDoc(flowId);
                List<Map<String, Object>> list = jsonHelper.get("docs");
                Map<String, Object> flowInfo = list.get(0);
                Worker worker = workerDao.selectById(thirdPartyUserId);
                if (worker != null) {
                    worker.setAgreementSign(1);
                    worker.setAgreementUrl(String.valueOf(flowInfo.get("fileUrl")));
                    workerDao.updateById(worker);
                }
                return ReturnJson.success("签署加盟合同成功！");
            }
        return ReturnJson.success("签署流程开启！");
    }
}
