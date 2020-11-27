package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_worker_task")
public class WorkerTask extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String workerId;

    private String taskId;

    /**
     * 0表示接单成功，1表示被剔除
     */
    private Integer taskStatus;

    /**
     * 获得方式1,抢单获得；2，派单获得
     */
    private Integer getType;

    /**
     * 工作成果说明
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

    /**
     * 创客完成状态
     */
    private Integer status;

}
