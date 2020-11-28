package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 创客绑定的银行卡号
 * </p>
 *
 * @author hzp
 * @since 2020-09-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_worker_bank")
public class WorkerBank extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 创客ID
     */
    private String workerId;

    /**
     * 户名
     */
    private String realName;

    /**
     * 开户行
     */
    private String bankName;

    /**
     * 银行卡号
     */
    private String bankCode;

    /**
     * 优先度（越小越先）
     */
    private Integer sort;

}
