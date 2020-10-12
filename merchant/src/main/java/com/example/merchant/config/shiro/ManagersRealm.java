package com.example.merchant.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatis.entity.Managers;
import com.example.mybatis.entity.Menu;
import com.example.mybatis.entity.MerchantRole;
import com.example.mybatis.mapper.ManagersDao;
import com.example.mybatis.mapper.MerchantRoleDao;
import lombok.SneakyThrows;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ManagersRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(ManagersRealm.class);

    @Autowired
    private ManagersDao managersDao;
    @Autowired
    private MerchantRoleDao merchantRoleDao;

    /**
     * 授权逻辑
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("执行授权逻辑");
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String loginName = (String) principals.getPrimaryPrincipal();//获取登录用户
        System.out.println("当前的用户-------------------------------" + loginName);
        Managers managers = managersDao.selectOne(new QueryWrapper<Managers>().eq("user_name", loginName));//查询登录用户的角色
        MerchantRole merchantRole = merchantRoleDao.selectById(managers.getRoleId());
        List<Menu> menuList = merchantRoleDao.getMenuById(merchantRole.getId());
        authorizationInfo.addRole(merchantRole.getRoleName());//添加角色
        Set<String> permissions = new HashSet<>();
        for (int i = 0; i < menuList.size(); i++) {
            permissions.add(menuList.get(i).getMenuName());//添加权限
        }
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    /**
     * 认证逻辑
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @SneakyThrows
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("执行认证逻辑-----------------------Managers---doGetAuthenticationInfo");
        //获取用户的输入的账号

        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                username, //用户名
                password, //密码
                getName()  //realm name
        );
        return authenticationInfo;
    }
}
