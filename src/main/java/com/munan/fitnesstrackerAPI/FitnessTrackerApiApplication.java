package com.munan.fitnesstrackerAPI;

import com.munan.fitnesstrackerAPI.config.security.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class FitnessTrackerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitnessTrackerApiApplication.class, args);
	}

}
