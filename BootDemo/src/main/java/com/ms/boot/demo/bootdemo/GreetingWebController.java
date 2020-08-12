package com.ms.boot.demo.bootdemo;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GreetingWebController {
	
	@RequestMapping(path = "/web/greet/{name}",method = RequestMethod.GET)
	public String renderComplexGreeting(@PathVariable String name, Model model)
	{
		GreetingViewBean vb = new GreetingViewBean("Hello","Mr",name, new Date());
		model.addAttribute("greeting", vb);
		return "index";
		
		
	}

}
