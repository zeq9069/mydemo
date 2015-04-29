package com.demo.DesignPatterns.InterpreterPattern;

public class Plus implements Expression{

	public int interpreter(Context context) {
		return context.getNum1()+context.getNum2();
	}
}
