package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * tb_crowd_sourcing_application
 *
 * @author
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_crowd_sourcing_application")
public class CrowdSourcingApplication extends BaseEntity {
    private static final long serialVersionUID = 1L;

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

}