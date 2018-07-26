package com.ldchotels.util;

import java.io.IOException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class CryptUtils {
	private static String Algorithm = "DES";
	private static String VALUE_ENCODING="UTF-8";
	//private static byte[] DEFAULT_KEY=new byte[] {-53, 122, -42, -88, -110, -123, -60, -74};
	
	private static byte[] encryptData(byte[] input, byte[] key) throws Exception {
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] cipherByte = c1.doFinal(input);
		return cipherByte;
	}

	private static byte[] decryptData(byte[] input, byte[] key) throws Exception {
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.DECRYPT_MODE, deskey);
		byte[] clearByte = c1.doFinal(input);
		return clearByte;
	}

	private static String bytesToBase64(byte[] buffer) {
		BASE64Encoder encoder = new BASE64Encoder();
		encoder.encode(buffer);
		return encoder.encode(buffer);
	}
	
	private static byte[] base64ToBytes(String value) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		decoder.decodeBuffer(value);
		return decoder.decodeBuffer(value);
	}
	
	private static String encryptString(String value, byte[] key) {
		try {
			byte[] data=value.getBytes(VALUE_ENCODING);
			data=encryptData(data, key);
			return bytesToBase64(data);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static String decryptString(String value, byte[] key) {
		try {
			byte[] data=base64ToBytes(value);
			data=decryptData(data, key);
			return new String(data, VALUE_ENCODING);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String encryptString(String value, String key) {
		return encryptString(value, new String(key).getBytes());
	}
	
	public static String decryptString(String value, String key) {
		return decryptString(value, new String(key).getBytes());
	}
}