package com.example.mybatis.entity;

<<<<<<< HEAD
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
=======
import com.baomidou.mybatisplus.annotation.*;
>>>>>>> e313d3f739bfa1db8fe37f7b824cc242965cb147
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_worker_task")
public class WorkerTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String workerId;

    private String taskId;

    /**
     * 0表示接单成功，1表示被剔除
     */
    private Integer taskStatus;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;

    /**
     * 获得方式1,抢单获得；2，派单获得
     */
    private Integer getType;

    /**
     *工作成果说明
     */
    private String achievementDesc;

    /**
     * 工作成果附件,可以多个文件
     */
    private String achievementFiles;

    /**
     * 提交工作成果日期
     */
    private LocalDateTime achievementDate;

    /**
     * 验收金额
     */
    private Double checkMoney;

    /**
     * 验收人员
     */
    private String checkPerson;

    /**
     * 验收日期
     */
    private LocalDateTime checkDate;

    /**
     * 派单人员
     */
    private String arrangePerson;

}
