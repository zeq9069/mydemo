package com.demo.DynamicProxyDemo.sample;

import java.beans.PropertyChangeListener;
import java.io.Serializable;

/**
 * 
 * @author kyrin
 *
 */
public abstract class Bean implements Serializable {

	private static final long serialVersionUID = 7474508196999968282L;
	String sampleProperty;

	abstract public void addPropertyChangeListener(PropertyChangeListener listener);

	abstract public void removePropertyCchangeListener(PropertyChangeListener listener);

	public String getSampleProperty() {
		return sampleProperty;
	}

	public void setSampleProperty(String sampleProperty) {
		this.sampleProperty = sampleProperty;
	}

}
