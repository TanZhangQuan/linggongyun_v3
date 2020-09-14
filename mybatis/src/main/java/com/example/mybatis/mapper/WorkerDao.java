package com.example.mybatis.mapper;

import com.example.mybatis.entity.Worker;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 创客表 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface WorkerDao extends BaseMapper<Worker> {
    List<Worker> selectByIdAndAccountNameAndMobile(@Param("merchantId") String merchantId, @Param("id") String id, @Param("accountName")String accountName, @Param("mobileCode") String mobileCode);
}
