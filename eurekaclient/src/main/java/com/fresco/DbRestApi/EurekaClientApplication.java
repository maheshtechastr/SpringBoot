package com.fresco.DbRestApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EurekaClientApplication {

	public static void main(String[] args) {
		System.setProperty("os.arch", "x86_64");
		SpringApplication.run(EurekaClientApplication.class, args);
	}

}
