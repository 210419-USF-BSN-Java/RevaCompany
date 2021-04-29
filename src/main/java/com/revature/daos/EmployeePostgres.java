package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.revature.models.Department;
import com.revature.models.Employee;

import util.ConnectionUtil;

public class EmployeePostgres implements EmployeeDao{
	
	public Employee add(Employee e) {
		Employee employee = null;
		String sql = "insert into employee (empl_id, empl_name, monthly_salary, empl_position, manager_id, dept_id)"
				+ " values (?,?,?,?,?,?)";
		
		try(Connection con = ConnectionUtil.getConnectionFromEnv()){
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, e.getId());
			ps.setString(2, e.getName());
			ps.setString(3, e.getPosition());
			ps.setFloat(4, e.getMonthlySalary());
			ps.setObject(5, e.getDepartment());
			ps.setObject(6, e.getManager());
			
			ResultSet rs = ps.executeQuery();
//			ps.executeUpdate();
//			ResultSet rs = ps.getGeneratedKeys();
			
			if(rs.next()) {
				employee = e;
				employee.setId(rs.getInt(1));
			}
			
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return e;
	}
	public Employee getById(Integer id) {
		String str2 = "SELECT e.empl_id, e.empl_name, e.monthly_salary, e.empl_position, e.manager_id, e.dept_id"
				+ " m.empl_id, m.empl_name, m.monthly_salary, m.empl_position, m.manager_id, m.dept_id"
				+ " d.dept_id, d.dept_name, d.monthly_budget"
				+ " FROM employee e"
				+ " join employees m on e.empl_id = m.empl_id"
				+ " join departments d on e.dept_id = d.dept_it WHERE empl_id = ?";
		
		Employee e = new Employee();
		
		try(Connection connection = ConnectionUtil.getConnectionFromEnv()){
			PreparedStatement ps = connection.prepareStatement(str2);
			ps.setInt(1, id);
	        ResultSet rs = ps.executeQuery();  // SAUCE
	        while(rs.next()) {
	        	e.setId(rs.getInt("e.empl_id"));
	        	e.setName(rs.getString("e.empl_name"));
	        	e.setPosition(rs.getString("e.monthly_salary"));
	        	e.setMonthlySalary(rs.getFloat("e.empl_position"));
	        	e.setDepartment(new Department(rs.getInt("d.dept_id"), rs.getString("d.dept_name"), rs.getDouble("d.monthly_budget")));
	        	e.setManager(new Employee(rs.getInt("m.empl_id"), rs.getString("m.empl_name"), rs.getString("m.position"), rs.getFloat("m.monthly_salary"), new Department(rs.getInt("d.dept_id"), rs.getString("d.dept_name"), rs.getDouble("d.monthly_budget")), new Employee()));
	        }
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
        return e;
	}
	public List<Employee> getAll() {
		return null;
	}
	public Integer update(Employee e) {
		return null;
	}
	public Integer delete(Employee e) {
		return null;
	}
	public Employee getByName(String name) {
		return null;
	}
}
