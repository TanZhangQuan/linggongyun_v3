package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.util.MD5;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.MerchantDto;
import com.example.mybatis.entity.Menu;
import com.example.mybatis.entity.Merchant;
import com.example.mybatis.entity.MerchantRole;
import com.example.mybatis.entity.MerchantRoleMenu;
import com.example.mybatis.mapper.MenuDao;
import com.example.merchant.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatis.mapper.MerchantDao;
import com.example.mybatis.mapper.MerchantRoleDao;
import com.example.mybatis.mapper.MerchantRoleMenuDao;
import com.example.mybatis.vo.MenuListVo;
import com.example.mybatis.vo.RoleMenuVo;
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
    @Autowired
    private MerchantDao merchantDao;

    /**
     * 商户端查询所有的权限列表
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
     * 平台端端查询所有的权限列表
     *
     * @return
     */
    @Override
    public ReturnJson getPlatformMenuList() {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        List<MenuListVo> listVos = menuDao.getPlatformMenuList();
        if (listVos != null && listVos.size() != 0) {
            returnJson = new ReturnJson("查询成功", listVos, 200);
        }
        return returnJson;
    }

    /**
     * 添加子账户
     *
     * @param merchantDto
     * @return
     */
    @Override
    @Transactional
    public ReturnJson saveRole(MerchantDto merchantDto) {
        if (merchantDto.getMerchantId() == null) {
            return ReturnJson.error("请先登录");
        }
        int count = merchantDao.selectCount(new QueryWrapper<Merchant>().eq("parent_id", merchantDto.getMerchantId()));
        if (count == 3) {
            return ReturnJson.error("子账户达到上限");
        }
        MerchantRole merchantRole = new MerchantRole();
        merchantRole.setRoleName(merchantDto.getRoleNmae());
        merchantRole.setRolePosition(merchantDto.getRolePosition());
        merchantRole.setCreateDate(LocalDateTime.now());
        int ins = merchantRoleDao.insert(merchantRole);
        if (ins > 0) {
            String[] meunId = merchantDto.getMenuIds().split(",");
            for (int i = 0; i < meunId.length; i++) {
                MerchantRoleMenu roleMenu = new MerchantRoleMenu();
                roleMenu.setMerchantRoleId(merchantRole.getId());
                roleMenu.setMenuId(meunId[i]);
                merchantRoleMenuDao.insert(roleMenu);
            }
        }
        if (ins > 0) {
            Merchant merchantParent = merchantDao.selectById(merchantDto.getMerchantId());
            Merchant merchant = new Merchant();
            merchant.setRealName(merchantDto.getRealName());
            merchant.setParentId(merchantDto.getMerchantId());
            merchant.setLoginMobile(merchantDto.getMobileCode());
            merchant.setPassWord(PWD_KEY + MD5.md5(merchantDto.getPassWord()));
            merchant.setRoleId(merchantRole.getId());
            merchant.setCompanyId(merchantParent.getCompanyId());
            merchant.setCompanyName(merchantParent.getCompanyName());
            merchant.setCreateDate(LocalDateTime.now());
            merchantDao.insert(merchant);
            return ReturnJson.success("操作成功");
        }
        return ReturnJson.error("操作失败");
    }

    /**
     * 修改子用户
     *
     * @param merchantDto
     * @return
     */
    @Override
    @Transactional
    public ReturnJson updateRole(MerchantDto merchantDto) {
        MerchantRole merchantRole = new MerchantRole();
        merchantRole.setId(merchantDto.getRoleId());
        merchantRole.setRoleName(merchantDto.getRoleNmae());
        merchantRole.setRolePosition(merchantDto.getRolePosition());
        merchantRole.setUpdateDate(LocalDateTime.now());
        int num = merchantRoleDao.updateById(merchantRole);
        if (num > 0) {
            merchantRoleMenuDao.delete(new QueryWrapper<MerchantRoleMenu>().eq("merchant_role_id", merchantDto.getRoleId()));
            String[] meunId = merchantDto.getMenuIds().split(",");
            for (int i = 0; i < meunId.length; i++) {
                MerchantRoleMenu roleMenu = new MerchantRoleMenu();
                roleMenu.setMerchantRoleId(merchantRole.getId());
                roleMenu.setMenuId(meunId[i]);
                merchantRoleMenuDao.insert(roleMenu);
            }

            Merchant merchant = new Merchant();
            merchant.setId(merchantDto.getMerchantId());
            merchant.setRealName(merchantDto.getRealName());
            merchant.setParentId(merchantDto.getMerchantId());
            merchant.setLoginMobile(merchantDto.getMobileCode());
            merchant.setPassWord(PWD_KEY + MD5.md5(merchantDto.getPassWord()));
            merchant.setUpdateDate(LocalDateTime.now());
            merchantDao.updateById(merchant);
            return ReturnJson.success("操作成功");
        }
        return ReturnJson.error("操作失败");
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
        List<RoleMenuVo> list = merchantRoleDao.getRolemenu(merchantId);
        return ReturnJson.success(list);
    }

    /**
     * 删除子账户
     *
     * @param merchantId
     * @return
     */
    @Override
    @Transactional
    public ReturnJson daleteRole(String merchantId) {
        Merchant merchant=merchantDao.selectById(new QueryWrapper<Merchant>().eq("id",merchantId));
        merchantRoleMenuDao.delete(new QueryWrapper<MerchantRoleMenu>().eq("merchant_role_id",merchant.getRoleId()));
        merchantRoleDao.deleteById(merchant.getRoleId());
        merchantDao.deleteById(merchantId);
        return ReturnJson.error("操作成功");
    }

    /**
     * 启用或停用子账户
     *
     * @param status
     * @return
     */
    @Override
    public ReturnJson updataRoleStatus(String merchantId, Integer status) {
        Merchant merchant = new Merchant();
        merchant.setId(merchantId);
        merchant.setStatus(status);
        int num = merchantDao.updateById(merchant);
        if (num > 0) {
            return ReturnJson.success("操作成功");
        }
        return ReturnJson.error("操作失败");
    }
}
