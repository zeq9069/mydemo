package com.kyrin.JsonLexer;
/**
 * json 文法(词法)解析器
 * 
 * JSON::='{'<CONTENT>'}' ;
 * CONTENT::=<KV>(','<KV>)*;
 * KV::=<EXP> ':' <EXP> ;
 * EXP::='"' <VAL> '"'
 * VAL::={'a'..'z'|'A'..'Z'|0..9}+
 * 
 * {"TEXT":"TEXT"}
 * 此法单元有：{ 、 " 、 : 、 } 、 TEXT 总共5个Token ,忽略一些空格无用的字符
 * 
 * 仅仅是遍历字符串的词，校验遍历的词是否合法，并将词组成Token词法单元，这一步并不能做json格式的校验，是在Parser（语法分析器）中做的
 * 
 * 这种方式虽然能解析json字符串，但是并不能进行格式的校验，需要采用LL(1)或者LL(2)甚至LL(N)模式
 * 
 * @author kyrin
 *
 */
public class JSONLexer extends Lexer{

	//定义Token
	public static int LBRACK=2; 	//{
	public static int RLRACK=3;		//}
	public static int DQUOTES=4;	//""
	public static int SEMICOLON=5;	//:
	public static int TEXT=6;		//
	public static int COMMA=7;		//,
	public static int LBRACKETS=8;  //[
	public static int RBRACKETS=9;  //]
	public static int SINGLEQUOTES; //'
	
	public static String[] tokenNames=new String[]{"n/a","<EOF>","LBRACK","RBRACK","DQUOTES","SEMICOLON","TEXT","COMMA","LBRACKETS","LBRACKETS","SINGLEQUOTES"};
	
	public JSONLexer(String input) {
		super(input);
	}
	
	public Token nextToken() {
		while(c!=EOF){
			switch(c){
				case ' ' :case '\t':case '\n':case '\r':WS();continue;
				case '{':consume(); return new Token(LBRACK,"{");
				case '"':consume();return new Token(DQUOTES,"\"");
				case ':':consume();return new Token(SEMICOLON,":");
				case ',':consume();return new Token(COMMA,",");
				case '}':consume();return new Token(RLRACK, "{");
				case '[':consume();return new Token(LBRACKETS, "[");
				case ']':consume();return new Token(RBRACKETS, "]");
				case '\'':consume();return new Token(SINGLEQUOTES, "'");
				default:
					if(isLETTER()) return EXP();
					else throw new Error("invalid char :"+c);
			}
		}
		return new Token(EOF_TYPE,"EOF");
	}
	
	public void WS(){
		while(c==' ' || c=='\t' || c=='\n' || c=='\r')consume();
	}
	
	public Token EXP(){
		StringBuffer sb=new StringBuffer();
		while(isLETTER()){
			sb.append(c);
			consume();
		}
		return new Token(TEXT,sb.toString());
	}
	
	public void consume(){
		p++;
		if(p>=input.length()) c=EOF;
		else c=input.charAt(p);
	}
	
	public void match(char x){
		if(c==x) consume();
		else throw new Error("expecting"+x+";found "+c);
	}
	
	public String getTokenName(int tokenType){
		return tokenNames[tokenType];
	}
	
	public boolean isLETTER(){
		return c>='a' && c<='z' || c>='A' && c<='Z' || c>='0' && c<='9';
	}
	
}
