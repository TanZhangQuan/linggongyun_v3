package com.example.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
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
@TableName("tb_company_worker")
public class CompanyWorker implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 创客id
     */
    @TableId(value = "worker_id", type = IdType.ASSIGN_ID)
    private String workerId;

    /**
     * 商户ID
     */
    private String companyId;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    private LocalDateTime updateDate;


}
