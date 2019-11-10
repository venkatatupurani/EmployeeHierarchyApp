package com.employeehierarchy.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.employeehierarchy.service.EmployeeHierarchyService;

@RestController
public class EmployeeHierarchyServiceController {

	@Autowired
	EmployeeHierarchyService employeeHierarchyService;
	
	@GetMapping(value = "/employees/{employeeId}" )
	public String getEmployeeHierarchy(@PathVariable("employeeId") int employeeId) throws IOException {
		employeeHierarchyService.getEmployeeHierarchy(employeeId);
		return "Hierarchy is printed in console.";
	}
	
	@GetMapping(value = "/changeParentEmployee/{employeeId}" )
	public String changeParentEmployee(@PathVariable("employeeId") int employeeId) throws IOException {
		employeeHierarchyService.changeToParentNode(employeeId);
		return "Given node is changed to its parent. New Hierarchy is printed in console.";
	}
	
}
