package com.example.merchant.config.shiro;

import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

public class CustomizedModularRealmAuthorizer extends ModularRealmAuthorizer {
    public static final String MANAGER = "manager";
    public static final CharSequence MERCHANT = "merchant";

    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        assertRealmsConfigured();
        Set<String> realmNames = principals.getRealmNames();
        //获取realm的名字
        String realmName = realmNames.iterator().next();
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)) {
                continue;
            }
            //匹配名字
            if ("manager".equals(realmName)) {
                if (realm instanceof ManagersRealm) {
                    return ((ManagersRealm) realm).isPermitted(principals, permission);
                }
            }
            if ("merchant".equals(realmName)) {
                if (realm instanceof MerchantRealm) {
                    return ((MerchantRealm) realm).isPermitted(principals, permission);
                }
            }
        }
        return false;
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
        assertRealmsConfigured();
        Set<String> realmNames = principals.getRealmNames();
        //获取realm的名字
        String realmName = realmNames.iterator().next();
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)) {
                continue;
            }
            //匹配名字
            if ("manager".equals(realmName)) {
                if (realm instanceof ManagersRealm) {
                    return ((ManagersRealm) realm).isPermitted(principals, permission);
                }
            }
            //匹配名字
            if ("merchant".equals(realmName)) {
                if (realm instanceof MerchantRealm) {
                    return ((MerchantRealm) realm).isPermitted(principals, permission);
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        assertRealmsConfigured();
        Set<String> realmNames = principals.getRealmNames();
        //获取realm的名字
        String realmName = realmNames.iterator().next();
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)) {
                continue;
            }
            //匹配名字
            if ("manager".equals(realmName)) {
                if (realm instanceof ManagersRealm) {
                    return ((ManagersRealm) realm).hasRole(principals, roleIdentifier);
                }
            }
            //匹配名字
            if ("merchant".equals(realmName)) {
                if (realm instanceof MerchantRealm) {
                    return ((MerchantRealm) realm).hasRole(principals, roleIdentifier);
                }
            }
        }
        return false;
    }


}
