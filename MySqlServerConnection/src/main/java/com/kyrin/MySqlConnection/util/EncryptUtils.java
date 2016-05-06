package com.kyrin.MySqlConnection.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author kyrin
 *
 */

public final class EncryptUtils {
 
	public static byte[] SHA1(String text){
		MessageDigest md=null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return calculate( md, text);
	}
	public static byte[] SHA256(String text){
		MessageDigest md=null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return calculate( md, text);
	}
	
	private static byte[] calculate(MessageDigest md,String text){
		md.update(text.getBytes());
		byte[] result=md.digest();
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(((byte)0)^1);
	}
}
