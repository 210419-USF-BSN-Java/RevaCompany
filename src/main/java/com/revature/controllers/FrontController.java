package com.revature.controllers;

import com.revature.models.Department;
import com.revature.services.DepartmentService;
import com.revature.services.DepartmentServiceImplementation;

public class FrontController {

	private static DepartmentService ds;
	
	public static void main(String[] args) {
		
		ds =  new DepartmentServiceImplementation();
		
		System.out.println(ds.getDepartments());
		ds.addDepartment(new Department(4, "Fun", 7001.00));
		System.out.println(ds.getDepartments());
		System.out.println(ds.getDepartmentsByMonthlyBudget(7000.00));
	}
}
