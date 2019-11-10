package com.employeehierarchy;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.employeehierarchy.service.EmployeeHierarchyService;

@SpringBootApplication
@ComponentScan("com.employeehierarchy")
public class EmployeeHierarchyServiceApplication {

	public static void main(String[] args) throws IOException {
		ApplicationContext appContext = SpringApplication.run(EmployeeHierarchyServiceApplication.class, args);		
	}
}
