package com.revature.controllers;

import com.revature.daos.DepartmentPostgres;
import com.revature.daos.EmployeePostgres;
import com.revature.models.Department;
import com.revature.models.Employee;
import com.revature.services.DepartmentService;

public class FrontController {

	private static DepartmentService ds;
	
	public static void main(String[] args) {
		
		DepartmentPostgres dp = new DepartmentPostgres();
		System.out.println(dp.getAll());
		Department d = new Department();
		d.setName("Sales");
		d.setMonthlyBudget(7000.00);
		System.out.println(dp.add(d));
		System.out.println(dp.getAll());
		
		System.out.println(dp.getById(5));
		
		System.out.println(dp.update(new Department (5, null, 5000.00)));
		
		System.out.println(dp.delete(new Department(2,null, null)));
		
		EmployeePostgres emp = new EmployeePostgres();
		d.setId(5);
		Employee e= new Employee(null, "Devaraj", "Trainee", 3000.00f, new Department(5, "mm", null), new Employee(16));
		
		emp.add(e);
	}
}
