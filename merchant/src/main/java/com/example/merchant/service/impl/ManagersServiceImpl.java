package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.sms.SenSMS;
import com.example.common.util.JsonUtils;
import com.example.common.util.MD5;
import com.example.common.util.ReturnJson;
import com.example.merchant.config.shiro.CustomizedToken;
import com.example.mybatis.entity.Managers;
import com.example.mybatis.mapper.ManagersDao;
import com.example.merchant.service.ManagersService;
import com.example.merchant.util.JwtUtils;
import com.example.redis.dao.RedisDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author hzp
 * @since 2020-09-15
 */
@Service("")
public class ManagersServiceImpl extends ServiceImpl<ManagersDao, Managers> implements ManagersService {

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${TOKEN}")
    private String TOKEN;

    @Value("${PWD_KEY}")
    private String PWD_KEY;

    @Autowired
    private SenSMS senSMS;

    @Autowired
    private RedisDao redisDao;

    private static final String MANAGERS= "MANAGERS";

    @Override
    public ReturnJson managersLogin(String userName, String passWord, HttpServletResponse response) {
        Managers managers = this.getOne(new QueryWrapper<Managers>().eq("user_name", userName).eq("pass_word", PWD_KEY+ MD5.md5(passWord)));
        Subject currentUser = SecurityUtils.getSubject();
        if (managers != null) {
            CustomizedToken customizedToken = new CustomizedToken(userName, PWD_KEY+ MD5.md5(passWord), MANAGERS);
            String token = jwtUtils.generateToken(managers.getId());
            managers.setPassWord("");
            redisDao.set(managers.getId(), JsonUtils.objectToJson(managers));
            response.setHeader(TOKEN,token);
            redisDao.setExpire(managers.getId(),7, TimeUnit.DAYS);
            currentUser.login(customizedToken);//shiro验证身份
            return ReturnJson.success(managers);
        }
        return ReturnJson.error("你输入的用户名或密码有误！");
    }

    @Override
    public ReturnJson senSMS(String mobileCode) {
        ReturnJson rj = new ReturnJson();
        Managers managers = this.getOne(new QueryWrapper<Managers>().eq("mobile_code", mobileCode));
        if (managers == null){
            rj.setCode(401);
            rj.setMessage("你还未注册，请先去注册！");
            return rj;
        }
        Map<String, Object> result = senSMS.senSMS(mobileCode);
        if ("000000".equals(result.get("statusCode"))) {
            rj.setCode(200);
            rj.setMessage("验证码发送成功");
            redisDao.set(mobileCode,String.valueOf(result.get("checkCode")));
            redisDao.setExpire(mobileCode,5*60);
        } else if ("160040".equals(result.get("statusCode"))) {
            rj.setCode(300);
            rj.setMessage(String.valueOf(result.get("statusMsg")));
        } else {
            rj.setCode(300);
            rj.setMessage(String.valueOf(result.get("statusMsg")));
        }
        return rj;
    }

    @Override
    public ReturnJson loginMobile(String loginMobile, String checkCode, HttpServletResponse resource) {
        String redisCheckCode = redisDao.get(loginMobile);
        if (StringUtils.isBlank(redisCheckCode)){
          return ReturnJson.error("验证码以过期，请重新获取！");
        } else if (!checkCode.equals(redisCheckCode)){
            return ReturnJson.error("输入的验证码有误!");
        } else {
            redisDao.remove(loginMobile);
            Managers managers = this.getOne(new QueryWrapper<Managers>().eq("mobile_code", loginMobile));
            managers.setPassWord("");
            String token = jwtUtils.generateToken(managers.getId());
            resource.setHeader(TOKEN,token);
            redisDao.set(managers.getId(), JsonUtils.objectToJson(managers));
            redisDao.setExpire(managers.getId(),60*60*24*7);
            return ReturnJson.success(managers);
        }
    }
}
