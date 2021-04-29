package com.revature.controllers;


import java.sql.SQLException;

import com.revature.daos.DepartmentPostgres;
import com.revature.models.Department;
import com.revature.services.DepartmentService;

import util.ConnectionUtil;

public class FrontController {

	private static DepartmentService ds;
	
	public static void main(String[] args) throws SQLException {
		
		DepartmentPostgres dp = new DepartmentPostgres();
		Department d = new Department(14, "Systems", 1414.14);
		//dp.add(d);
		//dp.delete(d);
		
		//System.out.println(dp.getById(3));
		//System.out.println(dp.update(d));
		//System.out.println(dp.add(new Department(8, "Another Test", 3030.03)));
		//System.out.println(dp.add(new Department(14, "clowns", 1414.14)));
		
		//System.out.println(dp.delete())
	
	}
}