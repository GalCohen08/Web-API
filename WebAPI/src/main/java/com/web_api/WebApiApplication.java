package com.web_api;

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
			contactRepository.save(new Contact("Gustavo", "Ponce", "Basel","galcohen08@gmail.com","0792880361"));
			contactRepository.save(new Contact("John", "Smith","Kajungo@bluewin.ch","Zurich","0792880361"));
			contactRepository.save(new Contact("Jim ", "Morrison","JimMorrison@bluewin.ch","Schmitten","0792880332"));
			contactRepository.save(new Contact("David", "Gilmour","DavidGilmour@gmail.com","Bern","0792880361"));
			logger.info("The sample data has been generated");
		};
	}
}
