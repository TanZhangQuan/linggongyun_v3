package com.example.mybatis.mapper;

import com.example.mybatis.entity.InvoiceCatalog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-21
 */
public interface InvoiceCatalogDao extends BaseMapper<InvoiceCatalog> {
    /**
     * 根据商户id查找商户所能开的开票类目
     *
     * @param id
     * @return
     */
    List<InvoiceCatalog> getListInv(List<String> id);
}
