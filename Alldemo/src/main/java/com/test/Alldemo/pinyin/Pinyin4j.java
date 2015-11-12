package com.test.Alldemo.pinyin;

/**
 * 测试汉字转十六进制字符，十六进制转unicode，再转为汉字
 * @author zhangerqiang
 *
 */
public class Pinyin4j {
	// ascaii一个字符两个字节，一个字节8位，四位表示一个十六进制字符
	// String
	// hex_result="0x"+hexChar[val>>>12]+""+hexChar[val>>>8&0xf]+""+hexChar[val>>>4&0xf]+""+hexChar[val&0xf];
	// System.out.println("16进制："+hex_result+" = "+"10进制："+22247);
	// System.out.println("汉字:"+(char)22247);
	// 十六进制转char
	// System.out.println((char)0x9FA5);

	static char[] hexChar = new char[] { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static void main(String[] args) {

		String str = "囧";
		char val = str.charAt(0);
		String hexStr = charToHexStr(val);
		System.out.println("[" + val + "]转换为16进制：" + hexStr);
		System.out.println("[" + hexStr + "]转换为汉字：" + hexStrToChar(hexStr));
	}

	/**
	 * 在ASCII码中，一个英文字母（不分大小写）占一个字节的空间，一个中文汉字占两个字节的空间。
	 * 一个字符两个字节，一个字节8位，四位表示一个十六进制字符
	 * 
	 * @param val
	 * @return
	 */
	private static String charToHexStr(char val) {
		return String.format("%s%s%s%s", hexChar[val >>> 12],
				hexChar[val >>> 8 & 0xf], hexChar[val >>> 4 & 0xf],
				hexChar[val & 0xf]);
	}

	// 不带0x前缀
	private static char hexStrToChar(String hex) {
		int length = hex.length();
		int result = 0;
		for (int i = 0; i < length; i++) {
			char ch = hex.charAt(length - 1 - i);
			int val = index(ch);
			result += val * Math.pow(16, i);
		}
		return (char) result;
	}

	private static int index(char value) {
		for (int i = 0; i < hexChar.length; i++) {
			if (hexChar[i] == value) {
				return i;
			}
		}
		return -1;
	}

}
