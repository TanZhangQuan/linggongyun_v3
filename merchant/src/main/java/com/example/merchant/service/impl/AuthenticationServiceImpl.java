package com.example.merchant.service.impl;

import com.example.common.contract.SignAContractUtils;
import com.example.common.contract.exception.DefineException;
import com.example.common.contract.helper.SignHelper;
import com.example.common.enums.IdCardSide;
import com.example.common.enums.MessageStatus;
import com.example.common.enums.UserType;
import com.example.common.util.IdCardUtils;
import com.example.common.util.JsonUtils;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.makerend.IdCardInfoDto;
import com.example.merchant.dto.makerend.WorkerBankDto;
import com.example.merchant.service.AuthenticationService;
import com.example.merchant.service.FileOperationService;
import com.example.merchant.util.RealnameVerifyUtil;
import com.example.merchant.websocket.WebsocketServer;
import com.example.mybatis.entity.CommonMessage;
import com.example.mybatis.entity.Worker;
import com.example.mybatis.entity.WorkerBank;
import com.example.mybatis.mapper.WorkerBankDao;
import com.example.mybatis.mapper.WorkerDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Resource
    private WorkerDao workerDao;

    @Resource
    private WorkerBankDao workerBankDao;

    @Value("${PathImage_KEY}")
    private String PathImage_KEY;

    @Value("${TemplateFile.Contract}")
    private String contract;

    @Value("${appSecret}")
    private String appSecret;

    @Resource
    private FileOperationService fileOperationService;

    @Resource
    private WebsocketServer websocketServer;

    @Override
    public ReturnJson getIdCardInfo(String filePath, IdCardSide idCardSide) throws Exception {
        String[] fileFormats = {"jpg", "png", "bmp"};
        String fileFormat = filePath.substring(filePath.lastIndexOf(".") + 1);
        log.info(fileFormat);
        if (!Arrays.asList(fileFormats).contains(fileFormat.toLowerCase())) {
            return ReturnJson.error("现在只支持jpg、png、bmp，格式的图片");
        }
        Map<String, String> idCardInfo = IdCardUtils.getIdCardInfo(filePath, idCardSide);
        return ReturnJson.success(idCardInfo);
    }

    @Override
    public ReturnJson saveIdCardinfo(IdCardInfoDto idCardInfoDto, String workerId) throws Exception {
        Worker worker = workerDao.selectById(workerId);
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

    @Override
    public ReturnJson saveBankInfo(WorkerBankDto workerBankDto, String workerId) throws Exception {
        Worker worker = workerDao.selectById(workerId);
        if (worker == null) {
            return ReturnJson.error("您输入的用户不存在！");
        }
        WorkerBank workerBank = new WorkerBank();
        BeanUtils.copyProperties(workerBankDto, workerBank);
        workerBank.setWorkerId(worker.getId());
        int insert = workerBankDao.insert(workerBank);
        if (insert == 1) {
            return ReturnJson.success("银行卡绑定成功！");
        }
        return ReturnJson.error("银行卡绑定失败！");
    }

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

    @Override
    public ReturnJson senSignAContract(String workerId) throws DefineException {
        Worker worker = workerDao.selectById(workerId);
        if (worker == null) {
            return ReturnJson.error("该用户不存在！");
        }
        if (worker.getAttestation() != 1) {
            return ReturnJson.error("请您先完成实名认证！");
        }
        if (worker.getAgreementSign() == 0 || worker.getAgreementSign() == -1) {
            ReturnJson returnJson = SignAContractUtils.signAContract(contract, worker.getId(), worker.getAccountName(), worker.getIdcardCode(),
                    worker.getMobileCode());
            worker.setAgreementSign(1);
            workerDao.updateById(worker);
            return returnJson;
        } else if (worker.getAgreementSign() == 1) {
            return ReturnJson.error("加盟合同正在签署中，请查看手机短信并通过链接进行网签《加盟合同》！");
        }
        return ReturnJson.error("您已经签署了加盟合同，请勿重复签署！");
    }

    @Override
    public synchronized ReturnJson callBackSignAContract(HttpServletRequest request) {
        //查询body的数据进行验签
        Map map = null;
        Worker worker = null;
        boolean res = false;
        try {
            String rbody = RealnameVerifyUtil.getRequestBody(request, "UTF-8");
            map = JsonUtils.jsonToPojo(rbody, Map.class);
            res = RealnameVerifyUtil.checkPass(request, rbody, appSecret);
        } catch (Exception e) {
            log.error(e.toString() + ":" + e.getMessage());
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
            worker = workerDao.selectById(thirdPartyUserId);
            if (worker != null) {
                if (worker.getAgreementSign() == 2) {
                    log.info("该创客加盟合同已签署完成！");
                    return ReturnJson.error("该创客加盟合同已签署完成!");
                }
            } else {
                log.error("用户不存在");
                return ReturnJson.error("用户不存在！");
            }
        } else {
            log.error("用户ID为空!");
            return ReturnJson.error("用户ID为空！");
        }
        if (signResult != null && signResult == 2 && !StringUtils.isBlank(flowId)) {
            log.info("---------------------签署完成后，通知回调，平台方进行签署流程归档 start-----------------------------");
            try {
                SignHelper.archiveSignFlow(flowId);
            } catch (DefineException e) {
                log.error(e.toString() + ":" + e.getMessage());
                worker.setAgreementSign(-1);
                workerDao.updateById(worker);
                return null;
            }
            log.info("---------------------归档后，获取文件下载地址 start-----------------------------");
            Map<String, List<Map<String, Object>>> jsonHelper = null;
            try {
                jsonHelper = (Map<String, List<Map<String, Object>>>) SignHelper.downloadFlowDoc(flowId);
            } catch (DefineException e) {
                log.error(e.toString() + ":" + e.getMessage());
                worker.setAgreementSign(-1);
                workerDao.updateById(worker);
                return null;
            }
            List<Map<String, Object>> list = jsonHelper.get("docs");
            Map<String, Object> flowInfo = list.get(0);
            String fileUrl = String.valueOf(flowInfo.get("fileUrl"));
            String url = fileOperationService.uploadJpgOrPdf(fileUrl, request);
            if (StringUtils.isBlank(url)) {
                worker.setAgreementSign(-1);
                workerDao.updateById(worker);
                this.senMsg("签署加盟合同失败!", "", "0", UserType.ADMIN, worker.getId(), UserType.WORKER);
                return ReturnJson.success("签署加盟合同失败！");
            }
            worker.setAgreementSign(2);
            worker.setAgreementUrl(url);
            workerDao.updateById(worker);
            this.senMsg("签署加盟合同成功!", "", "0", UserType.ADMIN, worker.getId(), UserType.WORKER);
            return ReturnJson.success("签署加盟合同成功！");
        }

        if (signResult != null && signResult == 3 && !StringUtils.isBlank(thirdPartyUserId)) {
            worker.setAgreementSign(-1);
            workerDao.updateById(worker);
            String resultDescription = (String) map.get("resultDescription");
            return ReturnJson.success(resultDescription);
        }
        return ReturnJson.success("签署流程开启！");
    }

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

    private ReturnJson senMsg(String msg, String NoOrder, String sendUserId, UserType sendUserType, String receiveUserId, UserType receiveUserType) {
        List<CommonMessage> commonMessageList = new ArrayList<>();
        CommonMessage commonMessage = new CommonMessage();
        commonMessage.setMessageStatus(MessageStatus.UNREADE);
        commonMessage.setNoOrder(NoOrder);
        commonMessage.setMessage(msg);
        commonMessage.setSendUserId(sendUserId);
        commonMessage.setSendUserType(sendUserType);
        commonMessage.setReceiveUserId(receiveUserId);
        commonMessage.setReceiveUserType(receiveUserType);
        commonMessageList.add(commonMessage);
        websocketServer.onMessage(JsonUtils.objectToJson(commonMessageList));
        return ReturnJson.success("发送成功！");
    }
}
