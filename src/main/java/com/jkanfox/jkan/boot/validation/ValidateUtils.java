package com.jkanfox.jkan.boot.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {

	/**
	 * 验证邮箱地址是否正确
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		try {
			String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Matcher matcher = Pattern.compile(check).matcher(email);
			return matcher.matches();
		} catch (Exception e) {
			return false;
		}
	}
	
}
