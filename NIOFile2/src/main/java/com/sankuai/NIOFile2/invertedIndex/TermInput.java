package com.sankuai.NIOFile2.invertedIndex;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * 根据位置去查找文本
 * @author zhangerqiang
 *
 */
public class TermInput {
	
	SeekableByteChannel channel;

	public TermInput(SeekableByteChannel channel) {
		this.channel = channel;
	}
	
	public List<Term> search(List<Integer> docIds) throws IOException{
		List<Term> terms = new ArrayList<Term>();
		ByteBuffer header =ByteBuffer.allocate(8);
		channel.read(header);
		header.flip();
		if(header.getInt() != 0x3fd76c17){
			return null;
		}

		int termCount =header.getInt();
		
		if(termCount < 0){
			return null;
		}
		
		for(int i = 0;i<termCount;i++){
			ByteBuffer len = ByteBuffer.allocate(8);
			channel.read(len);
			len.flip();
			int termLen = len.getInt();
			int termId = len.getInt();
			if(docIds.contains(new Integer(termId))){
				ByteBuffer text = ByteBuffer.allocate(termLen);
				channel.read(text);
				text.flip();
				terms.add(new Term(new String(text.array()), termId));
				text.clear();
			}else{
				channel.position(channel.position()+termLen);
			}
			len.clear();
		}
		header.clear();
		return terms;
	}
	
	public void close(){
		if(channel != null){
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
