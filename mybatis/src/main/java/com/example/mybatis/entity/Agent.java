package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_agent")
public class Agent extends BaseEntity {
    private static final long serialVersionUID = 1L;

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

}
