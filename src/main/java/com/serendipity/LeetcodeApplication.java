package com.serendipity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LeetcodeApplication {

	public static void main(String[] args) {
		System.out.println(ClassLoader.getSystemClassLoader().getClass());
		SpringApplication.run(LeetcodeApplication.class, args);
	}

}
