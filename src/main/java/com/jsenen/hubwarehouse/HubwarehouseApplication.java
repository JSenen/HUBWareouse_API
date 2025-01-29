package com.jsenen.hubwarehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.jsenen.hubwarehouse")
public class HubwarehouseApplication {

	public static void main(String[] args) {



		SpringApplication.run(HubwarehouseApplication.class, args);
	}

}
