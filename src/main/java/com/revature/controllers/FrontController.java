package com.revature.controllers;


import com.revature.daos.DepartmentPostgres;
import com.revature.daos.EmployeePostgres;
import com.revature.models.Department;

import com.revature.services.DepartmentService;
import com.revature.services.DepartmentServiceImplementation;
import com.revature.services.EmployeeService;
import com.revature.services.EmployeeServiceImplementation;

public class FrontController {


	static DepartmentService ds = new DepartmentServiceImplementation();
	static EmployeeService es = new EmployeeServiceImplementation();



	
	public static void main(String[] args) {
		
		DepartmentPostgres dp = new DepartmentPostgres();
		EmployeePostgres ep = new EmployeePostgres();
		//System.out.println(dp.getAll());
		Department d = new Department();
		//System.out.println(dp.getById(2));
		//System.out.println(ep.getById(3));
		System.out.println(ep.getAll());
//		d.setName("Sales");
//		d.setMonthlyBudget(7000.00);
//		System.out.println(dp.add(d));
//		System.out.println(dp.getAll());
	}

}
