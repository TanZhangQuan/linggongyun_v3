package com.example.merchant.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatis.entity.Menu;
import com.example.mybatis.entity.Merchant;
import com.example.mybatis.entity.MerchantRole;
import com.example.mybatis.mapper.MerchantDao;
import com.example.mybatis.mapper.MerchantRoleDao;

import lombok.SneakyThrows;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 授权相关服务-shiro
 *
 * @author qiguliuxing
 * @since 1.0.0
 */
public class MerchantRealm extends org.apache.shiro.realm.AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(MerchantRealm.class);

    @Autowired
    private MerchantDao merchantDao;
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
        System.out.println("登录的用户-------------------------------"+loginName);
        Merchant merchant = merchantDao.selectOne(new QueryWrapper<Merchant>().eq("user_name", loginName));//查询登录用户的角色
        MerchantRole merchantRole = merchantRoleDao.selectById(merchant.getRoleId());
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
        logger.info("执行认证逻辑");
        //获取用户的输入的账号
        //redis鉴定，从redis取数据，不用频繁的冲db查询，如果redis数据被删除，过期，重新查询
        if (authenticationToken instanceof UsernamePasswordToken) {
            UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

            String username = (String) authenticationToken.getPrincipal();
            System.out.println(authenticationToken.getCredentials());
            //通过username从数据库中查找 merchant对象，如果找到，没找到.
            //这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
            Merchant merchant = merchantDao.selectOne(new QueryWrapper<Merchant>().eq("user_name", username));
            if (Objects.isNull(merchant)) {
                throw new AuthenticationException("账号或密码错误");
            }
            if (merchant.getStatus() == 1) {
                throw new LockedAccountException("账号已被禁用");
            }

            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    username, //用户名
                    merchant.getPassWord(), //密码
                    getName()  //realm name
            );
            return authenticationInfo;
        } else {
            logger.error("账号验证错误:{}", authenticationToken);
            throw new UnknownAccountException("账号验证错误");
        }
    }

}
