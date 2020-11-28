package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author hzp
 * @since 2020-09-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_company_worker")
public class CompanyWorker extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 创客id
     */
    private String workerId;

    /**
     * 商户ID
     */
    private String companyId;

}
