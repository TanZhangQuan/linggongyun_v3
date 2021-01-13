package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 小程序轮播图
 * </p>
 *
 * @author xjw
 * @since 2021-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_applet_banner")
public class AppletBanner extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    private Integer serialNumber;

    /**
     * 图片
     */
    private String picture;

}
