package com.sankuai.NIOFile2.invertedIndex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * 搜索
 * @author zhangerqiang
 *
 */
public class IndexSearch {
	
	TreeSet<Path> idxFiles;
	
	TreeSet<Path> termFiles;
	
	public IndexSearch(FSFactory factory) {
		idxFiles = factory.getIdxFiles();
		termFiles = factory.getIdxDoc();
	}
	
	public List<Term> search(String word) throws IOException{
		List<Term> terms = new ArrayList<Term>();
		for(Path path : idxFiles){
			IndexInput idxInput = new IndexInput(Files.newByteChannel(path,StandardOpenOption.READ));
			List<Integer> ids = idxInput.search(word);
			idxInput.close();
			String name = path.toFile().getName();
			TermInput termInput = new TermInput(Files.newByteChannel(path.getParent().resolve(name.substring(0,name.length()-4)+".doc"),StandardOpenOption.READ));
			List<Term> ts = termInput.search(ids);
			termInput.close();
			terms.addAll(ts);
		}
		return terms;
	}

}
