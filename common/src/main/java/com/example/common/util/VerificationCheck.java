package com.example.common.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;


public abstract class VerificationCheck {

	// isBlank 是在 isEmpty 的基础上进行了为空（字符串都为空格、制表符、tab 的情况）的判断。
	public static boolean isBlank(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isNull(Object object) {
		if (object == null) {
			return false;
		} else {
			return true;
		}
	}
	public static boolean CheckLenght(Object object,int maxLenght) {
		if (object.toString().length()!= maxLenght) {
			return false;
		} else {
			return true;
		}
	}
	public static boolean isMobile(String mobile){
		return mobile.matches(Const.REGEX_MOBILE);
	}
}
