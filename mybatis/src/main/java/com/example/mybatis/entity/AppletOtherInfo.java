package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.example.common.enums.AppletOtherType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 小程序其他问题
 * </p>
 *
 * @author xjw
 * @since 2021-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_applet_other_info")
public class AppletOtherInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 项目名称
     */
    private String entryName;

    /**
     * 内容
     */
    private String content;

    /**
     * 上传类型
     */
    private AppletOtherType type;

}
