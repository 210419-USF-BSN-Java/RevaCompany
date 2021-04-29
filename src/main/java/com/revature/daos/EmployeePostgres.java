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

public class EmployeePostgres implements EmployeeDao {
	
	DepartmentPostgres dp = new DepartmentPostgres();
	
	@Override
	public Employee add(Employee e) {

		Employee employee = null;
		String sql = "insert into employees (empl_name, monthly_budget) values (?,?) returning dept_id;";
		String[] keys = {"empl_id"};
		
		try(Connection con = ConnectionUtil.getConnectionFromEnv()){

			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,e.getName());
			ps.setDouble(2, e.getMonthlySalary());
			ps.setString(3, e.getPosition());
			ps.setInt(4, e.getManager().getId());
			ps.setInt(5, e.getDepartment().getId());
			
			ResultSet rs = ps.executeQuery();

			
			if(rs.next()) {
				employee = e;
				employee.setId(rs.getInt(1));
			}
			
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return employee;
	}

	@Override
	public Employee getById(Integer id) {
		// TODO Auto-generated method stub
		Employee e = new Employee();
		
		String sql = "SELECT * FROM employees WHERE empl_id = ?;";
		try (Connection c = ConnectionUtil.getConnectionFromEnv()){
			
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				e.setId(id);
				e.setName(rs.getString("dept_name"));
				e.setMonthlySalary(rs.getFloat("monthly_salary"));
				e.setPosition(rs.getString("empl_position"));
				e.setManager(e);
				e.setDepartment(dp.getById(rs.getInt("dept_id")));
			}
		}
			catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		
		return e;
	}

	@Override
	public Integer update(Employee e) {
		String sql = "UPDATE departments SET dept_name = ?, monthly_budget = ? WHERE dept_id = ?";
		int results = 0;
		try (Connection c = ConnectionUtil.getConnectionFromEnv()){
			
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, e.getId());
			ps.setString(2, e.getName());
	
			
			results = ps.executeUpdate();
			
		}
			catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		return results;
	}

	@Override
	public Integer delete(Employee e) {
		String sql = "DELETE FROM employees WHERE empl_name = ?;";
		int result = 0;
		try (Connection c = ConnectionUtil.getConnectionFromEnv()){
			
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, e.getId());
			
			result = ps.executeUpdate();
		}
			catch (SQLException ex) {
			
				ex.printStackTrace();
			}
		return result;
	}


	@Override
	public Employee getByName(String name) {
		String sql = "SELECT * FROM employees WHERE empl_name = ?";
		List<Employee> employees = new ArrayList<>();
		
		try (Connection c = ConnectionUtil.getConnectionFromEnv()){
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, name);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Employee e = new Employee();
				e.setId(rs.getInt("manager_id"));
				employees.add(new Employee(rs.getInt("empl_id"), 	rs.getString("empl_name"), rs.getString("empl_position"),
						rs.getFloat("monthly_salary"), dp.getById(rs.getInt("dept_id")), e));
		
			}
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
		return e;
	}

	@Override
	public List<Employee> getAll() {
		List<Employee> employees = new ArrayList<>();
		String sql = "select * from employees;";

		try (Connection c = ConnectionUtil.getConnectionFromEnv()){
			
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);
			
			while(rs.next()) {
				int empl_id = rs.getInt("empl_id");
				String empl_name = rs.getString("empl_name");
				String empl_position = rs.getString("empl_position");
				float monthly_salary = rs.getFloat("monthly_salary");
				Employee manager = new Employee();
				manager.setId(rs.getInt("manager_id"));
				Department department = dp.getById(rs.getInt("dept_id"));
				employees.add(new Employee(empl_id, empl_name, empl_position, monthly_salary, department, manager));
			}
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return employees;
	}

	
}
