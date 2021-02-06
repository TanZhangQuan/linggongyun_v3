package com.example.common.contract.helper;


import com.alibaba.fastjson.JSONObject;
import com.example.common.contract.comm.HttpHelper;
import com.example.common.contract.comm.JSONHelper;
import com.example.common.contract.constant.ConfigConstant;
import com.example.common.contract.enums.RequestType;
import com.example.common.contract.exception.DefineException;
import com.example.common.contract.param.SignParamUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 宫清
 * @description 签署服务相关 辅助类
 * @date 2019年7月21日 下午5:21:31
 * @since JDK1.7
 */
@Slf4j
public class SignHelper {

    private SignHelper() {
    }

    // ------------------------------------------------------------------------------------------------签署流程相关start---------------------

    /**
     * @return
     * @throws DefineException
     * @description 创建签署流程
     * <p>
     * 组装参数：
     * <p>
     * {@link SignParamUtil#createSignFlowParam()}
     * @author 宫清
     * @date 2019年7月21日 下午5:25:31
     */
    public static JSONObject createSignFlow() throws DefineException {
        String param = SignParamUtil.createSignFlowParam();
        JSONObject json = HttpHelper.doCommHttp(RequestType.POST, ConfigConstant.createFlows_URL(), param);
        return JSONHelper.castDataJson(json, JSONObject.class);
    }

    /**
     * @param flowId 创建签署流程时返回的签署流程ID
     * @return
     * @throws DefineException
     * @description 查询签署流程
     * <p>
     * 说明：
     * <p>
     * 查询签署流程的详细信息，包括流程配置、签署状态等。
     * @author 宫清
     * @date 2019年7月21日 下午5:32:51
     */
    public static JSONObject qrySignFlow(String flowId) throws DefineException {
        JSONObject json = HttpHelper.doCommHttp(RequestType.GET, ConfigConstant.queryFlows_URL(flowId), null);
        return JSONHelper.castDataJson(json, JSONObject.class);
    }

    /**
     * @param flowId 创建签署流程时返回的签署流程ID
     * @throws DefineException
     * @description 签署流程开启
     * <p>
     * 说明：
     * <p>
     * 开启签署流程，开启后流程文档不可再添加或修改文档，签署任务会自动按照设置开始流转。流程开启后，归档前，可随时追加签署区（指定签署人的签署信息）
     * @author 宫清
     * @date 2019年7月21日 下午5:35:48
     */
    public static void startSignFlow(String flowId) throws DefineException {
        JSONObject json = HttpHelper.doCommHttp(RequestType.PUT, ConfigConstant.startFlows_URL(flowId), null);
        JSONHelper.castDataJson(json, Object.class);
    }

    /**
     * @param flowId 创建签署流程时返回的签署流程ID
     * @throws DefineException
     * @description 签署流程撤销
     * <p>
     * 说明：
     * <p>
     * 撤销签署流程，撤销后流程中止，所有签署无效
     * @author 宫清
     * @date 2019年7月21日 下午5:38:42
     */
    public static void revokeSignFlow(String flowId) throws DefineException {
        JSONObject json = HttpHelper.doCommHttp(RequestType.PUT, ConfigConstant.revokeFlows_URL(flowId), null);
        JSONHelper.castDataJson(json, Object.class);
    }

    /**
     * @param flowId 创建签署流程时返回的签署流程ID
     * @throws DefineException
     * @description 签署流程归档
     * <p>
     * 说明：
     * <p>
     * 手动归档签署流程，归档后所有资源均不可修改。归档前签署流程中的所有签署人必须都签署完成。如创建流程时设置了自动归档，则无需调用本接口，签署完成后系统会自动调用
     * @author 宫清
     * @date 2019年7月21日 下午5:40:25
     */
    public static void archiveSignFlow(String flowId) throws DefineException {
        JSONObject json = HttpHelper.doCommHttp(RequestType.PUT, ConfigConstant.archiveFlows_URL(flowId), null);
        JSONHelper.castDataJson(json, Object.class);
    }
    // ------------------------------------------------------------------------------------------------签署流程相关end-----------------------

    // ------------------------------------------------------------------------------------------------流程文档相关start---------------------

    /**
     * @param flowId 创建签署流程时返回的签署流程ID
     * @throws DefineException
     * @description 流程文档添加
     * <p>
     * 说明：
     * <p>
     * 向流程中添加待签署文档，文档必须先用文档管理接口创建，创建方式请参见文件管理接口文档。已经开启的流程不能再添加签署文档.
     * <p>
     * 组装参数：
     * <p>
     * {@link SignParamUtil addFlowDocParam(String, Integer, String, String)}
     * @author 宫清
     * @date 2019年7月21日 下午5:44:33
     */
    public static void addFlowDoc(String flowId, String fileIds) throws DefineException {

        String param = SignParamUtil.addFlowDocParam(fileIds);
        JSONObject json = HttpHelper.doCommHttp(RequestType.POST, ConfigConstant.aboutDocument_URL(flowId, null),
                param);
        JSONHelper.castDataJson(json, Object.class);
    }

    /**
     * @param flowId  创建签署流程时返回的签署流程ID
     * @param fileIds 文档id列表,多个id使用英文“，”分隔
     * @throws DefineException
     * @description 流程文档删除
     * <p>
     * 说明：
     * <p>
     * 删除流程中指定的文档，流程开启后不可删除
     * @author 宫清
     * @date 2019年7月21日 下午5:59:45
     */
    public static void delFlowDoc(String flowId, String fileIds) throws DefineException {
        JSONObject json = HttpHelper.doCommHttp(RequestType.DELETE, ConfigConstant.aboutDocument_URL(flowId, fileIds),
                null);
        JSONHelper.castDataJson(json, Object.class);
    }

    /**
     * @param flowId 创建签署流程时返回的签署流程ID
     *               fileIds 文档id列表,多个id使用英文“，”分隔
     * @throws DefineException
     * @description 流程文档下载
     * <p>
     * 说明：
     * <p>
     * 流程归档后，查询和下载签署后的文件
     * @author 宫清
     * @date 2019年7月21日 下午5:59:45
     */
    public static Object downloadFlowDoc(String flowId) throws DefineException {
        JSONObject json = HttpHelper.doCommHttp(RequestType.GET, ConfigConstant.aboutDocument_URL(flowId, null), null);
        JSONHelper.castDataJson(json, Object.class);
        return JSONHelper.castDataJson(json, Object.class);
    }

    // ------------------------------------------------------------------------------------------------流程文档相关end-----------------------

    // ------------------------------------------------------------------------------------------------流程附件相关start---------------------

    /**
     * @param flowId 创建签署流程时返回的签署流程ID
     * @return
     * @throws DefineException
     * @description 流程附件列表
     * <p>
     * 说明：
     * <p>
     * 查询流程关联的所有附件。
     * @author 宫清
     * @date 2019年7月21日 下午6:07:30
     */
    public static JSONObject qryFlowAttachments(String flowId) throws DefineException {
        JSONObject json = HttpHelper.doCommHttp(RequestType.GET, ConfigConstant.aboutAttachments_URL(flowId, null),
                null);
        return JSONHelper.castDataJson(json, JSONObject.class);
    }

    /**
     * @param flowId  创建签署流程时返回的签署流程ID
     * @param fileIds 附件Id，英文","拼接
     * @throws DefineException
     * @description 流程附件添加
     * <p>
     * 说明：
     * <p>
     * 为流程添加附件, 附件必须先用文档管理接口创建，附件无需签署，只作为签署过程中的参考，比如录音、视频, 图片, 文档等
     * <p>
     * 组装参数：
     * <p>
     * {@link SignParamUtil addFlowAttachParam(String, String)}
     * @author 宫清
     * @date 2019年7月21日 下午6:13:22
     */
    public static void addFlowAttachments(String flowId, List<String> fileIds, List<String> attachNames)
            throws DefineException {

        String param = SignParamUtil.addFlowAttachParam(fileIds, attachNames);
        JSONObject json = HttpHelper.doCommHttp(RequestType.POST, ConfigConstant.aboutAttachments_URL(flowId, null),
                param);
        JSONHelper.castDataJson(json, JSONObject.class);
    }

    /**
     * @param flowId  创建签署流程时返回的签署流程ID
     * @param fileIds 附件Id，英文","拼接
     * @throws DefineException
     * @description 流程附件删除
     * <p>
     * 说明：
     * <p>
     * 从流程中删除附件，流程开启前可以随意删除，流程开启后只能增加不能删除
     * @author 宫清
     * @date 2019年7月21日 下午6:48:32
     */
    public static void delFlowAttachments(String flowId, String fileIds) throws DefineException {
        JSONObject json = HttpHelper.doCommHttp(RequestType.DELETE,
                ConfigConstant.aboutAttachments_URL(flowId, fileIds), null);
        JSONHelper.castDataJson(json, JSONObject.class);
    }

    // ------------------------------------------------------------------------------------------------流程附件相关end-----------------------

    // ------------------------------------------------------------------------------------------------流程签署区相关start--------------------

    /**
     * @param flowId       创建签署流程时返回的签署流程ID
     * @param accountId    账号id，默认所有签署人
     * @param signfieldIds 指定签署区id列表，逗号分割，默认所有签署区
     * @return
     * @throws DefineException
     * @description 查询签署区列表
     * <p>
     * 说明：
     * <p>
     * 询流程签署区列表，可以查询指定指定id或者签署人所属的签署区
     * @author 宫清
     * @date 2019年7月21日 下午6:50:52
     */
    public static JSONObject qrySignArea(String flowId, String accountId, String signfieldIds) throws DefineException {
        JSONObject json = HttpHelper.doCommHttp(RequestType.GET,
                ConfigConstant.getSignFields_URL(flowId, accountId, signfieldIds), null);
        return JSONHelper.castDataJson(json, JSONObject.class);
    }

    /**
     * @param flowId  创建签署流程时返回的签署流程ID
     * @param fileIds 文件id集合
     * @param sealIds 印章id集合，其元素与文件id集合一一对应
     * @return
     * @throws DefineException
     * @description 添加平台自动盖章签署区
     * <p>
     * 说明：
     * <p>
     * 向指定流程中创建签署区，每个签署区视为一个任务，系统会自动按照流程流转。 签署区的添加必须在签署文档添加之后,
     * 签署区信息内部包含签署人、签署文档信息。
     * 签署区创建完成，流程开启后，系统将自动完成“用户自动盖章签署区”的盖章。用户可全程无感完成本次签署。创建签署方自动盖章签署区前，需确定已完成账号静默签署授权
     * 。
     * <p>
     * 签署方自动盖章的合同不符合电子签名法中对可靠的要求，仅适用于对法律效力要求不高的场景，或由对接平台方自行校验真实身份和真实意愿
     * 组装参数：
     * <p>
     * {@link SignParamUtil#addPlatformSignAreaParam(List, List)}
     * @author 宫清
     * @date 2019年7月21日 下午8:09:07
     */
    public static JSONObject addPlatformAutoSignArea(String flowId, List<String> fileIds, List<String> sealIds)
            throws DefineException {

        String param = SignParamUtil.addPlatformSignAreaParam(fileIds, sealIds);
        JSONObject json = HttpHelper.doCommHttp(RequestType.POST,
                ConfigConstant.addAutoSignfieldsForPlatform_URL(flowId), param);
        return JSONHelper.castDataJson(json, JSONObject.class);
    }

    /**
     * @param flowId               创建签署流程时返回的签署流程ID
     * @param fileIds              文件id集合
     * @param authorizedAccountIds 授权主体账号ID集合
     * @param sealIds              印章id集合，其元素与文件id集合、授权主体账号ID一一对应
     * @return
     * @throws DefineException
     * @description 添加签署方自动盖章签署区
     * <p>
     * 说明：
     * <p>
     * 向指定流程中创建签署区，每个签署区视为一个任务，系统会自动按照流程流转。 签署区的添加必须在签署文档添加之后,
     * 签署区信息内部包含签署文档信息（平台自动签无需指定签署人信息，默认签署人是对接的企业）。
     * <p>
     * 签署区创建完成，流程开启后，系统将自动完成“对接平台自动盖章签署区”的盖章，对接平台可全程无感完成本次签署
     * 添加签署方自动盖章签署区，需要是非实名签署，可通过后台配置
     * <p>
     * 组装参数：
     * <p>
     * {@link SignParamUtil#addSignerAutoSignAreaParam(List, List, List)}
     * @author 宫清
     * @date 2019年7月21日 下午8:09:07
     */
    public static JSONObject addSignerAutoSignArea(String flowId, List<String> fileIds,
                                                   List<String> authorizedAccountIds, List<String> sealIds) throws DefineException {

        String param = SignParamUtil.addSignerAutoSignAreaParam(fileIds, authorizedAccountIds, sealIds);
        JSONObject json = HttpHelper.doCommHttp(RequestType.POST, ConfigConstant.addAutoSignfieldsForPerson_URL(flowId),
                param);
        return JSONHelper.castDataJson(json, JSONObject.class);
    }

    /**
     * @param flowId               创建签署流程时返回的签署流程ID
     * @param fileIds              文件Id列表
     * @param signerAccountIds     签署人账号ID列表
     * @param authorizedAccountIds 授权主体账号ID列表,其元素与文件id、签署人账号ID集合一一对应
     * @return
     * @throws DefineException
     * @description 添加签署方手动盖章签署区
     * <p>
     * 说明：
     * <p>
     * 向指定流程中创建签署区，每个签署区视为一个任务，系统会自动按照流程流转。 签署区的添加必须在签署文档添加之后,
     * 签署区信息内部包含签署文档信息.
     * <p>
     * 签署区创建完成，流程开启后，可获取用户手动签署链接，通过此链接可打开文件签署页面，进行人工确认签署
     * <p>
     * 组装参数：
     * <p>
     * {@link SignParamUtil#addSignerAutoSignAreaParam(List, List, List)}
     * @author 宫清
     * @date 2019年7月21日 下午8:09:07
     */
    public static JSONObject addSignerHandSignArea(String flowId, List<String> fileIds, List<String> signerAccountIds,
                                                   List<String> authorizedAccountIds) throws DefineException {

        String param = SignParamUtil.addSignerHandSignAreaParam(fileIds, signerAccountIds, authorizedAccountIds);
        JSONObject json = HttpHelper.doCommHttp(RequestType.POST, ConfigConstant.addHandSignfieldsForPerson_URL(flowId),
                param);
        return JSONHelper.castDataJson(json, JSONObject.class);
    }

    /**
     * @param flowId       创建签署流程时返回的签署流程ID
     * @param signfieldIds 签署区id列表，逗号分割
     * @return
     * @throws DefineException
     * @description 删除签署区
     * <p>
     * 说明：
     * <p>
     * 删除指定id的签署区，只能删除未签署状态的签署区
     * @author 宫清
     * @date 2019年7月21日 下午9:20:17
     */
    public static JSONObject delSignArea(String flowId, String signfieldIds) throws DefineException {
        JSONObject json = HttpHelper.doCommHttp(RequestType.DELETE,
                ConfigConstant.deleteSignfields_URL(flowId, signfieldIds), null);
        return JSONHelper.castDataJson(json, JSONObject.class);
    }
    // ------------------------------------------------------------------------------------------------流程签署区相关end----------------------

    // ------------------------------------------------------------------------------------------------流程签署人相关start--------------------

    /**
     * @param flowId 创建签署流程时返回的签署流程ID
     * @return
     * @throws DefineException
     * @description 流程签署人列表
     * <p>
     * 说明：
     * <p>
     * 查询流程所有签署人列表
     * @author 宫清
     * @date 2019年7月21日 下午9:22:49
     */
    public static JSONObject qryFlowSigners(String flowId) throws DefineException {
        JSONObject json = HttpHelper.doCommHttp(RequestType.GET, ConfigConstant.getSignersList_URL(flowId), null);
        return JSONHelper.castDataJson(json, JSONObject.class);
    }

    /**
     * @param flowId 创建签署流程时返回的签署流程ID
     * @throws DefineException
     * @description 流程签署人催签
     * <p>
     * 说明：
     * <p>
     * 向当前轮到签署但还未签署的签署人发送催签提醒, 支持指定签署人发送催签提醒。
     * 被催签人accoutId是非必填的，该参数为空时，系统将向所有待签的签署人发送签署通知。 目前支持发起人催签，
     * 非发起人不能执行催签操作
     * <p>
     * 组装参数：
     * <p>
     * {@link SignParamUtil#rushsignersParam(String, String, String)}
     * @author 宫清
     * @date 2019年7月21日 下午9:27:41
     */
    public static void rushSign(String flowId, String accoundId, String noticeTypes, String rushsignAccountId)
            throws DefineException {

        String param = SignParamUtil.rushsignersParam(accoundId, noticeTypes, rushsignAccountId);
        JSONObject json = HttpHelper.doCommHttp(RequestType.PUT, ConfigConstant.urgeSgin_URL(flowId), param);
        JSONHelper.castDataJson(json, Object.class);
    }

    /**
     * @param flowId     创建签署流程时返回的签署流程ID
     * @param accountId  签署人账号id, 可指定, 默认用当前登录人账号id
     * @param organizeId 机构Id，传入本参数后，获取当前操作人代签企业的签署任务,默认空
     * @param urlType    链接类型: 0-签署链接;1-预览链接;默认0
     * @return
     * @throws DefineException
     * @description 获取签署地址
     * <p>
     * 说明：
     * <p>
     * 流程开启后，获取指定签署人的签署链接地址，如仅传入签署人账号id，则获取的签署任务链接仅包含本人的签署任务；如同时签署人账号id+机构id，则获取的签署任务链接包含企业与个人的签署任务
     * @author 宫清
     * @date 2019年7月21日 下午9:33:12
     */
    public static JSONObject qrySignUrl(String flowId, String accountId, String organizeId, String urlType)
            throws DefineException {
        JSONObject json = HttpHelper.doCommHttp(RequestType.GET,
                ConfigConstant.Sign_URL(flowId, accountId, organizeId, urlType), null);
        return JSONHelper.castDataJson(json, JSONObject.class);
    }

    // ------------------------------------------------------------------------------------------------流程签署人相关end----------------------

}
