package com.sankuai.meituan.canyin.risk.virbius;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.google.common.base.Stopwatch;

public class TestZip {
	
    private TestZip() {
        //noop
    }

    public static byte[] gzip(byte[] ungzipped) {
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try {
            final GZIPOutputStream gzipOutputStream = new GZIPOutputStream(bytes);
            gzipOutputStream.write(ungzipped);
            gzipOutputStream.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return bytes.toByteArray();
    }

    public static byte[] ungzip(final byte[] gzipped) {
        byte[] ungzipped = new byte[0];
        try {
            final GZIPInputStream inputStream = new GZIPInputStream(new ByteArrayInputStream(gzipped));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(gzipped.length);
            final byte[] buffer = new byte[1500];
            int bytesRead = 0;
            while (bytesRead != -1) {
                bytesRead = inputStream.read(buffer, 0, 1500);
                if (bytesRead != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                }
            }
            ungzipped = byteArrayOutputStream.toByteArray();
            inputStream.close();
            byteArrayOutputStream.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return ungzipped;
    }

    
	
   public static void main(String[] args) throws IOException {
       String s = "{\"fingerprint_info\":\"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\"}";
       s+=s;
       for(int i = 0 ;i<10;i++){
	       Stopwatch stop = Stopwatch.createStarted();
	       byte[] gzip = gzip(s.getBytes(Charset.forName("UTF-8")));
	       byte[] ungzip = ungzip(gzip);
	       System.out.println(new String(ungzip));
	       System.out.println("压缩前长度:"+s.getBytes().length+" , 压缩后："+gzip.length + " , 压缩比率："+gzip.length*1.0/s.getBytes().length+" , 解/压总耗时 ： "+stop.stop());
       }
   }
       
}