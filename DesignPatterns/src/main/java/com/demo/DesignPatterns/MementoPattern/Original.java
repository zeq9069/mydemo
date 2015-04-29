package com.demo.DesignPatterns.MementoPattern;

public class Original {

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	/*
	 * 创建备份
	 */
	public Memento createMemento(){
		return new Memento(value);
	}
	
	/*
	 * 恢复备份
	 */
	public void restoreMemento(Memento memento){
		this.value=memento.getValue();
	}
	
}
