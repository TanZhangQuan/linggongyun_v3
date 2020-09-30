package com.example.common.contract.run;


import com.example.common.contract.exception.DefineException;
import com.example.common.contract.helper.SignHelper;
import com.example.common.contract.helper.TokenHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 宫清
 * @description 签署流程测试启动
 * @date 2019年7月21日 下午9:38:31
 * @since JDK1.7
 */
public class RunProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunProcess.class);
    // -------------------------------------------公有方法start-------------------------------------------------------
    public static void main(String[] args) {

        try {
//            String filePath = "G:/upload/image/test.pdf";
//
//            LOGGER.info("---------------------获取token start------------------------------");
            TokenHelper.getTokenData();
//
//            LOGGER.info("---------------------创建个人账号start-------------------------------");
//            JSONObject personAcctJson = AccountHelper.createPersonAcct("DDDD", "罗玉兵", null, "431021199910107514", "15243556316", "yixi991010@163.com");
//            String acctId = personAcctJson.getString("accountId");
//
////			LOGGER.info("---------------------创建个人模板印章start-------------------------------");
////			JSONObject personSealJson = SealHelper.createPersonTemplateSeal(acctId, "测试个人印章", "RED", null, null, "YGYJFCS");
////			String personSealId = personAcctJson.getString("sealId");
//
//            LOGGER.info("---------------------创建机构账号start----------------------------------");
//            JSONObject orgAcctJson = AccountHelper.createOrgAcct("BBBB", acctId, "李四", null, null);
//            String orgId = orgAcctJson.getString("orgId");
//
//            LOGGER.info("---------------------创建机构印章start----------------------------------");
//            JSONObject orgSealJson = SealHelper.createOrgTemplateSeal(orgId, "顺利创印章", "BLUE", null, null, null, null, "TEMPLATE_ROUND", "STAR");
//            String orgSealId = orgSealJson.getString("");
//
//            LOGGER.info("---------------------通过上传方式创建文件start-----------------------------");
//            JSONObject uploadJson = FileTemplateHelper.createFileByUpload(filePath, "劳动合同.pdf", orgId);
//            String uploadUrl = uploadJson.getString("uploadUrl");
//            String fileId = uploadJson.getString("fileId");
//
//            LOGGER.info("---------------------文件流上传文件start---------------------------------");
//            FileTemplateHelper.streamUpload(filePath, uploadUrl);
//
//            LOGGER.info("---------------------签署流程创建 start---------------------------------");
//            //可通过SignParamUtil.createSignFlowStart()进行组装入参，具体使用中根据实际情况传参
//            JSONObject flowJson = SignHelper.createSignFlow();
//            String flowId = flowJson.getString("flowId");
//
//            LOGGER.info("---------------------流程文档添加 start---------------------------------");
//            SignHelper.addFlowDoc(flowId, fileId);
//
//            LOGGER.info("---------------------添加平台自动盖章签署区 start---------------------------");
//            SignHelper.addPlatformAutoSignArea(flowId, Lists.newArrayList(fileId), Lists.newArrayList(orgSealId));
//
//            LOGGER.info("---------------------添加手动盖章签署区 start-----------------------------");
//            SignHelper.addSignerHandSignArea(flowId, Lists.newArrayList(fileId), Lists.newArrayList(acctId), null);
//
//            LOGGER.info("---------------------签署流程开启 start-----------------------------");
//            SignHelper.startSignFlow(flowId);

			LOGGER.info("---------------------签署完成后，通知回调，平台方进行签署流程归档 start-----------------------------");
			SignHelper.archiveSignFlow("e2596bc1687f4a679cbf49de68ffbc0e");

			LOGGER.info("---------------------归档后，获取文件下载地址 start-----------------------------");
			SignHelper.downloadFlowDoc("e2596bc1687f4a679cbf49de68ffbc0e");

        } catch (DefineException e) {
            e.getE().printStackTrace();
        }

    }
}
