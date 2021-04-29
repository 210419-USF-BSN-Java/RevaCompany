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
		d.setMonthlyBudget(8000.00);
		System.out.println(dp.update(d));
		System.out.println(dp.delete(d));
		System.out.println(dp.getById(2));

		EmployeePostgres ep = new EmployeePostgres();
		ep.getAll().stream().forEach(em -> System.out.println(em));

		Department dept = dp.getById(2);
		Employee manager = new Employee();
		manager.setId(2);

		Employee emp = new Employee();
		emp.setName("FirstName LastName");
		emp.setMonthlySalary(2000.00f);
		emp.setPosition("Manager");
		emp.setManager(manager);
		emp.setDepartment(dept);

		System.out.println(ep.add(emp));
		emp.setMonthlySalary(3000.00f);

		System.out.println(ep.update(emp));
		System.out.println(ep.delete(emp));
		System.out.println(ep.getById(85)); // shows that the deletion was correct!

	}
}
