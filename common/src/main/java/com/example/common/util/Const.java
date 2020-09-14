package com.example.common.util;

/**
 * 项目名称：
 * 修改日期：2015/11/2
*/
public class Const {
	/****************管理平台的用户session标志	***************/
	public static final String SESSION_USER = "RuiTingccSessionUser";				//管理平台的用户session标志	
	public static final String SESSION_MANAGERGUID = "ManagerRUID";				
	public static final String SESSION_USER_SIGN = "RTccSessionUserSign";				
	public static final String SESSION_USER_CUSTOMID= "RTccSessionUserFrmCustID";	
	public static final String SESSION_allmenuList	= "AllmenuList";	
	public static final String SESSION_USERNAME = "RTUserName";				//用户名
	/************公司用户平台的用户session标志**********/
	public static final String Company_SESSION_USER = "RuiTingccCompanySessionUser";				//管理平台的用户session标志	
	public static final String Company_SESSION_MANAGERGUID = "CompanyManagerRUID";				
	public static final String Company_SESSION_USER_SIGN = "RTccSessionCompanyUserSign";				
	public static final String Company_SESSION_allmenuList	= "AllCompanymenuList";		
	public static final String Company_SESSION_USERNAME = "RTCompanyUserName";				//用户名
	public static final String PAGE	= "20";				//分页条数配置路径

	public static final String SESSION_QX	= "";				//分页条数配置路径
	//public static final String SystemName= "零工云";	//平台名称的变量
	//public static final String SystemName= "蚂蚁短工";	//平台名称的变量
	//public static final String SystemName= "薪米";	//平台名称的变量
	public static final String SystemName= "中盈";	//平台名称的变量
	//public static final String SMSConfigInfo= "8a216da85d7dbf78015d940353180651,d95759cadec242408341a0063938230a,8a216da86f17653b016f565d642e2aad,493926";	//短信的配置
	// 中盈短信
	public static final String SMSConfigInfo= "8a216da870bf531b0170c273d03c0102,ac8b94ccb72443e1bbef823534eda4fd,8a216da870bf531b0170c273d0c90109,572988";
	public static final String SystemUrl = "https://xt.tianbanggroup.com/";					//系统图片访问前缀地址
	//public static final String SystemUrl = "http://xt.tianbanggroup.com/";					//系统图片访问前缀地址
	public static final String CentosSaveDir="/root/apache-tomcat-8.5.23/webapps/ROOT";	//linux保存文件路径
	public static final String REGEX_MOBILE = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
	
}
