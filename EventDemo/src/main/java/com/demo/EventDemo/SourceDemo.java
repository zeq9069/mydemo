package com.demo.EventDemo;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Hello world!
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SourceDemo {
	private Vector v = new Vector();

	public void addListener(ListenerDemo ld) {
		v.addElement(ld);
	}

	public void notifyEvent() {
		Enumeration it = v.elements();
		while (it.hasMoreElements()) {
			ListenerDemo d = (ListenerDemo) it.nextElement();
			d.handlerEvent(new EventDemo(this));
		}
	}
}
