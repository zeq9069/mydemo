package com.kyrin.MySqlConnection.util;

/**
 * byte转十六进制
 * @author kyrin
 *
 */
public final class HexTranslate {

	//16进制对应字符
	static String[] hex=new String[]{"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
	
	public static String paser(byte val){
		String s = Integer.toHexString(val & 0xFF);
        if (s.length() == 1){
            return "0" + s;
        }else{
            return s;
        }
	}
	
	public static String paser(byte [] val){
		StringBuffer sb=new StringBuffer();
		for(byte b:val){
			sb.append(paser(b));
		}
		return sb.toString();
	}
}
