package com.demo.JavassistBytecodeDemo.Example;

import java.lang.reflect.Method;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

/**
 * javassist   java字节码练习
 * ZEQ 2015年1月9日
 *
 */
public class Test1 {

	//1获取一个class文件，并且重新生成一个class
	public static void method1() {
		try {
			ClassPool pool = ClassPool.getDefault();
			CtClass ct = pool.get("com.demo.JavassistBytecodeDemo.App");//获取一个class文件
			ct.setSuperclass(pool.get("com.demo.JavassistBytecodeDemo.App1"));
			ct.setName("com.demo.JavassistBytecodeDemo.JavassistCreateByMethod1AppTest"); //设置类名，最好加上包名
			ct.writeFile(); //写入disk
			//通过java反射调用新生成的类JavassistCreateAppTest
			Class<?> cl = ct.toClass();
			Method m = cl.getDeclaredMethod("m1");
			m.invoke(cl.newInstance());
			System.out.println("superClass:" + cl.getSuperclass().getName());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//2，定义一个新类,并且添加一个新的方法
	public static void method2() {
		try {
			ClassPool pool = ClassPool.getDefault();
			CtClass ct = pool.makeClass("com.demo.JavassistBytecodeDemo.JavassistCreateByMethod2AppTest");
			CtMethod NewMethod = CtNewMethod
					.make("public void m3(){System.out.println(\"my name is m3 from JavassistCreateByMethod2AppTest\");}",
							ct);
			ct.addMethod(NewMethod);
			ct.writeFile();
			ct.freeze();
			//根据java反射，调用类的方法
			Class<?> cl = ct.toClass();
			Method mm = cl.getDeclaredMethod("m3");
			mm.invoke(cl.newInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		//method1();
		method2();

	}
}
