package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.util.MD5;
import com.example.common.util.ReturnJson;
import com.example.merchant.dto.merchant.MerchantDto;
import com.example.merchant.dto.platform.SaveManagersRoleDto;
import com.example.mybatis.entity.*;
import com.example.mybatis.mapper.*;
import com.example.merchant.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mybatis.vo.MenuListVo;
import com.example.mybatis.vo.QueryPassRolemenuVo;
import com.example.mybatis.vo.RoleMenuPassVo;
import com.example.mybatis.vo.RoleMenuVo;
import com.example.redis.dao.RedisDao;
import org.springframework.beans.BeanUtils;
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
    private RedisDao redisDao;

    @Resource
    private MenuDao menuDao;

    @Resource
    private ObjectMenuDao objectMenuDao;

    @Resource
    private MerchantDao merchantDao;

    @Resource
    private ManagersDao managersDao;

    @Override
    public ReturnJson getMenuList() {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        List<MenuListVo> listVos = menuDao.getMenuList();
        if (listVos != null && listVos.size() != 0) {
            returnJson = new ReturnJson("查询成功", listVos, 200);
        }
        return returnJson;
    }

    @Override
    public ReturnJson getPlatformMenuList() {
        ReturnJson returnJson = new ReturnJson("查询失败", 300);
        List<MenuListVo> listVos = menuDao.getPlatformMenuList();
        if (listVos != null && listVos.size() != 0) {
            returnJson = new ReturnJson("查询成功", listVos, 200);
        }
        return returnJson;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson saveRole(MerchantDto merchantDto, String merchantId) {
        String token = redisDao.get(merchantId);
        if (token == null) {
            return ReturnJson.error("请先登录");
        }
        int count = merchantDao.selectCount(new QueryWrapper<Merchant>().eq("parent_id", merchantId));
        if (count == 3) {
            return ReturnJson.error("子账户达到上限");
        }
        Merchant merchant = merchantDao.selectById(merchantDto.getId());

        if ("".equals(merchantDto.getId())) {
            Merchant merchant1=merchantDao.selectById(merchantId);
            merchant = new Merchant();
            BeanUtils.copyProperties(merchantDto, merchant);
            merchant.setParentId(merchantId);
            merchant.setCompanyId(merchant1.getCompanyId());
            merchant.setCompanyName(merchant1.getCompanyName());
            merchant.setPassWord(PWD_KEY + MD5.md5(merchantDto.getPassWord()));
            int m = merchantDao.insert(merchant);
            if (m > 0) {
                String[] meunId = merchantDto.getMenuIds().split(",");
                for (int i = 0; i < meunId.length; i++) {
                    ObjectMenu roleMenu = new ObjectMenu();
                    roleMenu.setObjectUserId(merchant.getId());
                    roleMenu.setMenuId(meunId[i]);
                    objectMenuDao.insert(roleMenu);
                }
            }
            return ReturnJson.success("添加成功");
        } else {
            BeanUtils.copyProperties(merchantDto, merchant);
            merchant.setParentId(merchantId);
            merchant.setPassWord(PWD_KEY + MD5.md5(merchantDto.getPassWord()));
            merchantDao.updateById(merchant);
            objectMenuDao.delete(new QueryWrapper<ObjectMenu>().eq("object_user_id", merchant.getId()));
            String[] meunId = merchantDto.getMenuIds().split(",");
            for (int i = 0; i < meunId.length; i++) {
                ObjectMenu roleMenu = new ObjectMenu();
                roleMenu.setObjectUserId(merchant.getId());
                roleMenu.setMenuId(meunId[i]);
                objectMenuDao.insert(roleMenu);
            }
            return ReturnJson.success("修改成功");
        }
    }

    @Override
    public ReturnJson getAllRole(String merchantId) {
        if (merchantId == null) {
            return ReturnJson.error("merchantId不能为空");
        }
        List<RoleMenuVo> list = objectMenuDao.getRolemenu(merchantId);
        return ReturnJson.success(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson daleteRole(String merchantId) {
        objectMenuDao.deleteMenu(merchantId);
        merchantDao.deleteById(merchantId);
        return ReturnJson.success("操作成功");
    }

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson savePlatRole(SaveManagersRoleDto saveManagersRoleDto, String managersId) {

        Managers managersOne = managersDao.selectOne(new QueryWrapper<Managers>().eq("mobile_code",
                saveManagersRoleDto.getMobileCode()));
        if (managersOne != null) {
            return ReturnJson.error("此手机号码已近注册过！");
        }
        Managers managers = new Managers();
        BeanUtils.copyProperties(saveManagersRoleDto, managers);
        managers.setParentId(managersId);
        managers.setUserSign(3);
        managers.setPassWord(PWD_KEY + MD5.md5(saveManagersRoleDto.getPassWord()));
        if ("".equals(saveManagersRoleDto.getId())) {
            managersDao.insert(managers);
            String[] menuId = saveManagersRoleDto.getMenuIds().split(",");
            for (int i = 0; i < menuId.length; i++) {
                ObjectMenu roleMenu = new ObjectMenu();
                roleMenu.setObjectUserId(managers.getId());
                roleMenu.setMenuId(menuId[i]);
                objectMenuDao.insert(roleMenu);
            }
            return ReturnJson.success("添加成功");
        } else {
            managersDao.updateById(managers);
            objectMenuDao.delete(new QueryWrapper<ObjectMenu>().eq("object_user_id", managers.getId()));
            String[] menuId = saveManagersRoleDto.getMenuIds().split(",");
            for (int i = 0; i < menuId.length; i++) {
                ObjectMenu roleMenu = new ObjectMenu();
                roleMenu.setObjectUserId(managers.getId());
                roleMenu.setMenuId(menuId[i]);
                objectMenuDao.insert(roleMenu);
            }
            return ReturnJson.success("修改成功");
        }
    }

    @Override
    public ReturnJson getPassAllRole(String managersId) {
        if (managersId == null) {
            return ReturnJson.error("managersId不能为空");
        }
        List<RoleMenuPassVo> list = objectMenuDao.getPassRolemenu(managersId);
        return ReturnJson.success(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnJson daletePassRole(String managersId) {
        objectMenuDao.deleteMenu(managersId);
        managersDao.deleteById(managersId);
        return ReturnJson.success("操作成功");
    }

    @Override
    public ReturnJson updataPassRoleStatus(String managersId, Integer status) {
        Managers managers = new Managers();
        managers.setId(managersId);
        managers.setStatus(status);
        int num = managersDao.updateById(managers);
        if (num > 0) {
            return ReturnJson.success("操作成功");
        }
        return ReturnJson.error("操作失败");
    }

    @Override
    public ReturnJson getManagersInfo(String managersId) {
        QueryPassRolemenuVo roleMenuPassVo = objectMenuDao.queryPassRolemenu(managersId);
        return ReturnJson.success(roleMenuPassVo);
    }

    @Override
    public ReturnJson queryMerchantMeun(String userId) {
        if (userId == null) {
            return ReturnJson.error("merchantId不能为空");
        }
        List<RoleMenuVo> list = objectMenuDao.getRolemenu(userId);
        RoleMenuVo roleMenuVo=list.get(0);
        return ReturnJson.success(roleMenuVo);
    }


}
