package com.revature.controllers;

import com.revature.daos.DepartmentPostgres;
import com.revature.services.DepartmentService;

public class FrontController {

	private static DepartmentService ds;
	
	public static void main(String[] args) {
		
		DepartmentPostgres dp = new DepartmentPostgres();
		System.out.println(dp.getAll());
		
	}
}
