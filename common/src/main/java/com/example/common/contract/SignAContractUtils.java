package com.example.common.contract;

import com.agile.ecloud.sdk.bean.ECloudDomain;
import com.agile.ecloud.sdk.bean.EcloudPublicKey;
import com.agile.ecloud.sdk.http.EcloudClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.common.contract.exception.DefineException;
import com.example.common.contract.helper.*;
import com.example.common.util.HttpUtil;
import com.example.common.util.ReturnJson;
import com.example.common.util.UuidUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

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
        JSONObject orgAcctJson = AccountHelper.createOrgAcct("SLC", acctId, "零工云（上海）科技发展有限公司", "CRED_ORG_USCC", null);
        String orgId = orgAcctJson.getString("orgId");

        log.info("---------------------创建机构印章start----------------------------------");
        JSONObject orgSealJson = SealHelper.createOrgTemplateSeal(orgId, "顺利创印章", "RED", 159, 159, null, null, "TEMPLATE_ROUND", "STAR");
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

        log.info("---------------------添加平台自动盖章签署区 start---------------------------");
        SignHelper.addPlatformAutoSignArea(flowId, Lists.newArrayList(fileId), Lists.newArrayList(orgSealId));

        log.info("---------------------添加手动盖章签署区 start-----------------------------");
        SignHelper.addSignerHandSignArea(flowId, Lists.newArrayList(fileId), Lists.newArrayList(acctId), null);

        log.info("---------------------签署流程开启 start-----------------------------");
        SignHelper.startSignFlow(flowId);
        return ReturnJson.success("网签短信也发送到" + mobile + "请查看手机短信并通过链接进行网签《加盟合同》", 0);
    }


    public static ReturnJson signYiYunAContract(String filePath, String yyqAppKey, String yyqSecrept, String yyqUrlString, String uuid, String PathImage_KEY, String realName, String IdCardCode, String mobile, String taxName, String linkMobile, String creditCode, String taxMan, Integer attestation, Integer platformCertificationState) throws DefineException {

        log.info("---------------------获取token start------------------------------");
        TokenHelper.getTokenData();
        EcloudPublicKey.init(yyqAppKey, yyqSecrept, "1.0", yyqUrlString);

        if (attestation != 1) {
            //创客实名
            ECloudDomain workerCloudDomain = EcloudClient.threeElementsIdentification(realName, mobile, IdCardCode);
            if (!workerCloudDomain.getCode().equals("0")) {
                return ReturnJson.error("创客实名不通过");
            }
        }

        if (platformCertificationState != 1) {
            //企业实名
            ECloudDomain serviceProviderCloudDomain = EcloudClient.busThreeElementsIdentification(taxMan, taxName, creditCode);
            if (!serviceProviderCloudDomain.getCode().equals("0")) {
                return ReturnJson.error("企业实名不通过");
            }
        }

        log.info("---------------------个人申请证书-------------------------------");
        EcloudClient.applyCert("1", "0", IdCardCode, realName, mobile);

        log.info("---------------------企业申请证书----------------------------------");
        EcloudClient.applyCert("2", "8", creditCode, taxName, linkMobile);

        log.info("---------------------服务商自动生成印章----------------------------------");
        ECloudDomain serviceProviderSeal = EcloudClient.createSeal(linkMobile, taxName, null, null);
        //获取服务商签名对象
        Map<String, Object> serviceProviderSealObject = (Map) serviceProviderSeal.getData();

        log.info("---------------------创客自动生成签名-----------------------------");
        ECloudDomain workerSeal = EcloudClient.createSign(mobile, realName, null, null, null, null);
        //获取创客签名对象
        Map<String, Object> workerSealObject = (Map) workerSeal.getData();

        log.info("---------------------获取用户签名照片-----------------------------");
        ECloudDomain workerSignImg = EcloudClient.getSignImg(mobile, workerSealObject.get("signId").toString());
        //获取用户签名照片签名对象
        Map<String, Object> workerSignImgObject = (Map) workerSignImg.getData();

        log.info("---------------------添加创客签名---------------------------------");
        ECloudDomain workerAddSign = EcloudClient.addSign(mobile, "1", workerSignImgObject.get("signImg").toString());

        log.info("---------------------获取服务商的印章-----------------------------");
        ECloudDomain serviceProviderSignImg = EcloudClient.getSignImg(linkMobile, serviceProviderSealObject.get("signId").toString());
        //获取用户签名照片签名对象
        Map<String, Object> serviceProviderSignImgObject = (Map) serviceProviderSignImg.getData();

        log.info("---------------------添加服务商的印章---------------------------------");
        ECloudDomain serviceProviderSign = EcloudClient.addSign(linkMobile, "2", serviceProviderSignImgObject.get("signImg").toString());

        log.info("---------------------添加模板---------------------------------");
        ECloudDomain contract = EcloudClient.createContract("", mobile, new File(filePath));
        //添加模板对象
        Map<String, Object> contractMap = (Map) contract.getData();

        log.info("---------------------创客坐标自动签署合同---------------------------------");
        Map<String, String> map = new HashMap<>();
        map.put("type", "1");
        map.put("cardType", "0");
        map.put("idCardNum", IdCardCode);
        map.put("name", realName);
        map.put("mobilePhone", mobile);
        String param = JSON.toJSONString(map);
        List<Map<String, String>> listMap = new ArrayList<>();
        Map<String, String> positionMap = new HashMap();
        positionMap.put("page", "1");
        positionMap.put("x", "200");
        positionMap.put("y", "200");
        positionMap.put("signId", workerSealObject.get("signId").toString());
        listMap.add(positionMap);
        String listMapStr = JSON.toJSONString(listMap);
        EcloudClient.autoSignByPoistion(param, contractMap.get("contractNum").toString(), 1, listMapStr);


        log.info("---------------------服务商坐标自动签署合同---------------------------------");
        Map<String, String> serviceProviderMap = new HashMap<>();
        serviceProviderMap.put("type", "2");
        serviceProviderMap.put("cardType", "8");
        serviceProviderMap.put("idCardNum", creditCode);
        serviceProviderMap.put("name", taxName);
        serviceProviderMap.put("mobilePhone", linkMobile);
        String serviceProviderParam = JSON.toJSONString(serviceProviderMap);
        List<Map<String, String>> serviceProviderListMap = new ArrayList<>();
        Map<String, String> serviceProviderPositionMap = new HashMap();
        serviceProviderPositionMap.put("page", "1");
        serviceProviderPositionMap.put("x", "150");
        serviceProviderPositionMap.put("y", "200");
        serviceProviderPositionMap.put("signId", serviceProviderSealObject.get("signId").toString());
        serviceProviderListMap.add(serviceProviderPositionMap);
        String serviceProviderListMapStr = JSON.toJSONString(serviceProviderListMap);
        EcloudClient.autoSignByPoistion(serviceProviderParam, contractMap.get("contractNum").toString(), 0, serviceProviderListMapStr);

        //下载合同
        EcloudClient.downloadContract(contractMap.get("contractNum").toString(), PathImage_KEY + uuid);
        return ReturnJson.success("下载成功");
    }


    public static ReturnJson signH5YiYunAContract(String workerId, String filePath, String yyqAppKey, String yyqSecrept, String yyqAES, String yyqUrl, String yyqCallBack, String realName, String IdCardCode, String mobile, String taxName, String linkMobile, String creditCode, String taxMan, Integer attestation, Integer platformCertificationState) throws DefineException {

        log.info("---------------------获取token start------------------------------");
        EcloudPublicKey.init(yyqAppKey, yyqSecrept, "1.0", yyqUrl);

        if (attestation != 1) {
            //创客实名
            ECloudDomain workerCloudDomain = EcloudClient.threeElementsIdentification(realName, mobile, IdCardCode);
            log.info(mobile + "---------------------" + workerCloudDomain.getCode() + "------------------------------" + workerCloudDomain.getMessage());
            if (!workerCloudDomain.getCode().equals("0")) {
                return ReturnJson.error("创客实名不通过");
            }
            if(!workerCloudDomain.getMessage().equals("一致")){
                return ReturnJson.error("创客实名不通过");
            }
        }

        if (platformCertificationState != 1) {
            //企业实名
            ECloudDomain serviceProviderCloudDomain = EcloudClient.busThreeElementsIdentification(taxMan, taxName, creditCode);
            if (!serviceProviderCloudDomain.getCode().equals("0")) {
                return ReturnJson.error("企业实名不通过");
            }
            if(!serviceProviderCloudDomain.getMessage().equals("一致")){
                return ReturnJson.error("企业实名不通过");
            }
        }

        log.info("---------------------个人申请证书-------------------------------");
        EcloudClient.applyCert("1", "0", IdCardCode, realName, mobile);

        log.info("---------------------企业申请证书----------------------------------");
        EcloudClient.applyCert("2", "8", creditCode, taxName, linkMobile);

        log.info("---------------------服务商自动生成印章----------------------------------");
        ECloudDomain serviceProviderSeal = EcloudClient.createSeal(linkMobile, taxName, null, null);
        //获取服务商签名对象
        Map<String, Object> serviceProviderSealObject = (Map) serviceProviderSeal.getData();
        log.info("---------------------获取服务商的印章-----------------------------");
        ECloudDomain serviceProviderSignImg = EcloudClient.getSignImg(linkMobile, serviceProviderSealObject.get("signId").toString());
        //获取用户签名照片签名对象
        Map<String, Object> serviceProviderSignImgObject = (Map) serviceProviderSignImg.getData();

        log.info("---------------------添加服务商的印章---------------------------------");
        ECloudDomain serviceProviderSign = EcloudClient.addSign(linkMobile, "2", serviceProviderSignImgObject.get("signImg").toString());

        log.info("---------------------添加模板---------------------------------");
        List<Map<String, String>> signPositionlistMap = new ArrayList<>();
        Map<String, String> signPositionMap = new HashMap<>();
        signPositionMap.put("positionName", "用户");
        signPositionMap.put("x", "200");
        signPositionMap.put("y", "200");
        signPositionMap.put("page", "1");
        signPositionlistMap.add(signPositionMap);
        String signPositionMapStr = JSON.toJSONString(signPositionlistMap);

        StringBuffer sb = new StringBuffer("");
        try {
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 添加模板
        ECloudDomain template = EcloudClient.addHtmlTemplate("加盟合同", sb.toString(), null, signPositionMapStr);
        //添加模板对象
        Map<String, Object> templateMap = (Map) template.getData();

        ECloudDomain contract = EcloudClient.createContractByTemplate(templateMap.get("templateNumber").toString(), "{a:1312}", null);
        Map<String, Object> contractMap = (Map) contract.getData();
        List<Map<String, String>> listMap = new ArrayList<>();
        Map<String, String> positionMap = new HashMap();
        positionMap.put("x", "200");
        positionMap.put("y", "200");
        positionMap.put("positionName", "用户");
        listMap.add(positionMap);
        String listMapStr = JSON.toJSONString(listMap);


        log.info("---------------------服务商坐标自动签署合同---------------------------------");
        Map<String, String> serviceProviderMap = new HashMap<>();
        serviceProviderMap.put("type", "2");
        serviceProviderMap.put("cardType", "8");
        serviceProviderMap.put("idCardNum", creditCode);
        serviceProviderMap.put("name", taxName);
        serviceProviderMap.put("mobilePhone", linkMobile);
        String serviceProviderParam = JSON.toJSONString(serviceProviderMap);
        List<Map<String, String>> serviceProviderListMap = new ArrayList<>();
        Map<String, String> serviceProviderPositionMap = new HashMap();
        serviceProviderPositionMap.put("page", "1");
        serviceProviderPositionMap.put("x", "200");
        serviceProviderPositionMap.put("y", "200");
        serviceProviderPositionMap.put("signId", serviceProviderSealObject.get("signId").toString());
        serviceProviderListMap.add(serviceProviderPositionMap);
        String serviceProviderListMapStr = JSON.toJSONString(serviceProviderListMap);
        EcloudClient.autoSignByPoistion(serviceProviderParam, contractMap.get("contractNum").toString(), 1, serviceProviderListMapStr);
        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Map<String, String> H5Map = new HashMap<>();
        H5Map.put("appKey", yyqAppKey);
        H5Map.put("secret", yyqSecrept);
        H5Map.put("timestamp", sfd.format(new Date()));
        H5Map.put("v", "1.0");
        H5Map.put("telephone", mobile);
        H5Map.put("contractNum", contractMap.get("contractNum").toString());
        H5Map.put("signPosition", listMapStr);
        H5Map.put("cardType", "0");
        H5Map.put("idCardNum", IdCardCode);
        H5Map.put("name", realName);
        H5Map.put("isFinish", "0");
        H5Map.put("callBack", yyqCallBack + "?workerId=" + workerId + "&contractNum=" + contractMap.get("contractNum").toString());
        String H5url = "https://h5.ecloudsign.com/signFilePage?";
        String s = null;
        try {
            s = HttpUtil.buildMap(H5Map, yyqAES);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String substring = s.substring(0, s.length() - 1);

        return ReturnJson.success(H5url + substring, contractMap.get("contractNum").toString());
    }
}
