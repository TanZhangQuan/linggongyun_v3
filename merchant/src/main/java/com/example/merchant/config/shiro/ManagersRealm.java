package com.example.merchant.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.util.UserUtils;
import com.example.mybatis.entity.Managers;
import com.example.mybatis.entity.Menu;
import com.example.mybatis.entity.Merchant;
import com.example.mybatis.entity.MerchantRole;
import com.example.mybatis.mapper.ManagersDao;
import com.example.mybatis.mapper.MerchantRoleDao;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class ManagersRealm extends AuthorizingRealm {

    private static final String MANAGER = "MANAGER";

    {
        super.setName("manager");//设置realm的名字，非常重要
    }

    @Resource
    private ManagersDao managersDao;

    @Resource
    private MerchantRoleDao merchantRoleDao;

    /**
     * 授权逻辑
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("执行授权逻辑");
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String loginName = (String) principals.getPrimaryPrincipal();//获取登录用户
        System.out.println("当前的用户--------------Manager-----------------" + loginName);

        Managers managers = managersDao.selectOne(new QueryWrapper<Managers>().eq("user_name", loginName));//查询登录用户的角色

        MerchantRole merchantRole = merchantRoleDao.selectById(managers.getRoleId());
        Set<String> permissions =  merchantRoleDao.getMenuById(merchantRole.getId());
        authorizationInfo.addRole(merchantRole.getRoleName());//添加角色
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
        log.info("执行认证逻辑-----------------------Managers---doGetAuthenticationInfo");
        //获取用户的输入的账号

        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());

        String realmName = getName();
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                username, //用户名
                password, //密码
                realmName  //realm name
        );
        return authenticationInfo;
    }

}
