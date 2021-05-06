package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
		/*
		 * Department is being passed in
		 * Take in the fields
		 * add a record to the department table
		 * Insert into tableName (col1, col2) values(x,y);
		 * insert into departments (dept_name, monthly budget) values (t.getName(), t.getBudget());
		 * get connection
		 * create prepared statement from connection
		 * setting the variables
		 * execute
		 */
		Department department = null;
		//format is schema.table
		String sql = "insert into test.departments (dept_name, monthly_budget) values (?,?) returning dept_id;";

		String[] keys = {"dept_id"};
		
		try(Connection con = ConnectionUtil.getHardCodedConnection()){
//			PreparedStatement ps = con.prepareStatement(sql,keys);
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,t.getName());
			ps.setDouble(2, t.getMonthlyBudget());
			
			ResultSet rs = ps.executeQuery();
//			ps.executeUpdate();
//			ResultSet rs = ps.getGeneratedKeys();
			
			if(rs.next()) {
				department = t;
				department.setId(rs.getInt(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return department;
	}

	@Override
	public Department getById(Integer id) {
		Department departmentid = new Department();
		String sql = "SELECT * FROM test.departments WHERE dept_id = ?";
		try {
			PreparedStatement ps = ConnectionUtil.getHardCodedConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				departmentid.setId(rs.getInt("dept_id"));
				departmentid.setName(rs.getString("dept_name"));
				departmentid.setMonthlyBudget(rs.getDouble("monthly_budget"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		return departmentid;
	}

	@Override
	public List<Department> getAll() {
		List<Department> departments = new ArrayList<>();
		String sql = "select * from test.departments";

		try {
			PreparedStatement ps = ConnectionUtil.getHardCodedConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
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
		Integer result = null;
		String sql = "UPDATE test.departments SET (dept_name, monthly_budget) = (?,?) WHERE dept_id = ? ";
		
		try {
			PreparedStatement ps = ConnectionUtil.getHardCodedConnection().prepareStatement(sql);
			ps.setString(1,t.getName());
			ps.setDouble(2, t.getMonthlyBudget());
			ps.setInt(3, t.getId());
			result = ps.executeUpdate();
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Integer delete(Department t) {
		Integer result = null;
		String sql = "DELETE FROM test.departments where dept_id = ?";
		try {
			PreparedStatement ps = ConnectionUtil.getHardCodedConnection().prepareStatement(sql);
			ps.setInt(1, t.getId());
			result = ps.executeUpdate();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Department> getDepartmentsByMonthlyBudget(Double budget) {
		String sql = "SELECT * FROM test.departments where monthly_budget = ?";
		List<Department> departments = new ArrayList<>();
		
		try {
			PreparedStatement ps = ConnectionUtil.getHardCodedConnection().prepareStatement(sql);
			ps.setDouble(1, budget);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				departments.add(new Department(rs.getInt("dept_id"), rs.getString("dept_name"), rs.getDouble("monthly_budget")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return departments;
	}

}