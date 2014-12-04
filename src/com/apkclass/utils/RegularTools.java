package com.apkclass.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正色表达式匹配类
 * 
 * @author 姜坤
 * 
 */

public class RegularTools {

	/**
	 * 匹配手机号码
	 * @author 姜坤
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		log.d(m.matches() + "---");
		return m.matches();
	}
	
	/**
	 * 匹配Email格式
	 * @param email
	 */
	public static boolean isEmailAddress(String email) {
	    String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
	    Pattern p = Pattern.compile(str);     
	    Matcher m = p.matcher(email);     
	    log.e(m.matches()+"---");     
	    return m.matches();     
	}

}
