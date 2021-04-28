package com.revature.controllers;

import java.util.List;

import com.revature.daos.DepartmentPostgres;
import com.revature.daos.EmployeePostgres;
import com.revature.models.Department;
import com.revature.models.Employee;

public class FrontController {

	// private static DepartmentService ds;
	
	public static void main(String[] args) {
		
		DepartmentPostgres dp = new DepartmentPostgres();
		EmployeePostgres ep = new EmployeePostgres();
		
//		System.out.println(dp.getAll());
//		Department d = new Department();
//		d.setName("Sales");
//		d.setMonthlyBudget(7000.00);
//		System.out.println(dp.add(d));
//		System.out.println(dp.getAll());
		System.out.println(dp.getById(5));
		System.out.println(dp.update(new Department(2, "Test", 4000.55)));
		System.out.println(dp.add(new Department(8, "Another Test", 3030.03)));
		System.out.println(dp.delete(new Department(8, "Another Test", 3030.03)));
		
		System.out.println(ep.add(new Employee(0, "TEST EMPLOYEE", "TESTER", 20.02f, dp.getById(2), ep.getById(4))) + "\n");
		
		List<Employee> li = ep.getAll();
		for (Employee e : li) {
			System.out.println(e);
		}
		
		Employee test = new Employee(14, "NEW TEST", "HELLO", 40.04f, dp.getById(6), ep.getById(8));
		System.out.println(ep.update(test));
		System.out.println(ep.delete(new Employee(16, "", "", 0f, new Department(), new Employee())) + "\n");
		
		li = ep.getByName("TEST EMPLOYEE");
		for (Employee e : li) {
			System.out.println(e);
		}
	}
}
