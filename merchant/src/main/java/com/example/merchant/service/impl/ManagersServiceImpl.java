package com.example.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.config.JwtConfig;
import com.example.common.sms.SenSMS;
import com.example.common.util.JsonUtils;
import com.example.common.util.MD5;
import com.example.common.util.ReturnJson;
import com.example.merchant.config.shiro.CustomizedToken;
import com.example.merchant.service.ManagersService;
import com.example.merchant.util.JwtUtils;
import com.example.mybatis.entity.Managers;
import com.example.mybatis.mapper.ManagersDao;
import com.example.redis.dao.RedisDao;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
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
@Service
public class ManagersServiceImpl extends ServiceImpl<ManagersDao, Managers> implements ManagersService {

    private static final String MANAGERS = "manager";
    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private SenSMS senSMS;
    @Resource
    private RedisDao redisDao;

    @Override
    public ReturnJson managersLogin(String userName, String passWord, HttpServletResponse response) {
        Managers managers = this.getOne(new QueryWrapper<Managers>().lambda()
                .eq(Managers::getUserName, userName)
                .eq(Managers::getPassWord, MD5.md5(JwtConfig.getSecretKey() + passWord)));
        Subject currentUser = SecurityUtils.getSubject();
        if (managers != null) {
            CustomizedToken customizedToken = new CustomizedToken(userName, MD5.md5(JwtConfig.getSecretKey() + passWord), MANAGERS);
            String token = jwtUtils.generateToken(managers.getId());
            redisDao.set(managers.getId(), token);
            response.setHeader(JwtConfig.getHeader(), token);
            redisDao.setExpire(managers.getId(), 1, TimeUnit.DAYS);
            currentUser.login(customizedToken);//shiro验证身份
            return ReturnJson.success("登录成功", token);
        }
        return ReturnJson.error("你输入的用户名或密码有误！");
    }

    @Override
    public ReturnJson senSMS(String mobileCode) {
        ReturnJson rj = new ReturnJson();
        List<Managers> managers = this.list(new QueryWrapper<Managers>().lambda()
                .eq(Managers::getMobileCode, mobileCode));
        if (managers == null) {
            rj.setCode(401);
            rj.setMessage("你还未注册，请先去注册！");
            return rj;
        }
        Map<String, Object> result = senSMS.senSMS(mobileCode);
        if ("000000".equals(result.get("statusCode"))) {
            rj.setCode(200);
            rj.setMessage("验证码发送成功");
            redisDao.set(mobileCode, String.valueOf(result.get("checkCode")));
            redisDao.setExpire(mobileCode, 5 * 60);
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
        if (StringUtils.isBlank(redisCheckCode)) {
            return ReturnJson.error("验证码以过期，请重新获取！");
        } else if (!checkCode.equals(redisCheckCode)) {
            return ReturnJson.error("输入的验证码有误!");
        } else {
            redisDao.remove(loginMobile);
            Managers managers = this.getOne(new QueryWrapper<Managers>().lambda()
                    .eq(Managers::getMobileCode, loginMobile));
            managers.setPassWord("");
            String token = jwtUtils.generateToken(managers.getId());
            resource.setHeader(JwtConfig.getHeader(), token);
            redisDao.set(managers.getId(), JsonUtils.objectToJson(managers));
            redisDao.setExpire(managers.getId(), 60 * 60 * 24);
            return ReturnJson.success(managers);
        }
    }

    /**
     * 通过token获取用户信息
     *
     * @param customizedId
     * @return
     */
    @Override
    public ReturnJson getCustomizedInfo(String customizedId) {
        Managers managers = this.getById(customizedId);
        managers.setPassWord("");
        return ReturnJson.success(managers);
    }

    @Override
    public ReturnJson logout(String manangerId) {
        Claims c = jwtUtils.getClaimByToken(manangerId);
        redisDao.remove(c.getSubject());
        return ReturnJson.success("登出成功");
    }

    @Override
    public ReturnJson updataPassWord(String loginMobile, String checkCode, String newPassWord) {
        String redisCode = redisDao.get(loginMobile);
        if (redisCode == null) {
            return ReturnJson.error("你的验证码已过期请重新发送！");
        }
        if (redisCode.equals(checkCode)) {
            Managers managers = new Managers();
            managers.setPassWord(MD5.md5(JwtConfig.getSecretKey() + newPassWord));
            boolean flag = this.update(managers, new QueryWrapper<Managers>().lambda()
                    .eq(Managers::getMobileCode, loginMobile));
            if (flag) {
                redisDao.remove(loginMobile);
                return ReturnJson.success("密码修改成功！");
            } else {
                return ReturnJson.success("密码修改失败！");
            }
        }
        return ReturnJson.error("你的验证码有误！");
    }

    @Override
    public ReturnJson updateHeadPortrait(String userId, String headPortrait) {
        Managers managers = this.getById(userId);
        managers.setUserHead(headPortrait);
        this.updateById(managers);
        return ReturnJson.success("修改成功");
    }
}
