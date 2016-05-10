package com.kyrin.MySqlConnection;

/**
 * mysal capability flags (25)
 * @author kyrin
 *
 */
public final class CapabilityFlags {


	public static int CLIENT_LONG_PASSWORD=0x00000001;//Use the improved version of Old Password Authentication.
	
	public static int CLIENT_FOUND_ROWS=0x00000002;//Send found rows instead of affected rows in EOF_Packet.

	public static int CLIENT_LONG_FLAG=0x00000004;//Longer flags in Protocol::ColumnDefinition320.

	/*
	 * Database (schema) name can be specified on connect in Handshake Response Packet.
	 *Server
	 *Supports schema-name in Handshake Response Packet.
	 *
	 *Client
	 *Handshake Response Packet contains a schema-name. 
	 */
	public static int CLIENT_CONNECT_WITH_DB=0x00000008;

	
	public static int CLIENT_NO_SCHEMA=0x00000010;//Server Do not permit database.table.column.

	/*
	 * Compression protocol supported.
     *
	 *Server
     *Supports compression.  
     *
     *Client
     *Switches to Compression compressed protocol after successful authentication.
	 */
	public static int CLIENT_COMPRESS=0x00000020;

	public static int CLIENT_ODBC=0x00000040;//Special handling of ODBC behavior.Note : No special behavior since 3.22.

	/*
	 * Can use LOAD DATA LOCAL.
	 *
	 *Server
	 *Enables the LOCAL INFILE request of LOAD DATA|XML.
	 *
	 *Client	
	 *Will handle LOCAL INFILE request.
	 */
	public static int CLIENT_LOCAL_FILES=0x00000080;

	/*
	 * Server
	 *Parser can ignore spaces before '('.
     *
     *Client
     *Let the parser ignore spaces before '('.
	 */
	public static int CLIENT_IGNORE_SPACE=0x00000100;

	/*
	 *Server
	 *Supports the 4.1 protocol.
     *
     *Client
     *Uses the 4.1 protocol. 
     *Note:
	 *this value was CLIENT_CHANGE_USER in 3.22, unused in 4.0
	 */
	public static int CLIENT_PROTOCOL_41=0x00000200;

	/*
	 * wait_timeout versus wait_interactive_timeout.
	 * Server
	 *Supports interactive and noninteractive clients.
     *
     *Client
     *Client is interactive.
     *See
	 *mysql_real_connect()
	 */
	public static int CLIENT_INTERACTIVE=0x00000400;

	/*Server
	 *Supports SSL.
     *
	 *Client
	 *Switch to SSL after sending the capability-flags.
	 */
	public static int CLIENT_SSL=0x00000800;
	
	/*Client
	 *Do not issue SIGPIPE if network failures occur (libmysqlclient only).
     *
     *See
     *mysql_real_connect()
     */
	public static int CLIENT_IGNORE_SIGPIPE=0x00001000;

	/*
	 * Server
	 *Can send status flags in EOF_Packet.
	 *
	 *Client
	 *Expects status flags in EOF_Packet.
     *
	 *Note
	 *This flag is optional in 3.23, but always set by the server since 4.0.
	 */
	public static int CLIENT_TRANSACTIONS=0x00002000;

	/*
	 * Note
	 *Was named CLIENT_PROTOCOL_41 in 4.1.0.
	 */
	public static int CLIENT_RESERVED=0x00004000;

	/*
	 * Server
	 *Supports Authentication::Native41.
     *
     * Client
     *Supports Authentication::Native41.
	 */
	public static int CLIENT_SECURE_CONNECTION=0x00008000;

	/*
	 * Server
     *Can handle multiple statements per COM_QUERY and COM_STMT_PREPARE.
     *
     * Client
     *May send multiple statements per COM_QUERY and COM_STMT_PREPARE.
     *
     * Note
     *Was named CLIENT_MULTI_QUERIES in 4.1.0, renamed later.
	 */
	public static int CLIENT_MULTI_STATEMENTS=0x00010000;

	/*
	 * 	Server
	 *Can send multiple resultsets for COM_QUERY.
     *
	 *	Client
	 *Can handle multiple resultsets for COM_QUERY.
     *
	 *	Requires
	 *CLIENT_PROTOCOL_41
	 */
	public static int CLIENT_MULTI_RESULTS=0x00020000;

	/*
	 * 	Server
 	 *Can send multiple resultsets for COM_STMT_EXECUTE.
     *
     *	Client
     *Can handle multiple resultsets for COM_STMT_EXECUTE.
	 */
	public static int CLIENT_PS_MULTI_RESULTS=0x00040000;

	/*
	 * Server
	 *Sends extra data in Initial Handshake Packet and supports the pluggable authentication protocol.
	 *
	 * Client
	 *Supports authentication plugins.
     *
	 * Requires
	 *CLIENT_PROTOCOL_41
	 */
	public static int CLIENT_PLUGIN_AUTH=0x00080000;

	/*
	 * Server
	 *Permits connection attributes in Protocol::HandshakeResponse41.
	 *
	 * Client
	 *Sends connection attributes in Protocol::HandshakeResponse41.
	 */
	public static int CLIENT_CONNECT_ATTRS=0x00100000;

	/*
	 * Server
	 *Understands length-encoded integer for auth response data in Protocol::HandshakeResponse41.
	 *
	 * Client
	 *Length of auth response data in Protocol::HandshakeResponse41 is a length-encoded integer.
	 *
	 * Note
	 *The flag was introduced in 5.6.6, but had the wrong value.
	 */
	public static int CLIENT_PLUGIN_AUTH_LENENC_CLIENT_DATA=0x00200000;

	/*
	 * Server
	 *Announces support for expired password extension.
	 *
	 *Client
	 *Can handle expired passwords.
	 */
	public static int CLIENT_CAN_HANDLE_EXPIRED_PASSWORDS=0x00400000;

	/*
	 * Server
	 *Can set SERVER_SESSION_STATE_CHANGED in the Status Flags and send session-state change data after a OK packet.
	 *
	 * Client
	 *Expects the server to send sesson-state changes after a OK packet.
	 */
	public static int CLIENT_SESSION_TRACK=0x00800000;

	/*
	 * Server
	 *Can send OK after a Text Resultset.
	 *
	 * Client
	 *Expects an OK (instead of EOF) after the resultset rows of a Text Resultset.s
	 */
	public static int CLIENT_DEPRECATE_EOF=0x01000000;

	static int [] flags=new int[]{CLIENT_LONG_PASSWORD, CLIENT_FOUND_ROWS,CLIENT_LONG_FLAG,CLIENT_CONNECT_WITH_DB,CLIENT_NO_SCHEMA,
			CLIENT_COMPRESS,CLIENT_ODBC,CLIENT_LOCAL_FILES,CLIENT_IGNORE_SPACE,CLIENT_PROTOCOL_41,
			CLIENT_INTERACTIVE,CLIENT_SSL,CLIENT_IGNORE_SIGPIPE, CLIENT_TRANSACTIONS,CLIENT_RESERVED,
			CLIENT_SECURE_CONNECTION, CLIENT_MULTI_STATEMENTS,CLIENT_MULTI_RESULTS,CLIENT_PS_MULTI_RESULTS,
            CLIENT_PLUGIN_AUTH,CLIENT_CONNECT_ATTRS,CLIENT_PLUGIN_AUTH_LENENC_CLIENT_DATA,CLIENT_CAN_HANDLE_EXPIRED_PASSWORDS,
            CLIENT_SESSION_TRACK,CLIENT_DEPRECATE_EOF
		};
	
	public static int calculate(int capabilityFlags){
		int clientParam=0;
		for(int i=0;i<flags.length;i++){
			if((capabilityFlags&flags[i])==flags[i]){
				clientParam|=flags[i];					//用|拼接你要传输的多个参数\
				System.out.println(i);
			}
		}
		return clientParam;
	}
	
	public static void main(String[] args) {
		System.out.println(Integer.toHexString(calculate(0x05a60300)));
		System.out.println(Integer.toHexString(CLIENT_IGNORE_SPACE|CLIENT_MULTI_RESULTS|CLIENT_PS_MULTI_RESULTS|CLIENT_PROTOCOL_41|CLIENT_SECURE_CONNECTION |CLIENT_DEPRECATE_EOF|CLIENT_SESSION_TRACK ));
	}
	
}
