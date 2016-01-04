package com.jkanfox.jkan.boot.security;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * SecurityUtils: 工具类
 * @author larry.qi
 */
public class SecurityUtils {
	
	/**
	 * MD5加密
	 * @param salt
	 * @param password
	 * @return
	 */
	public static String encryptPassword(String salt, String password) {
		return DigestUtils.md5Hex((password.concat(salt)).toString()).toUpperCase();
	}
	
	/**
	 * 生成盐值
	 * @return
	 */
	public static String randomSalt() {
		return UUID.randomUUID().toString().toUpperCase();
	}
	
}
