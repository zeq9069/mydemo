package com.test.Alldemo.pinyin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 讲一个汉字转换为拼音
 * 
 * @author kyrin
 *
 */
public class ChaineseToPinyinTest {
	static char[] hexChar = new char[] { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	private static Map<String, List<String>> keys = new ConcurrentHashMap<String, List<String>>();

	//初始化，加载拼音文件
	public static void init() {
		InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("unicode_to_hanyu_pinyin.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String str = "";
		try {
			while ((str = br.readLine()) != null) {
				String[] key_value = str.split(" ");
				keys.put(key_value[0], resolveString(key_value[1]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 解析字符串，格式如：(xxx,yyy)
	private static List<String> resolveString(String value) {
		String value_1 = value.substring(1, value.length() - 1);
		String[] vals = value_1.split(",");
		return Arrays.asList(vals);
	}

	public static void main(String[] args) {
		init();
		String s = "我熬了一个通宵，你说我困不困？";
		for (int i = 0; i < s.length(); i++) {
			char res=s.charAt(i);
			if(res!=','  && res!='，' && res!='.' && res!='。'&& res!='？'&& res!='?' && res!=';'&& res!='；'){
				List<String> values = keys.get(charToHexStr(res));
				String first=values.get(0);
				System.out.print(first.substring(0,first.length()-1)+" ");
			}else{
				System.out.print(res);
			}
		}

	}

	private static String charToHexStr(char val) {
		return String.format("%s%s%s%s", hexChar[val >>> 12],
				hexChar[val >>> 8 & 0xf], hexChar[val >>> 4 & 0xf],
				hexChar[val & 0xf]);
	}

}
