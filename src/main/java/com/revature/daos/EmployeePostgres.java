package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Department;
import com.revature.models.Employee;

import util.ConnectionUtil;

public class EmployeePostgres implements EmployeeDao{

	@Override
	public Employee add(Employee t) {
		Employee employee = null;
		String sql = "insert into employees (empl_id, empl_name, empl_position, monthly_salary, dept_id, manager_id) values (?,?,?,?,?,?);";
		String[] keys = {"empl_id"};
		
		try(Connection con = ConnectionUtil.getConnectionFromEnv()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(2,t.getName());
			ps.setString(3,t.getPosition());
			ps.setFloat(4,t.getMonthlySalary());
			ps.setInt(5,t.g); //TODO: get department somehow
			ps.setInt(4,t.getManager());
			
			ResultSet rs = ps.executeQuery();
			
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
		String sql = "SELECT * FROM employee where empl_id = ? JOIN departments on employees.dept_id = departments.dept_id";
		Employee  employee = new Employee();
		DepartmentPostgres department = new DepartmentPostgres();
		Employee manager = new Employee();
		manager.setId(2);
		
		try {
			PreparedStatement ps = ConnectionUtil.getConnectionFromEnv().prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				employee = new Employee(rs.getInt("empl_id"), 
						rs.getString("empl_name"), 
						rs.getString("empl_position"),
						rs.getFloat("monthly_salary"),
						department.getById(rs.getInt("empl_id")),
						manager);		
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return employee;
	}

	@Override
	public List<Employee> getAll() {
		List<Employee> employee = new ArrayList<>();
		String sql = "select * from employees;";
		DepartmentPostgres department = new DepartmentPostgres();

		try {
			Connection c = ConnectionUtil.getConnectionFromEnv();
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				int empId = rs.getInt("empl_id");
				String name = rs.getString("empl_name");
				String position = rs.getString("empl_position");
				Float monthlySalary = rs.getFloat("monthly_salary");
				Department dept = department.getById(rs.getInt("empl_id"));
				employee.add(new Employee());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employee;
	}

	@Override
	public Integer update(Employee t) {
		String sql = "update departments set (empl_name, monthly_salary, empl_position, manager_id, dept_id) = (?,?,?,?,?) where empl_id = ?; ";
		int success = 0;
		try {
			PreparedStatement ps = ConnectionUtil.getConnectionFromEnv().prepareStatement(sql);
			ps.setString(1,t.getName());
			ps.setFloat(2,t.getMonthlySalary());
			ps.setString(3,t.getPosition());
			ps.setInt(4,t.getManager().getId());
			ps.setInt(5,t.getDepartment().getId());
			
			success = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public Integer delete(Employee t) {
		String sql = "DELETE FROM employees where empl_id = ?; ";
		int success = 0;
		try {
			PreparedStatement ps = ConnectionUtil.getConnectionFromEnv().prepareStatement(sql);
			ps.setInt(1,t.getId());
			
			success = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	@Override
	public Employee getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
