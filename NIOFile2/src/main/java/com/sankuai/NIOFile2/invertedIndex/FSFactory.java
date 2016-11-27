package com.sankuai.NIOFile2.invertedIndex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.PKIXRevocationChecker.Option;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * 倒排索引
 * @author zhangerqiang
 *
 */
public class FSFactory {
	
	private Path parent;
	
	public FSFactory(String dir) throws IOException {
		parent = Paths.get(dir);
		ensure();
	}
	
	public TreeSet<Path> getIdxFiles(){
		return getFile(".idx");
	}
	
	public TreeSet<Path> getIdxDoc(){
		return getFile(".doc");
	}
	
	private TreeSet<Path> getFile(String subffix){
		try {
			TreeSet<Path> paths = new TreeSet<Path>(new Comparator<Path>() {
				public int compare(Path o1, Path o2) {
					return o1.getFileName().compareTo(o2.getFileName());
				}
			});
			Stream<Path> p = Files.list(parent);
			Iterator<Path> it = p.iterator();
			while(it.hasNext()){
				Path temp = it.next();
				if(temp.toString().endsWith(subffix)){
					paths.add(temp);
				}
			}
			return paths;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String generateFileNamePrefix(){
		TreeSet<Path> files = getIdxFiles();
		if(files.size()==0){
			return "_0";
		}
		Path ps = files.pollLast();
		String name = ps.toFile().getName();
		String nameCount = name.substring(1,name.length()-4);
		return "_"+(Integer.parseInt(nameCount)+1);
	}
	
	private void ensure() throws IOException{
		if(!Files.isDirectory(parent)){
			throw new IOException("parent is not a dir.");
		}
		if(!Files.exists(parent)){
			throw new IOException("parent is not exist.");
		}
	}
	
	public Path getParent(){
		return parent;
	}
}
