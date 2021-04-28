package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.revature.models.Department;
import com.revature.models.Employee;


import util.ConnectionUtil;

public class EmployeePostgres implements EmployeeDao  {

	@Override
	public Employee add(Employee t) {
		// TODO Auto-generated method stub
		/* */
		Employee employee = null;
		String sql = "INSERT INTO public.employees(\n"
				+ "	 empl_name, monthly_salary, empl_position,"
				+ " manager_id, dept_id)\n"
				+ "	VALUES (?, ?, ?, ?, ?);";
//		String sql = "insert into departments (dept_name, monthly_budget) values (?,?);";
		String[] keys = {"empl_id"};
		
		try(Connection con = ConnectionUtil.getConnectionFromEnv()){
			PreparedStatement ps = con.prepareStatement(sql,keys);
			//PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, t.getName());
			ps.setDouble(2, t.getMonthlySalary());
			ps.setString(3,  t.getPosition());
			ps.setString(4, t.getPosition());
			ps.setInt(5, t.getId());
		
			
			
			//ResultSet rs = ps.executeQuery();
			ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
			
			if(rs.next()) {
				employee = t;
				employee.setId(rs.getInt(1));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employee;
	
	}

	@Override
	public Employee getById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer update(Employee t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(Employee t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
