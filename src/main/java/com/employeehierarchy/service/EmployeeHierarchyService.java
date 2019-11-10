package com.employeehierarchy.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.employeehierarchy.domain.Employee;

@Service
public class EmployeeHierarchyService {
	
	static Map<Integer, Employee> employees = new HashMap<Integer, Employee>();

	/**
	 * Define job level for employee hierarchy
	 */
	public enum JobLevel {
		CEO(0),
		COO(1),
		CFO(1),
		VP_MARKETING(1),
		VP_ENGINEERING(1),
		VP_IT(1),
		VP_SALES(1),
		OPERATIONS_DIRECTOR(2),
		PRODUCTIONS_DIRECTOR(2),
		IT_DIRECTOR(2),
		IT_ASSOC_DIRECTOR(3),
		ENGAGEMENT_LEAD(4),
		SR_MANAGER(5),
		SR_TECH_ARCHITECT(5),
		MANAGER(6),
		TECH_ARCHITECT(6),
		TECH_LEAD(7),
		SR_ASSOCIATE(7),
		SR_SOFTWARE_ENGINEER(8),
		SOFTWARE_ENGINEER(9),
		ASSOCIATE(8);
		
		private int value;
		private JobLevel(int value) {
			this.value = value;
		}
	}
	
	/**
	 *  This method reads data from csv file and stores it in  in-memory hashmap.
	 * @throws IOException
	 */
	public static void readDataAndCreateMap() throws IOException {
		String csvFile = "/Users/bluearmy/Documents/Work/STS-workspace/EmployeeData.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		Employee employee = null;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] values = line.split(cvsSplitBy);
				try {
					if (values.length > 1) {
						employee = new Employee(values[0], values[1], values[2], JobLevel.valueOf(values[3]).value);
					}
				} catch (Exception e) {
					// means this is root node / CEO.
					employee = new Employee(values[0], values[1], "0", JobLevel.valueOf(values[2]).value);
				}
				// Save them in static map.
				employees.put(employee.getEmployeeId(), employee);
			}
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException: " + e);
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 *  build tree recursively
	 * @param empId
	 * @throws IOException
	 */
	public void getEmployeeHierarchy(int empId) throws IOException {
		if(employees.isEmpty()) {
			readDataAndCreateMap();
		} 
		Employee employee = employees.get(empId);
		System.out.println("Employee Hierarchy for : " + employee.getEmployeeName() + "\n");
		printEmployeeHierarchy(employee);
	}
	
	/**
	 *  print employee hierarchy in console.
	 * @param emp
	 */
	private void printEmployeeHierarchy(Employee emp) {
		for (int i = 0; i < emp.getPositionId() + 1; i++) {
			System.out.print("\t");
		}
		System.out.println(emp.getEmployeeName());
		// Get subordinates by building hierarchy
		List<Employee> subordinates = getSubordinatesById(emp.getEmployeeId());
		if (subordinates.size() == 0) {
			return;
		}
		System.out.print(" ");
		for (Employee e : subordinates) {
			printEmployeeHierarchy(e);
		}
	
	}

	/**
	 *  scan whole employee hashMap to form a list of subordinates for the given id
	 * @param empReportToId
	 * @return
	 */
	private List<Employee> getSubordinatesById(int empReportToId) {
		List<Employee> subordinates = new ArrayList<Employee>();
		for (Employee emp : employees.values()) {
			if (emp.getEmpReportToId() == empReportToId) {
				subordinates.add(emp);
			}
		}
		return subordinates;
	}
	
	/**
	 * This method swaps given employee to its parent. IF root is given, then it swaps with its first subordinate.
	 * Once swapped, it prints the hierarchy for given node.
	 * @param empId
	 * @throws IOException
	 */
	public void changeToParentNode(int empId) throws IOException {
		if(employees.isEmpty()) {
			readDataAndCreateMap();
		} 
		
		Employee employee = employees.get(empId);
		Employee parentEmployee = null;
		if(employee.getEmpReportToId() == 0) {
			// This is root node. In this case, we will replace it with first child node.
			List<Employee> subordinates = getSubordinatesById(employee.getEmployeeId());
			if(subordinates.size() > 0) {
				Optional<Employee> firstSubordinate = subordinates.stream().findFirst();
				parentEmployee = employees.get(firstSubordinate.get().getEmpReportToId());
			}
		}
		else {
			parentEmployee = employees.get(employee.getEmpReportToId());
		}
		// save given node employee details in temp variables.
		int tempEmpid = employee.getEmployeeId();
		int tempReportid = employee.getEmpReportToId();
		int tempPosid = employee.getPositionId();
		
		
		employee.setEmployeeId(employees.get(employee.getEmpReportToId()).getEmployeeId());
		employee.setEmpReportToId(employees.get(employee.getEmpReportToId()).getEmpReportToId());
		employee.setPositionId(employees.get(employee.getEmpReportToId()).getPositionId());
		
		parentEmployee.setEmployeeId(tempEmpid);
		parentEmployee.setEmpReportToId(tempReportid);
		parentEmployee.setPositionId(tempPosid);
		
		employees.replace(empId, parentEmployee);
		employees.replace(parentEmployee.getEmployeeId(), employee);
		// print hierarchy.
		getEmployeeHierarchy(employee.getEmployeeId());
	}

}
