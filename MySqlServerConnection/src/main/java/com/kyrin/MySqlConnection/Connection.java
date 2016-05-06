package com.kyrin.MySqlConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

import com.kyrin.MySqlConnection.util.EncryptUtils;
import com.kyrin.MySqlConnection.util.HexTranslate;

/**
 * mysql s/c protocol 测试
 * 测试环境
 * 	OS：win7
 * 	mysql_server_version:5.7.4-m14
 * 
 * 			Successful Authentication
 * 
 *    c									s
 *    |			connect()				|
 *    | ------------------------------> |
 *    |									|
 *    |		initial Handshake Packet	|
 *    |	<------------------------------	|
 *    |									|
 *    |	 Handshake Response	 Packet		|
 *    | ------------------------------>	|
 *    |									|
 *    |									|
 *  --|client and server possibly exchange----
 *    |			 further packets		|
 *    |									|
 *    |			OK packet				|
 *    |	<------------------------------	|
 *    |									|
 * ----client and server enter Command Phase-----
 * 	  |									|
 *
 */
public class Connection {
    
	public static void main( String[] args ) throws UnknownHostException, IOException, InterruptedException{
		Socket client=new Socket("127.0.0.1",3306);
		OutputStream os=client.getOutputStream();
		InputStream is=client.getInputStream();
		ByteBuffer bb=ByteBuffer.allocate(1024);
		TimeUnit.MILLISECONDS.sleep(100);
		is.read(bb.array());
		byte[] f8 = new byte[8];   //提取scrambled前八位
		byte[] f12=new byte[12];   //提取scrambled后12位
		int j=1;
		for(byte res:bb.array()){
			System.out.print(HexTranslate.paser(res)+"["+res+"] ");
			if(j>=20 && j<28){
				f8[j-20]=res;
			}
			if(j>=47 && j<59){
				f12[j-47]=res;
			}
			j++;
		}
		System.out.println();
		System.out.println(new String(bb.array()));
		
		
		String scrambled=new String(f8)+new String(f12);
		System.out.println("scrambled="+scrambled);
		//client -> server
		byte[] sha=EncryptUtils.SHA1("root");
		byte[] pass_res=EncryptUtils.SHA1(scrambled+new String(EncryptUtils.SHA1(new String(sha))));
		byte pass_sha1[]=new byte[20];
		for(int i=0;i<20;i++){
			pass_sha1[i]=(byte) (sha[i]^pass_res[i]);
		}
		
		
		
		for(byte b:pass_sha1){
			System.out.print(HexTranslate.paser(b)+" ");
		}
		System.out.println();
		String username="root2";
		String dbname="demo";
		String auth="mysql_native_password";
		int packetLen=4+4+4+1+23+username.getBytes().length+1+1+pass_sha1.length+dbname.getBytes().length+1+auth.getBytes().length;
		ByteBuffer sendServer=ByteBuffer.allocate(packetLen);
		sendServer.put((byte)(packetLen-4));
		sendServer.put((byte)0x00);
		sendServer.put((byte)0x00);
		sendServer.put((byte)0x01);
		
		sendServer.put((byte)0x05);
		sendServer.put((byte)0xa6);
		sendServer.put((byte)0x03);
		sendServer.put((byte)0x00);
		
		sendServer.put((byte)0x00);
		sendServer.put((byte)0x00);
		sendServer.put((byte)0x00);
		sendServer.put((byte)0x01);
		
		sendServer.put((byte)8);
		
		for(int i=0;i<23;i++){
			sendServer.put((byte)0);
		}
		
		sendServer.put(username.getBytes());
		
		sendServer.put((byte)0);
		
		sendServer.put((byte)pass_sha1.length);
		
		sendServer.put(pass_sha1);
		
		sendServer.put(dbname.getBytes());
		sendServer.put((byte)0);
		sendServer.put(auth.getBytes());
		
		
		
		
		os.write(sendServer.array());
		
		for(byte bbbb:sendServer.array()){
			System.out.print(HexTranslate.paser(bbbb)+"["+bbbb+"] ");
		}
		System.out.println();
		os.flush();
		
		
		ByteBuffer bb1=ByteBuffer.allocate(80);
		TimeUnit.MILLISECONDS.sleep(100);
		is.read(bb1.array());
		for(byte res:bb1.array()){
			System.out.print(HexTranslate.paser(res)+"["+res+"] ");
		}
		System.out.println();
		System.out.println(new String(bb1.array()));
		
    }
}

/**

1. client -> server
	<connection>

2. server -> client

4d[77] 00[0] 00[0] 00[0] 0a[10] 35[53] 2e[46] 37[55] 2e[46] 34[52] 2d[45] 6d[109] 31[49] 34[52] 00[0] ca[-54] 00[0] 00[0] 00[0] 5b[91] 37[55] 3e[62] 72[114] 46[70] 46[70] 6a[106] 65[101] 00[0] ff[-1] f7[-9] 21[33] 02[2] 00[0] ff[-1] 80[-128] 15[21] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 3b[59] 36[54] 3d[61] 70[112] 3c[60] 7a[122] 71[113] 4b[75] 5f[95] 7e[126] 79[121] 68[104] 00[0] 6d[109] 79[121] 73[115] 71[113] 6c[108] 5f[95] 6e[110] 61[97] 74[116] 69[105] 76[118] 65[101] 5f[95] 70[112] 61[97] 73[115] 73[115] 77[119] 6f[111] 72[114] 64[100] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 

1              [0a] protocol version 											:0a[10]
string[NUL]    server version													:35[53] 2e[46] 37[55] 2e[46] 34[52] 2d[45] 6d[109] 31[49] 34[52]
4              connection id													:ca[-54] 00[0] 00[0] 00[0]
string[8]      auth-plugin-data-part-1											:5b[91] 37[55] 3e[62] 72[114] 46[70] 46[70] 6a[106] 65[101]
1              [00] filler														:00[0]
2              capability flags (lower 2 bytes)									:ff[-1] f7[-9]
  if more data in the packet:
1              character set													:21[33]
2              status flags														:02[2] 00[0] 
2              capability flags (upper 2 bytes)									:ff[-1] 80[-128]
  if capabilities & CLIENT_PLUGIN_AUTH {
1              length of auth-plugin-data										:15[21]
  } else {
1              [00]																
  }
string[10]     reserved (all [00])												:00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0]
  if capabilities & CLIENT_SECURE_CONNECTION {
string[$len]   auth-plugin-data-part-2 ($len=MAX(13, length of auth-plugin-data - 8))	:3b[59] 36[54] 3d[61] 70[112] 3c[60] 7a[122] 71[113] 4b[75] 5f[95] 7e[126] 79[121] 68[104] 00[0]
  if capabilities & CLIENT_PLUGIN_AUTH {
string[NUL]    auth-plugin name															:6d[109] 79[121] 73[115] 71[113] 6c[108] 5f[95] 6e[110] 61[97] 74[116] 69[105] 76[118] 65[101] 5f[95] 70[112] 61[97] 73[115] 73[115] 77[119] 6f[111] 72[114] 64[100]
  }

  
  3. client -> server
  
4              capability flags, CLIENT_PROTOCOL_41 always set
4              max-packet size
1              character set
string[23]     reserved (all [0])
string[NUL]    username
  if capabilities & CLIENT_PLUGIN_AUTH_LENENC_CLIENT_DATA {
lenenc-int     length of auth-response
string[n]      auth-response
  } else if capabilities & CLIENT_SECURE_CONNECTION {
1              length of auth-response
string[n]      auth-response
  } else {
string[NUL]    auth-response
  }
  if capabilities & CLIENT_CONNECT_WITH_DB {
string[NUL]    database
  }
  if capabilities & CLIENT_PLUGIN_AUTH {
string[NUL]    auth plugin name
  }
  if capabilities & CLIENT_CONNECT_ATTRS {
lenenc-int     length of all key-values
lenenc-str     key
lenenc-str     value
   if-more data in 'length of all key-values', more keys and value pairs
  }
  
**/