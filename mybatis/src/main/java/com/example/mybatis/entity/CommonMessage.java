package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.enums.MessageStatus;
import com.example.common.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author hzp
 * @since 2020-11-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_common_message")
public class CommonMessage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 消息
     */
    private String message;

    /**
     * 消息状态：0未读，1已读
     */
    private MessageStatus messageStatus;

    /**
     * 支付订单号（可以是连连支付的订单号）
     */
    private String noOrder;

    /**
     * 发送方ID(0为系统消息)
     */
    private String sendUserId;

    /**
     * 发送方用户类型
     */
    private UserType sendUserType;

    /**
     * 接收方ID
     */
    private String receiveUserId;

    /**
     * 接收方用户类型
     */
    private UserType receiveUserType;

}
