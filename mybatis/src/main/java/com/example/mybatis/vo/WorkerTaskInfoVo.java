package com.example.mybatis.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description
 * @Author JWei <jwei0401@163.com>
 * @Date 2020/11/23
 */
@Data
public class WorkerTaskInfoVo {

    /**
     * 任务编号
     */
    private String taskCode;

    /**
     * 创客名称
     */
    private String accountName;

    /**
     * 发包方名称
     */
    private String companyName;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务金额
     */
    private BigDecimal taskAmount;

    /**
     * 任务状态
     */
    private Integer taskStatus;

    /**
     * 接单时间
     */
    private LocalDateTime createDate;

    /**
     * 完成时间
     */
    private LocalDateTime checkDate;
}
