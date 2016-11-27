package com.sankuai.NIOFile2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Hello world!
 *
 */
//magic : 0x3fd76c17
public class App {
	
	private final String file_path = "/Users/zhangerqiang/Desktop/nio2_test.idx";

	public static void main( String[] args ){
    	App app = new App();

    	try {
        	//1.创建文件
			Path path = app.createFile();
			
			//2.写文件
	    	ByteBuffer buffer = ByteBuffer.allocate(18);
	    	buffer.putInt(0x3fd76c17);
	    	buffer.putInt(10);
	    	buffer.put("qwqwqwqwqw".getBytes());
			app.appendContext(path,buffer);
			
		} catch (IOException e) {
			System.out.println("创建文件失败");
			e.printStackTrace();
		}
    	
    	
    }
    
    public Path createFile() throws IOException{
    	Path path = Paths.get(file_path);
    	if(Files.isDirectory(path)){
    		return null;
    	}
    	if(!Files.exists(path)){
    		return Files.createFile(path);
    	}
    	return path;
    }
    
    public boolean deleteIfExist(Path path) throws IOException{
    	return Files.deleteIfExists(path);
    }
    
    public void appendContext(Path path,ByteBuffer context) throws IOException{
    	BufferedWriter writer = Files.newBufferedWriter(path,StandardOpenOption.WRITE);
    	writer.append(new String(context.array()));
    	writer.flush();
    }
    
    public ByteBuffer read(Path path,int capacity) throws IOException{
    	ByteBuffer buffer = ByteBuffer.allocate(capacity);
    	SeekableByteChannel channel = Files.newByteChannel(path,StandardOpenOption.READ);
    	channel.read(buffer);
    	return buffer;
    }
    
}
