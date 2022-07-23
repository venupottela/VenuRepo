package com.poc.onpassive.util;

import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DecryptCode {
	private static String secretKey = "secretkey123";

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
		if (strToDecrypt.contains("=")) {
			strToDecrypt = strToDecrypt.replaceAll("=", "%3D");
		}
		if (strToDecrypt.contains("/")) {
			strToDecrypt = strToDecrypt.replace("/", "%2F");
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
		if (strToDecrypt.contains("%3D")) {
			strToDecrypt = strToDecrypt.replace("%3D", "=");
		}
		if (strToDecrypt.contains("%2F")) {
			strToDecrypt = strToDecrypt.replace("%2F", "/");
		}

		String decryptedText=null;
		try {
			// decode base64 encoding
			byte[] ciphertextData = Base64.getDecoder().decode(strToDecrypt);
			byte[] saltData = Arrays.copyOfRange(ciphertextData, 8, 16);
			// generate key & iv
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, saltData, secretKey.getBytes(StandardCharsets.UTF_8),
					md5);
			SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
			IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);
			// get encrypted data without iv
			byte[] encrypted = Arrays.copyOfRange(ciphertextData, 16, ciphertextData.length);
			Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
			aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
			// decryption
			byte[] decryptedData = aesCBC.doFinal(encrypted);
			decryptedText = new String(decryptedData, StandardCharsets.UTF_8);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return decryptedText;

	}

	public static byte[][] GenerateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password,
			MessageDigest md) {
		int digestLength = md.getDigestLength();
		int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
		byte[] generatedData = new byte[requiredLength];
		int generatedLength = 0;
		try {
			md.reset();
			// Repeat process until sufficient data has been generated
			while (generatedLength < keyLength + ivLength) {
				// Digest data (last digest if available, password data, salt if available)
				if (generatedLength > 0)
					md.update(generatedData, generatedLength - digestLength, digestLength);
				md.update(password);
				if (salt != null)
					md.update(salt, 0, 8);
				md.digest(generatedData, generatedLength, digestLength);
				// additional rounds
				for (int i = 1; i < iterations; i++) {
					md.update(generatedData, generatedLength, digestLength);
					md.digest(generatedData, generatedLength, digestLength);
				}
				generatedLength += digestLength;
			}
			// Copy key and IV into separate byte arrays
			byte[][] result = new byte[2][];
			result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
			if (ivLength > 0)
				result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);
			return result;
		} catch (DigestException e) {
			throw new RuntimeException(e);
		} finally {
			// Clean out temporary data
			Arrays.fill(generatedData, (byte) 0);
		}
	}
}
