package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 代理商信息
 *
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_agent")
public class Agent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 代理商ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 对应的管理人员
     */
    private String managersId;

    /**
     * 所属业务员
     */
    private String salesManId;

    /**
     * 代理商地址
     */
    private String companyAddress;

    /**
     * 联系人
     */
    private String linkMan;

    /**
     * 代理商电话
     */
    private String linkMobile;

    /**
     * 合同文件
     */
    private String contractFile;

    /**
     * 代理商名称名称
     */
    private String agentName;

    /**
     * 0可以用1禁用
     */
    private Integer agentStatus;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    /**
     * 用户修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;


}
