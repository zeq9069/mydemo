
    
    spring-security-oauth2 server demo1
    
  该demo实现了简单的oauth2.0的认证
 
 一，使用方式
 	GET http://localhost:8888/oauth/authorize?response_type=code&client_id=test&redirect_uri=http://test.com
  
  	1) Get the authorization code

		Request         GET /oauth/authorize
		Request Body    response_type=code&client_id=test&redirect_uri=http://localhost:8080/yourapp
                		# Submit the login form with an authorized user
		Response Code   302 Found
		Redirect to     http://test.com/?code=7m6TKQ
                    
	2) 请求 access token

		Request         POST /oauth/token
		Request Body    grant_type=authorization_code&client_id=test&client_secret=test&code=7m6TKQ&redirect_uri=http://test.com
		Response Codes  200 OK
		Response Body   ::

                 {
                    "access_token": "7ad8f410-d9d4-4106-b8c2-13cc48c0269d",
                    "token_type": "bearer",
                    "expires_in": 43200,
                    "scope": "read write"
                 }
                 
                 
  	3) check_token
  	
  		Request         POST /oauth/check_token
		Request Body    token=7ad8f410-d9d4-4106-b8c2-13cc48c0269d
		Request Headers Authorization: Basic dGVzdDp0ZXN0 => stands for Base64.encode(client_id:client_secret)
                		Content-Type: application/x-www-form-encoded
		Response Codes  200 OK
		Response Body   ::
              
               {
    			"aud": [
       					 "resource_1"
    					],
   					  "exp": 1432143121,
    			"user_name": "kyrin",
    		  "authorities": [
        						"ROLE_USER"
    							],
    			"client_id": "test",
    				"scope": [
        						"read",
       							 "write"
   							 ]
				}
  
  
  	4)请求资源
  	
  	-GET http://localhost:8888/home/client/info?access_token=7ea44bf1-570b-44df-802f-e551134f45ab
  	
  	返回：client_info
  	
  	或者
  	
  	
  	Request 			-GET http://localhost:8888/home/client/info
  	Request Headers		Authorization: Bearer 7ea44bf1-570b-44df-802f-e551134f45ab
  	
  			
  			
  	5)refresh_token(每次refresh_token都会更新，也可以一直使用一个固定不变，可以设置)
  	
 	http://localhost:8888/oauth/token?grant_type=refresh_token&client_id=test&client_secret=test&refresh_token=9603b406-0035-4e52-9227-f6ef7f66a71e
  	
  	
		Request         POST /oauth/token
		Request Body    grant_type=refresh_token&client_id=test&client_secret=test
							&refresh_token=8c8d7232-9523-4838-85f7-14cb3aaa174c
		Response Codes  200 OK
		Response Body   ::

                 {
                    "access_token": "7ad8f410-d9d4-4106-b8c2-13cc48c0269d",
                    "token_type": "bearer",
                    "refresh_token": "8c8d7232-9523-4838-85f7-14cb3aaa174c",
                    "expires_in": 43200,
                    "scope": "read write"
                 }
  	
  
  二，自定义token和refresh_token的有效期时间
  
  	通过对授权服务对继承AuthorizationServerConfigurerAdapter的授权服务端的方法configure(AuthorizationServerEndpointsConfigurer endpoints)
  进行自定义，实现有效期的变更。
  
  
  三，自定义数据库表结构
  
  	本项目在以前的项目的功能基础之上，添加了对spring-security中用户-角色的表结构自定义。但是没有对spring-security-oauth的client定义数据库。
  	client的信息还是存放于内存的，下个版本将会添加。
  
  四，基于自带的client表结构定义
  	
  	该版本添加了基于spring-security-oauth自带的client表机构定义，将client的信息存储于数据库。
  但是有个小问题，就是数据库中的access_toeken和refresh_token的过期时间没起作用，这个会在下一个版本中修复
  	
 五，access_token和refresh_token有效时间
 
   修改自定义的失效时间，实现JdbcClientDetailsService，代替原来基于内存的InMemoryClientDetailsService，这样自定义的时间就不再起作用了，
  自动使用数据库中的时间 
  
 六，增加Md5PasswordEncoder解密
 	
 	对用户的密码采用Md5PasswordEncoder解密，自定义saltSource，让每个用户的username的值当做salt
  	
 七，实现将token和refresh_token存放于数据库
 
 	定义两个表oauth_access_token和oauth_refresh_token，两者有级联关系。同时由JdbcTokenStore代替InMemoryTokenStore
 	
 八，scope参数
 
 		该参数时你对申请资源的操作范围，比如：read，write等。
 	在将client信息存放于数据库时，其中有两个字段跟请求的scope有关，它们是scope和autoapprove，scope可以是多这个值，指client的请求的
 	资源的范围，这些范围不需要用户授予（也就是说，登陆之后不会跳转到用户授权的页面），是自动获取的。一般scope是空得。autoapprove是指哪些scope的值可以自动授予，
 	不需要用户去授予，可以是多个。当第三方请求的scope的值是这里的任意一个时，自动跳过授权页面。可以参考BaseClientDetails
 	
 	使用方法：
 		http://localhost:8888/oauth/authorize?response_type=code&client_id=test&redirect_uri=http://test.com&scope=READ write
 		（多个值，用空格隔开）
 		
 		登陆之后，就会跳转到授权页面。
 	
 	
 	
 
 	
