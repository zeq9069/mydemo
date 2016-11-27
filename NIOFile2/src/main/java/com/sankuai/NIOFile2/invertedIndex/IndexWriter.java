package com.sankuai.NIOFile2.invertedIndex;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class IndexWriter {
	
	IndexOutput indexOuput;
	
	TermOutput termOutput;
	
	public IndexWriter(FSFactory factory) throws IOException {
		String name = factory.generateFileNamePrefix();
		indexOuput = new IndexOutput(factory.getParent(), name+".idx");
		termOutput = new TermOutput(factory.getParent(), name+".doc");
	}
	
	public void write(List<String> index , String text , int startId) throws IOException{
		Term term = new Term(text,startId);
		termOutput.write(term);

		for(String s:index){
			Index idx = new Index(s, Arrays.asList(startId));
			indexOuput.write(idx);
		}
	}
}
