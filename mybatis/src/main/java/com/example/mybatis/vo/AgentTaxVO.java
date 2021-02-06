package com.example.mybatis.vo;

import com.example.mybatis.entity.AgentLadderService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author .
 * @date 2021/2/2.
 * @time 21:07.
 */
@Data
public class AgentTaxVO implements Serializable {
    /**
     * 服务商id
     */
    private String taxId;
    /**
     * 代理商
     */
    private String agentId;
    /**
     * 费用类型0为一口价，1为梯度价
     */
    private Integer chargeStatus;
    /**
     * 一口价的服务费(如果为梯度价这为空)
     */
    private BigDecimal serviceCharge;
    /**
     * 合作类型
     */
    private Integer packageStatus;
    /**
     * 合作合同地址
     */
    private String contract;

    /**
     * 主键
     */
    private String id;

    /**
     * 用户创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDate;

    /**
     * 用户修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateDate;


    private List<AgentLadderService> agentLadderServices;
}
