package com.sankuai.NIOFile2.invertedIndex;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

/**
 * 
 * @author zhangerqiang
 *
 */
public class TermOutput {
	
	Path path;
	
	boolean isCreate = true;
	
	public TermOutput(Path parent,String name) throws IOException {
		Path p = Paths.get(parent.toString(), name);
		path = Files.createFile(p);
	}
	
	public void write(Term term) throws IOException{
		if(isCreate){
			ByteBuffer termBuffer = ByteBuffer.allocate(4+4+4+4+term.getText().getBytes().length);
			termBuffer.putInt(0x3fd76c17);
			termBuffer.putInt(1);
			termBuffer.putInt(term.getText().getBytes().length);
			termBuffer.putInt(term.getId());
			termBuffer.put(term.getText().getBytes());
			Files.write(path, termBuffer.array(), StandardOpenOption.APPEND);
			isCreate=false;
		}else{
			FileChannel channel = FileChannel.open(path, EnumSet.of(StandardOpenOption.READ,StandardOpenOption.WRITE));
			ByteBuffer head = ByteBuffer.allocate(8);
			channel.read(head);
			head.flip();
			if(head.getInt() != 0x3fd76c17){
				return;
			}
			
			int count = head.getInt();
			count++;
			ByteBuffer countByte = ByteBuffer.allocate(4);
			countByte.putInt(count);
			countByte.flip();
			channel.position(4);
			channel.write(countByte);
			
			ByteBuffer termBuffer = ByteBuffer.allocate(4+4+term.getText().getBytes().length);
			termBuffer.putInt(term.getText().getBytes().length);
			termBuffer.putInt(term.getId());
			termBuffer.put(term.getText().getBytes());
			Files.write(path, termBuffer.array(), StandardOpenOption.APPEND);
		}
	}
	
}
