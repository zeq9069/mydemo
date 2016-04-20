package com.kyrin.JsonLexer;
/**
 * json 文法解析器
 * 
 * JSON::='{'<CONTENT>'}' ;
 * CONTENT::=<KV>(','<KV>)*;
 * KV::=<EXP> ':' <EXP> ;
 * EXP::='"' <VAL> '"'
 * VAL::={'a'..'z'|'A'..'Z'|0..9}+
 * 
 * 
 * 这种方式虽然能解析json字符串，但是并不能进行格式的校验，需要采用LL(1)或者LL(2)甚至LL(N)模式
 * 
 * @author kyrin
 *
 */
public class JSONLexer extends Lexer{

	

	//定义Token
	private static int LBRACK=2; 	//{
	private static int RLRACK=3;	//}
	private static int DQUOTES=4;	//""
	private static int SEMICOLON=5;	//:
	private static int EXP=6;		//key
	private static int COMMA=7;		//,
	
	public static String[] tokenNames=new String[]{"n/a","<EOF>","LBRACK","RBRACK","DQUOTES","SEMICOLON","EXP","COMMA"};
	
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
		return new Token(EXP,sb.toString());
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
