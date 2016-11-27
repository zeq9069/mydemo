package com.sankuai.NIOFile2.invertedIndex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 倒排索引demo
 * @author zhangerqiang
 *
 */
public class Test {
	
	public static void main(String[] args) throws IOException {
		
		FSFactory fs = new FSFactory("/Users/zhangerqiang/Desktop/temp");

		System.out.println(System.currentTimeMillis());
		IndexSearch search = new IndexSearch(fs);
		List<Term> terms1 = search.search("数据库");
		List<Term> terms2 = search.search("分库分表");
		List<Term> terms3 = search.search("love");

		List<Term> terms = new ArrayList<Term>();
		terms.addAll(terms1);
		terms.addAll(terms2);
		terms.addAll(terms3);

		System.out.println(System.currentTimeMillis());
		for(Term term : terms){
			System.out.println("id = "+term.getId()+" ,  text = "+term.getText());
		}
		
	}
	
	//打印字节
	public static void read() throws IOException{
		Path path = Paths.get("/Users/zhangerqiang/Desktop/temp", "_0.idx");
		byte[] all = Files.readAllBytes(path);
		for(byte b :all){
			System.out.println(b);
		}
	}
	
	public static void write(FSFactory fs) throws IOException{
		
		IndexWriter write = new IndexWriter(fs);
		
		write.write(Arrays.asList("你好","谢谢"), "你好啊，谢谢你啊", 1);
		write.write(Arrays.asList("科创","望京"), "北京望京科创大厦A座", 2);
		write.write(Arrays.asList("love"), "I love you", 3);
		write.write(Arrays.asList("分库分表" , "数据库" ,"一致性"), "传统关系数据库（RDBMS）强调数据一致性甚于性能，是目前所有公司核心业务的首选。但是，随着业务数据量飞速增长，单机数据库存在明显的性能和容量瓶颈，无法满足高并发和高性能的访问需求，因此如何规避传统单机数据库的缺点是目前人们重点关注的问题。分库分表产品在这种背景下应运而生，根据实现方式主要分为：ORM框架层分库分表（MyBatis、Hibernate）、基于Spring AOP方式分库分表、基于JDBC API层的分库分表和基于应用与数据库间代理层的分库分表。京东金融中间件团队在分析了这些实现方式利弊后，根据自身业务特点实现了基于客户端的分库分表中间件CDS，即Completed Database Sharding。", 4);
	}

}
