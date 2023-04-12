package com.example.hospitalDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class HospitalDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalDemoApplication.class, args);
	}

}
