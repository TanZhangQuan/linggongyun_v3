package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author hzp
 * @since 2020-09-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_company_tax")
public class CompanyTax implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;


    private String companyId;

    private String taxId;

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

    private LocalDateTime createDate;

    private LocalDateTime updateDate;


}
