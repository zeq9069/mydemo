package com.demo.NettyDemo.example.eight.service;

import java.util.List;


/**
 * ********************************
 *    netty 练习
 *    
 *   RPC：利用JDK和cglib实现远程服务调用
 *   编码：java序列化编码
 *
 * ********************************
 * @author kyrin kyrincloud@qq.com 
 *
 * @date [2015年3月31日]
 *
 */
public interface UserService {
	public boolean create(User user);
	public boolean delete(int id);
	public User get(int id);
	public User update();
	public List<User> getList();
	
}
