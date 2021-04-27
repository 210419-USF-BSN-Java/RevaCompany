package com.revature.daos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.revature.models.Department;

public class DepartmentArrayList implements DepartmentDao{

	private List<Department> departments;
	
	public DepartmentArrayList() {
		departments = new ArrayList<>();
		
		Department d = new Department(0, "HR", 2000.00);
		Department d1 = new Department(1, "Sales", 10000.00);
		Department d2 = new Department(2, "Accounting", 7000.00);
		Department d3 = new Department(3, "Finance", 7000.00);
		departments.add(d);
		departments.add(d1);
		departments.add(d2);
		departments.add(d3);
		
	}
	
	@Override
	public Department add(Department t) {
		departments.add(t);
		return t;
	}

	@Override
	public Department getById(Integer id) {
		for(Department d: departments) {
			if(d.getId() == id) {
				return d;
			}
		}
		return null;
	}

	@Override
	public List<Department> getAll() {
		return departments;
	}

	@Override
	public Integer update(Department t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(Department t) {
		return departments.remove(t)? 1 : 0;
	}

	@Override
	public List<Department> getDepartmentsByMonthlyBudget(Double budget) {
//		ArrayList<Department> deps = new ArrayList<>();
//		for(Department d: departments) {
//			if(d.getMonthlyBudget() == budget) {
//				deps.add(d);
//			}
//		}
//		return deps;
		
		return departments.stream().filter(d->budget.equals(d.getMonthlyBudget())).collect(Collectors.toList());
	}

	
}
