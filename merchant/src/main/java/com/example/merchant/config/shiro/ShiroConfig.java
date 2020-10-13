package com.example.merchant.config.shiro;

import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {

    private Environment environment;

    @Bean
    public Realm merchantRealm() {
        MerchantRealm merchant = new MerchantRealm();
        merchant.setName("MERCHANT");
        return merchant;
    }

    @Bean
    public Realm managerRealm() {
        ManagersRealm managers = new ManagersRealm();
        managers.setName("MANAGERS");
        return managers;
    }

    /**
     * Filter工厂，设置对应的过滤条件和跳转条件
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/");
        //anon 放行;authc,需要权限验证
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        filterChainDefinitionMap.put("/swagger/**", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/wsocket/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/chat/**", "anon");
        filterChainDefinitionMap.put("/application/**", "anon");
        filterChainDefinitionMap.put("/common/**", "anon");
        filterChainDefinitionMap.put("/static/image/**", "anon");
        filterChainDefinitionMap.put("/merchant/merchant/login", "anon");
        filterChainDefinitionMap.put("/platform/managers/passLogin", "anon");

        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    /**
     * 设置会话管理器
     *
     * @return
     */
    @Bean
    public SessionManager sessionManager() {
        WebSessionManager mySessionManager = new WebSessionManager();
        mySessionManager.setSessionValidationSchedulerEnabled(true);
        mySessionManager.setSessionIdCookieEnabled(true);
        return mySessionManager;
    }

    /**
     * 配置核心安全事务管理器
     *
     * @return securityManager
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setAuthenticator(modularRealmAuthenticator());
        List<Realm> realms = new ArrayList<>();
        realms.add(merchantRealm());
        realms.add(managerRealm());
        securityManager.setRealms(realms);//关联realm
        securityManager.setSessionManager(sessionManager());//关联session管理器
        return securityManager;
    }

    /**
     * 系统自带的Realm管理，主要针对多realm
     */
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator() {
        //自己重写的ModularRealmAuthenticator
        CustomizedModularRealmAuthenticator modularRealmAuthenticator = new CustomizedModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }


    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     *
     * @return
     */
    @Bean(name = "defaultAdvisorAutoProxyCreator")
    public static DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
     * 开启aop注解支持
     *
     * @param securityManager
     * @return
     */
    @Bean(name = "authorizationAttributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
