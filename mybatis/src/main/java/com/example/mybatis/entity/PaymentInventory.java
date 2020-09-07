package com.example.mybatis.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 支付清单明细

 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_payment_inventory")
public class PaymentInventory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 支付清单ID
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 支付单ID
     */
    private String paymentOrderId;

    /**
     * 创客ID
     */
    private String workerId;

    /**
     * 创客姓名
     */
    private String workerName;

    /**
     * 创客电话
     */
    private String mobileCode;

    /**
     * 创客身份证号码
     */
    private String idCardCode;

    /**
     * 创客的银行账号
     */
    private String bankCode;

    /**
     * 创客的实际到手的金额
     */
    private BigDecimal realMoney;

    /**
     * 服务费
     */
    private Integer serviceMoney;

    /**
     * 综合税率
     */
    private BigDecimal compositeTax;

    /**
     * 商户支付金额
     */
    private BigDecimal merchantPaymentMoney;

    /**
     * 实名认证状态(0未认证，1已认证)
     */
    private Integer attestation;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    private LocalDateTime updateDate;


}
