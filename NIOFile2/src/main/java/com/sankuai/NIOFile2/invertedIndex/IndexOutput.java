package com.sankuai.NIOFile2.invertedIndex;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

public class IndexOutput {
	
	Path path;
	
	boolean isCreate = true;
	
	public IndexOutput(Path parent,String name) throws IOException{
		Path p = Paths.get(parent.toString(), name);
		path = 	Files.createFile(p);
	}
	
	public void write(Index index) throws IOException{
	
		if(isCreate){
			
			int length = 8+4+4+index.getIndex().getBytes().length+4+index.getIds().size()*4;
			
			ByteBuffer indexBuffer = ByteBuffer.allocate(length);
			indexBuffer.putInt(0x3fd76c17);
			indexBuffer.putInt(1);
			indexBuffer.putInt(length-8-4);
			indexBuffer.putInt(index.getIndex().getBytes().length);
			indexBuffer.put(index.getIndex().getBytes());
			indexBuffer.putInt(index.getIds().size());
			for(Integer id : index.getIds()){
				indexBuffer.putInt(id);
			}
			Files.write(path, indexBuffer.array(), StandardOpenOption.APPEND);
			isCreate = false;
		}else{
				FileChannel channel = FileChannel.open(path,EnumSet.of(StandardOpenOption.READ,StandardOpenOption.WRITE));
				ByteBuffer head = ByteBuffer.allocate(8);
				channel.read(head);
				head.flip();
				if(head.getInt() != 0x3fd76c17){
					return ;
				}
				ByteBuffer countBuffer = ByteBuffer.allocate(4);
				int c = head.getInt();
				c++;
				countBuffer.putInt(c);
				countBuffer.flip();
				channel.position(4);
				channel.write(countBuffer);
	            channel.close();
				countBuffer.clear();
				
				int length = 4+4+index.getIndex().getBytes().length+4+index.getIds().size()*4;
				
				ByteBuffer indexBuffer = ByteBuffer.allocate(length);
				indexBuffer.putInt(length-4);
				indexBuffer.putInt(index.getIndex().getBytes().length);
				indexBuffer.put(index.getIndex().getBytes());
				indexBuffer.putInt(index.getIds().size());
				for(Integer id : index.getIds()){
					indexBuffer.putInt(id);
				}
				Files.write(path, indexBuffer.array(), StandardOpenOption.APPEND);
		}
	}
	
}
