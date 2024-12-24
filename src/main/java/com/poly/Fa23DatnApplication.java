package com.poly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootApplication
public class Fa23DatnApplication {
	 @Bean
	    public ObjectMapper objectMapper() {
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	        return objectMapper;
	    }

	public static void main(String[] args) {
		SpringApplication.run(Fa23DatnApplication.class, args);
	}

}
