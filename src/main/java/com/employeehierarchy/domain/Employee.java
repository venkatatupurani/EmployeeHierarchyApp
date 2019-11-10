package com.employeehierarchy.domain;

import java.util.List;

import org.springframework.stereotype.Component;

public class Employee {
	int employeeId;
	String employeeName;
	int empReportToId;
	int positionId;
	List<Employee> subordinates;
	
	public Employee(String employeeId, String employeeName, String empReportToId, int positionId) {
		try {
	        this.employeeId = Integer.parseInt(employeeId);	        
	        this.employeeName = employeeName;
	        this.empReportToId = Integer.parseInt(empReportToId);
	        this.positionId = positionId;
    	}  catch (Exception e) {
			 System.out.println("Exception creating employee:" + e.getMessage());
		}
	}
	
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public int getEmpReportToId() {
		return empReportToId;
	}

	public void setEmpReportToId(int empReportToId) {
		this.empReportToId = empReportToId;
	}

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	public List<Employee> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(List<Employee> subordinates) {
		this.subordinates = subordinates;
	}
	
}
