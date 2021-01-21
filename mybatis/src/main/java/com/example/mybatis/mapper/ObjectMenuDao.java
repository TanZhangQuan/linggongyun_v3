package com.example.mybatis.mapper;

import com.example.mybatis.entity.ObjectMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.vo.RoleMenuPassVO;
import com.example.mybatis.vo.RoleMenuVO;
import com.example.mybatis.vo.QueryPassRolemenuVO;

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

    List<RoleMenuVO> getRolemenu(String userId);

    List<RoleMenuPassVO> getPassRolemenu(String userId);

    QueryPassRolemenuVO queryPassRolemenu(String userId);

    Integer deleteMenu(String managerId);

    List<String> queryMenuByUserId(String userId);
}
