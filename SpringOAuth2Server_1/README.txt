
    
    spring-security-oauth2 server demo1
    
  该demo实现了简单的oauth2.0的认证
 
 
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
  	
  
  
