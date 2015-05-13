package com.demo.SpringBootDemo.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="school")
public class School implements Serializable{

	private static final long serialVersionUID = 8328467996937139408L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	
	public School(String name) {
		this.name=name;
	}
	
	@Column(name="name",nullable=false)
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
