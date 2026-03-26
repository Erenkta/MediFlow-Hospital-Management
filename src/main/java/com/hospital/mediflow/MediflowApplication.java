package com.hospital.mediflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MediflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediflowApplication.class, args);
	}

}
