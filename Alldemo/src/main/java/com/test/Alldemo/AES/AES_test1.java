package com.test.Alldemo.AES;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
/**
 * AES加密/解密
 * @author kyrin
 *
 */
public class AES_test1 {
	private static final String AES = "AES";
	static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";
	/*
	 * 
	 */
	static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";
	/* 
	 * AES/CBC/NoPadding 要求
	 * 密钥必须是16位的；Initialization vector (IV) 必须是16位
	 * 待加密内容的长度必须是16的倍数，如果不是16的倍数，就会出如下异常：
	 * javax.crypto.IllegalBlockSizeException: Input length not multiple of 16 bytes
	 * 
	 *  由于固定了位数，所以对于被加密数据有中文的, 加、解密不完整
	 *  
	 *  可 以看到，在原始数据长度为16的整数n倍时，假如原始数据长度等于16*n，则使用NoPadding时加密后数据长度等于16*n，
	 *  其它情况下加密数据长 度等于16*(n+1)。在不足16的整数倍的情况下，假如原始数据长度等于16*n+m[其中m小于16]，
	 *  除了NoPadding填充之外的任何方 式，加密数据长度都等于16*(n+1).
	 */
	static final String CIPHER_ALGORITHM_CBC_NoPadding = "AES/CBC/NoPadding"; 
	
	
	public static byte[] Encrypt_1(byte[] text, byte[] key) throws Exception {
		//SecretKey key1 = KeyGenerator.getInstance(AES).generateKey();//自动生成key，加密和解密的key要相同
		SecretKeySpec key1 = new SecretKeySpec(key, AES);//自定义key
		Cipher cc = Cipher.getInstance(AES);
		cc.init(Cipher.ENCRYPT_MODE, key1);
		byte[] bb = cc.doFinal(text);
		return bb;
	}

	public static String Decrypt_1(byte[] text, byte[] key) throws Exception {
		// SecretKey key1 = KeyGenerator.getInstance(AES).generateKey();//自动生成key
		SecretKeySpec key1 = new SecretKeySpec(key, AES);//自定义key
		Cipher cc = Cipher.getInstance(AES);
		cc.init(Cipher.DECRYPT_MODE, key1);
		return new String(cc.doFinal(text));
	}
	
	public static byte[] Encrypt_2(byte[] text, byte[] key) throws Exception {
		//SecretKey key1 = KeyGenerator.getInstance(AES).generateKey();//自动生成key，加密和解密的key要相同
		SecretKeySpec key1 = new SecretKeySpec(key, AES);//自定义key
		Cipher cc = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
		cc.init(Cipher.ENCRYPT_MODE, key1);
		byte[] bb = cc.doFinal(text);
		return bb;
	}

	public static String Decrypt_2(byte[] text, byte[] key) throws Exception {
		// SecretKey key1 = KeyGenerator.getInstance(AES).generateKey();//自动生成key
		SecretKeySpec key1 = new SecretKeySpec(key, AES);//自定义key
		Cipher cc = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
		cc.init(Cipher.DECRYPT_MODE, key1);
		return new String(cc.doFinal(text));
	}

	public static byte[] Encrypt_3(byte[] text, byte[] key) throws Exception {
		//SecretKey key1 = KeyGenerator.getInstance(AES).generateKey();//自动生成key，加密和解密的key要相同
		SecretKeySpec key1 = new SecretKeySpec(key, AES);//自定义key
		Cipher cc = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
		cc.init(Cipher.ENCRYPT_MODE, key1);
		byte[] bb = cc.doFinal(text);
		return bb;
	}

	public static String Decrypt_3(byte[] text, byte[] key) throws Exception {
		// SecretKey key1 = KeyGenerator.getInstance(AES).generateKey();//自动生成key
		SecretKeySpec key1 = new SecretKeySpec(key, AES);//自定义key
		Cipher cc = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
		cc.init(Cipher.DECRYPT_MODE, key1);
		return new String(cc.doFinal(text));
	}
	
	public static byte[] Encrypt_4(byte[] text, byte[] key) throws Exception {
		//SecretKey key1 = KeyGenerator.getInstance(AES).generateKey();//自动生成key，加密和解密的key要相同
		SecretKeySpec key1 = new SecretKeySpec(key, AES);//自定义key
		Cipher cc = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
		cc.init(Cipher.ENCRYPT_MODE, key1,new IvParameterSpec(key));
		byte[] bb = cc.doFinal(text);
		return bb;
	}

	public static String Decrypt_4(byte[] text, byte[] key) throws Exception {
		// SecretKey key1 = KeyGenerator.getInstance(AES).generateKey();//自动生成key
		SecretKeySpec key1 = new SecretKeySpec(key, AES);//自定义key
		Cipher cc = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
		cc.init(Cipher.DECRYPT_MODE, key1,new IvParameterSpec(key));
		return new String(cc.doFinal(text));
	}
	public static String bytes2hex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String temp = (Integer.toHexString(bytes[i] & 0XFF));
			if (temp.length() == 1) {
				temp = "0" + temp;
			}
			sb.append(temp);
			sb.append(" ");
		}
		return sb.toString().toUpperCase();
	}

	public static void main(String[] args) {
		try {
			String key = "1234567812345678";// 长度必须是16的倍数
			String text = "I'm kyrin!你好啊！ ";

			
			byte[] encrypt_text = Encrypt_1(text.getBytes(), key.getBytes());

			System.out.println("\n======================[第一种模式：AES]===============================\n");
			
			System.out.println(String.format("[%s]加密之后 : %s", text,
					bytes2hex(encrypt_text)));
			System.out.println(String.format("[%s]解密之后 : %s", text,
					Decrypt_1(encrypt_text, key.getBytes())));
			
			System.out.println("\n=====================[第二种模式：AES/ECB/PKCS5Padding]================\n");
			
			encrypt_text = Encrypt_2(text.getBytes(), key.getBytes());
			System.out.println(String.format("[%s]加密之后 : %s", text,
					bytes2hex(encrypt_text)));
			System.out.println(String.format("[%s]解密之后 : %s", text,
					Decrypt_2(encrypt_text, key.getBytes())));
			
			System.out.println("\n=====================[第三种模式：AES/ECB/PKCS5Padding]================\n");
			
			encrypt_text = Encrypt_3(text.getBytes(), key.getBytes());
			System.out.println(String.format("[%s]加密之后 : %s", text,
					bytes2hex(encrypt_text)));
			System.out.println(String.format("[%s]解密之后 : %s", text,
					Decrypt_3(encrypt_text, key.getBytes())));
			
			System.out.println("\n=====================[第四种模式：AES/CBC/NoPadding]===================\n");
			
			encrypt_text = Encrypt_4(text.getBytes(), key.getBytes());
			System.out.println(String.format("[%s]加密之后 : %s", text,
					bytes2hex(encrypt_text)));
			System.out.println(String.format("[%s]解密之后 : %s", text,
					Decrypt_4(encrypt_text, key.getBytes())));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}