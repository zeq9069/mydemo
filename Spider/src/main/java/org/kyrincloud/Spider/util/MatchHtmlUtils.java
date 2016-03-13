package org.kyrincloud.Spider.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Attribute;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.sax.Attributes;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TagFindingVisitor;

/**
 * 用正则表达式去提取感兴趣的代码片段
 * @author lenovo
 *
 */
public final class MatchHtmlUtils {
	
	public static String match(String regex,String text){
		Pattern p=Pattern.compile(regex,Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
		Matcher m=p.matcher(text);
		while(m.find()){
			return m.group();
		}
		return null;
	}
	
	public static void main(String[] args) {
		Parser parser=new Parser();
		try {
			parser.setInputHTML("<html><input type='hidden' value='wwww'><span id='ss'>ddd   </span> sss</html>");
			AndFilter tag=new AndFilter(new TagNameFilter("span"),new HasAttributeFilter("id"));
			NodeList nodes=parser.parse(tag);
			Node node=nodes.elementAt(0);
			TagNode tagNode=new TagNode();
			tagNode.setText(node.toHtml());
			//System.out.println(tagNode.toPlainTextString());
			String ddd="                "+
"www";
			System.out.println(ddd.replace("\n\t", ""));
		} catch (ParserException e) {
			e.printStackTrace();
		}
	}

}
