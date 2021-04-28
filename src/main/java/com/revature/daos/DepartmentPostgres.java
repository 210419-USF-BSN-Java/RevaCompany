package com.revature.daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Department;

import util.ConnectionUtil;

public class DepartmentPostgres implements DepartmentDao{

	@Override
	public Department add(Department t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Department getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Department> getAll() {
		List<Department> departments = new ArrayList<>();
		String sql = "select * from departments;";

		try {
			Connection c = ConnectionUtil.getConnectionFromEnv();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				int deptId = rs.getInt("dept_id");
				String deptName = rs.getString("dept_name");
				double budget = rs.getDouble("monthly_budget");
				departments.add(new Department(deptId, deptName, budget));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return departments;
	}

	@Override
	public Integer update(Department t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(Department t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Department> getDepartmentsByMonthlyBudget(Double budget) {
		// TODO Auto-generated method stub
		return null;
	}

}
