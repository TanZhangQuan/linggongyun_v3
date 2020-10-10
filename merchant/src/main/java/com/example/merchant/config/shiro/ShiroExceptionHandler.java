package com.example.merchant.config.shiro;

import com.example.common.util.ReturnJson;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class ShiroExceptionHandler {

	@ExceptionHandler(AuthenticationException.class)
	@ResponseBody
	public ReturnJson unauthenticatedHandler(AuthenticationException e) {
		e.printStackTrace();
		return ReturnJson.error(e.getMessage());
	}

	@ExceptionHandler(AuthorizationException.class)
	@ResponseBody
	public ReturnJson unauthorizedHandler(AuthorizationException e) {
		e.printStackTrace();
		return ReturnJson.error("权限不够访问该接口");
	}

}
