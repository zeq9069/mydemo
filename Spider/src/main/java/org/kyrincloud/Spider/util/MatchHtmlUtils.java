package org.kyrincloud.Spider.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用正则表达式去提取感兴趣的代码片段
 * @author lenovo
 *
 */
public final class MatchHtmlUtils {
	
	public static String match(String regex,String text){
		Pattern p=Pattern.compile(regex);
		Matcher m=p.matcher(text);
		while(m.find()){
			return m.group();
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(match("12a", "<html>124312awfwe</html>"));
	}

}
