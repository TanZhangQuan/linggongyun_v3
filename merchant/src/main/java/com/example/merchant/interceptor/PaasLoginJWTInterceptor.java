package com.example.merchant.interceptor;

import com.example.merchant.exception.CommonException;
import com.example.merchant.util.JwtUtils;
import com.example.redis.dao.RedisDao;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class PaasLoginJWTInterceptor implements HandlerInterceptor {

    @Value("${TOKEN}")
    private String TOKEN;

    @Resource
    private JwtUtils jwtUtils;

    @Resource
    private RedisDao redisDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(LoginRequired.class)) {
            LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
            //进入方法之前进行的操作
            //获取token
            String token = null;
            try {
                token = request.getHeader(TOKEN);
            } catch (Exception e) {

            }
            if (!loginRequired.required()) {
                return true;
            } else {
                if (StringUtils.isBlank(token)) {
                    throw new CommonException(403, "请求格式错误！");
                }
                Claims claim = jwtUtils.getClaimByToken(token);
                if (claim == null) {
                    throw new CommonException(403, "请求格式错误！");
                }
                if (jwtUtils.isTokenExpired(claim.getExpiration())) {
                    throw new CommonException(402, "你的登录以过期请重新登录！");
                }
                String userId = claim.getSubject();
                String user = redisDao.get(userId);
                if (StringUtils.isBlank(user)) {
                    throw new CommonException(402, "你的登录以过期请重新登录！");
                }
                request.setAttribute("userId", userId);
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
