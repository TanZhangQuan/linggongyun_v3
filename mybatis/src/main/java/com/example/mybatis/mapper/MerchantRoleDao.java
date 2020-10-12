package com.example.mybatis.mapper;

import com.example.mybatis.entity.Menu;
import com.example.mybatis.entity.Merchant;
import com.example.mybatis.entity.MerchantRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mybatis.entity.MerchantRoleMenu;
import com.example.mybatis.vo.RoleMenuVo;

import java.util.List;

/**
 * <p>
 * 商户角色信息
 Mapper 接口
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
public interface MerchantRoleDao extends BaseMapper<MerchantRole> {

    List<Menu> getMenuById(String MerchantId);

    List<RoleMenuVo> getRolemenu(String merchantId);

    List<RoleMenuVo> getPassRolemenu(String managersId);
}
