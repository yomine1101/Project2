package com.practice.springsecondphrasepractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpringSecondPhrasePracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecondPhrasePracticeApplication.class, args);
	}

}
