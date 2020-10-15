package com.example.common.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;


public class UserUtils {
    private static Session getSession(){
        return SecurityUtils.getSubject().getSession();
    }

    public static Object getCurrentUser(){
        return getSession().getAttribute(CommonConstants.USERINFO);
    }

    public static void setCurrentUser(Object currentUser){
        getSession().setAttribute(CommonConstants.USERINFO, currentUser);
    }

    public static void clearCachedAuthorizationInfo() {
        getSession().removeAttribute(CommonConstants.USERINFO);
        SecurityUtils.getSubject().logout();
    }
}
