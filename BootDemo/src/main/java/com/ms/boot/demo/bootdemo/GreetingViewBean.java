package com.ms.boot.demo.bootdemo;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(name = "GreetingView" )
public class GreetingViewBean {

	private String greetText;
	private String salutation;
	private String greetingTo;
	private Date greetAt;

	public GreetingViewBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GreetingViewBean(String greetText, String salutation, String greetingTo, Date greetAt) {
		super();
		this.greetText = greetText;
		this.salutation = salutation;
		this.greetingTo = greetingTo;
		this.greetAt = greetAt;
	}

	public String getGreetText() {
		return greetText;
	}

	public void setGreetText(String greetText) {
		this.greetText = greetText;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getGreetingTo() {
		return greetingTo;
	}

	public void setGreetingTo(String greetingTo) {
		this.greetingTo = greetingTo;
	}

	public Date getGreetAt() {
		return greetAt;
	}

	public void setGreetAt(Date greetAt) {
		this.greetAt = greetAt;
	}

	@Override
	public String toString() {
		return "GreetingViewBean [greetText=" + greetText + ", salutation=" + salutation + ", greetingTo=" + greetingTo
				+ ", greetAt=" + greetAt + "]";
	}
	
	

}
