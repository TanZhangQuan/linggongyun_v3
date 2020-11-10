package com.example.merchant.websocket;

import com.example.common.enums.MessageStatus;
import com.example.common.enums.UserType;
import com.example.common.util.JsonUtils;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.CommonMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class SenMessage {
    @Resource
    private WebsocketServer websocketServer;

    public ReturnJson senMsg(String msg, String NoOrder, String sendUserId, UserType sendUserType, String receiveUserId, UserType receiveUserType) {
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

    public ReturnJson senMsg(List<CommonMessage> commonMessageList) {
        websocketServer.onMessage(JsonUtils.objectToJson(commonMessageList));
        return ReturnJson.success("发送成功！");
    }

    public CommonMessage getCommonMessage(String msg, String NoOrder, String sendUserId, UserType sendUserType, String receiveUserId, UserType receiveUserType) {
        CommonMessage commonMessage = new CommonMessage();
        commonMessage.setMessageStatus(MessageStatus.UNREADE);
        commonMessage.setNoOrder(NoOrder);
        commonMessage.setMessage(msg);
        commonMessage.setSendUserId(sendUserId);
        commonMessage.setSendUserType(sendUserType);
        commonMessage.setReceiveUserId(receiveUserId);
        commonMessage.setReceiveUserType(receiveUserType);
        return commonMessage;
    }
}
