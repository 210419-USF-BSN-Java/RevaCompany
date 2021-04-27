package com.revature.services;

import java.util.List;

import com.revature.models.Department;

public interface DepartmentService {

	public Integer addDepartment(Department d);
	public Department getDepartmentById(Integer id);
	public List<Department> getDepartments();
	public List<Department> getDepartmentsByMonthlyBudget(Double budget);
	
}
