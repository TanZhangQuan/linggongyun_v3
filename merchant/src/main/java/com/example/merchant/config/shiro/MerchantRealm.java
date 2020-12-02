package com.example.merchant.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatis.entity.Merchant;
import com.example.mybatis.mapper.ManagersDao;
import com.example.mybatis.mapper.MerchantDao;
import com.example.mybatis.mapper.ObjectMenuDao;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 授权相关服务-shiro
 *
 * @author
 * @since 1.0.0
 */
@Slf4j
public class MerchantRealm extends AuthorizingRealm {

    private static final String MANAGER = "MERCHANT";

    {
        super.setName("merchant");//设置realm的名字，非常重要
    }


    @Resource
    private MerchantDao merchantDao;

    @Resource
    private ObjectMenuDao objectMenuDao;


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
        System.out.println("当前的用户--------------Merchant-----------------" + loginName);
        Merchant user = merchantDao.selectOne(new QueryWrapper<Merchant>().eq("user_name", loginName));//查询登录用户的角色

        Set<String> permissions =  objectMenuDao.getMenuById(user.getId());
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
        log.info("执行认证逻辑---------------------------Merchant----doGetAuthenticationInfo");
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
