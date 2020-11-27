package com.example.merchant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.enums.MessageStatus;
import com.example.common.enums.UserType;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.CommonMessage;

import java.util.List;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author hzp
 * @since 2020-11-03
 */
public interface CommonMessageService extends IService<CommonMessage> {

    /**
     * 功能描述: 获取未读取的消息
     *
     * @param receiveUserType
     * @param id
     * @param unreade
     * @Return java.util.List<com.example.mybatis.entity.CommonMessage>
     * @Author 忆惜
     * @Date 2020/11/3 16:19
     */
    List<CommonMessage> findByReceiveUserTypeAndReceiveUserIdAndMessageStatus(UserType receiveUserType, String id, MessageStatus unreade);

    /**
     * 功能描述: 新增消息
     *
     * @param commonMessageObj 消息信息
     * @Return com.example.common.util.ReturnJson
     * @Author 忆惜
     * @Date 2020/11/3 15:00
     */
    ReturnJson saveMessage(CommonMessage commonMessageObj);

    /**
     * 功能描述: 获取未读消息数
     *
     * @param id 消息接收者的ID
     * @Return com.example.common.util.ReturnJson
     * @Author 忆惜
     * @Date 2020/11/3 15:02
     */
    ReturnJson queryUnreade(Long id);
}
