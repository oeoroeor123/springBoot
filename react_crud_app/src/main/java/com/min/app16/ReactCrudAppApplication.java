package com.min.app16;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ReactCrudAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactCrudAppApplication.class, args);
	}

}
