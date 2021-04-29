package com.revature.daos;

import java.util.List;

import com.revature.models.Employee;

public interface EmployeeDao extends GenericDao<Employee>{

	public List<Employee> getByName(String name);
}
