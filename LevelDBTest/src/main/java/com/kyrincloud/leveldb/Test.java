package com.kyrincloud.leveldb;

import java.io.File;
import java.io.IOException;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBFactory;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.WriteOptions;
import org.iq80.leveldb.impl.Iq80DBFactory;

public class Test {
	
	public static void main(String[] args) {
		
		try {
			DBFactory factory = new Iq80DBFactory();
	        Options options = new Options().createIfMissing(true);
	        
			DB db = factory.open(new File("/tmp/leveldb"),options);
			WriteOptions wropt = new WriteOptions().sync(true);
			for(int i = 0 ; i < 1000;i++)
			//db.put("name".getBytes(), "kyrin".getBytes(),wropt);
			System.out.println(4<<20);
			System.out.println(new String(db.get("name".getBytes())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
