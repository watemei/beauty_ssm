package com.iigeo.ssm.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * AES加解密算法
 * @author zhong.wang
 * key:每次登陆动态随机生成(大小写字母和数字组成)，并保存在session中
 * 此处使用AES-128-CBC加密模式，key需要为16位
 */
public class AESUtil {
	private static Logger log = LogManager.getLogger();
	
	public static boolean isAES = Constant.AES.ISAES;
	public static String sKey = Constant.AES.SKEY;
	
	// 加密
	public static String encrypt(String sSrc) throws Exception {
		
		if(!isAES) {
			return sSrc;
		}
		if (sKey == null) {
			//System.out.print("Key为空null");
			return null;
		}
		// 判断Key是否为16位
		if (sKey.length() != 16) {
			//System.out.print("Key长度不是16位");
			return null;
		}
		byte[] raw = sKey.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
		IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		//加密前要进行编码,否则js无法解码
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));

		return Base64.encodeBase64String(encrypted);// 此处使用BAES64做转码功能，同时能起到2次加密的作用。
	}

	// 解密
	public static String decrypt(String sSrc) throws Exception {
		if(!isAES) {
			return sSrc;
		}
		// 判断Key是否正确
		if (sKey == null) {
			//System.out.print("Key为空null");
			return null;
		}
		// 判断Key是否为16位
		if (sKey.length() != 16) {
			//System.out.print("Key长度不是16位");
			return null;
		}
		byte[] raw = sKey.getBytes("ASCII");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec("0102030405060708"
				.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
		byte[] encrypted1 = Base64.decodeBase64(sSrc);// 先用bAES64解密
		//System.out.println(encrypted1.length);
		byte[] original = cipher.doFinal(encrypted1);
		String originalString = new String(original);
		return originalString;
	}

	// 生成随机密锁
	public static String getKey(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		// 参数length，表示生成几位随机数
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				sb.append((char) (random.nextInt(26) + temp));
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				sb.append(String.valueOf(random.nextInt(10)));
			}
		}

		try {
			return new String(sb.toString().getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		}
		return "mapabc2014214yxj";
	}

	public static void main(String[] args) {
		//AES.sKey = getKey(16);
		AESUtil.isAES = true;
		try {
			//String str = AES.encrypt("你好1.2#3:4//5_6,1 2&3?4a/bc5=6");
			//String str = AES.encrypt("{\"account\":\"ez\",\"password\":\"123456\"}");
			String str = AESUtil.encrypt("{\"cityId\":\"110000\",\"cityType\":\"1\"}");
			System.out.println(str);
			String str1 = AESUtil.decrypt(str);
			System.out.println(str1);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}