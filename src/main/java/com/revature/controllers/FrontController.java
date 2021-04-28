package com.revature.controllers;

import com.revature.daos.EmployeePostgres;
import com.revature.models.Employee;
import com.revature.services.DepartmentService;

public class FrontController {

	private static DepartmentService ds;
	
	public static void main(String[] args) {
		
//		DepartmentPostgres dp = new DepartmentPostgres();
//		System.out.println(dp.getAll());
//		Department d = new Department();
//		d.setName("Sales");
//		d.setMonthlyBudget(7000.00);
//		System.out.println(dp.add(d));
//		System.out.println(dp.getAll());		
//		Department d;
//		d = dp.getById(1);
//		System.out.println(d);
//		
//		d.setMonthlyBudget(5000.00);
//		dp.update(d);
//		
//		d = dp.getById(1);
//		System.out.println(d);
		
		EmployeePostgres ep = new EmployeePostgres();
		System.out.println(ep.getAll());
		
		Employee e = new Employee(0, "Kemo", "Assosicate", 1000f, null, null);
		
		ep.delete(e); //clear old data
		
		e = ep.add(e); //Set the id from the db
		System.out.println(e); //print if it was added to db and not null
		System.out.println(ep.getById(e.getId())); //print by id 
		e.setMonthlySalary(2000f); //Change salary 
		ep.update(e); //Update
		System.out.println(ep.getByName(e.getName())); //Get by name, and see if updated
	
	}
}
