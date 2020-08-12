package com.ms.boot.demo.bootdemo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Greeting {

	@Id
	private Integer id;
	private String greetText;
	private String type;
	public Greeting(Integer id, String greetText, String type) {
		super();
		this.id = id;
		this.greetText = greetText;
		this.type = type;
	}
	public Greeting() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGreetText() {
		return greetText;
	}
	public void setGreetText(String greetText) {
		this.greetText = greetText;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
	
}
