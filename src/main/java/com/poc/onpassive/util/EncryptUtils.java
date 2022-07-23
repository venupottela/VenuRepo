package com.poc.onpassive.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.PatternSyntaxException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

/**
 * This class is responsible for Encrypting (OR) Decrypting String
 * 
 * @author KANCHI HARISH
 * @version 2.0
 * @since 05-03-2021
 */
@Component
@Log4j2
public class EncryptUtils {
	private static SecretKeySpec secretKey;
	private static String globalsecretKey = "ZHU#DqL6oJTcBk\nUbNvBPuGfsa/Ja10NoOvo2";
	public static String globalsecretKeyForLogin = "ZHU#DqL6oJTcBkUbNvBPuGfsaJa10NoOvo2nUbNvBPuGfsa";
	private static byte[] key;

	public static void setKey(String myKey) {
		MessageDigest sha = null;
		try {
			key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			

			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
		

			e.printStackTrace();
		}
	}

	public static String encrypt(String strToEncrypt) {
		try {
			setKey(globalsecretKey);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			String en = Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
			en = replaceSymbols(en);
			return en;
		} catch (Exception e) {
			

			System.err.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	public static String decrypt(String strToDecrypt) {
		if (strToDecrypt.contains("+")) {
			strToDecrypt = strToDecrypt.replace("+", "%2B");
		}
		if (strToDecrypt.contains("-")) {
			strToDecrypt = strToDecrypt.replace("-", "%2D");
		}
		if (strToDecrypt.contains("*")) {
			strToDecrypt = strToDecrypt.replace("*", "%2A");
		}
		if (strToDecrypt.contains(",")) {
			strToDecrypt = strToDecrypt.replace(",", "%2C");
		}
		if (strToDecrypt.contains(".")) {
			strToDecrypt = strToDecrypt.replaceAll(".", "%2E");
		}
		if (strToDecrypt.contains(" ")) {
			strToDecrypt = strToDecrypt.replace(" ", "+");
		}
		if (strToDecrypt.contains("%2B")) {
			strToDecrypt = strToDecrypt.replace("%2B", "+");
		}
		if (strToDecrypt.contains("%2D")) {
			strToDecrypt = strToDecrypt.replace("%2D", "-");
		}
		if (strToDecrypt.contains("%2A")) {
			strToDecrypt = strToDecrypt.replace("%2A", "*");
		}
		if (strToDecrypt.contains("%2C")) {
			strToDecrypt = strToDecrypt.replace("%2C", ",");
		}
		if (strToDecrypt.contains("%2E")) {
			strToDecrypt = strToDecrypt.replace("%2E", ".");
		}
		if (strToDecrypt.contains("%2F")) {
			strToDecrypt = strToDecrypt.replaceAll("%2F", "/");
		}

		// if(strToDecrypt.contains("_"))
//		{
//			strToDecrypt=strToDecrypt.replaceAll("_", "/");
//		}

		try {
			setKey(globalsecretKey);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			

			System.err.println("Error while decrypting: " + e.toString());
		}
		return null;
	}

	public static String decryptCaptcha(String strToDecrypt) {
		if (strToDecrypt.contains("%2B")) {
			strToDecrypt = strToDecrypt.replace("%2B", "+");
		}
		if (strToDecrypt.contains("%2D")) {
			strToDecrypt = strToDecrypt.replace("%2D", "-");
		}
		if (strToDecrypt.contains("%2A")) {
			strToDecrypt = strToDecrypt.replace("%2A", "*");
		}
		if (strToDecrypt.contains("%2C")) {
			strToDecrypt = strToDecrypt.replace("%2C", ",");
		}
		if (strToDecrypt.contains("%2E")) {
			strToDecrypt = strToDecrypt.replace("%2E", ".");
		}
		if (strToDecrypt.contains("%2F")) {
			strToDecrypt = strToDecrypt.replaceAll("%2F", "/");
		}
		String encrypt = "";
		try {
			setKey(globalsecretKey);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			encrypt = new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {



			System.err.println("Error while decrypting: " + e.toString());
			encrypt = "DECRYPT_MODE Failed";
		}
		return encrypt;
	}

	public static String replaceSymbols(String data) {
		String strToDecrypt = data;
		try {
			if (strToDecrypt.contains("+")) {
				strToDecrypt = strToDecrypt.replace("+", "%2B");
			}
			if (strToDecrypt.contains("-")) {
				strToDecrypt = strToDecrypt.replace("-", "%2D");
			}
			if (strToDecrypt.contains("*")) {
				strToDecrypt = strToDecrypt.replace("*", "%2A");
			}
			if (strToDecrypt.contains(",")) {
				strToDecrypt = strToDecrypt.replace(",", "%2C");
			}
			if (strToDecrypt.contains(".")) {
				strToDecrypt = strToDecrypt.replaceAll(".", "%2E");
			}
			if (strToDecrypt.contains("/")) {
				strToDecrypt = strToDecrypt.replaceAll("/", "%2F");
			}

		} catch (PatternSyntaxException e) {

			e.printStackTrace();
		}
		return strToDecrypt;
	}
}
