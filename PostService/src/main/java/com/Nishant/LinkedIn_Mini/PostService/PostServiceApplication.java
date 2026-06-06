package com.Nishant.LinkedIn_Mini.PostService;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients
@SpringBootApplication
//@EntityScan("com.Nishant.LinkedIn_Mini")
//@EnableJpaRepositories("com.Nishant.LinkedIn_Mini")
public class PostServiceApplication {

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
		SpringApplication.run(PostServiceApplication.class, args);
	}

}
