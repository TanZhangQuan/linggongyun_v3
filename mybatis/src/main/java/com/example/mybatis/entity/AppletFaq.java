package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 小程序常见问题
 * </p>
 *
 * @author xjw
 * @since 2021-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_applet_faq")
public class AppletFaq extends BaseEntity{

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    private Integer serialNumber;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

}
