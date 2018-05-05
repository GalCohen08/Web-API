package com.web_api;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.web_api.model.Contact;
import com.web_api.repository.ContactRepository;

@SpringBootApplication(scanBasePackages={"com.web_api"})
public class WebApiApplication {
	private static final Logger logger = LoggerFactory.getLogger(WebApiApplication.class); 
	public static void main(String[] args) {
		SpringApplication.run(WebApiApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner setup(ContactRepository contactRepository){
		return (args) -> {
			contactRepository.save(new Contact("Gustavo", "Ponce", "Basel","1@1","0792880361"));
			contactRepository.save(new Contact("John", "Smith","2@1","Zurich","0792880361"));
			contactRepository.save(new Contact("Jim ", "Morrison","3@1","Schmitten","0792880332"));
			contactRepository.save(new Contact("David", "Gilmour","4@1","Bern","0792880361"));
			logger.info("The sample data has been generated");
		};
	}
}
