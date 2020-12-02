package com.example.mybatis.mapper;

import com.example.mybatis.entity.ObjectMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.vo.RoleMenuPassVo;
import com.example.mybatis.vo.RoleMenuVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface ObjectMenuDao extends BaseMapper<ObjectMenu> {

    Set<String> getMenuById(String userId);

    List<RoleMenuVo> getRolemenu(String userId);

    List<RoleMenuPassVo> getPassRolemenu(String userId);
}
