package com.example.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatis.dto.RegulatorTaxDto;
import com.example.mybatis.entity.RegulatorTax;
import com.example.mybatis.entity.Tax;
import com.example.mybatis.vo.TaxVo;
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

    IPage<TaxVo> selServiceProviders(Page page,@Param("tax") RegulatorTaxDto regulatorTaxDto);

    List<TaxVo> selTaxListByIds(@Param("ids") List<String> ids);
}
