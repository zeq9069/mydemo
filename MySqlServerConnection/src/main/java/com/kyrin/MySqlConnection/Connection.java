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
 * mysql s/c protocol 测试 测试环境 OS：win7 mysql_server_version:5.7.4-m14
 * 
 * Successful Authentication
 * 
 * c s | connect() | | ------------------------------> | | | | initial Handshake
 * Packet | | <------------------------------ | | | | Handshake Response Packet
 * | | ------------------------------> | | | | | --|client and server possibly
 * exchange---- | further packets | | | | OK packet | |
 * <------------------------------ | | | ----client and server enter Command
 * Phase----- | |
 *
 */
public class Connection {

	static int MYSQL_SERVER_VERSION_LEN = "5.7.4-m14".length();

	static String username = "root";

	static String password = "root";

	static String scrambled;// server -> client 密码加密

	static int capabilityFlags;

	private static Socket client;

	public static void main(String[] args) throws UnknownHostException,
			IOException, InterruptedException, NoSuchAlgorithmException {
		client = new Socket("127.0.0.1", 3306);

		OutputStream os = client.getOutputStream();
		InputStream is = client.getInputStream();

		// 2.server -> client
		initialHandShake(is);

		// 3.client -> server
		HandshakeResponse41(os);

		// 4. OK packate
		authResponse(is);

		// 5.send command Text protocol
		com_select(is, os);
		// com_sleep(is, os);
		// com_quit(is, os);
		// com_init_db(is, os);
		// com_create_db(is, os);
		// com_drop_db(is, os);
		// com_statistic(is, os);
		// com_connect(is, os);
		// com_debug(is, os);
		// com_ping(is, os);
		// com_time(is, os);
		// com_delayed_insert(is, os);
		//com_reset_connection(is, os);
		//com_daemon(is, os);
	}

	// server -> client
	public static void initialHandShake(InputStream is)
			throws InterruptedException, IOException {
		ByteBuffer bb = ByteBuffer.allocate(81);
		TimeUnit.MILLISECONDS.sleep(100);
		is.read(bb.array());
		byte[] f8 = new byte[8]; // 提取scrambled前八位
		byte[] f12 = new byte[12]; // 提取scrambled后12位
		byte[] capabilityFlagsLower2 = new byte[2];
		byte[] capabilityFlagsUpper2 = new byte[2];
		int j = 1;
		
		printf(bb.array(),"[initialHandshak response packate]");

		// 获取scrambled
		int auth_plugin_data_part_1_index = 4 + 1 + MYSQL_SERVER_VERSION_LEN
				+ 1 + 4 + 1;
		int auth_plugin_data_part_2_index = auth_plugin_data_part_1_index + 8
				+ 1 + 2 + 1 + 2 + 2 + 1 + 10;
		int capabilityFlags_lower_2_index = 4 + 1 + MYSQL_SERVER_VERSION_LEN
				+ 1 + 4 + 8 + 1 + 1;
		int capabilityFlags_upper_2_index = capabilityFlags_lower_2_index + 2 + 1 + 2;

		for (byte res : bb.array()) {
			if (j >= auth_plugin_data_part_1_index
					&& j < auth_plugin_data_part_1_index + 8) {
				f8[j - auth_plugin_data_part_1_index] = res;
			}
			if (j >= auth_plugin_data_part_2_index
					&& j < auth_plugin_data_part_2_index + 12) {
				f12[j - auth_plugin_data_part_2_index] = res;
			}
			if (j >= capabilityFlags_lower_2_index
					&& j < capabilityFlags_lower_2_index + 2) {
				capabilityFlagsLower2[j - capabilityFlags_lower_2_index] = res;
			}
			if (j >= capabilityFlags_upper_2_index
					&& j < capabilityFlags_upper_2_index + 2) {
				capabilityFlagsUpper2[j - capabilityFlags_upper_2_index] = res;
			}
			j++;
		}
		scrambled = new String(f8) + new String(f12);
		System.out.println("\nscrambled=" + scrambled);

		byte[] capabilityFlagsBytes = new byte[4];
		capabilityFlagsBytes[0] = capabilityFlagsUpper2[1];
		capabilityFlagsBytes[1] = capabilityFlagsUpper2[0];
		capabilityFlagsBytes[2] = capabilityFlagsLower2[1];
		capabilityFlagsBytes[3] = capabilityFlagsLower2[0];

		capabilityFlags = HexTranslate.bytesToInt(capabilityFlagsBytes, 0);
		System.out.println(HexTranslate.paser(capabilityFlagsBytes));
	}

	// client -> server (username and password)
	public static void HandshakeResponse41(OutputStream os)
			throws NoSuchAlgorithmException, IOException {
		byte pass_sha1[] = Security.scramble411(password, scrambled, "");// 加密后的密码，也就是要传输给server端的字节数据
		int packetLen = 4 + 4 + 4 + 1 + 23 + username.getBytes().length + 1 + 1
				+ pass_sha1.length;
		ByteBuffer sendServer = ByteBuffer.allocate(packetLen);
		sendServer.put((byte) (packetLen - 4));
		sendServer.put((byte) 0x00);
		sendServer.put((byte) 0x00);
		sendServer.put((byte) 0x01);

		// 这四个字符是如何生成的？
		/*
		 * sendServer.put((byte)0x05); sendServer.put((byte)0xa6);
		 * sendServer.put((byte)0x03); sendServer.put((byte)0x00);
		 */
		// sendServer.putInt(0x01868300);//CLIENT_IGNORE_SPACE|CLIENT_MULTI_RESULTS|CLIENT_PS_MULTI_RESULTS|CLIENT_PROTOCOL_41|CLIENT_SECURE_CONNECTION
		// |CLIENT_SESSION_TRACK|CLIENT_DEPRECATE_EOF )
		sendServer.putInt(0x05a60300);

		sendServer.put((byte) 0x00);
		sendServer.put((byte) 0x00);
		sendServer.put((byte) 0x00);
		sendServer.put((byte) 0x01);

		sendServer.put((byte) 8);

		for (int i = 0; i < 23; i++) {
			sendServer.put((byte) 0);
		}

		sendServer.put(username.getBytes());

		sendServer.put((byte) 0);

		sendServer.put((byte) pass_sha1.length);

		sendServer.put(pass_sha1);

		os.write(sendServer.array());
		os.flush();
		System.out.println("\n[HandshakeResponse41 数据包]");
		for (byte bbbb : sendServer.array()) {
			System.out.print(HexTranslate.paser(bbbb) + "[" + bbbb + "] ");
		}
		System.out.println();

	}

	// As the client provided the right password and the flags are fine, the
	// server responds with a OK_Packet. That closes auth-phase and switches to
	// the command-phase:
	public static void authResponse(InputStream is) throws InterruptedException,
			IOException {
		ByteBuffer arr = ByteBuffer.allocate(13);
		TimeUnit.MILLISECONDS.sleep(100);
		is.read(arr.array());
		printf(arr.array(),"[auth reponse packate]");
	}

	// send command "SELECT USER()" (正确)
	public static void com_select(InputStream is, OutputStream os)
			throws IOException, InterruptedException {
		
		ByteBuffer bb = ByteBuffer.allocate(18);
		bb.put((byte) 0x0e);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x03);
		bb.put((byte) 0x73);
		bb.put((byte) 0x65);
		bb.put((byte) 0x6c);
		bb.put((byte) 0x65);
		bb.put((byte) 0x63);
		bb.put((byte) 0x74);
		bb.put((byte) 0x20);
		bb.put((byte) 0x55);
		bb.put((byte) 0x53);
		bb.put((byte) 0x45);
		bb.put((byte) 0x52);
		bb.put((byte) 0x28);
		bb.put((byte) 0x29);
		os.write(bb.array());
		os.flush();
		ByteBuffer arr = ByteBuffer.allocate(74);
		TimeUnit.SECONDS.sleep(1);
		is.read(arr.array());
		printf(arr.array(),"[com_select response packate]");
	}

	// sleep
	public static void com_sleep(InputStream is, OutputStream os)
			throws IOException, InterruptedException {

		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.put((byte) 0x01);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		os.write(bb.array());
		os.flush();
		ByteBuffer arr = ByteBuffer.allocate(74);
		TimeUnit.SECONDS.sleep(1);
		is.read(arr.array());
		printf(arr.array(),"[com_sleep response packate]");
	}

	// quit
	public static void com_quit(InputStream is, OutputStream os)
			throws IOException, InterruptedException {

		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.put((byte) 0x01);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x01);
		os.write(bb.array());
		os.flush();
		ByteBuffer arr = ByteBuffer.allocate(10);
		TimeUnit.SECONDS.sleep(1);
		is.read(arr.array());
		printf(arr.array(),"[com_quit response packate]");
	}

	// init db test(正确)
	public static void com_init_db(InputStream is, OutputStream os)
			throws IOException, InterruptedException {

		ByteBuffer bb = ByteBuffer.allocate(9);
		bb.put((byte) 0x05);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x02);
		bb.put((byte) 0x74);
		bb.put((byte) 0x65);
		bb.put((byte) 0x73);
		bb.put((byte) 0x74);
		os.write(bb.array());
		os.flush();
		ByteBuffer arr = ByteBuffer.allocate(36);
		TimeUnit.SECONDS.sleep(1);
		is.read(arr.array());
		printf(arr.array(),"[com_init_db response packate]");
	}

	// create db test
	public static void com_create_db(InputStream is, OutputStream os)
			throws IOException, InterruptedException {

		ByteBuffer bb = ByteBuffer.allocate(9);
		bb.put((byte) 0x05);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x05);
		bb.put((byte) 0x74);
		bb.put((byte) 0x65);
		bb.put((byte) 0x73);
		bb.put((byte) 0x74);
		os.write(bb.array());
		os.flush();
		ByteBuffer arr = ByteBuffer.allocate(36);
		TimeUnit.SECONDS.sleep(1);
		is.read(arr.array());
		printf(arr.array(),"[com_create-db response packate]");
	}

	// drop db test
	public static void com_drop_db(InputStream is, OutputStream os)
			throws IOException, InterruptedException {

		ByteBuffer bb = ByteBuffer.allocate(9);
		bb.put((byte) 0x05);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x06);
		bb.put((byte) 0x74);
		bb.put((byte) 0x65);
		bb.put((byte) 0x73);
		bb.put((byte) 0x74);
		os.write(bb.array());
		os.flush();
		ByteBuffer arr = ByteBuffer.allocate(36);
		TimeUnit.SECONDS.sleep(1);
		is.read(arr.array());
		printf(arr.array(),"[com_dop_db response packate]");
	}

	// As of MySQL 5.7.11, COM_REFRESH is deprecated and will be removed in a
	// future version of MySQL. Instead, use mysql_query() to execute a FLUSH
	// statement.
	public static void com_refersh(InputStream is, OutputStream os)
			throws IOException, InterruptedException {

	}

	// COM_SHUTDOWN is deprecated as of MySQL 5.7.9 and removed in MySQL 5.8.0.
	// Instead, use mysql_query() to execute a SHUTDOWN statement.
	public static void com_shutdown(InputStream is, OutputStream os)
			throws IOException, InterruptedException {

	}

	// get a list of active threads (正确)
	public static void com_statistic(InputStream is, OutputStream os)
			throws IOException, InterruptedException {

		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.put((byte) 0x01);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x09);
		os.write(bb.array());
		os.flush();
		ByteBuffer arr = ByteBuffer.allocate(200);
		TimeUnit.SECONDS.sleep(1);
		is.read(arr.array());
		printf(arr.array(),"[com_statistic response packate]");
	}

	// As of MySQL 5.7.11, COM_PROCESS_INFO is deprecated and will be removed in
	// a future version of MySQL. Instead, use mysql_query() to execute a SHOW
	// PROCESSLIST statement.
	public static void com_process_info(InputStream is, OutputStream os)
			throws IOException, InterruptedException {

	}

	public static void com_connect(InputStream is, OutputStream os)
			throws IOException, InterruptedException {

		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.put((byte) 0x01);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x0b);
		os.write(bb.array());
		os.flush();
		ByteBuffer arr = ByteBuffer.allocate(200);
		TimeUnit.SECONDS.sleep(1);
		is.read(arr.array());
		printf(arr.array(),"[com_connect response packate]");
	}

	// As of MySQL 5.7.11, COM_PROCESS_KILL is deprecated and will be removed in
	// a future version of MySQL. Instead, use mysql_query() to execute a KILL
	// statement.
	public static void com_process_kill(InputStream is, OutputStream os)
			throws IOException, InterruptedException {

	}

	// (正确)
	// COM_DEBUG triggers a dump on internal debug info to stdout of the
	// mysql-server.
	// The SUPER privilege is required for this operation.
	public static void com_debug(InputStream is, OutputStream os)
			throws IOException, InterruptedException {

		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.put((byte) 0x01);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x0d);
		os.write(bb.array());
		os.flush();
		ByteBuffer arr = ByteBuffer.allocate(200);
		TimeUnit.SECONDS.sleep(1);
		is.read(arr.array());
		printf(arr.array(),"[com_debug response packate]");
	}

	// check if the server is alive (正确)
	public static void com_ping(InputStream is, OutputStream os)
			throws IOException, InterruptedException {

		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.put((byte) 0x01);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x0e);
		os.write(bb.array());
		os.flush();
		ByteBuffer arr = ByteBuffer.allocate(200);
		TimeUnit.SECONDS.sleep(1);
		is.read(arr.array());
		printf(arr.array(),"[com_ping response packate]");
	}

	// an internal command in the server
	public static void com_time(InputStream is, OutputStream os)
			throws IOException, InterruptedException {

		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.put((byte) 0x01);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x0f);

		os.write(bb.array());
		os.flush();
		ByteBuffer arr = ByteBuffer.allocate(200);
		TimeUnit.SECONDS.sleep(1);
		is.read(arr.array());
		printf(arr.array(),"[com_time response packate]");
	}

	// an internal command in the server
	public static void com_delayed_insert(InputStream is, OutputStream os)
			throws IOException, InterruptedException {

		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.put((byte) 0x01);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x10);
		os.write(bb.array());
		os.flush();
		ByteBuffer arr = ByteBuffer.allocate(200);
		TimeUnit.SECONDS.sleep(1);
		is.read(arr.array());
		printf(arr.array(),"[com_delayed_insert response packate]");
	}

	// COM_CHANGE_USER changes the user of the current connection and reset the
	// connection state.
	public static void com_change_user(InputStream is, OutputStream os)
			throws IOException, InterruptedException {

	}

	// 正确
	// Resets the session state; more lightweight than COM_CHANGE_USER because
	// it does not close and reopen the connection, and does not re-authenticate
	public static void com_reset_connection(InputStream is, OutputStream os)
			throws IOException, InterruptedException {

		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.put((byte) 0x01);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x1f);
		os.write(bb.array());
		os.flush();
		ByteBuffer arr = ByteBuffer.allocate(200);
		TimeUnit.SECONDS.sleep(1);
		is.read(arr.array());
		printf(arr.array(),"[com_reset_connection response packate]");
	}
	
	
	public static void com_daemon(InputStream is, OutputStream os)
			throws IOException, InterruptedException {
		ByteBuffer bb = ByteBuffer.allocate(5);
		bb.put((byte) 0x01);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x00);
		bb.put((byte) 0x1d);
		os.write(bb.array());
		os.flush();
		ByteBuffer arr = ByteBuffer.allocate(200);
		TimeUnit.SECONDS.sleep(1);
		is.read(arr.array());
		printf(arr.array(),"[com_daemon response packate]");
	}
	
	private static void printf(byte [] arry,String name){
		System.out.println(String.format("\n%s", name));
		for (byte res : arry) {
			System.out.print(HexTranslate.paser(res) + "[" + res + "] ");
		}
		System.out.println("\n" + new String(arry));
	}
	
}

/**
 * 1. client -> server <connection>
 * 
 * 2. server -> client
 * 
 * 4d[77] 00[0] 00[0] 00[0] 0a[10] 35[53] 2e[46] 37[55] 2e[46] 34[52] 2d[45]
 * 6d[109] 31[49] 34[52] 00[0] ca[-54] 00[0] 00[0] 00[0] 5b[91] 37[55] 3e[62]
 * 72[114] 46[70] 46[70] 6a[106] 65[101] 00[0] ff[-1] f7[-9] 21[33] 02[2] 00[0]
 * ff[-1] 80[-128] 15[21] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0]
 * 00[0] 3b[59] 36[54] 3d[61] 70[112] 3c[60] 7a[122] 71[113] 4b[75] 5f[95]
 * 7e[126] 79[121] 68[104] 00[0] 6d[109] 79[121] 73[115] 71[113] 6c[108] 5f[95]
 * 6e[110] 61[97] 74[116] 69[105] 76[118] 65[101] 5f[95] 70[112] 61[97] 73[115]
 * 73[115] 77[119] 6f[111] 72[114] 64[100]
 * 
 * 
 * 1 [0a] protocol version :0a[10] string[NUL] server version :35[53] 2e[46]
 * 37[55] 2e[46] 34[52] 2d[45] 6d[109] 31[49] 34[52] 4 connection id :ca[-54]
 * 00[0] 00[0] 00[0] string[8] auth-plugin-data-part-1 :5b[91] 37[55] 3e[62]
 * 72[114] 46[70] 46[70] 6a[106] 65[101] 1 [00] filler :00[0] 2 capability flags
 * (lower 2 bytes) :ff[-1] f7[-9] if more data in the packet: 1 character set
 * :21[33] 2 status flags :02[2] 00[0] 2 capability flags (upper 2 bytes)
 * :ff[-1] 80[-128] if capabilities & CLIENT_PLUGIN_AUTH { 1 length of
 * auth-plugin-data :15[21] } else { 1 [00] } string[10] reserved (all [00])
 * :00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] 00[0] if capabilities
 * & CLIENT_SECURE_CONNECTION { string[$len] auth-plugin-data-part-2
 * ($len=MAX(13, length of auth-plugin-data - 8)) :3b[59] 36[54] 3d[61] 70[112]
 * 3c[60] 7a[122] 71[113] 4b[75] 5f[95] 7e[126] 79[121] 68[104] 00[0] if
 * capabilities & CLIENT_PLUGIN_AUTH { string[NUL] auth-plugin name :6d[109]
 * 79[121] 73[115] 71[113] 6c[108] 5f[95] 6e[110] 61[97] 74[116] 69[105] 76[118]
 * 65[101] 5f[95] 70[112] 61[97] 73[115] 73[115] 77[119] 6f[111] 72[114] 64[100]
 * }
 * 
 * 
 * 3. client -> server
 * 
 * 4 capability flags, CLIENT_PROTOCOL_41 always set 4 max-packet size 1
 * character set string[23] reserved (all [0]) string[NUL] username if
 * capabilities & CLIENT_PLUGIN_AUTH_LENENC_CLIENT_DATA { lenenc-int length of
 * auth-response string[n] auth-response } else if capabilities &
 * CLIENT_SECURE_CONNECTION { 1 length of auth-response string[n] auth-response
 * (password ，SHA1输出160bit 也就是20字节) } else { string[NUL] auth-response (password
 * ，SHA1输出160bit 也就是20字节) } if capabilities & CLIENT_CONNECT_WITH_DB {
 * string[NUL] database } if capabilities & CLIENT_PLUGIN_AUTH { string[NUL]
 * auth plugin name } if capabilities & CLIENT_CONNECT_ATTRS { lenenc-int length
 * of all key-values lenenc-str key lenenc-str value if-more data in 'length of
 * all key-values', more keys and value pairs }
 **/
