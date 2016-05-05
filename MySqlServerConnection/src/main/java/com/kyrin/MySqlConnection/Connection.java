package com.kyrin.MySqlConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

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
		Socket client=new Socket("localhost",3306);
		OutputStream os=client.getOutputStream();
		InputStream is=client.getInputStream();
		ByteBuffer bb=ByteBuffer.allocate(78);
		TimeUnit.MILLISECONDS.sleep(1024);
		is.read(bb.array());
		for(byte res:bb.array()){
			System.out.print(HexTranslate.paser(res)+"["+res+"] ");
		}
		System.out.println();
		System.out.println(new String(bb.array()));
		
		
    }
	
}

/**

1. client -> server
	<connection>

2. server -> client

4d[77] 00[0] 00[0] 00[0] 0a[10] 35[53] 2e[46] 37[55] 2e[46] 34[52] 2d[45] 6d[109] 31[49] 34[52] 00[0] 1a[26] 00[0] 00[0] 00[0] 35[53] 3c[60] 6c[108] 2c[44] 32[50] 58[88] 46[70] 35[53] 00[0] [-1] [-9] 21[33] 02[2] 00[0] [-1] [-128] 15[21] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 76[118] 72[114] 5c[92] 53[83] 6a[106] 3f[63] 38[56] 3d[61] 66[102] 6d[109] 71[113] 6c[108] 00[0] 6d[109] 79[121] 73[115] 71[113] 6c[108] 5f[95] 6e[110] 61[97] 74[116] 69[105] 76[118] 65[101] 5f[95] 70[112] 61[97] 73[115] 73[115] 77[119] 6f[111] 72[114] 64[100] 00[0]

1              [0a] protocol version 											:0a[10]
string[NUL]    server version													:35[53] 2e[46] 37[55] 2e[46] 34[52] 2d[45] 6d[109] 31[49] 34[52] 00[0]
4              connection id													:1a[26] 00[0] 00[0] 00[0]
string[8]      auth-plugin-data-part-1											:35[53] 3c[60] 6c[108] 2c[44] 32[50] 58[88] 46[70] 35[53]
1              [00] filler														:00[0]
2              capability flags (lower 2 bytes)									:NULL
  if more data in the packet:
1              character set													:21[33]
2              status flags														:02[2] 00[0]
2              capability flags (upper 2 bytes)									:NULL
  if capabilities & CLIENT_PLUGIN_AUTH {
1              length of auth-plugin-data										:15[21]
  } else {
1              [00]																
  }
string[10]     reserved (all [00])												:00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0]
  if capabilities & CLIENT_SECURE_CONNECTION {
string[$len]   auth-plugin-data-part-2 ($len=MAX(13, length of auth-plugin-data - 8))	:76[118] 72[114] 5c[92] 53[83] 6a[106] 3f[63] 38[56] 3d[61] 66[102] 6d[109] 71[113] 6c[108] 00[0]
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
string[n]      auth-response  (password  ，SHA1输出160bit 也就是20字节)
  } else {
string[NUL]    auth-response (password  ，SHA1输出160bit 也就是20字节)
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