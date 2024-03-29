package com.curso.cast.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfiguration {
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.curso.cast"))
				.paths(PathSelectors.ant("/**"))
				.build().apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfo ("API para controle de cursos", 
				"Sistema de API desenvolvido em SpringBoot com Hibernate e JPA",
				"Versão 1.0",
				"http://castgroup.com.br",
				new Contact("Vansley Curso Cast Group",
						"https://github.com/vansley",
						"vansleytsilva@gmail.com"),
						"Licença da API",
						"https://github.com/vansley", Collections.emptyList());
	}
	

}