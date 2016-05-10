package com.kyrin.MySqlConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import com.kyrin.MySqlConnection.util.HexTranslate;
import com.mysql.jdbc.Security;

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
	
	static int MYSQL_SERVER_VERSION_LEN="5.7.4-m14".length();
	
	static String username="root";

	static String password="root";
	
	static String scrambled;//server -> client 密码加密
	
	static int capabilityFlags;

	private static Socket client;
	
	public static void main( String[] args ) throws UnknownHostException, IOException, InterruptedException, NoSuchAlgorithmException{
		client = new Socket("127.0.0.1",3306);
		
		OutputStream os=client.getOutputStream();
		InputStream is=client.getInputStream();
		
		//2.server -> client
		initialHandShake(is);
	
		
		//3.client -> server
		HandshakeResponse41(os);
		
		//4. OK packate
		OK(is);
		
		ByteBuffer bb=ByteBuffer.allocate(9);
		bb.put((byte)0x05);
		bb.put((byte)0x00);
		bb.put((byte)0x00);
		bb.put((byte)0x00);
		bb.put((byte)0x05);
		bb.put((byte)0x74);
		bb.put((byte)0x65);
		bb.put((byte)0x73);
		bb.put((byte)0x74);
		os.write(bb.array());
		os.flush();
    }
	
	//server -> client
	public static void initialHandShake(InputStream is) throws InterruptedException, IOException{
		ByteBuffer bb=ByteBuffer.allocate(81);
		TimeUnit.MILLISECONDS.sleep(100);
		is.read(bb.array());
		byte[] f8 = new byte[8];   //提取scrambled前八位
		byte[] f12=new byte[12];   //提取scrambled后12位
		byte[] capabilityFlagsLower2=new byte[2];
		byte[] capabilityFlagsUpper2=new byte[2];
		int j=1;
		System.out.println(new String(bb.array()));
		System.out.println("\n[initialHandshake数据包]");
		
		//获取scrambled
		int auth_plugin_data_part_1_index=4+1+MYSQL_SERVER_VERSION_LEN+1+4+1;
		int auth_plugin_data_part_2_index=auth_plugin_data_part_1_index+8+1+2+1+2+2+1+10;
		int capabilityFlags_lower_2_index=4+1+MYSQL_SERVER_VERSION_LEN+1+4+8+1+1;
		int capabilityFlags_upper_2_index=capabilityFlags_lower_2_index+2+1+2;

		for(byte res:bb.array()){
			System.out.print(HexTranslate.paser(res)+"["+res+"] ");
			if(j>=auth_plugin_data_part_1_index && j<auth_plugin_data_part_1_index+8){
				f8[j-auth_plugin_data_part_1_index]=res;
			}
			if(j>=auth_plugin_data_part_2_index && j<auth_plugin_data_part_2_index+12){
				f12[j-auth_plugin_data_part_2_index]=res;
			}
			if(j>=capabilityFlags_lower_2_index && j<capabilityFlags_lower_2_index+2){
				capabilityFlagsLower2[j-capabilityFlags_lower_2_index]=res;
			}
			if(j>=capabilityFlags_upper_2_index && j<capabilityFlags_upper_2_index+2){
				capabilityFlagsUpper2[j-capabilityFlags_upper_2_index]=res;
			}
			j++;
		}
		scrambled=new String(f8)+new String(f12);
		System.out.println("\nscrambled="+scrambled);
		
		byte[] capabilityFlagsBytes=new byte[4];
		capabilityFlagsBytes[0]=capabilityFlagsUpper2[1];
		capabilityFlagsBytes[1]=capabilityFlagsUpper2[0];
		capabilityFlagsBytes[2]=capabilityFlagsLower2[1];
		capabilityFlagsBytes[3]=capabilityFlagsLower2[0];
		
		capabilityFlags=HexTranslate.bytesToInt(capabilityFlagsBytes, 0);
		System.out.println(HexTranslate.paser(capabilityFlagsBytes));
	}
	 
	//client -> server (username and password)
	public static void HandshakeResponse41(OutputStream os) throws NoSuchAlgorithmException, IOException{
		byte pass_sha1[]=Security.scramble411(password,scrambled, "");//加密后的密码，也就是要传输给server端的字节数据
		int packetLen=4+4+4+1+23+username.getBytes().length+1+1+pass_sha1.length;
		ByteBuffer sendServer=ByteBuffer.allocate(packetLen);
		sendServer.put((byte)(packetLen-4));
		sendServer.put((byte)0x00);
		sendServer.put((byte)0x00);
		sendServer.put((byte)0x01);
		
		//这四个字符是如何生成的？
		/*sendServer.put((byte)0x05);
		sendServer.put((byte)0xa6);
		sendServer.put((byte)0x03);
		sendServer.put((byte)0x00);*/
		//sendServer.putInt(0x01868300);//CLIENT_IGNORE_SPACE|CLIENT_MULTI_RESULTS|CLIENT_PS_MULTI_RESULTS|CLIENT_PROTOCOL_41|CLIENT_SECURE_CONNECTION |CLIENT_SESSION_TRACK|CLIENT_DEPRECATE_EOF )
		sendServer.putInt(0x05a60300);
		
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
		
		os.write(sendServer.array());
		os.flush();
		System.out.println("\n[HandshakeResponse41数据包]");
		for(byte bbbb:sendServer.array()){
			System.out.print(HexTranslate.paser(bbbb)+"["+bbbb+"] ");
		}
		System.out.println();
		
	}
	
	//As the client provided the right password and the flags are fine, the server responds with a OK_Packet. That closes auth-phase and switches to the command-phase:
	public static void OK(InputStream is) throws InterruptedException, IOException{
		ByteBuffer bb1=ByteBuffer.allocate(13);
		TimeUnit.MILLISECONDS.sleep(100);
		is.read(bb1.array());
		
		System.out.println("\n[result 数据包]");
		for(byte res:bb1.array()){
			System.out.print(HexTranslate.paser(res)+"["+res+"] ");
		}
		System.out.println("\n"+new String(bb1.array()));
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