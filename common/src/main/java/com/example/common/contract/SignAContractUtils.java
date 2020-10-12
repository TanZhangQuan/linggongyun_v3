package com.example.common.contract;

import com.alibaba.fastjson.JSONObject;
import com.example.common.contract.exception.DefineException;
import com.example.common.contract.helper.*;
import com.example.common.util.ReturnJson;
import com.example.common.util.UuidUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignAContractUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignAContractUtils.class);

    public static ReturnJson signAContract(String filePath, String userId, String realName, String IdCardCode, String mobile) throws DefineException {

        LOGGER.info("---------------------获取token start------------------------------");
        TokenHelper.getTokenData();

        LOGGER.info("---------------------创建个人账号start-------------------------------");
        JSONObject personAcctJson = AccountHelper.createPersonAcct(userId, realName, null, IdCardCode, mobile, null);
        String acctId = personAcctJson.getString("accountId");

        LOGGER.info("---------------------创建机构账号start----------------------------------");
        JSONObject orgAcctJson = AccountHelper.createOrgAcct("BBBB", acctId, "李四", null, null);
        String orgId = orgAcctJson.getString("orgId");

        LOGGER.info("---------------------创建机构印章start----------------------------------");
        JSONObject orgSealJson = SealHelper.createOrgTemplateSeal(orgId, "顺利创印章", "BLUE", null, null, null, null, "TEMPLATE_ROUND", "STAR");
        String orgSealId = orgSealJson.getString("");

        LOGGER.info("---------------------通过上传方式创建文件start-----------------------------");
        JSONObject uploadJson = FileTemplateHelper.createFileByUpload(filePath, UuidUtil.get32UUID() +".pdf", orgId);
        String uploadUrl = uploadJson.getString("uploadUrl");
        String fileId = uploadJson.getString("fileId");

        LOGGER.info("---------------------文件流上传文件start---------------------------------");
        FileTemplateHelper.streamUpload(filePath, uploadUrl);

        LOGGER.info("---------------------签署流程创建 start---------------------------------");
        //可通过SignParamUtil.createSignFlowStart()进行组装入参，具体使用中根据实际情况传参
        JSONObject flowJson = SignHelper.createSignFlow();
        String flowId = flowJson.getString("flowId");

        LOGGER.info("---------------------流程文档添加 start---------------------------------");
        SignHelper.addFlowDoc(flowId, fileId);

//        LOGGER.info("---------------------添加平台自动盖章签署区 start---------------------------");
//        SignHelper.addPlatformAutoSignArea(flowId, Lists.newArrayList(fileId), Lists.newArrayList(orgSealId));

        LOGGER.info("---------------------添加手动盖章签署区 start-----------------------------");
        SignHelper.addSignerHandSignArea(flowId, Lists.newArrayList(fileId), Lists.newArrayList(acctId), null);

        LOGGER.info("---------------------签署流程开启 start-----------------------------");
        SignHelper.startSignFlow(flowId);
        return ReturnJson.success("网签短信也发送到" + mobile + "请查看手机短信并通过链接进行网签《加盟合同》");
    }
}
