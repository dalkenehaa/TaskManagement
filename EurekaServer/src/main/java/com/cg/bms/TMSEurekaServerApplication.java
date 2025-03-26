package com.cg.bms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class TMSEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TMSEurekaServerApplication.class, args);
		System.out.println("Eureka server is started on 8761 port");
	}

}
