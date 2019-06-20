package com.locationinfo;

import com.locationinfo.serviceimpl.FourSquareServiceProvider;
import com.locationinfo.serviceimpl.GoogleServiceProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Sadashiv Kadam
 */
@SpringBootApplication
@EnableAutoConfiguration
public class LocationInfoApplication {

	public static void main(String[] args) {

		SpringApplication.run(LocationInfoApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	@Bean
	public GoogleServiceProvider googleProviderService() {
		return new GoogleServiceProvider();
	}

	@Bean
	public FourSquareServiceProvider fourSquareServiceProvider() {
		return new FourSquareServiceProvider();
	}


}
