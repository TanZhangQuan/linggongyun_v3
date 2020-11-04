package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.enums.MessageStatus;
import com.example.common.enums.UserType;
import com.example.common.util.ReturnJson;
import com.example.merchant.service.CommonMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatis.entity.CommonMessage;
import com.example.mybatis.mapper.CommonMessageDao;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-11-03
 */
@Service
public class CommonMessageServiceImpl extends ServiceImpl<CommonMessageDao, CommonMessage> implements CommonMessageService {

    @Override
    public List<CommonMessage> findByReceiveUserTypeAndReceiveUserIdAndMessageStatus(UserType receiveUserType, String id, MessageStatus unreade) {
        return this.list(new QueryWrapper<CommonMessage>().lambda().eq(CommonMessage::getReceiveUserId, id).eq(CommonMessage::getMessageStatus, unreade).eq(CommonMessage::getReceiveUserType, receiveUserType));
    }

    @Override
    public ReturnJson saveMessage(CommonMessage commonMessageObj) {
        this.saveOrUpdate(commonMessageObj);
        return ReturnJson.success("新增成功！");
    }

    @Override
    public ReturnJson queryUnreade(Long id) {
        int count = this.count(new QueryWrapper<CommonMessage>().lambda().eq(CommonMessage::getReceiveUserId, id).eq(CommonMessage::getMessageStatus, MessageStatus.UNREADE));
        return ReturnJson.success("查询成功！", count);
    }
}
