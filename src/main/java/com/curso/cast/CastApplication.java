package com.curso.cast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.curso.cast.services.AuditingService;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditingService")
public class CastApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(CastApplication.class);

	public static void main(String[] args) {
		LOGGER.info("iniciando Api");
		SpringApplication.run(CastApplication.class, args);
		LOGGER.info("Api iniciada com sucesso");
		
	}
	 @Bean
	    AuditorAware<String> auditorProvider() {
		 return new AuditingService();
	    }
}
