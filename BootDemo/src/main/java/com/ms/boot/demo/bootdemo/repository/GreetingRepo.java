package com.ms.boot.demo.bootdemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ms.boot.demo.bootdemo.model.Greeting;

@Repository
public interface GreetingRepo extends JpaRepository<Greeting, Integer> {
	
	List<Greeting> findByType(String type);

}
