package com.demo.DesignPatterns.InterpreterPattern;

public class Minus implements Expression{

	public int interpreter(Context context) {
		return context.getNum1()-context.getNum2();
	}

}
