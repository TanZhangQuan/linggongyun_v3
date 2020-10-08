package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.util.MD5;
import com.example.common.util.ReturnJson;
import com.example.mybatis.entity.Menu;
import com.example.mybatis.entity.Merchant;
import com.example.mybatis.entity.MerchantRole;
import com.example.mybatis.entity.MerchantRoleMenu;
import com.example.mybatis.mapper.MenuDao;
import com.example.merchant.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatis.mapper.MerchantRoleDao;
import com.example.mybatis.mapper.MerchantRoleMenuDao;
import com.example.mybatis.vo.MenuListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-07
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {

    @Value("${PWD_KEY}")
    private String PWD_KEY;

    @Resource
    private MenuDao menuDao;
    @Autowired
    private MerchantRoleDao merchantRoleDao;
    @Autowired
    private MerchantRoleMenuDao merchantRoleMenuDao;

    /**
     * 查询所有的权限列表
     *
     * @return
     */
    @Override
    public ReturnJson getMenuList() {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        List<MenuListVo> listVos = menuDao.getMenuList();
        if (listVos != null && listVos.size() != 0) {
            returnJson = new ReturnJson("查询成功", listVos, 200);
        }
        return returnJson;
    }

    /**
     * 添加子账户
     *
     * @param merchantRole
     * @return
     */
    @Override
    @Transactional
    public ReturnJson saveRole(MerchantRole merchantRole, String menuIds) {
        if (merchantRole.getMerchantId() == null) {
            return ReturnJson.error("请先登录");
        }
        int count = merchantRoleDao.selectCount(new QueryWrapper<MerchantRole>().eq("merchant_id", merchantRole.getId()));
        if (count == 4) {
            return ReturnJson.error("只能拥有三个子账户");
        }
        if (StringUtils.isEmpty(menuIds)) {
            return ReturnJson.error("没有给子账户赋予权限");
        }
        String encryptPWD = PWD_KEY + MD5.md5(merchantRole.getLoginPassword());
        merchantRole.setLoginPassword(encryptPWD);
        merchantRole.setStatus(1);
        merchantRole.setCreateDate(LocalDateTime.now());
        int num = merchantRoleDao.insert(merchantRole);
        if (num > 0) {
            String[] menuId = menuIds.split(",");
            for (int i = 0; i < menuId.length; i++) {
                MerchantRoleMenu roleMenu = new MerchantRoleMenu();
                roleMenu.setMenuId(menuId[i]);
                roleMenu.setMerchantRoleId(merchantRole.getId());
                merchantRoleMenuDao.insert(roleMenu);
                return ReturnJson.success("账户添加成功");
            }
        }
        return ReturnJson.error("系统出现异常，添加失败");
    }

    /**
     * 修改子用户
     *
     * @param merchantRole
     * @param menuIds
     * @return
     */
    @Override
    public ReturnJson updateRole(MerchantRole merchantRole, String menuIds) {
        if (StringUtils.isEmpty(menuIds)) {
            return ReturnJson.error("没有给子账户赋予权限");
        }
        String[] menuId = menuIds.split(",");
        merchantRole.setUpdateDate(LocalDateTime.now());
        int num = merchantRoleDao.updateById(merchantRole);
        if (num > 0) {
            List<MerchantRoleMenu> menuList = merchantRoleMenuDao.selectList(new QueryWrapper<MerchantRoleMenu>().eq("merchant_role_id", merchantRole.getId()));
            for (int i = 0; i < menuList.size(); i++) {
                merchantRoleMenuDao.deleteById(menuList.get(i).getId());
            }
            for (int i = 0; i < menuId.length; i++) {
                MerchantRoleMenu menu = new MerchantRoleMenu();
                menu.setMenuId(menuId[i]);
                menu.setMerchantRoleId(merchantRole.getId());
                merchantRoleMenuDao.insert(menu);
            }
            return ReturnJson.success("修改成功");
        }
        return ReturnJson.error("服务器或者系统异常");
    }

    /**
     * 查看所有的账户权限
     *
     * @param merchantId
     * @return
     */
    @Override
    public ReturnJson getAllRole(String merchantId) {
        if (merchantId == null) {
            return ReturnJson.error("merchantId不能为空");
        }
        List<MerchantRole> list = merchantRoleDao.getRolemenu(merchantId);
        return ReturnJson.success(list);
    }

    /**
     * 删除子账户
     *
     * @param merchantRoleId
     * @return
     */
    @Override
    public ReturnJson daleteRole(String merchantRoleId) {
        List<MerchantRoleMenu> menuList = merchantRoleMenuDao.selectList(new QueryWrapper<MerchantRoleMenu>().eq("merchant_role_id", merchantRoleId));
        for (int i = 0; i < menuList.size(); i++) {
            merchantRoleMenuDao.deleteById(menuList.get(i).getId());
        }
        int num = merchantRoleDao.deleteById(merchantRoleId);
        if (num > 0) {
            return ReturnJson.success("删除成功");
        }
        return ReturnJson.error("服务器或者系统异常");
    }

    /**
     * 启用或停用子账户
     *
     * @param status
     * @return
     */
    @Override
    public ReturnJson updataRoleStatus(String merchantRoleId, Integer status) {
        MerchantRole merchantRole = new MerchantRole();
        merchantRole.setId(merchantRoleId);
        merchantRole.setStatus(status);
        int num = merchantRoleDao.updateById(merchantRole);
        if (num > 0) {
            return ReturnJson.success("修改成功");
        }
        return ReturnJson.error("服务器或者系统异常");
    }
}
