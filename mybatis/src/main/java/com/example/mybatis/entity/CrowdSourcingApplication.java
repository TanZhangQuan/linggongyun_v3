package com.example.mybatis.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * tb_crowd_sourcing_application
 * @author 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_crowd_sourcing_application")
public class CrowdSourcingApplication implements Serializable {
    /**
     * 众包开票申请id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 众包支付id
     */
    private String paymentOrderManyId;

    /**
     * 开票类目
     */
    private String invoiceCatalogType;

    /**
     * 申请地址 对应地址表id
     */
    private String applicationAddressId;

    /**
     * 申请时间
     */
    private Date applicationDate;

    /**
     * 申请状态：0.未申请；1.申请中；2.已拒绝；3.已开票，4未开票
     */
    private Integer applicationState;

    /**
     * 申请说明
     */
    private String applicationDesc;

    /**
     * 审核说明
     */
    private String auditDesc;

    private static final long serialVersionUID = 1L;
}