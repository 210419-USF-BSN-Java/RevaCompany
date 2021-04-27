package com.revature.daos;

import java.util.List;

import com.revature.models.Department;

public interface DepartmentDao extends GenericDao<Department> {

	public List<Department> getDepartmentsByMonthlyBudget(Double budget);
}
