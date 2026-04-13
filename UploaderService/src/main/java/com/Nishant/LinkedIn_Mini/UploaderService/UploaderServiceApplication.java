package com.Nishant.LinkedIn_Mini.UploaderService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class UploaderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploaderServiceApplication.class, args);
	}

}
