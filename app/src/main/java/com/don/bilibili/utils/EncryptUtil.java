package com.don.bilibili.utils;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {

	// 初始化向量，随机填充
	private static byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8 };

	/**
	 * @param encryptString
	 *            需要加密的明文
	 * @param encryptKey
	 *            秘钥
	 * @return 加密后的密文
	 * @throws Exception
	 */
	public static String encryptDES(String encryptString, String encryptKey)
			throws Exception {
		// 实例化IvParameterSpec对象，使用指定的初始化向量
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		// 实例化SecretKeySpec类，根据字节数组来构造SecretKey
		SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
		// 创建密码器
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		// 用秘钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
		// 执行加密操作
		byte[] encryptedData = cipher.doFinal(encryptString.getBytes());

		return Base64.encodeToString(encryptedData, Base64.DEFAULT);
	}

	/****
	 * 
	 * @param decrypString
	 *            密文
	 * @param decryptKey
	 *            解密密钥
	 * @return
	 * @throws Exception
	 */
	public static String decryptDES(String decrypString, String decryptKey)
			throws Exception {

		byte[] byteMi = Base64.decode(decrypString, Base64.DEFAULT);
		// 实例化IvParameterSpec对象，使用指定的初始化向量
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		// 实例化SecretKeySpec类，根据字节数组来构造SecretKey
		SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
		// 创建密码器
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		// 用秘钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
		// 执行解密操作
		byte[] decryptedData = cipher.doFinal(byteMi);

		return new String(decryptedData);
	}

	public static String getMD5(String text) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(text.getBytes("UTF-8"));
			byte[] byteArray = messageDigest.digest();
			StringBuffer md5StrBuff = new StringBuffer();
			for (int i = 0; i < byteArray.length; i++) {
				if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
					md5StrBuff.append("0").append(
							Integer.toHexString(0xFF & byteArray[i]));
				else
					md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
			return md5StrBuff.toString();
		} catch (Exception e) {
		}
		return null;
	}

	public static String AESCrypt_Encrypt(String key, String text) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(key.getBytes("UTF-8"));
			byte[] keyBytes = new byte[32];
			System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
			AlgorithmParameterSpec spec = getIV();

			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, spec);
			byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
			return new String(Base64.encode(encrypted, Base64.DEFAULT), "UTF-8");
		} catch (Exception e) {
		}

		return null;
	}

	public static String AESCrypt_Decrypt(String key, String text) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(key.getBytes("UTF-8"));
			byte[] keyBytes = new byte[32];
			System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
			AlgorithmParameterSpec spec = getIV();

			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, spec);
			byte[] bytes = Base64.decode(text, Base64.DEFAULT);
			byte[] decrypted = cipher.doFinal(bytes);
			return new String(decrypted, "UTF-8");
		} catch (Exception e) {
		}

		return null;
	}

	private static AlgorithmParameterSpec getIV() {
		byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };
		IvParameterSpec ivParameterSpec;
		ivParameterSpec = new IvParameterSpec(iv);
		return ivParameterSpec;
	}

	public static String encrypt(String text) {
		StringBuffer buffer = new StringBuffer();
		char[] chars = text.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			String binary = Integer.toBinaryString(chars[i]);
			StringBuffer b = new StringBuffer();
			for (char c : binary.toCharArray()) {
				b.insert(0, c);
			}
			buffer.append(b);
			if (i != chars.length - 1) {
				buffer.append(",");
			}
		}
		return buffer.toString();
	}

	public static String decrypt(String text) {
		StringBuffer buffer = new StringBuffer();
		String[] array = text.split(",");
		for (int i = 0; i < array.length; i++) {
			StringBuffer b = new StringBuffer();
			for (char c : array[i].toCharArray()) {
				b.insert(0, c);
			}
			buffer.append(toChar(b.toString()));
		}
		return buffer.toString();
	}

	private static char toChar(String s) {
		int[] temp = toIntArray(s);
		int sum = 0;

		for (int i = 0; i < temp.length; i++) {
			sum += temp[temp.length - 1 - i] << i;
		}
		return (char) sum;

	}

	private static int[] toIntArray(String s) {

		char[] temp = s.toCharArray();
		int[] result = new int[temp.length];

		for (int i = 0; i < temp.length; i++) {
			result[i] = temp[i] - 48;
		}
		return result;
	}
}
