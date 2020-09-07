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
 * 发票相关
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tb_invoice")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 商户名称
     */
    private String merchantsName;

    /**
     * 支付编号
     */
    private String payCode;

    /**
     * 支付清单
     */
    private String payDetailedAccount;

    /**
     * 总包支付回单
     */
    private String gcPayReceipt;

    /**
     * 众包支付回单
     */
    private String cPaymentReceipt;

    /**
     * 总包发票
     */
    private String gcInvoice;

    /**
     * 众包发票
     */
    private String cInvoice;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    private LocalDateTime updateDate;


}
