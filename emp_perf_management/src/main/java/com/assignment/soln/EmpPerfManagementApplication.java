package com.assignment.soln;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class EmpPerfManagementApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(EmpPerfManagementApplication.class, args);
		System.out.println("Employee Performance Management Application Started Successfully! on PORT :: "
				+ context.getEnvironment().getProperty("server.port"));
	}

}
