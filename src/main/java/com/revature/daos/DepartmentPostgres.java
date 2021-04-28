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
		String sql = "insert into departments (dept_name, monthly_budget) values (?,?) returning dept_id;";
//		String sql = "insert into departments (dept_name, monthly_budget) values (?,?);";
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
		String sql = "SELECT * FROM departments where dept_id = ?";
		Department department = new Department();
		
		try {
			PreparedStatement ps = ConnectionUtil.getHardCodedConnection().prepareStatement(sql);
			ps.setDouble(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				department.setId(rs.getInt("dept_id"));
				department.setName( rs.getString("dept_name"));
				department.setMonthlyBudget(rs.getDouble("monthly_budget"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return department;
	}

	@Override
	public List<Department> getAll() {
		List<Department> departments = new ArrayList<>();
		String sql = "select * from departments;";

		try {
			Connection c = ConnectionUtil.getHardCodedConnection();
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
		String sql = "UPDATE departments set monthly_budget = ? WHERE dept_id = ? returning dept_id;";
		Integer numbeOfRowEffected =0;
		
		try(Connection con = ConnectionUtil.getHardCodedConnection()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDouble(1, t.getMonthlyBudget());
			ps.setInt(2, t.getId());
			ResultSet rs = ps.executeQuery();			
			while(rs.next()) {
				numbeOfRowEffected++;
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return numbeOfRowEffected;
	}

	@Override
	public Integer delete(Department t) {
		String sql = "DELETE FROM departments where dept_id = ? returning dept_id;";
		Integer numbeOfRowEffected =0;
		
		try(Connection con = ConnectionUtil.getHardCodedConnection()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, t.getId());
			ResultSet rs = ps.executeQuery();			
			while(rs.next()) {
				numbeOfRowEffected++;
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return numbeOfRowEffected;
	}

	@Override
	public List<Department> getDepartmentsByMonthlyBudget(Double budget) {
		String sql = "SELECT * FROM departments where monthly_budget = ?";
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
