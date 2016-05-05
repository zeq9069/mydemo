package com.kyrin.MySqlConnection;

/**
 * byte转十六进制
 * @author kyrin
 *
 */
public final class HexTranslate {

	//16进制对应字符
	static String[] hex=new String[]{"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
	
	public static String paser(byte b){
		StringBuilder sb=new StringBuilder();
		int hg=b>>4;
		int low=b&0x0f;
		if(hg>=0 && hg<=15 && low>=0 && low <=15){
			sb.append(hex[hg]);
			sb.append(hex[low]);
		}
		return sb.toString();
	}
}
