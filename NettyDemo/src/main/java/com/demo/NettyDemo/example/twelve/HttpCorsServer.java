package com.demo.NettyDemo.example.twelve;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
29   * This example server aims to demonstrate
30   * <a href="http://www.w3.org/TR/cors/">Cross Origin Resource Sharing</a> (CORS) in Netty.
31   * It does not have a client like most of the other examples, but instead has
32   * a html page that is loaded to try out CORS support in a web brower.
33   * <p>
34   *
35   * CORS is configured in {@link HttpCorsServerInitializer} and by updating the config you can
36   * try out various combinations, like using a specific origin instead of a
37   * wildcard origin ('*').
38   * <p>
39   *
40   * The file {@code src/main/resources/cors/cors.html} contains a very basic example client
41   * which can be used to try out different configurations. For example, you can add
42   * custom headers to force a CORS preflight request to make the request fail. Then
43   * to enable a successful request, configure the CorsHandler to allow that/those
44   * request headers.
45   *
46   * <h2>Testing CORS</h2>
47   * You can either load the file {@code src/main/resources/cors/cors.html} using a web server
48   * or load it from the file system using a web browser.
49   *
50   * <h3>Using a web server</h3>
51   * To test CORS support you can serve the file {@code src/main/resources/cors/cors.html}
52   * using a web server. You can then add a new host name to your systems hosts file, for
53   * example if you are on Linux you may update /etc/hosts to add an addtional name
54   * for you local system:
55   * <pre>
56   * 127.0.0.1   localhost domain1.com
57   * </pre>
58   * Now, you should be able to access {@code http://domain1.com/cors.html} depending on how you
59   * have configured you local web server the exact url may differ.
60   *
61   * <h3>Using a web browser</h3>
62   * Open the file {@code src/main/resources/cors/cors.html} in a web browser. You should see
63   * loaded page and in the text area the following message:
64   * <pre>
65   * 'CORS is not working'
66   * </pre>
67   *
68   * If you inspect the headers being sent using your browser you'll see that the 'Origin'
69   * request header is {@code 'null'}. This is expected and happens when you load a file from the
70   * local file system. Netty can handle this by configuring the CorsHandler which is done
71   * in the {@link HttpCorsServerInitializer}.
72   *
73   */
public class HttpCorsServer {
	
	private static String host="127.0.0.1";
	private static int port=8989;
	
	public static void main(String[] args) {
		EventLoopGroup boss=new NioEventLoopGroup();
		EventLoopGroup work=new NioEventLoopGroup();
		
		ServerBootstrap server=new ServerBootstrap();
		server.group(boss, work);
		server.channel(NioServerSocketChannel.class);
		server.handler(new LoggingHandler(LogLevel.INFO));
		server.childHandler(new HttpCorsServerInitializer());
		
		try {
			server.bind(host,port).channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			boss.shutdownGracefully();
			work.shutdownGracefully();
		}
		
	}

}
