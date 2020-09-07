package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代理商信息

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
    @TableId(value = "id", type = IdType.UUID)
    private String id;

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
     * 公司名称
     */
    private String companyName;

    /**
     * 0可以用1禁用
     */
    private Integer agentStatus;

    /**
     * 代理商登录账号
     */
    private String agentUserName;

    /**
     * 代理商登录密码
     */
    private String agentPwd;

    /**
     * 代理商编码
     */
    private String agentCode;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    private LocalDateTime updateDate;


}
