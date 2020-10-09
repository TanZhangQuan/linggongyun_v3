package com.example.merchant;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShiroTest {

    SimpleAccountRealm simpleAccountRealm=new SimpleAccountRealm();

    @BeforeEach
    public  void Adduser(){
        //添加一个用户 密码123456 权限 admin
        simpleAccountRealm.addAccount("yixi","esdafwcaf1a3dfb505ffed0d024130f58c5cfa","admin");
    }

    @Test
    public void test(){
        //构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager =new DefaultSecurityManager();//创建一个默认的安全管理器
        defaultSecurityManager.setRealm(simpleAccountRealm); //将账户域添加到安全管理器中
        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);//安全管理器提交到安全工具类中
        Subject subject=SecurityUtils.getSubject();

        UsernamePasswordToken token=new UsernamePasswordToken("yixi","esdafwcaf1a3dfb505ffed0d024130f58c5cfa");
        System.out.println(token);
        subject.login(token); //通过login方法和token进行认证

        subject.isAuthenticated(); //是否认证成功的方法
        System.out.println(subject.isAuthenticated());  //认证成功 输出 true
    }
}
