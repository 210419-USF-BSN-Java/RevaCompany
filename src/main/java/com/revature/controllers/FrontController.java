package com.revature.controllers;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import com.revature.models.Department;
import com.revature.models.Employee;
import com.revature.services.DepartmentService;
import com.revature.services.DepartmentServiceImplementation;
import com.revature.services.EmployeeService;
import com.revature.services.EmployeeServiceImplementation;

import util.h2Util;

public class FrontController {

	static DepartmentService ds = new DepartmentServiceImplementation();
	static EmployeeService es = new EmployeeServiceImplementation();

}
