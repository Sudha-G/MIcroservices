package com.ms.boot.demo.bootdemo;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ms.boot.demo.bootdemo.model.Greeting;
import com.ms.boot.demo.bootdemo.service.GreetingService;

@RestController
public class GreetingRESTController {

	@Autowired
	private GreetingService greetingService;

	@Value("${defaultgreeting}")
	private String defaultGreeting;
	
	@RequestMapping(path = "/greet/default", method = RequestMethod.GET)
	public String defaultGreeting() {
		return defaultGreeting + (new Random()).nextInt();

	}

	@RequestMapping(path = "/greet/{name}", method = RequestMethod.GET)
	public @ResponseBody GreetingViewBean greetComplex(@PathVariable String name) {
		return new GreetingViewBean("Hello", "Mr", name, new Date());
	}

	@RequestMapping(path = "/greet/{id}/{name}")
	public @ResponseBody GreetingViewBean greetfromDb(@PathVariable Integer id, @PathVariable String name) {
		Greeting greeting = greetingService.getGreeting(id);
		if (greeting != null) {
			return new GreetingViewBean(greeting.getGreetText(), "Mr", name, new Date());
		} else {
			return new GreetingViewBean("UNKNOWN", "UNKNOWN", name, new Date());
		}

	}

	@RequestMapping(path = "/greet/all", method = RequestMethod.GET)
	public List<Greeting> getAllGreetings() {
		return greetingService.getAllGreetings();

	}

}
