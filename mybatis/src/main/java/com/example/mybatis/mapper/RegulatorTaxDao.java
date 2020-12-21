package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.dto.RegulatorTaxDto;
import com.example.mybatis.entity.RegulatorTax;
import com.example.mybatis.vo.TaxVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 监管部门监管的服务商 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-25
 */
public interface RegulatorTaxDao extends BaseMapper<RegulatorTax> {

    IPage<TaxVO> selServiceProviders(Page page, @Param("tax") RegulatorTaxDto regulatorTaxDto, @Param("regulatorId") String regulatorId);

    List<TaxVO> selTaxListByIds(@Param("ids") List<String> ids);
}
