package com.Nishant.LinkedIn_Mini.PostService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
//@EntityScan("com.Nishant.LinkedIn_Mini")
//@EnableJpaRepositories("com.Nishant.LinkedIn_Mini")
public class PostServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostServiceApplication.class, args);
	}

}
