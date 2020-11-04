package com.example.merchant.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.common.enums.MessageStatus;
import com.example.common.enums.UserType;
import com.example.merchant.service.CommonMessageService;
import com.example.mybatis.entity.CommonMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author niebo
 */
@ServerEndpoint(value = "/wsocket/{user_id}")
@Component
public class WebsocketServer {
    private static final Logger logger = LoggerFactory.getLogger(WebsocketServer.class);

    /**
     * 存放在线用户的session信息
     */
    private static Map<String, WebSocketClient> socketClientMap = new ConcurrentHashMap<>();

    //自动注入失败，手动注入
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    private CommonMessageService commonMessageService;

    /**
     * 用户连接时触发，我们将其添加到
     * 保存客户端连接信息的socketServers中
     *
     * @param session
     * @param
     */
    @OnOpen
    public void open(Session session, @PathParam(value = "user_id") String userId) {

        //如果重新链接，删除老的session客户端
        if (socketClientMap.containsKey(userId)) {
            socketClientMap.remove(userId);
        }
        socketClientMap.put(userId, new WebSocketClient(userId, session));
        logger.info("用户:【{}】连接成功", userId);
        //获取不在线时收到的消息

        if (commonMessageService == null) {
            commonMessageService = applicationContext.getBean(CommonMessageService.class);
        }

        String userType = userId.substring(0, userId.indexOf("_"));
        String id = userId.substring(userId.indexOf("_") + 1);
        UserType threeUserType;
        switch (userType) {
            case "WORKER":

                threeUserType = UserType.WORKER;
                break;

            case "MERCHANT":

                threeUserType = UserType.MERCHANT;
                break;

            case "ADMIN":

                threeUserType = UserType.ADMIN;
                break;

            default:
                return;
        }

        List<CommonMessage> unReadCommonMessages = commonMessageService.findByReceiveUserTypeAndReceiveUserIdAndMessageStatus(threeUserType, id, MessageStatus.UNREADE);
        //有未发送的消息，该用户登陆后发送给他
        if (!(CollectionUtils.isEmpty(unReadCommonMessages))) {
            JSONArray messageJson = (JSONArray) JSON.toJSON(unReadCommonMessages);
            onMessage(messageJson.toJSONString());
        }
    }


    /**
     * 连接关闭触发，通过sessionId来移除
     * socketServers中客户端连接信息
     */
    @OnClose
    public void onClose(Session session, @PathParam(value = "user_id") String userId) {

        if (socketClientMap.containsKey(userId)) {
            socketClientMap.remove(userId);
            logger.info("用户:【{}】断开连接", userId);
        }

    }

    @OnMessage
    public void onMessage(String message) {
        logger.info("收到消息：{}", message);
        //如果用户没上线，则下次上线再发送，标记好消息状态

        if (commonMessageService == null) {
            commonMessageService = applicationContext.getBean(CommonMessageService.class);
        }
        List<CommonMessage> commonMessageList = JSONObject.parseArray(message, CommonMessage.class);
        logger.info("消息信息------>"+commonMessageList.toString());
        for (CommonMessage commonMessageObj : commonMessageList) {
            String userId = commonMessageObj.getReceiveUserType() + "_" + commonMessageObj.getReceiveUserId();
            if (socketClientMap.containsKey(userId)) {
                commonMessageObj.setMessageStatus(MessageStatus.HASREAD);
                Map<String, Object> map = new HashMap<>();
                map.put("send_user_type", commonMessageObj.getSendUserType());
                map.put("send_user_id", commonMessageObj.getSendUserId());
                map.put("create_date", commonMessageObj.getCreateDate());
                map.put("msg", commonMessageObj.getMessage());
                JSONObject json = (JSONObject) JSON.toJSON(map);
                logger.info("消息信息的json---->"+json.toString());
                sendMessage(String.valueOf(json), userId);
                commonMessageService.saveMessage(commonMessageObj);
            } else {
                commonMessageObj.setMessageStatus(MessageStatus.UNREADE);
                commonMessageService.saveMessage(commonMessageObj);
            }

        }
    }

    /**
     * 发生错误时触发
     *
     * @param error
     */
    @OnError
    public void onError(@PathParam(value = "user_id") String userId, Throwable error) {

        if (socketClientMap.containsKey(userId)) {
            socketClientMap.remove(userId);
            logger.info("用户:【{}】断开连接", userId);
        }
        logger.error("用户:【{}】发生异常", userId);
        logger.error("websocket错误！", error);
    }

    /**
     * 信息发送的方法，通过客户端的userName
     * 拿到其对应的session，调用信息推送的方法
     *
     * @param message
     * @param receivedUserId
     */
    public synchronized static void sendMessage(String message, String receivedUserId) {
        try {
            WebSocketClient webSocketClient = socketClientMap.get(receivedUserId);
            webSocketClient.getSession().getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.error("websocket sendMessage:", e);
        }

    }

}
