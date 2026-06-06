package com.Nishant.LinkedIn_Mini.UploaderService;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class UploaderServiceApplication {

	static {
		// Automatically searches for a .env file in your project root directory
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing()
				.load();

		// Inject the variables directly into Java's system runtime memory pool
		dotenv.entries().forEach(entry ->
				System.setProperty(entry.getKey(), entry.getValue())
		);
	}

	public static void main(String[] args) {
		SpringApplication.run(UploaderServiceApplication.class, args);
	}

}
