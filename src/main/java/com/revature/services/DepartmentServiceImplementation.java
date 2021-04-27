package com.revature.services;

import java.util.List;

import com.revature.daos.DepartmentArrayList;
import com.revature.daos.DepartmentDao;
import com.revature.models.Department;

public class DepartmentServiceImplementation implements DepartmentService {
	
	private DepartmentDao dd;
	
	public DepartmentServiceImplementation() {
		dd = new DepartmentArrayList();
	}
	
	@Override
	public Integer addDepartment(Department d) {
		return dd.add(d).getId();
	}

	@Override
	public Department getDepartmentById(Integer id) {
		return dd.getById(id);
	}

	@Override
	public List<Department> getDepartments() {
		return dd.getAll();
	}

	@Override
	public List<Department> getDepartmentsByMonthlyBudget(Double budget) {
		return dd.getDepartmentsByMonthlyBudget(budget);
	}

}
