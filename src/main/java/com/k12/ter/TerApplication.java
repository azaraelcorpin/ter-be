package com.k12.ter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
@EnableSwagger2
public class TerApplication {

	public static final Logger LOGGER = LogManager.getLogger(TerApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(TerApplication.class, args);

		LOGGER.info("Info level log message");
        LOGGER.debug("Debug level log message");
        LOGGER.error("Error level log message");
	}

}
