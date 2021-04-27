package com.revature.daos;

import com.revature.models.Employee;

public interface EmployeeDao extends GenericDao<Employee>{

	public Employee getByName(String name);
}
