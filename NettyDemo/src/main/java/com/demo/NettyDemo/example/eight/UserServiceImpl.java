package com.demo.NettyDemo.example.eight;

import java.util.ArrayList;
import java.util.List;

import com.demo.NettyDemo.example.eight.service.User;
import com.demo.NettyDemo.example.eight.service.UserService;

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
public class UserServiceImpl implements UserService{

	private User user=new User();
	
	public boolean create(User user) {
		System.out.println("服务端：create()方法执行！");
		return true;
	}

	public boolean delete(int id) {
		System.out.println("服务端：delete()方法执行！");
		return true;
	}

	public User get(int id) {
		System.out.println("服务端：get()方法执行！");
		user.setId(1);
		user.setName("kyrin");
		user.setPassword("get");
		return user;
	}

	public User update() {
		System.out.println("服务端：update()方法执行！");
		user.setId(1);
		user.setName("kyrin");
		user.setPassword("update");
		return null;
	}

	public List<User> getList() {
		System.out.println("服务端：getList()方法执行！");
		user.setId(1);
		user.setName("kyrin");
		user.setPassword("123");
		List<User> list=new ArrayList<User>();
		list.add(user);
		return list;
	}

}
