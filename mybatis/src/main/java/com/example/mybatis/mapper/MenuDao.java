package com.example.mybatis.mapper;

import com.example.mybatis.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.vo.MenuListVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface MenuDao extends BaseMapper<Menu> {

    List<MenuListVo> getMenuList();

    List<MenuListVo> getPlatformMenuList();
}
