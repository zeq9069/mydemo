package com.kyrincloud.RPCDemo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.kyrincloud.RPCDemo.codec.MyCodec;
import com.kyrincloud.RPCDemo.message.Request;
import com.kyrincloud.RPCDemo.message.Response;
import com.kyrincloud.RPCDemo.protocol.factory.RPCProtocolFactory;
import com.kyrincloud.RPCDemo.service.HelloImpl;
import com.kyrincloud.RPCDemo.service.IHello;

/**
 * rpc服务发布端
 * 
 * 通信协议： header+body = 协议（1byte）+ 消息类型（1byte）+接口类长度（4byte）+方法名长度（4byte）+方法参数个数（4byte）+方法参数个数*4byte +方法参数类型个数（4byte）+方法参数类型个数*4byte+body内容
 * 注意：参数类型的个数和参数的个数是一样的
 * @author kyrin
 *
 */
public class RPCServer {
	
	static Map<String,Method> methodCache=new HashMap<String, Method>();
	static Map<String,Object> classCache=new HashMap<String, Object>();
	private static ServerSocket server;
	
	public static void cacheService(){
		IHello hello=new HelloImpl();
		classCache.put(IHello.class.getName(),hello);
		StringBuffer str=new StringBuffer();
		str.append(IHello.class.getName());
		
		Method[] methods=hello.getClass().getDeclaredMethods();
		for(Method method:methods){
			StringBuffer m=new StringBuffer();
			m.append(method.getName());
			m.append("$");
			Class<?>[] clazzs=method.getParameterTypes();
			for(Class<?> clazz:clazzs){
				m.append(clazz.getName()+"_");
			}
			methodCache.put(str.toString()+"#"+m.toString(), method);
		}
	}

	public static void main(String[] args) {
		//1,把发布的服务先laod缓存起来
		cacheService();
		
		//2,启动
		try {
			System.out.println("\n[create by Kyrin  kyrincloud@qq.com]\n");
			System.out.println("----------服务启动----------------");
			start(9999);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void start(int port) throws IOException{
		server = new ServerSocket(port);
		while(true){
			Socket ss=server.accept();
			InputStream is=ss.getInputStream();
			OutputStream os=ss.getOutputStream();
			Object requestObj=RPCProtocolFactory.getProtocol(1).decode(is);
			if(requestObj instanceof Request){
				Request request=(Request)requestObj;
				Object result= handleRequest(request);	//获取client发送的数据包，并执行本地服务返回结果
				sendResponse(result, os);			//把结果发送到client
			} 
			is.close();
			os.close();
		}
	}
	
	
	public static Object handleRequest(Request request) throws IOException{
		Object result=null;
		/*byte[] version=new byte[2];
		is.read(version);
		if(version[0]==(byte)1 || version[1]==(byte)1){
			ByteBuffer buff=ByteBuffer.allocate(4);
			
			int clazz_len=readInt(is, buff);
			int method_len=readInt(is, buff);
			int argsNum=readInt(is, buff);
			List<Integer> allArgsLen=new ArrayList<Integer>();
			List<Integer> allArgsTypeLen=new ArrayList<Integer>();
			for(int i=1;i<=argsNum;i++){
				allArgsLen.add(readInt(is, buff));
			}
			is.skip(4);
			for(int i=1;i<=argsNum;i++){
				allArgsTypeLen.add(readInt(is, buff));
			}
			
			byte[] _clazz=new byte[clazz_len];
			byte[] _method=new byte[method_len];
			
			is.read(_clazz);
			is.read(_method);
			
			List<byte[]> args=new ArrayList<byte[]>();
			List<byte[]> argsType=new ArrayList<byte[]>();
			
			for(int i=0;i<argsNum;i++){
				byte[] b=new byte[allArgsLen.get(i)];
				is.read(b);
				args.add(b);
			}
			
			for(int i=0;i<argsNum;i++){
				byte[] b=new byte[allArgsTypeLen.get(i)];
				is.read(b);
				argsType.add(b);
			}
			*/
			
		
			try {
				result=invokeResult(request); //处理请求的数据
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			//--------------万恶的分割线-----------------
			System.out.println("\n服务端接收到的className :"+request.getTargetInterfaceName());
			System.out.println("服务端接收到的method :"+request.getMethodName());
			for(byte[] b:request.getArgs()){
				System.out.println("服务端接收到的args :"+MyCodec.decode(b));
			}
			for(byte[] b:request.getArgsType()){
				System.out.println("服务端接收到的argsType :"+new String(b)+"\n");
			}
		return result;
	}
	
	/*private static int readInt(InputStream is,ByteBuffer buff) throws IOException{
		byte[] clazz=buff.array();
		is.read(clazz);
		int result= buff.getInt();
		buff.clear();
		return result;
	}*/
	
	//处理请求的数据
	public static Object invokeResult(Request request) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{

		Object instance=classCache.get(request.getTargetInterfaceName());
		Class<?>[] argsTypeObject=new Class<?>[request.getArgsType().length];
		Object[] argseObject=new Object[request.getArgs().length];
		for(int i=0;i<request.getArgsType().length;i++){
			argsTypeObject[i]=new String(request.getArgsType()[i]).getClass();
			System.out.println(new String(request.getArgs()[i]));
			argseObject[i]=MyCodec.decode(request.getArgs()[i]);
		}
		//构造methodKey，获取指定要执行的Method，这些Method都是你提前缓存好的
		StringBuffer methodKey=new StringBuffer();
		methodKey.append(request.getTargetInterfaceName());
		methodKey.append("#");
		methodKey.append(request.getMethodName());
		methodKey.append("$");
		for(byte[] at:request.getArgsType()){
			methodKey.append(new String(at)+"_");
		}
		Method method=methodCache.get(methodKey.toString());
		return method.invoke(instance, argseObject);
		
		//如说是实时的获取类的方法的话，如果方法用到了泛型的话，这样获取不到
//		Object instance=cache.get(instanceName);
//		Class<?>[] argsTypeObject=new Class<?>[argsType.size()];
//		Object[] argseObject=new Object[args.size()];
//		for(int i=0;i<argsType.size();i++){
//			System.out.println(new String(argsType.get(i)));
//			argsTypeObject[i]=new String(argsType.get(i)).getClass();
//			argseObject[i]=MyCodec.decode(args.get(i));
//		}
//		Method m=instance.getClass().getMethod(methodName, argsTypeObject);
//		return m.invoke(instance, argseObject);
	}
	
	//发送执行结果到client
	public static void sendResponse(Object result,OutputStream os) throws IOException{
		Response response=new Response();
		response.setResult(MyCodec.encode(result));
		os.write(RPCProtocolFactory.getProtocol(1).encode(response));
		os.flush();
	}
}
