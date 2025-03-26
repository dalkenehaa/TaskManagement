package com.cg.tms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EnableDiscoveryClient
public class AttachmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttachmentServiceApplication.class, args);
		System.out.println("Attachment Service Application started on port number 8083...");
	}

}
