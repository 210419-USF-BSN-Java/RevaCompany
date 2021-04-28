package com.revature.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Employee;

import util.ConnectionUtil;

public class EmployeePostgres implements EmployeeDao {

	@Override
	public Employee add(Employee t) {
		Employee emp = null;
		String sql = "insert into employees (empl_name, monthly_salary, empl_position, manager_id, dept_id) values (?,?,?,?,?) returning dept_id;";
		
		try {
			PreparedStatement ps = ConnectionUtil.getConnectionFromEnv().prepareStatement(sql);
			ps.setString(1, t.getName());
			ps.setDouble(2, t.getMonthlySalary());
			ps.setString(3, t.getPosition());
			ps.setInt(4, t.getManager().getId());
			ps.setInt(5, t.getDepartment().getId());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				emp = t;
				t.setId(rs.getInt("empl_id"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return emp;
	}

	@Override
	public Employee getById(Integer id) {
		String sql = "select * from employees where empl_id = ?;";
		
		
		PreparedStatement ps;
		try {
			ps = ConnectionUtil.getConnectionFromEnv().prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return new Employee(rs.getInt("empl_id"), rs.getString("empl_name"), rs.getString("empl_position"), rs.getFloat("monthly_salary"), null, null);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Employee> getAll() {
		List<Employee> employees = new ArrayList<>();
		String sql = "select * from employees;";
		
		try {
			PreparedStatement ps = ConnectionUtil.getConnectionFromEnv().prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery(sql);
			
			
			while (rs.next()) {
				employees.add(new Employee(rs.getInt("empl_id"), rs.getString("empl_name"), rs.getString("empl_position"), rs.getFloat("monthly_salary"), null, null));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return employees;
	}

	@Override
	public Integer update(Employee t) {
		String sql = "update employees set empl_name = ?, empl_position = ?, monthly_salary = ?, manager_id = ?, dept_id = ?  where empl_id = ?;";
		int id = t.getId(); 
		
		
		try {
			PreparedStatement ps = ConnectionUtil.getConnectionFromEnv().prepareStatement(sql);
			ps.setString(1, t.getName());
			ps.setString(2, t.getPosition());
			ps.setDouble(3, t.getMonthlySalary());
			ps.setInt(4, t.getManager().getId());
			ps.setInt(5, t.getDepartment().getId());
			ps.setInt(6, id);
			
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
