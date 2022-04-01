package com.cms.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableScheduling
@RestController
@EnableHystrix
@EnableEurekaServer
public class Application extends SpringBootServletInitializer implements ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
    public static void main(String[] args) {
    	logger.info("RUN SPRING BOOT APP CLASS...");
        SpringApplication.run(Application.class, args);
    }
    
    public void run(ApplicationArguments arg0) throws Exception {
       System.out.println("Application Runner Start");
    }

}
