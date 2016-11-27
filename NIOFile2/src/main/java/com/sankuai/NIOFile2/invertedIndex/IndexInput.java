package com.sankuai.NIOFile2.invertedIndex;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * 索引创建
 * @author zhangerqiang
 *
 */
public class IndexInput {
	
	SeekableByteChannel channel;
	
	
	public IndexInput(SeekableByteChannel channel){
		this.channel = channel;
	}
	
	public List<Integer> search(String word) throws IOException{
		List<Integer> ids = new ArrayList<Integer>();
		ByteBuffer head= ByteBuffer.allocate(8);
		channel.read(head);
		head.flip();
		if(head.getInt() != 0x3fd76c17){
			return null;
		}
		
		int idxCount = head.getInt();//获取索引的数量
		
		if(idxCount == 0){
			return null;
		}
		
		for(int i = 0;i<idxCount;i++){
			ByteBuffer idxHead= ByteBuffer.allocate(4);
			channel.read(idxHead);
			idxHead.flip();
			ByteBuffer body = ByteBuffer.allocate(idxHead.getInt());
			channel.read(body);
			List<Integer> id = searchPos(body, word);
			if(id!=null){
				ids.addAll(id);
			}
		}
		return ids;
	}
	
	private List<Integer> searchPos(ByteBuffer buffer,String word){
		buffer.flip();
		List<Integer> ids = new ArrayList<Integer>();
		int idx_len = buffer.getInt();
		byte[] idx = new byte[idx_len];
		buffer.get(idx);
		if(word.equals((new String(idx)).toString())){
			int idCount = buffer.getInt();
			for(int i =0;i<idCount;i++){
				ids.add(buffer.getInt());
			}
		}
		return ids;
	}

}
