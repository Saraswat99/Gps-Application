package com.veichle.app;

import com.veichle.app.config.CustomAuditorAware;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;


@SpringBootApplication
@EnableJpaAuditing
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return ()-> {
			String userName=SecurityContextHolder.getContext().getAuthentication().getName();
			return Optional.ofNullable(userName);
		};
	}

}
