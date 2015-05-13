package com.demo.SpringBootDemo.resource;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.hateoas.ResourceSupport;

@XmlRootElement(name="root")
public class UserResource extends ResourceSupport{
	
	@XmlID
	public volatile Long id;
	
	@XmlAttribute
	 public volatile String name;
	
	@XmlAttribute
	public volatile String email;

}
