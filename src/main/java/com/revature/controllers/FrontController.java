package com.revature.controllers;

import com.revature.daos.DepartmentPostgres;
import com.revature.models.Department;
import com.revature.services.DepartmentService;

public class FrontController {

	private static DepartmentService ds;
	
	public static void main(String[] args) {
		
		Department d1 = new Department(2, "newDP", 2000.00);
		
		DepartmentPostgres dp = new DepartmentPostgres();
		System.out.println(dp.getById(2));
		System.out.println(dp.update(d1));
		System.out.println(dp.getById(2));
		System.out.println(dp.delete(d1));
		
		
		System.out.println(dp.getAll());
		Department d = new Department();
		d.setName("Sales");
		d.setMonthlyBudget(7000.00);
		System.out.println(dp.add(d));
		System.out.println(dp.getAll());
	}
}
