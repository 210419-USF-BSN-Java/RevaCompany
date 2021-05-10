  
package com.revature.controllers;

import com.revature.services.DepartmentService;
import com.revature.services.DepartmentServiceImplementation;
import com.revature.services.EmployeeService;
import com.revature.services.EmployeeServiceImplementation;

public class FrontController {

	static DepartmentService ds = new DepartmentServiceImplementation();
	static EmployeeService es = new EmployeeServiceImplementation();

}