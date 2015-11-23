package com.test.Alldemo.FastdfsProtocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * 注意：
 * 	fastdfs-client-java客户端对服务端的消息包解码有误
 * 
 * (参照fastdfs-client-java源代码) fastdfs 二进制通信协议：
 * 
 * request : 
 * 	message = header + body 
 * 		header = 8 byte + 1byte + 1byte = pkg_len( 8byte ) + cmd( 1byte ) + status_check( 1byte ) 
 * 		body = pkg_len byte (需要编解码)
 * 
 * response : 
 * 	message = header + body 
 * 		header = 8 byte + 1byte + 1byte = pkg_len( 8byte ) + (byte)100 + status_check( 1byte ) 
 * 		body = pkg_len byte (需要编解码)
 * 
 * @author zhangerqiang
 *
 */
public class FastDFSSocketClient {

	private static Socket client;
	private static final String TRACKER_SERVER_IP="202.205.176.154";
	private static final int TRACKER_SERVER_PORT=22122;

	public static void list_group() throws UnknownHostException, IOException {

		client = new Socket(TRACKER_SERVER_IP, TRACKER_SERVER_PORT);
		client.setSoTimeout(30000);
		OutputStream out = client.getOutputStream();
		byte[] p = packHeader((byte) 91, 0, (byte) 0);
		out.write(p);
		InputStream in = client.getInputStream();
		byte[] header = new byte[8 + 1 + 1];
		in.read(header);
		if (header[9] != 0) {
			return;
		}
		if (header[8] != (byte) 100) {
			return;
		}
		byte[] body = new byte[(int)buff2long(header, 0)];
		in.read(body);
		resolveList_group(body);
	}
	
	public static void list_storage(String groupName, String storageIpAddr) throws UnknownHostException, IOException {
		int len=0;
		client = new Socket(TRACKER_SERVER_IP, TRACKER_SERVER_PORT);
		client.setSoTimeout(30000);
		OutputStream out = client.getOutputStream();
			byte [] bs = groupName.getBytes("UTF-8");
			byte[] bGroupName = new byte[16];
			
			if (bs.length <= 16)
			{
				len = bs.length;
			}
			else
			{
				len = 16;
			}
			Arrays.fill(bGroupName, (byte)0);
			System.arraycopy(bs, 0, bGroupName, 0, len);
			
			int ipAddrLen;
			byte[] bIpAddr;
			if (storageIpAddr != null && storageIpAddr.length() > 0)
			{
				bIpAddr = storageIpAddr.getBytes("UTF-8");
				if (bIpAddr.length < 16)
				{
					ipAddrLen = bIpAddr.length;
				}
				else
				{
					ipAddrLen = 16 - 1;
				}
			}
			else
			{
				bIpAddr = null;
				ipAddrLen = 0;
			}
			
			byte[] header = packHeader((byte)92, 16+ipAddrLen, (byte)0);
			byte[] wholePkg = new byte[header.length + bGroupName.length + ipAddrLen];
			System.arraycopy(header, 0, wholePkg, 0, header.length);
			System.arraycopy(bGroupName, 0, wholePkg, header.length, bGroupName.length);
			if (ipAddrLen > 0)
			{
				System.arraycopy(bIpAddr, 0, wholePkg, header.length + bGroupName.length, ipAddrLen);
			}
			out.write(wholePkg);
			byte[] res_header=new byte[8+1+1];
			InputStream in=client.getInputStream();
			in.read(res_header);
			if (res_header[9] != 0) {
				return;
			}
			if (res_header[8] != (byte) 100) {
				return;
			}
			byte[] body = new byte[(int)buff2long(res_header, 0)];
			in.read(body);
			//计算包含几个storage_server信息
			if(body.length%600!=0){
				return;
			}
			int count=body.length/600;
			for(int i=1;i<=count;i++){
				System.out.println("开始解析storage : "+i);
				resolveList_storage(body,i);
			}
			
	}
	
	
	public static void resolveList_group(byte[] text){
		
		System.out.println("[ 开始解析list_group命令的消息包 ]");

		int index=0;
		
		//groupName
		String  groupName="";
		for(int i=0;i<16+1;i++){
			groupName+=(char)text[i+index];
		}
		index+=17;
		System.out.println(String.format("groupName : %s", groupName));
		
		//totalMB
		byte[] totalMB_=new byte[8];
		for(int i=0;i<8;i++){
			totalMB_[i]=text[i+index];
		}
		long totalMB=buff2long(totalMB_, 0);
		index+=8;
		System.out.println(String.format("totalMB : %d MB",totalMB));
		
		//freeMB
		byte[] freeMB_=new byte[8];
		for(int i=0;i<8;i++){
			freeMB_[i]=text[i+index];
		}
		long freeMB=buff2long(freeMB_, 0);
		index+=8;
		System.out.println(String.format("freeMB : %d MB",freeMB));
		
		//trunkFreeMB
		byte[] trunkFreeMB_=new byte[8];
		for(int i=0;i<8;i++){
			trunkFreeMB_[i]=text[i+index];
		}
		long trunkFreeMB=buff2long(trunkFreeMB_, 0);
		index+=8;
		System.out.println(String.format("trunkFreeMB : %d MB",trunkFreeMB));
		
		
		//storageCount
		byte[] storageCount_=new byte[8];
		for(int i=0;i<8;i++){
			storageCount_[i]=text[i+index];
		}
		long storageCount=buff2long(storageCount_, 0);
		index+=8;
		System.out.println(String.format("storageCount : %d MB",storageCount));
		
		
		//storagePort
		byte[] storagePort_=new byte[8];
		for(int i=0;i<8;i++){
			storagePort_[i]=text[i+index];
		}
		long storagePort=buff2long(storagePort_, 0);
		index+=8;
		System.out.println(String.format("storagePort : %d ",storagePort));
				
	
		//storageHttpPort
		byte[] storageHttpPort_=new byte[8];
		for(int i=0;i<8;i++){
			storageHttpPort_[i]=text[i+index];
		}
		long storageHttpPort=buff2long(storageHttpPort_, 0);
		index+=8;
		System.out.println(String.format("storageHttpPort : %d ",storageHttpPort));
		
		

		//activeCount
		byte[] activeCount_=new byte[8];
		for(int i=0;i<8;i++){
			activeCount_[i]=text[i+index];
		}
		long activeCount=buff2long(activeCount_, 0);
		index+=8;
		System.out.println(String.format("activeCount : %d ",activeCount));
		
		
		//currentWriteServer
		byte[] currentWriteServer_=new byte[8];
		for(int i=0;i<8;i++){
			currentWriteServer_[i]=text[i+index];
		}
		long currentWriteServer=buff2long(currentWriteServer_, 0);
		index+=8;
		System.out.println(String.format("currentWriteServer : %d ",currentWriteServer));
		
		//storePathCount
		byte[] storePathCount_=new byte[8];
		for(int i=0;i<8;i++){
			currentWriteServer_[i]=text[i+index];
		}
		long storePathCount=buff2long(storePathCount_, 0);
		index+=8;
		System.out.println(String.format("storePathCount : %d ",storePathCount));
		
	
		//subdirCountPerPath
		byte[] subdirCountPerPath_=new byte[8];
		for(int i=0;i<8;i++){
			subdirCountPerPath_[i]=text[i+index];
		}
		long subdirCountPerPath=buff2long(subdirCountPerPath_, 0);
		index+=8;
		System.out.println(String.format("subdirCountPerPath : %d ",subdirCountPerPath));
		
	
		//currentTrunkFileId
		byte[] currentTrunkFileId_=new byte[8];
		for(int i=0;i<8;i++){
			currentTrunkFileId_[i]=text[i+index];
		}
		long currentTrunkFileId=buff2long(currentTrunkFileId_, 0);
		index+=8;
		System.out.println(String.format("currentTrunkFileId : %d ",currentTrunkFileId));
		
		System.out.println(String.format("[ body 字节总数 : %d ]", index+1));
	
	}
	
	
	
	
	
	
	
	/**
	 * 解析listStorage的服务端返回的消息包
	 * @param test byte[]
	 * @param count 解析第几个
	 * 
	 * 以下湿fastdfs-client-java中的错误的解析顺序：62个参数（正确的是58个）
	 * 
	 * 	protected byte status;
	 *	protected String id;
		protected String ipAddr;
		protected String srcIpAddr;
		protected String domainName; //http domain name
		protected String version;
		protected long totalMB; //total disk storage in MB
		protected long freeMB;  //free disk storage in MB
		protected int uploadPriority;  //upload priority
		protected Date joinTime; //storage join timestamp (create timestamp)
		protected Date upTime;   //storage service started timestamp
		protected int storePathCount;  //store base path count of each storage server
		protected int subdirCountPerPath;
		protected int storagePort;
		protected int storageHttpPort; //storage http server port
		protected int currentWritePath; //current write path index
		protected int connectionAllocCount;
		protected int connectionCurrentCount;
		protected int connectionMaxCount;
		protected long totalUploadCount;
		protected long successUploadCount;
		protected long totalAppendCount;
		protected long successAppendCount;
		protected long totalModifyCount;
		protected long successModifyCount;
		protected long totalTruncateCount;
		protected long successTruncateCount;
		protected long totalSetMetaCount;
		protected long successSetMetaCount;
		protected long totalDeleteCount;
		protected long successDeleteCount;
		protected long totalDownloadCount;
		protected long successDownloadCount;
		protected long totalGetMetaCount;
		protected long successGetMetaCount;
		protected long totalCreateLinkCount;
		protected long successCreateLinkCount;
		protected long totalDeleteLinkCount;
		protected long successDeleteLinkCount;
		protected long totalUploadBytes;
		protected long successUploadBytes;
		protected long totalAppendBytes;
		protected long successAppendBytes;
		protected long totalModifyBytes;
		protected long successModifyBytes;
		protected long totalDownloadloadBytes;
		protected long successDownloadloadBytes;
		protected long totalSyncInBytes;
		protected long successSyncInBytes;
		protected long totalSyncOutBytes;
		protected long successSyncOutBytes;
		protected long totalFileOpenCount;
		protected long successFileOpenCount;
		protected long totalFileReadCount;
		protected long successFileReadCount;
		protected long totalFileWriteCount;
		protected long successFileWriteCount;
		protected Date lastSourceUpdate;
		protected Date lastSyncUpdate;
		protected Date lastSyncedTimestamp;
		protected Date lastHeartBeatTime;
		protected boolean ifTrunkServer;
	 */
	public static void resolveList_storage(byte[] test,int count){
		
		System.out.println("[ 开始解析list_storage命令的消息包 ]");

		
		int index=(count-1)*600;
		
		//1.status
		System.out.println(String.format("status : %s",test[index]));
		
		//2.id
		index++;
		String id="";
		for(int i=0;i<16;i++){
			id+=(char)test[i+index];
		}
		index+=16;
		System.out.println(String.format("id : %s",id));

		//3.ipAddr
		String ipAddr="";
		for(int i=0;i<16;i++){
			ipAddr+=(char)test[i+index];
		}
		index+=16;
		System.out.println(String.format("ipAddr : %s",ipAddr));

		//4.domain
		String domain="";
		for(int i=0;i<128;i++){
			domain+=(char)test[i+index];
		}
		index+=128;
		System.out.println(String.format("domain : %s",domain));

		//5.srcIpAddr
		String srcIpAddr="";
		for(int i=0;i<16;i++){
			srcIpAddr+=(char)test[i+index];
		}
		index+=16;
		System.out.println(String.format("srcIpAddr : %s",srcIpAddr));

		//6.version
		String version="";
		for(int i=0;i<6;i++){
			version+=(char)test[i+index];
		}
		index+=6;
		System.out.println(String.format("version : %s",version));

		//7.joinTime
		byte[] joinTime_=new byte[8];
		for(int i=0;i<8;i++){
			joinTime_[i]=test[i+index];
		}
		long joinTime=buff2long(joinTime_, 0);
		index+=8;
		System.out.println(String.format("joinTime : %s",parseLongToDateString(joinTime)));
		
		
		//8.upTime
		byte[] upTime_=new byte[8];
		for(int i=0;i<8;i++){
			upTime_[i]=test[i+index];
		}
		long upTime=buff2long(upTime_, 0);
		index+=8;
		System.out.println(String.format("upTime : %s",parseLongToDateString(upTime)));
		
		//9.totalMB
		byte[] totalMB_=new byte[8];
		for(int i=0;i<8;i++){
			totalMB_[i]=test[i+index];
		}
		long totalMB=buff2long(totalMB_, 0);
		index+=8;
		System.out.println(String.format("totalMB : %dM",totalMB));
		
		
		//10.freeMB
		byte[] freeMB_=new byte[8];
		for(int i=0;i<8;i++){
			freeMB_[i]=test[i+index];
		}
		long freeMB=buff2long(freeMB_, 0);
		index+=8;
		System.out.println(String.format("freeMB : %dM",freeMB));
		
		//11.uploadPriority
		//源代码中是int
		byte[] uploadPriority_=new byte[8];
		for(int i=0;i<8;i++){
			uploadPriority_[i]=test[i+index];
		}
		long uploadPriority=buff2long(uploadPriority_, 0);
		index+=8;
		System.out.println(String.format("uploadPriority : %d",uploadPriority));
		
		//12.storePathCount
		//源代码中是int
		byte[] storePathCount_=new byte[8];
		for(int i=0;i<8;i++){
			storePathCount_[i]=test[i+index];
		}
		long storePathCount=buff2long(storePathCount_, 0);
		index+=8;
		System.out.println(String.format("storePathCount : %d",storePathCount));
		
		//13.subdirCountPerPath
		//源代码中是int
		byte[] subdirCountPerPath_=new byte[8];
		for(int i=0;i<8;i++){
			subdirCountPerPath_[i]=test[i+index];
		}
		long subdirCountPerPath=buff2long(subdirCountPerPath_, 0);
		index+=8;
		System.out.println(String.format("subdirCountPerPath : %d",subdirCountPerPath));
		
		
		//14.currentWritePath
		//源代码中是int
		byte[] currentWritePath_=new byte[8];
		for(int i=0;i<8;i++){
			currentWritePath_[i]=test[i+index];
		}
		long currentWritePath=buff2long(currentWritePath_, 0);
		index+=8;
		System.out.println(String.format("currentWritePath : %d",currentWritePath));
		
		//15.storagePort
		//源代码中是int
		byte[] storagePort_=new byte[8];
		for(int i=0;i<8;i++){
			storagePort_[i]=test[i+index];
		}
		long storagePort=buff2long(storagePort_, 0);
		index+=8;
		System.out.println(String.format("storagePort : %d",storagePort));
		
		//16.storageHttpPort
		//源代码中是int
		byte[] storageHttpPort_=new byte[8];
		for(int i=0;i<8;i++){
			storageHttpPort_[i]=test[i+index];
		}
		long storageHttpPort=buff2long(storageHttpPort_, 0);
		index+=8;
		System.out.println(String.format("storageHttpPort : %d",storageHttpPort));
		
	
		//17.total_upload_count
		byte[] total_upload_count_=new byte[8];
		for(int i=0;i<8;i++){
			total_upload_count_[i]=test[i+index];
		}
		long total_upload_count=buff2long(total_upload_count_, 0);
		index+=8;
		System.out.println(String.format("total_upload_count : %d",total_upload_count));
				
		
		//18.success_upload_count
		byte[] success_upload_count_=new byte[8];
		for(int i=0;i<8;i++){
			success_upload_count_[i]=test[i+index];
		}
		long current_write_path=buff2long(success_upload_count_, 0);
		index+=8;
		System.out.println(String.format("success_upload_count : %d",current_write_path));
				
		
		//19.total_append_count
		byte[] total_append_count_=new byte[8];
		for(int i=0;i<8;i++){
			total_append_count_[i]=test[i+index];
		}
		long total_append_count=buff2long(total_append_count_, 0);
		index+=8;
		System.out.println(String.format("success_upload_count : %d",total_append_count));
				
		
		//20.success_append_count
		byte[] success_append_count_=new byte[8];
		for(int i=0;i<8;i++){
			success_append_count_[i]=test[i+index];
		}
		long success_append_count=buff2long(success_append_count_, 0);
		index+=8;
		System.out.println(String.format("success_append_count : %d",success_append_count));
				
		//21.total_modify_count
		byte[] total_modify_count_=new byte[8];
		for(int i=0;i<8;i++){
			total_modify_count_[i]=test[i+index];
		}
		long total_modify_count=buff2long(total_modify_count_, 0);
		index+=8;
		System.out.println(String.format("total_modify_count : %d",total_modify_count));	
		
		
		//22.success_modify_count
		byte[] success_modify_count_=new byte[8];
		for(int i=0;i<8;i++){
			success_modify_count_[i]=test[i+index];
		}
		long success_modify_count=buff2long(success_modify_count_, 0);
		index+=8;
		System.out.println(String.format("success_modify_count : %d",success_modify_count));			
		
		
		//23.total_truncate_count
		byte[] total_truncate_count_=new byte[8];
		for(int i=0;i<8;i++){
			total_truncate_count_[i]=test[i+index];
		}
		long total_truncate_count=buff2long(total_truncate_count_, 0);
		index+=8;
		System.out.println(String.format("total_truncate_count : %d",total_truncate_count));	
		
		
		//24.success_truncate_count
		byte[] success_truncate_count_=new byte[8];
		for(int i=0;i<8;i++){
			success_truncate_count_[i]=test[i+index];
		}
		long success_truncate_count=buff2long(success_truncate_count_, 0);
		index+=8;
		System.out.println(String.format("success_truncate_count : %d",success_truncate_count));
		
		
		//25.total_set_meta_count
		byte[] total_set_meta_count_=new byte[8];
		for(int i=0;i<8;i++){
			total_set_meta_count_[i]=test[i+index];
		}
		long total_set_meta_count=buff2long(total_set_meta_count_, 0);
		index+=8;
		System.out.println(String.format("total_set_meta_count : %d",total_set_meta_count));
		
		//26.success_set_meta_count
		byte[] success_set_meta_count_=new byte[8];
		for(int i=0;i<8;i++){
			success_set_meta_count_[i]=test[i+index];
		}
		long success_set_meta_count=buff2long(success_set_meta_count_, 0);
		index+=8;
		System.out.println(String.format("success_set_meta_count : %d",success_set_meta_count));
		
		//27.total_delete_count
		byte[] total_delete_count_=new byte[8];
		for(int i=0;i<8;i++){
			total_delete_count_[i]=test[i+index];
		}
		long total_delete_count=buff2long(total_delete_count_, 0);
		index+=8;
		System.out.println(String.format("total_delete_count : %d",total_delete_count));
		
		
		//28.success_delete_count
		byte[] success_delete_count_=new byte[8];
		for(int i=0;i<8;i++){
			success_delete_count_[i]=test[i+index];
		}
		long success_delete_count=buff2long(success_delete_count_, 0);
		index+=8;
		System.out.println(String.format("total_delete_count : %d",success_delete_count));	
		
		
		//29.total_download_count
		byte[] total_download_count_=new byte[8];
		for(int i=0;i<8;i++){
			total_download_count_[i]=test[i+index];
		}
		long total_download_count=buff2long(total_download_count_, 0);
		index+=8;
		System.out.println(String.format("total_delete_count : %d",total_download_count));	
		
		//30.success_download_count
		byte[] success_download_count_=new byte[8];
		for(int i=0;i<8;i++){
			success_download_count_[i]=test[i+index];
		}
		long success_download_count=buff2long(success_download_count_, 0);
		index+=8;
		System.out.println(String.format("success_download_count : %d",success_download_count));	
		
		//31.total_get_meta_count
		byte[] total_get_meta_count_=new byte[8];
		for(int i=0;i<8;i++){
			total_get_meta_count_[i]=test[i+index];
		}
		long total_get_meta_count=buff2long(total_get_meta_count_, 0);
		index+=8;
		System.out.println(String.format("total_get_meta_count : %d",total_get_meta_count));
		
		//32.success_get_meta_count
		byte[] success_get_meta_count_=new byte[8];
		for(int i=0;i<8;i++){
			success_get_meta_count_[i]=test[i+index];
		}
		long success_get_meta_count=buff2long(success_get_meta_count_, 0);
		index+=8;
		System.out.println(String.format("success_get_meta_count : %d",success_get_meta_count));	
		
		//33.total_create_link_count
		byte[] total_create_link_count_=new byte[8];
		for(int i=0;i<8;i++){
			total_create_link_count_[i]=test[i+index];
		}
		long total_create_link_count=buff2long(total_create_link_count_, 0);
		index+=8;
		System.out.println(String.format("total_create_link_count : %d",total_create_link_count));	
		
		//34.success_create_link_count
		byte[] success_create_link_count_=new byte[8];
		for(int i=0;i<8;i++){
			success_create_link_count_[i]=test[i+index];
		}
		long success_create_link_count=buff2long(success_create_link_count_, 0);
		index+=8;
		System.out.println(String.format("success_create_link_count : %d",success_create_link_count));	
		
		//35.total_delete_link_count
		byte[] total_delete_link_count_=new byte[8];
		for(int i=0;i<8;i++){
			total_delete_link_count_[i]=test[i+index];
		}
		long total_delete_link_count=buff2long(total_delete_link_count_, 0);
		index+=8;
		System.out.println(String.format("total_delete_link_count : %d",total_delete_link_count));	
		
		
		//36.success_delete_link_count
		byte[] success_delete_link_count_=new byte[8];
		for(int i=0;i<8;i++){
			success_delete_link_count_[i]=test[i+index];
		}
		long success_delete_link_count=buff2long(success_delete_link_count_, 0);
		index+=8;
		System.out.println(String.format("success_delete_link_count : %d",success_delete_link_count));	
		
		//37.total_upload_bytes
		byte[] total_upload_bytes_=new byte[8];
		for(int i=0;i<8;i++){
			total_upload_bytes_[i]=test[i+index];
		}
		long total_upload_bytes=buff2long(total_upload_bytes_, 0);
		index+=8;
		System.out.println(String.format("total_upload_bytes : %d",total_upload_bytes));
		
		//38.success_upload_bytes
		byte[] success_upload_bytes_=new byte[8];
		for(int i=0;i<8;i++){
			success_upload_bytes_[i]=test[i+index];
		}
		long success_upload_bytes=buff2long(success_upload_bytes_, 0);
		index+=8;
		System.out.println(String.format("success_upload_bytes : %d",success_upload_bytes));
		
		//39.total_append_bytes
		byte[] total_append_bytes_=new byte[8];
		for(int i=0;i<8;i++){
			total_append_bytes_[i]=test[i+index];
		}
		long total_append_bytes=buff2long(total_append_bytes_, 0);
		index+=8;
		System.out.println(String.format("total_append_bytes : %d",total_append_bytes));
		
		//40.success_append_bytes
		byte[] success_append_bytes_=new byte[8];
		for(int i=0;i<8;i++){
			success_append_bytes_[i]=test[i+index];
		}
		long success_append_bytes=buff2long(success_append_bytes_, 0);
		index+=8;
		System.out.println(String.format("success_append_bytes : %d",success_append_bytes));
		
		//41.total_modify_bytes
		byte[] total_modify_bytes_=new byte[8];
		for(int i=0;i<8;i++){
			total_modify_bytes_[i]=test[i+index];
		}
		long total_modify_bytes=buff2long(total_modify_bytes_, 0);
		index+=8;
		System.out.println(String.format("success_append_bytes : %d",total_modify_bytes));
		
		
		//42.success_modify_bytes
		byte[] success_modify_bytes_=new byte[8];
		for(int i=0;i<8;i++){
			success_modify_bytes_[i]=test[i+index];
		}
		long success_modify_bytes=buff2long(success_modify_bytes_, 0);
		index+=8;
		System.out.println(String.format("success_modify_bytes : %d",success_modify_bytes));
		
		
		//43.stotal_download_bytes
		byte[] stotal_download_bytes_=new byte[8];
		for(int i=0;i<8;i++){
			stotal_download_bytes_[i]=test[i+index];
		}
		long stotal_download_bytes=buff2long(stotal_download_bytes_, 0);
		index+=8;
		System.out.println(String.format("stotal_download_bytes : %d",stotal_download_bytes));
		
		//44.success_download_bytes
		byte[] success_download_bytes_=new byte[8];
		for(int i=0;i<8;i++){
			success_download_bytes_[i]=test[i+index];
		}
		long success_download_bytes=buff2long(success_download_bytes_, 0);
		index+=8;
		System.out.println(String.format("success_download_bytes : %d",success_download_bytes));
		
		
		//45.total_sync_in_bytes
		byte[] total_sync_in_bytes_=new byte[8];
		for(int i=0;i<8;i++){
			total_sync_in_bytes_[i]=test[i+index];
		}
		long total_sync_in_bytes=buff2long(total_sync_in_bytes_, 0);
		index+=8;
		System.out.println(String.format("total_sync_in_bytes : %d",total_sync_in_bytes));
		
		
		//46.success_sync_in_bytes
		byte[] success_sync_in_bytes_=new byte[8];
		for(int i=0;i<8;i++){
			success_sync_in_bytes_[i]=test[i+index];
		}
		long success_sync_in_bytes=buff2long(success_sync_in_bytes_, 0);
		index+=8;
		System.out.println(String.format("success_sync_in_bytes : %d",success_sync_in_bytes));
		
		//47.total_sync_out_bytes
		byte[] total_sync_out_bytes_=new byte[8];
		for(int i=0;i<8;i++){
			total_sync_in_bytes_[i]=test[i+index];
		}
		long total_sync_out_bytes=buff2long(total_sync_out_bytes_, 0);
		index+=8;
		System.out.println(String.format("total_sync_in_bytes : %d",total_sync_out_bytes));
		
		//48.success_sync_out_bytes
		byte[] success_sync_out_bytes_=new byte[8];
		for(int i=0;i<8;i++){
			total_sync_in_bytes_[i]=test[i+index];
		}
		long success_sync_out_bytes=buff2long(success_sync_out_bytes_, 0);
		index+=8;
		System.out.println(String.format("success_sync_out_bytes : %d",success_sync_out_bytes));
		
		
		//49.total_file_open_count
		byte[] total_file_open_count_=new byte[8];
		for(int i=0;i<8;i++){
			total_file_open_count_[i]=test[i+index];
		}
		long total_file_open_count=buff2long(total_file_open_count_, 0);
		index+=8;
		System.out.println(String.format("total_file_open_count : %d",total_file_open_count));
		
		
		//50.success_file_open_count
		byte[] success_file_open_count_=new byte[8];
		for(int i=0;i<8;i++){
			success_file_open_count_[i]=test[i+index];
		}
		long success_file_open_count=buff2long(success_file_open_count_, 0);
		index+=8;
		System.out.println(String.format("success_file_open_count : %d",success_file_open_count));
		
		//51.total_file_read_count
		byte[] total_file_read_count_=new byte[8];
		for(int i=0;i<8;i++){
			total_file_read_count_[i]=test[i+index];
		}
		long total_file_read_count=buff2long(total_file_read_count_, 0);
		index+=8;
		System.out.println(String.format("total_file_read_count : %d",total_file_read_count));
		
		//52.success_file_read_count
		byte[] success_file_read_count_=new byte[8];
		for(int i=0;i<8;i++){
			success_file_read_count_[i]=test[i+index];
		}
		long success_file_read_count=buff2long(success_file_read_count_, 0);
		index+=8;
		System.out.println(String.format("success_file_read_count : %d",success_file_read_count));
		
		//53.total_file_write_count
		byte[] total_file_write_count_=new byte[8];
		for(int i=0;i<8;i++){
			total_file_write_count_[i]=test[i+index];
		}
		long total_file_write_count=buff2long(total_file_write_count_, 0);
		index+=8;
		System.out.println(String.format("total_file_open_count : %d",total_file_write_count));
		
		//54.success_file_write_count
		byte[] success_file_write_count_=new byte[8];
		for(int i=0;i<8;i++){
			success_file_write_count_[i]=test[i+index];
		}
		long success_file_write_count=buff2long(success_file_write_count_, 0);
		index+=8;
		System.out.println(String.format("success_file_write_count : %d",success_file_write_count));
		
		//55.last_source_update
		byte[] last_source_update_=new byte[8];
		for(int i=0;i<8;i++){
			last_source_update_[i]=test[i+index];
		}
		long last_source_update=buff2long(last_source_update_,0);
		index+=8;
		System.out.println(String.format("last_source_update : %s",parseLongToDateString(last_source_update)));
		
		//56.last_sync_update
		byte[] last_sync_update_=new byte[8];
		for(int i=0;i<8;i++){
			last_sync_update_[i]=test[i+index];
		}
		long last_sync_update=buff2long(last_sync_update_,0);
		index+=8;
		System.out.println(String.format("last_sync_update : %s",parseLongToDateString(last_sync_update)));
		
		//57.last_synced_timestamp
		byte[] last_synced_timestamp_=new byte[8];
		for(int i=0;i<8;i++){
			last_synced_timestamp_[i]=test[i+index];
		}
		long last_synced_timestamp=buff2long(last_synced_timestamp_,0);
		index+=8;
		System.out.println(String.format("last_synced_timestamp : %s",parseLongToDateString(last_synced_timestamp)));
		
		//58.last_heart_beat_time
		byte[] last_heart_beat_time_=new byte[8];
		for(int i=0;i<8;i++){
			last_heart_beat_time_[i]=test[i+index];
		}
		long last_heart_beat_time=buff2long(last_heart_beat_time_,0);
		index+=8;
		System.out.println(String.format("last_heart_beat_time : %s",parseLongToDateString(last_heart_beat_time)));
		
		System.out.println(String.format("[ body 字节总数 : %d ]", index+1));
	}
	
	//value 单位：秒
	public static String parseLongToDateString(long value){
		SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String d = format.format(value*1000);
		return d;
	}
	
	

	public static byte[] packHeader(byte cmd, long pkg_len, byte errno)
			throws UnsupportedEncodingException {
		byte[] header;
		byte[] hex_len;

		header = new byte[8 + 2];
		Arrays.fill(header, (byte) 0);

		hex_len = long2buff(pkg_len);
		System.arraycopy(hex_len, 0, header, 0, hex_len.length);
		header[8] = cmd;
		header[8 + 1] = errno;
		return header;
	}

	public static byte[] long2buff(long n) {
		byte[] bs;

		bs = new byte[8];
		bs[0] = (byte) ((n >> 56) & 0xFF);
		bs[1] = (byte) ((n >> 48) & 0xFF);
		bs[2] = (byte) ((n >> 40) & 0xFF);
		bs[3] = (byte) ((n >> 32) & 0xFF);
		bs[4] = (byte) ((n >> 24) & 0xFF);
		bs[5] = (byte) ((n >> 16) & 0xFF);
		bs[6] = (byte) ((n >> 8) & 0xFF);
		bs[7] = (byte) (n & 0xFF);

		return bs;
	}

	/**
	 * buff convert to int
	 * 
	 * @param bs
	 *            the buffer (big-endian)
	 * @param offset
	 *            the start position based 0
	 * @return int number
	 */
	public static int buff2int(byte[] bs, int offset) {
		return (((int) (bs[offset] >= 0 ? bs[offset] : 256 + bs[offset])) << 24)
				| (((int) (bs[offset + 1] >= 0 ? bs[offset + 1]
						: 256 + bs[offset + 1])) << 16)
				| (((int) (bs[offset + 2] >= 0 ? bs[offset + 2]
						: 256 + bs[offset + 2])) << 8)
				| ((int) (bs[offset + 3] >= 0 ? bs[offset + 3]
						: 256 + bs[offset + 3]));
	}

	public static long buff2long(byte[] bs, int offset) {
		return (((long) (bs[offset] >= 0 ? bs[offset] : 256 + bs[offset])) << 56)
				| (((long) (bs[offset + 1] >= 0 ? bs[offset + 1]
						: 256 + bs[offset + 1])) << 48)
				| (((long) (bs[offset + 2] >= 0 ? bs[offset + 2]
						: 256 + bs[offset + 2])) << 40)
				| (((long) (bs[offset + 3] >= 0 ? bs[offset + 3]
						: 256 + bs[offset + 3])) << 32)
				| (((long) (bs[offset + 4] >= 0 ? bs[offset + 4]
						: 256 + bs[offset + 4])) << 24)
				| (((long) (bs[offset + 5] >= 0 ? bs[offset + 5]
						: 256 + bs[offset + 5])) << 16)
				| (((long) (bs[offset + 6] >= 0 ? bs[offset + 6]
						: 256 + bs[offset + 6])) << 8)
				| ((long) (bs[offset + 7] >= 0 ? bs[offset + 7]
						: 256 + bs[offset + 7]));
	}

	public static void main(String[] args) {

		try {
			list_group();
			System.out.println("===========================================");
			list_storage("group1",null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
