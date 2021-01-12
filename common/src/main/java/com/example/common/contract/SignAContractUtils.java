package com.example.common.contract;

import com.alibaba.fastjson.JSONObject;
import com.example.common.contract.exception.DefineException;
import com.example.common.contract.helper.*;
import com.example.common.util.ReturnJson;
import com.example.common.util.UuidUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class SignAContractUtils {

    /**
     * 功能描述: 银行卡号3要素验证
     *
     * @param workerName 创客真实姓名
     * @param idCard     创客的身份证号码
     * @param bankCode   创客的银行卡号
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     * @Author 忆惜
     * @Date 2020/11/2 13:49
     */
    public static Map<String, Object> bank3Factors(String workerName, String idCard, String bankCode) throws DefineException {
        TokenHelper.getTokenData();
        JSONObject jsonObject = AccountHelper.bankAuthentication(workerName, idCard, bankCode);
        log.info(jsonObject.toString());
        return jsonObject.getInnerMap();
    }

    public static ReturnJson signAContract(String filePath, String userId, String realName, String IdCardCode, String mobile) throws DefineException {

        log.info("---------------------获取token start------------------------------");
        TokenHelper.getTokenData();

        log.info("---------------------创建个人账号start-------------------------------");
        JSONObject personAcctJson = AccountHelper.createPersonAcct(userId, realName, null, IdCardCode, mobile, null);
        String acctId = personAcctJson.getString("accountId");

        log.info("---------------------创建机构账号start----------------------------------");
        JSONObject orgAcctJson = AccountHelper.createOrgAcct("BBBB", acctId, "李四", null, null);
        String orgId = orgAcctJson.getString("orgId");

        log.info("---------------------创建机构印章start----------------------------------");
        JSONObject orgSealJson = SealHelper.createOrgTemplateSeal(orgId, "顺利创印章", "BLUE", null, null, null, null, "TEMPLATE_ROUND", "STAR");
        String orgSealId = orgSealJson.getString("");

        log.info("---------------------通过上传方式创建文件start-----------------------------");
        JSONObject uploadJson = FileTemplateHelper.createFileByUpload(filePath, UuidUtil.get32UUID() + ".pdf", orgId);
        String uploadUrl = uploadJson.getString("uploadUrl");
        String fileId = uploadJson.getString("fileId");

        log.info("---------------------文件流上传文件start---------------------------------");
        FileTemplateHelper.streamUpload(filePath, uploadUrl);

        log.info("---------------------签署流程创建 start---------------------------------");
        //可通过SignParamUtil.createSignFlowStart()进行组装入参，具体使用中根据实际情况传参
        JSONObject flowJson = SignHelper.createSignFlow();
        String flowId = flowJson.getString("flowId");

        log.info("---------------------流程文档添加 start---------------------------------");
        SignHelper.addFlowDoc(flowId, fileId);

//        LOGGER.info("---------------------添加平台自动盖章签署区 start---------------------------");
//        SignHelper.addPlatformAutoSignArea(flowId, Lists.newArrayList(fileId), Lists.newArrayList(orgSealId));

        log.info("---------------------添加手动盖章签署区 start-----------------------------");
        SignHelper.addSignerHandSignArea(flowId, Lists.newArrayList(fileId), Lists.newArrayList(acctId), null);

        log.info("---------------------签署流程开启 start-----------------------------");
        SignHelper.startSignFlow(flowId);
        return ReturnJson.success("网签短信也发送到" + mobile + "请查看手机短信并通过链接进行网签《加盟合同》");
    }
}
