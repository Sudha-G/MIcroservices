package com.ms.boot.demo.bootdemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ms.boot.demo.bootdemo.model.Greeting;
import com.ms.boot.demo.bootdemo.repository.GreetingRepo;

@Component
public class GreetingService {

	@Autowired
	private GreetingRepo repo;

	public List<Greeting> getAllGreetings() {
		return repo.findAll();
	}

	public Greeting getGreeting(Integer id) {
		Optional<Greeting> og = repo.findById(id);
		if (og.isPresent()) {
			return og.get();
		} else {
			return null;
		}

	}

}
