package com.revature.controllers;



import java.sql.SQLException;

import com.revature.daos.DepartmentPostgres;
import com.revature.models.Department;

import com.revature.services.DepartmentService;
import com.revature.services.DepartmentServiceImplementation;
import com.revature.services.EmployeeService;
import com.revature.services.EmployeeServiceImplementation;

import util.ConnectionUtil;

public class FrontController {

	static DepartmentService ds = new DepartmentServiceImplementation();
	static EmployeeService es = new EmployeeServiceImplementation();
	
}

