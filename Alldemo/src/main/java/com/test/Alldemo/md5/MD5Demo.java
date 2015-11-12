package com.test.Alldemo.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Demo {

	private static final String SHA_1 = "SHA-1";
	private static final String SHA_256 = "SHA-256";
	private static final String MD5 = "MD5";
	// 用来将字节转换成 16 进制表示的字符
	static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static void main(String[] args) {
		String value = "1234567890123123123qdwedwefw";

		try {
			MessageDigest md = MessageDigest.getInstance(MD5);
			md.update(value.getBytes());
			byte[] result = md.digest();
			String resultToString = byteToHexString(result);
			String resultToString1 = byteToHexStr(result);

			System.out.println("密文:" + resultToString + ",长度："
					+ resultToString.length());
			System.out.println("密文:" + resultToString1 + ",长度："
					+ resultToString1.length());

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 个人理解：
	 * 其实移位操作就是给这些字节，字符，十六进制，二进制等这些计算用的，因为这些通过移位操作很方便
	 * 
	 * 这个方法刚开始有两个地方不理解：
	 * 
	 * 1，str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
	 * 											// >>> 为逻辑右移，将符号位一起右移
	 * 
	 * 2，str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
	 * 
	 * 就是这两个地方，疑问是：
	 * 1，byte0 >>> 4 什么意思？为什么要移位操作？为什么要取高4位？& 0xf 又是什么意思？
	 * 2，byte0 & 0xf 是什么意思？为什么这样做就是取得低4位？
	 * 
	 * 后来仔细想想就想通了，下面写一下详细过程：
	 * 
	 * 1，首先，
	 * ASCII码：
　　			一个英文字母（不分大小写）占一个字节的空间，一个中文汉字占两个字节的空间
			一个字节占二进制8位。每4位一起，表示一个十六进制，8位也就是两个十六进制，如：0xFF
	 * 移位操作:
	 *  >> ：右移操作，>>1 ，相当于除一次2，>>> 是无符号的右移，右移一位，左边就补0
	 *  << :左移操作  << 2 相当于乘两次2
	 *  
	 *  &: 与运算，0&0=0，0&1=0，1&1=1，0&0=0，也就是相同的与运算就是相同的，不同的与运算就等于0
	 *  按位与运算有两种典型用法，一是取一个位串信息的某几位，如以下代码截取x的最低7位：x & 0177。二是让某变量保留某几位，其余位置0，如以下代码让x只保留最低6位：x = x & 077。以上用法都先要设计好一个常数，该常数只有需要的位是1，不需要的位是0。用它与指定的位串信息按位与
	 *  
	 * 2，基础知识知道了，我们就可以理解了，一个byte转换为十六进制必须把一个byte拆成两个4位，然后每4位一起去转换为十六进制，这样就分为，一个byte就分为高四位和死四位
	 *  
	 *  byte0>>>4 的意思也就是一个字节八位右移4位，这样低四位就没了，剩下高四位，比如： （1111 1111 = 0xFF） >>> 4 = 0000 1111 = 0x0f=0xf
	 *  byte0>>>4&0xF 的意思就是先右移4位，获取高四位，再与0xF与操作，比如：（1111 1111 = 0xFF） >>> 4 = 0000 1111 & 0000 1111 = 0000 1111 = 0x0f=0xf
	 * 	
	 * 以上就把高四位转换为了十六进制的一个char，然后看看低四位
	 * 
	 * byte0&0xf 这个怎么就会低四位的转换呢？ 举个例子： 0011 1011 & 0xf =  0011 1011 & 0x0f = 0011 1011 & 0000 1111 = 0000 1011 
	 *  看到了吧，因为0xf高四位是0000，无论跟0或者1与运算，结果都是0000，这样就相当于把高四位变成了0，只剩下低四位！！！！
	 *  
	 *  OK~~经过这两步的计算很巧妙的把一个byte转换为了十六进制的两个char！！！！！  
	 *  这也让我对这些逻辑运算有了深刻的理解！！
	 * 
	 * 
	 * @param tmp
	 * @return
	 */
	private static String byteToHexString(byte[] tmp) {
		String s;
		// 用字节表示就是 16 个字节
		char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，如：0xFF,前缀去掉就是FF，每一个F占用四位，八位是一个字节
		// 所以表示成 16 进制需要 32 个字符
		int k = 0; // 表示转换结果中对应的字符位置
		for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节，md5加密的话，byte[]长度固定16
			// 转换成 16 进制字符的转换
			byte byte0 = tmp[i]; // 取第 i 个字节
			str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
			// >>> 为逻辑右移，将符号位一起右移
			str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
		}
		s = new String(str); // 换后的结果转换为字符串
		return s;
	}

	private static String byteToHexStr(byte[] tmp) {
		String result = "";
		for (int i = 0; i < tmp.length; i++) {
			result += hexDigits[tmp[i] >>> 4 & 0xf];
			result += hexDigits[tmp[i] & 0xf];
		}
		return result;
	}

}
