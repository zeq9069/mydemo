import com.demo.DynamicProxyDemo.proxy.UserInvocationHandler;
import com.demo.DynamicProxyDemo.proxy.UserMethodIntercepter;
import com.demo.DynamicProxyDemo.service.UserService;
import com.demo.DynamicProxyDemo.service.impl.UserServiceImpl;
import com.demo.DynamicProxyDemo.service.impl.UserServiceImpl1;

/**
 * 
 * Kyrin 2015年1月8日
 *
 */
public class UserDynamicProxy {

	//JDK动态代理调用例子
	public static void method1() {
		UserService userService = new UserServiceImpl();
		UserInvocationHandler userInvocationHandler = new UserInvocationHandler(userService);
		UserService u = (UserService) userInvocationHandler.bind(userService);
		u.getUserName("123");
		u.deleteUser("123");
	}

	//cglib动态代理调用例子
	public static void method2() {
		UserServiceImpl1 userServiceImpl = new UserServiceImpl1();
		UserMethodIntercepter cglib = new UserMethodIntercepter();
		UserServiceImpl1 impl = (UserServiceImpl1) cglib.getInstance(userServiceImpl);
		impl.getUserName("123");
		impl.deleteUser("123");
	}

	public static void main(String args[]) {
		//method1();
		method2();
	}
}
