package com.newapp.Webapp;

import java.util.concurrent.CountDownLatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.newapp.Webapp.Controller","com.newapp.Webapp.Dto","com.newapp.Webapp.Entity","com.newapp.Webapp.enums" , "com.newapp.Webapp.exception"
		, "com.newapp.Webapp.Mapper" , "com.newapp.Webapp.Repo" , "com.newapp.Webapp.Security" , "com.newapp.Webapp.Service.implementation", "com.newapp.Webapp.Service.Interface",
		"com.newapp.Webapp.Specification"})
public class WebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
		
	}
	
	@Bean
	public  RestTemplate resttemplate() {
		return new RestTemplate();
	}

}
