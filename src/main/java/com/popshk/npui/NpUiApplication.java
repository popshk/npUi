package com.popshk.npui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NpUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NpUiApplication.class, args);
	}

}
