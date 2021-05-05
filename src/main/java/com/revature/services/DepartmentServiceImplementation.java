package com.revature.services;

import java.util.List;

import com.revature.daos.DAOFactory;
import com.revature.daos.DepartmentDao;
import com.revature.models.Department;

public class DepartmentServiceImplementation implements DepartmentService {
	
	private DepartmentDao dd;
	
	public DepartmentServiceImplementation() {
		dd = DAOFactory.getDAOFactory().getDepartmentDao();
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

	@Override
	public boolean updateDepartment(Department d) {
		if(dd.update(d) > 0){
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public boolean deleteDepartement(Department d) {
		if(dd.delete(d) > 0){
			return true;
		}else{
			return false;
		}
	}

}
